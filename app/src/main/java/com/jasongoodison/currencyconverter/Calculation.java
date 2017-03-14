package com.jasongoodison.currencyconverter;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.InputFilter;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import Data.HttpClient;
import Data.JSONParser;
import Util.DecimalDigitsInputFilter;

/**
 * Created by jason on 7/17/16.
 */
public class Calculation  extends AppCompatActivity {

    String convertedVal;
    String multiplier;
    boolean CouldNotConnect = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //////
        final String[] currencyArray = {"CAD","GBP","EUR","USD","JPY","AUD","CHF","MXN","CNY","NZD","SEK","RUB","HKD","BRL"};
        final String[] currencyArrayLong = {"Canadian Dollars", "Sterling", "Euros", "American Dollars", "Japanese Yen", "Australian Dollars",
                "Swiss Francs", "Mexican Pesos", "Chinese Yuan", "New Zealand Dollars", "Swedish Kronas", "Russian Rubles", "Hong Kong Dollars","Brazilian Reals"};
        final Map<String, String> countryMap = new HashMap<String, String>();
        fillMap(countryMap, currencyArray, currencyArrayLong);
        ///////
        setContentView(R.layout.activity_calculation);
        Bundle extras = getIntent().getExtras();
        String convertTo = "";
        String convertFrom = "";
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if(extras != null){
            convertTo = extras.getString("convertTo");
            convertFrom = extras.getString("convertFrom");
        }
        Log.v("Calculation", "convertTo: " + convertTo);
        Log.v("Calculation", "convertFrom: " + convertFrom);
        TextView textview = (TextView) findViewById(R.id.textView4);
        TextView textViewFrom = (TextView) findViewById(R.id.textView3);
        String covertFromText = countryMap.get(convertFrom);
        textViewFrom.setText("Please enter a value in " + covertFromText + " ");
        double usd;
        // Look into setting text with another resource
        String convertToText = countryMap.get(convertTo);
        textview.setText("The amount in " + convertToText + " is: ");
        final EditText editText = (EditText) findViewById(R.id.editText);

        editText.setFilters(new InputFilter[] {new DecimalDigitsInputFilter(2),
                new InputFilter.LengthFilter(18)});
        if (editText != null) {
            final String finalConvertTo = convertTo;
            final String finalConvertFrom = convertFrom;
            editText.setOnKeyListener(new View.OnKeyListener() {

                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event)
                {
                    if (event.getAction() == KeyEvent.ACTION_DOWN
                            && event.getKeyCode() ==       KeyEvent.KEYCODE_ENTER)
                    {
                        String usDollars = editText.getText().toString() ;
                        double val;
                        usDollars = editText.getText().toString();
                        if (usDollars.equals("")) {
                            val = 1;
                        } else {
                            val = Double.parseDouble(usDollars);
                        }
                        Log.i("Calculation", "You entered " + usDollars);
                        val = Convert(val, finalConvertTo, finalConvertFrom);
                        TextView printAnswer = (TextView) findViewById(R.id.textView5);
                        String result = String.format("%.2f",val);
                        printAnswer.setText(result);
                        return false;
                    }
                    return false;
                }
            });
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent =  new Intent(this,MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }

    }


    public DataTask DataTask (String convertTo) {

        DataTask dataTask = new DataTask();
        Log.v("dataTask", "about to execute with " + convertTo);
        dataTask.execute(new String[]{convertTo});
        return dataTask;

    }
    private class DataTask extends AsyncTask<String,Void,String> {
        @Override
        protected String doInBackground(String... params) {
            try {
                Log.v("doInBackground", "Params at 0 is " + params[0]);
                String data = (new HttpClient().getData(params[0]));
                convertedVal = (new JSONParser().getRate(data, params[0]));
                Log.v("GetData", "Converted val:" + convertedVal);
                multiplier = convertedVal;
                return convertedVal;
            } catch (IOException e) {
                CouldNotConnect = true;
                return null;
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.v("POSTEXECUTE", "just finished " + s);
            multiplier = s;

        }
    }
    protected double Convert(double us, String convertTo, String convertFrom) {
    	//find findConversionRaye
        String multiplierB = "1.0";
        String multiplierA = "1.0";

        double multiplierDoubleB = 1.0;
        double multiplierDoubleA = 1.0;

        try {
            DataTask dataTask = DataTask(convertTo);
            Log.v("WAITING", "about to wait");
            dataTask.get(10000, TimeUnit.MILLISECONDS);
            multiplierB = multiplier;
            DataTask dataTask2 = DataTask(convertFrom);
            dataTask2.get(10000, TimeUnit.MILLISECONDS);
            multiplierA = multiplier;
            Log.v("Convert", "MultiplierB is " + multiplierB + " MultiplierA is " + multiplierA);

        }
        catch (InterruptedException e) {
            Toast.makeText(this,"Communication with server was interrupted",Toast.LENGTH_LONG).show();
        }
        catch (TimeoutException e) {
            Toast.makeText(this,"Communication Timed Out. Please Check Connection",Toast.LENGTH_LONG).show();
        } catch (ExecutionException e) {
            Toast.makeText(this,"Could not execute communication",Toast.LENGTH_LONG).show();
        }
        if (CouldNotConnect) {
            Toast.makeText(this,"Could Not Connect. Please Check Connection",Toast.LENGTH_LONG).show();
            multiplierA = "1.0";
            multiplierB = "1.0";
        }
        Log.v("CONVERT", "executing the thing with " + multiplierB);
        multiplierDoubleB = Double.parseDouble(multiplierB);
        multiplierDoubleA = Double.parseDouble(multiplierA);
    	return us*(1/multiplierDoubleA) * multiplierDoubleB;
        //return 0;
    }

    void fillMap(Map<String, String> map, String[] keys, String[] vals) {
        int keyLength = keys.length;
        for(int i = 0; i < keyLength; i++) {
            map.put(keys[i],vals[i]);
        }
    }
}
