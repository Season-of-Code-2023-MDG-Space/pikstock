package com.example.pikstock;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import android.view.inputmethod.EditorInfo;
import android.view.KeyEvent;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

//import com.android.volley.Request;
//import com.android.volley.Response;
//import com.android.volley.VolleyError;
//import com.android.volley.toolbox.JsonArrayRequest;
//import com.android.volley.toolbox.JsonObjectRequest;
//import com.android.volley.toolbox.Volley;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.google.android.material.bottomnavigation.BottomNavigationView;
//import com.android.volley.RequestQueue;

//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.util.ArrayList;
import java.io.IOException;








public class Searchtab extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    private EditText searchbar;
    private String POST="POST";
    private String GET="GET";
    private TextView result;


    ArrayList<Integer> y_predicted = new ArrayList<>();
    ArrayAdapter<Integer> arrAdapter_search;


    LineChart mpLineChart_search;
    ListView listView_search;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchtab);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setTitle("PikStock");
        getSupportActionBar().setHomeAsUpIndicator(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


//        listView_search = findViewById(R.id.listView_search);


        mpLineChart_search=(LineChart) findViewById(R.id.linechart_search);






        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        bottomNavigationView.setSelectedItemId(R.id.search_final);

        bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId())
                {
                    case R.id.home_final:
                        startActivity(new Intent(getApplicationContext(),homepage.class));
                        overridePendingTransition(0,0);
                        return true;
                }

                return false;
            }
        });






        searchbar=findViewById(R.id.searchbar);
        searchbar.setOnEditorActionListener(new TextView.OnEditorActionListener(){
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                // Check if the Enter key was pressed
                if (actionId == EditorInfo.IME_ACTION_SEARCH ||
                        actionId == EditorInfo.IME_ACTION_DONE ||
                        event.getAction() == KeyEvent.ACTION_DOWN &&
                                event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {

                    // Perform your function here
                    performSearch();

                    // Return true to indicate that the event was handled
                    return true;
                }

                // Return false to indicate that the event was not handled
                return false;
            }
        });





//        result = findViewById(R.id.result);






    }





//    void sendRequest(String param){
//
////        TODO: replace url with proper uri
//        String url="https://sidkhanna08.pythonanywhere.com/";
//        String fullURL=url+"/"+GET+(param==null?"":"/"+param);
//        Request request;
//
//        OkHttpClient client = new OkHttpClient().newBuilder()
//                .connectTimeout(10, TimeUnit.SECONDS)
//                .readTimeout(10, TimeUnit.SECONDS)
//                .writeTimeout(10, TimeUnit.SECONDS).build();
//
//
//            /*If it's our get request, it doesn't require parameters, hence just sending with the url*/
//        request = new Request.Builder()
//                .url(fullURL)
//                .build();
//
//        /* this is how the callback get handled */
//        client.newCall(request).enqueue(new Callback() {
//            @Override
//            public void onFailure(@NonNull Call call, @NonNull IOException e) {
//                e.printStackTrace();
//            }
//
//            @Override
//            public void onResponse(Call call, final Response response) throws IOException {
//
//                // Read data on the worker thread
//                final String responseData = response.body().string();
//
//                // Run view-related code back on the main thread.
//                // Here we display the response message in our text view
//                Searchtab.this.runOnUiThread(() -> result.setText(responseData));
//            }
//        });
//    }






    private void performSearch() {

//        String searchText = searchbar.getText().toString();
//        Toast.makeText(this, "Searching for " + searchText, Toast.LENGTH_SHORT).show();
//        sendRequest(searchText);
//        Toast.makeText(this, "getting " + searchText, Toast.LENGTH_SHORT).show();




        String url = "https://sidkhanna08.pythonanywhere.com/prediction";


        AndroidNetworking.initialize(this);
        AndroidNetworking.get(url)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("RES", response.toString());

                        try {

                            for(int i=0; i<response.length(); i++)
                            {
                                JSONArray arrResult = response.getJSONArray(i);

                                Integer y_predicted_values = arrResult.getInt(0);

                                y_predicted.add(y_predicted_values);

                            }
//                            arrAdapter_search = new ArrayAdapter<String>(Searchtab.this,android.R.layout.simple_list_item_1, y_predicted);
//                            listView_search.setAdapter(arrAdapter_search);
//                            arrAdapter_search.notifyDataSetChanged();





                            LineDataSet lineDataSet_search = new LineDataSet(dataValues_search(),"Data Set Search");
                            ArrayList<ILineDataSet> dataSets_search = new ArrayList<>();
                            dataSets_search.add(lineDataSet_search);
                            LineData data_search = new LineData(dataSets_search);
                            mpLineChart_search.setData(data_search);
                            mpLineChart_search.invalidate();


                            lineDataSet_search.setCircleColor(Color.WHITE);
                            lineDataSet_search.setCircleRadius(1);






                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(ANError anError) {
                        anError.printStackTrace();
                        Log.e("ERROR", anError.toString());

                    }
                });

        mpLineChart_search.setNoDataText("No Data Available");
        mpLineChart_search.setDrawGridBackground(false);
        mpLineChart_search.setDrawBorders(true);
        mpLineChart_search.setBorderColor(Color.BLACK);
        mpLineChart_search.setBorderWidth(2);
        mpLineChart_search.setTouchEnabled(true);
        mpLineChart_search.setPinchZoom(true);
        mpLineChart_search.getAxisLeft().setTextColor(Color.WHITE);
        mpLineChart_search.getXAxis().setTextColor(Color.WHITE);
        mpLineChart_search.getAxisRight().setEnabled(false);


        Legend legend_search = mpLineChart_search.getLegend();
        legend_search.setEnabled(false);

        Description description_search = new Description();
        description_search.setText("GOOG Price Data");
        description_search.setTextColor(Color.WHITE);
        mpLineChart_search.setDescription(description_search);






//        OkHttpClient okHttpClient= new OkHttpClient();
//        String url = "https://sidkhanna08.pythonanywhere.com/";
//        Request request = new Request.Builder()
//                .url(url)
//                .build();

//        okHttpClient.newCall(request).enqueue(new Callback() {
//            @Override
//            public void onFailure(@NonNull Call call, @NonNull IOException e) {
//                e.printStackTrace();
//            }
//
//            @Override
//            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
//                if(response.isSuccessful()){
//                    final String myResponse= response.body().string();
//                    Searchtab.this.runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            result.setText(myResponse);
//                        }
//                    });
//                }
//            }
//        });


    }









    private ArrayList<Entry> dataValues_search()
    {
        ArrayList<Entry> dataVals_search = new ArrayList<Entry>();
//        dataVals.add(new Entry(1,2));
//        dataVals.add(new Entry(2,2));
//        dataVals.add(new Entry(3,2));
//        dataVals.add(new Entry(4,2));
//        dataVals.add(new Entry(5,2));
        for(int i=0; i<y_predicted.size(); i++)
        {
            dataVals_search.add(new Entry((float)i, (float)y_predicted.get(i)));

        }
        return dataVals_search;
    }





}