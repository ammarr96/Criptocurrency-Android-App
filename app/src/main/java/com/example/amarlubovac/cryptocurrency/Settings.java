package com.example.amarlubovac.cryptocurrency;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

public class Settings extends AppCompatActivity {
    private Spinner spinner;
    private String fiatCurrency;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        spinner = (Spinner) findViewById(R.id.spinner1);
        String [] values = {"USD","EUR","CNY"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, values);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        fiatCurrency = prefs.getString("fiatCurrency", null);
        for (int i = 0; i<values.length; i++) {
            if (values[i].equals(fiatCurrency)) {
                spinner.setSelection(i);
            }
        }


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                fiatCurrency = spinner.getSelectedItem().toString();

                SharedPreferences.Editor editor = getSharedPreferences("prefs", MODE_PRIVATE).edit();
                editor.putString("fiatCurrency", fiatCurrency);
                editor.commit();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }

    /*@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.home:
               onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }*/
}
