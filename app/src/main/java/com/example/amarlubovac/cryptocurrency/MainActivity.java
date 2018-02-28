package com.example.amarlubovac.cryptocurrency;

import android.app.DownloadManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    public static String data;
    private ListView listView;
    private TextView tv;
    private Spinner spinner;
    private List<Currency> currencyList;
    private List <String> currency_names;
    ArrayAdapter<String> arrayAdapter;
    String fiatCurrency;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.lista);
        spinner = (Spinner) findViewById(R.id.spinner1);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        currencyList = new ArrayList<>();
        currency_names = new ArrayList<>();

        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        fiatCurrency = prefs.getString("fiatCurrency", null);
        if (fiatCurrency == null) {
            SharedPreferences.Editor editor = getSharedPreferences("prefs", MODE_PRIVATE).edit();
            editor.putString("fiatCurrency", "USD");
            editor.commit();
        }

        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, currency_names );
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this, CurrencyDetails.class);
                intent.putExtra("id", currencyList.get(i).getId());
                intent.putExtra("name", currencyList.get(i).getName());
                intent.putExtra("rank", currencyList.get(i).getRank());
                intent.putExtra("symbol", currencyList.get(i).getSymbol());
                intent.putExtra("price", currencyList.get(i).getPrice());
                intent.putExtra("price_btc", currencyList.get(i).getPrice_bitcoin());
                intent.putExtra("volume", currencyList.get(i).getVolume_24());
                intent.putExtra("market", currencyList.get(i).getMarket_cap());
                intent.putExtra("available_supply", currencyList.get(i).getAvailable_supply());
                intent.putExtra("total_supply", currencyList.get(i).getTotal_supply());
                intent.putExtra("change1h", currencyList.get(i).getPercent_change_1h());
                intent.putExtra("change24h", currencyList.get(i).getPercent_change_24h());
                intent.putExtra("change7d", currencyList.get(i).getPercent_change_7d());
                intent.putExtra("fiat", fiatCurrency);
                startActivity(intent);
            }
        });

        final ImageButton imageButton = (ImageButton) findViewById(R.id.imageButton2);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callWebService();
            }
        });

    }


    @Override
    protected void onStart() {
        super.onStart();
        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        fiatCurrency = prefs.getString("fiatCurrency", null);
        if (fiatCurrency == null) {
            SharedPreferences.Editor editor = getSharedPreferences("prefs", MODE_PRIVATE).edit();
            editor.putString("fiatCurrency", "USD");
            fiatCurrency="USD";
            editor.apply();
        }
        callWebService();
    }


    private void callWebService() {
        progressBar.setVisibility(View.VISIBLE);
        progressBar.setProgress(1);

        currencyList.clear();
        currency_names.clear();
        String url = "https://api.coinmarketcap.com/v1/ticker/";

        final RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);

        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //tv.setText(response);
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i< jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);
                        Currency c = new Currency();
                        c.setRank(object.getInt("rank"));
                        c.setId(object.getString("id"));
                        c.setName(object.getString("name"));
                        c.setSymbol(object.getString("symbol"));
                        c.setPrice(object.getString("price_usd"));
                        c.setPrice_bitcoin(object.getString("price_btc"));
                        c.setAvailable_supply(object.getString("available_supply"));
                        c.setTotal_supply(object.getString("total_supply"));
                        c.setVolume_24(object.getString("24h_volume_usd"));
                        c.setPercent_change_1h(object.getString("percent_change_1h"));
                        c.setPercent_change_24h(object.getString("percent_change_24h"));
                        c.setPercent_change_7d(object.getString("percent_change_7d"));
                        c.setMarket_cap(object.getString("market_cap_usd"));
                        currencyList.add(c);
                        currency_names.add("Rank: " + Integer.toString(c.getRank()) + "\nSymbol: " + c.getSymbol() +"\nPrice: " + c.getPrice() + " " + fiatCurrency + "\n24h change: " + c.getPercent_change_24h() + "%");
                    }
                    arrayAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                requestQueue.stop();
                progressBar.setProgress(0);
                progressBar.setVisibility(View.GONE);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.print("Error!");
                error.printStackTrace();
                requestQueue.stop();
            }
        }){@Override
        public Map<String, String> getParams(){
            Map<String, String> params = new HashMap<>();
            params.put("convert", fiatCurrency);
            return params;
        }
        };

        requestQueue.add(request);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.my_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.postavke:
                Intent intent = new Intent(this, Settings.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
