package com.example.amarlubovac.cryptocurrency;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class CurrencyDetails extends AppCompatActivity {

    TextView name;
    TextView rank;
    TextView symbol;
    TextView price;
    TextView price_btc;
    TextView available_supply;
    TextView total_supply;
    TextView volume;
    TextView market;
    TextView change1h;
    TextView change24h;
    TextView change7d;
    String fiatCurrency;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currency_details);
        name = (TextView) findViewById(R.id.textView1);
        rank = (TextView) findViewById(R.id.textView2);
        symbol = (TextView) findViewById(R.id.textView3);
        price = (TextView) findViewById(R.id.textView4);
        price_btc = (TextView) findViewById(R.id.textView5);
        available_supply = (TextView) findViewById(R.id.textView6);
        total_supply = (TextView) findViewById(R.id.textView7);
        volume = (TextView) findViewById(R.id.textView8);
        market = (TextView) findViewById(R.id.textView9);
        change1h = (TextView) findViewById(R.id.textView10);
        change24h = (TextView) findViewById(R.id.textView11);
        change7d = (TextView) findViewById(R.id.textView12);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        Intent intent = getIntent();
        final Bundle b = intent.getExtras();

        fiatCurrency = b.getString("fiat");

        callWebService(b.getString("id"));

        final ImageButton imageButton = (ImageButton) findViewById(R.id.imageButton2);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callWebService(b.getString("id"));
            }
        });
    }

    @Override
    protected void onStart() {
        Intent intent = getIntent();
        Bundle b = intent.getExtras();
        super.onStart();
        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        fiatCurrency = prefs.getString("fiatCurrency", null);
        if (fiatCurrency == null) {
            SharedPreferences.Editor editor = getSharedPreferences("prefs", MODE_PRIVATE).edit();
            editor.putString("fiatCurrency", "USD");
            fiatCurrency="USD";
            editor.apply();
        }
        callWebService(b.getString("id"));
    }

    private void callWebService(String id) {
        progressBar.setVisibility(View.VISIBLE);
        progressBar.setProgress(1);
        String url = "https://api.coinmarketcap.com/v1/ticker/"+id+"/";

        final RequestQueue requestQueue = Volley.newRequestQueue(CurrencyDetails.this);

        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i< jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);
                        rank.setText("Rank: "+ Integer.toString(object.getInt("rank")));
                        name.setText("Name: " + object.getString("name"));
                        symbol.setText("Symbol: " + object.getString("symbol"));
                        price.setText("Price: " + object.getString("price_usd") + " " + fiatCurrency);
                        price_btc.setText("Price in bitcoins: " + object.getString("price_btc"));
                        available_supply.setText("Available supply: " + object.getString("available_supply"));
                        total_supply.setText("Total supply: " + object.getString("total_supply"));
                        volume.setText("24h volume: " + object.getString("24h_volume_usd") + " " + fiatCurrency);
                        change1h.setText("1h change: " + object.getString("percent_change_1h") + " %");
                        change24h.setText("24h change: " + object.getString("percent_change_24h") + " %");
                        change7d.setText("7d change: " + object.getString("percent_change_7d") + " %");
                        market.setText("Market cap: " + object.getString("market_cap_usd") + " " +fiatCurrency);
                    }
                    progressBar.setVisibility(View.GONE);
                    progressBar.setProgress(0);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                requestQueue.stop();
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
