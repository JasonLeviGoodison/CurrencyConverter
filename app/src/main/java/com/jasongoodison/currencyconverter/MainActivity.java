package com.jasongoodison.currencyconverter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    String ConvertTo = "";
    String ConvertFrom = "";
    int posOfLastselect1 = 0;
    int posOfLastselect2 = 0;
    private ListView listView;
    private CustomListViewAdapter customListViewAdapter;
    private CustomListViewAdapter customListViewAdapter2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //String convertTo = "";
        final String[] FlagName = {"CAD","GBP","EUR","USD","JPY","AUD","CHF","MXN","CNY","NZD","SEK","RUB","HKD","BRL"};
        final String[] currencyArray = {"CAD","GBP","EUR","USD","JPY","AUD","CHF","MXN","CNY","NZD","SEK","RUB","HKD","BRL"};
        final String[] currencyArrayLong = {"Canadian Dollars", "Sterling", "Euros", "American Dollars", "Japanese Yen", "Australian Dollars",
                                            "Swiss Francs", "Mexican Pesos", "Chinese Yuan", "New Zealand Dollars", "Swedish Kronas", "Russian Rubles", "Hong Kong Dollars","Brazilian Reals"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, currencyArrayLong);
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < currencyArray.length; i++) {
            HashMap<String,String> data = new HashMap<>();
            list.add(currencyArrayLong[i]);
        }
        ListView listView = (ListView) findViewById(R.id.listView);
        customListViewAdapter = new CustomListViewAdapter(getApplicationContext(),list);
        customListViewAdapter2 = new CustomListViewAdapter(getApplicationContext(), list);
        listView.setAdapter(customListViewAdapter);
        ListView listViewFrom = (ListView) findViewById(R.id.listView2);
        listViewFrom.setAdapter(customListViewAdapter2);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                String convertTo = currencyArray[position];
                Log.i("Main", "Hey, you clicked " + convertTo);
                ConvertTo = convertTo;
                if (!ConvertFrom.equals("")) {
                    Intent i = new Intent(getApplicationContext(),Calculation.class);

                    i.putExtra("convertTo", ConvertTo);
                    i.putExtra("convertFrom", ConvertFrom);

                    startActivity(i);
                }

            }

        });
        listViewFrom.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                String convertFrom = currencyArray[position];
                Log.i("Main", "Hey, you clicked " + convertFrom);
                ConvertFrom = convertFrom;
               // ConvertFrom = countryMap.get(convertFrom);
                if (!ConvertTo.equals("")) {
                    Intent i = new Intent(getApplicationContext(),Calculation.class);
                    i.putExtra("convertTo", ConvertTo);
                    i.putExtra("convertFrom", ConvertFrom);
                    startActivity(i);
                }
            }

        });


    }




}
