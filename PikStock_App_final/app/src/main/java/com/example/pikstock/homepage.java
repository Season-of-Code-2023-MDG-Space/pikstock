package com.example.pikstock;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;


import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;


import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;


import java.util.ArrayList;

import android.util.Log;

import android.content.Intent;
import android.os.Bundle;

import android.view.MenuItem;


//import com.example.pikstock.databinding.ActivityMainBinding;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.google.android.material.bottomnavigation.BottomNavigationView;












public class homepage extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    ArrayList<Integer> closePrices = new ArrayList<>();
    ArrayList<Integer> timestamp = new ArrayList<>();





    LineChart mpLineChart;







    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setTitle("PikStock");
        getSupportActionBar().setHomeAsUpIndicator(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);






        mpLineChart=(LineChart) findViewById(R.id.line_chart);


        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        bottomNavigationView.setSelectedItemId(R.id.home_final);

        bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.search_final:
                        startActivity(new Intent(getApplicationContext(), Searchtab.class));
                        overridePendingTransition(0, 0);
                        return true;
                }

                return false;
            }
        });

        String url = "https://query1.finance.yahoo.com/v8/finance/chart/orcl?range=1y&interval=1d";

//        ListView listView = findViewById(R.id.listView);


        AndroidNetworking.initialize(this);
        AndroidNetworking.get(url)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("RES", response.toString());


                        //parse
                        try {
                            JSONObject chart = response.getJSONObject("chart");
                            JSONArray result = chart.getJSONArray("result");
                            JSONObject zero = result.getJSONObject(0);
                            JSONArray timestamps = zero.getJSONArray("timestamp");
                            JSONObject indicators = zero.getJSONObject("indicators");
                            JSONArray quote = indicators.getJSONArray("quote");
                            JSONObject zero1 = quote.getJSONObject(0);
                            JSONArray close = zero1.getJSONArray("close");



                            for(int i=0; i<close.length(); i++)
                            {

                                Integer price = close.getInt(i);

                                closePrices.add(price);
//                                ArrayAdapter<Integer> arrAdapter = new ArrayAdapter<Integer>(homepage.this,android.R.layout.simple_list_item_1, closePrices);
//                                listView.setAdapter(arrAdapter);
                            }


                            for(int i=0; i<timestamps.length(); i++)
                            {

                                Integer time = timestamps.getInt(i);

                                timestamp.add(time);

//                                ArrayAdapter<Integer> arrAdapter = new ArrayAdapter<Integer>(homepage.this,android.R.layout.simple_list_item_1, timestamp);
//                                listView.setAdapter(arrAdapter);
                            }

                            LineDataSet lineDataSet1 = new LineDataSet(dataValues1(),"Data Set 1");
                            ArrayList<ILineDataSet> dataSets = new ArrayList<>();
                            dataSets.add(lineDataSet1);
                            LineData data = new LineData(dataSets);
                            mpLineChart.setData(data);
                            mpLineChart.invalidate();


                            lineDataSet1.setCircleColor(Color.WHITE);
                            lineDataSet1.setCircleRadius(1);






                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }

                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e("ERROR", anError.toString());

                    }
                });












        mpLineChart.setNoDataText("No Data");
        mpLineChart.setDrawGridBackground(false);
        mpLineChart.setDrawBorders(true);
        mpLineChart.setBorderColor(Color.BLACK);
        mpLineChart.setBorderWidth(2);
        mpLineChart.setTouchEnabled(true);
        mpLineChart.setPinchZoom(true);
        mpLineChart.getAxisLeft().setTextColor(Color.WHITE);
        mpLineChart.getXAxis().setTextColor(Color.WHITE);
        mpLineChart.getAxisRight().setEnabled(false);


        Legend legend = mpLineChart.getLegend();
        legend.setEnabled(false);

        Description description = new Description();
        description.setText("ORCL Price Data");
        description.setTextColor(Color.WHITE);
        mpLineChart.setDescription(description);











//        webView = findViewById(R.id.webView);
//
//        WebSettings webSettings = webView.getSettings();
//        webSettings.setJavaScriptEnabled(true);
//
//        String symbol = "AAPL"; // replace with the stock symbol you want to display
//
//        webView.loadUrl("https://finance.yahoo.com/chart/" + symbol);


    }








    private ArrayList<Entry> dataValues1()
    {
        ArrayList<Entry> dataVals = new ArrayList<Entry>();
//        dataVals.add(new Entry(1,2));
//        dataVals.add(new Entry(2,2));
//        dataVals.add(new Entry(3,2));
//        dataVals.add(new Entry(4,2));
//        dataVals.add(new Entry(5,2));
        for(int i=0; i<closePrices.size(); i++)
        {
            dataVals.add(new Entry((float)timestamp.get(i), (float)closePrices.get(i)));

        }
        return dataVals;
    }










}