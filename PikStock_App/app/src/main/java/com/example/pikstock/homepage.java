package com.example.pikstock;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.AsyncTask;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;



import org.json.JSONException;
import org.json.JSONObject;





public class homepage extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
//    TextView data;
//    String url;
//    private WebView webView;


    ImageView stock_graph;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setTitle("PikStock");
        getSupportActionBar().setHomeAsUpIndicator(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        bottomNavigationView.setSelectedItemId(R.id.home_final);

        bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId())
                {
                    case R.id.search_final:
                        startActivity(new Intent(getApplicationContext(),Searchtab.class));
                        overridePendingTransition(0,0);
                        return true;
                }

                return false;
            }
        });

        String symbol = "AAPL"; // replace with the stock symbol you want to display
        String url = "https://finance.yahoo.com/chart/"+symbol;
        String html = null;
        try {
            html = Jsoup.connect(url).get().html();
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        // Step 2: Extract the URL of the image containing the stock graph
        String imgUrl = null;
        Pattern pattern = Pattern.compile("(https://.*\\.png)");
        Matcher matcher = pattern.matcher(html);
        if (matcher.find()) {
            imgUrl = matcher.group(1);
        }

        // Step 3: Use Glide to download and load the image into the ImageView
        stock_graph = findViewById(R.id.stock_graph);
        Glide.with(this).load(imgUrl).into(stock_graph);
















//        webView = findViewById(R.id.webView);
//
//        WebSettings webSettings = webView.getSettings();
//        webSettings.setJavaScriptEnabled(true);
//
//        String symbol = "AAPL"; // replace with the stock symbol you want to display
//
//        webView.loadUrl("https://finance.yahoo.com/chart/" + symbol);







        // Replace "AAPL" with the stock symbol you want to fetch
//        String stockSymbol = "AAPL";
//        String url = "https://finance.yahoo.com/chart/" + stockSymbol;
//
//        new FetchGraphUrlTask().execute(url);

//        data=findViewById(R.id.data);
//        url = "https://finance.yahoo.com/quote/AAPL/chart/AAPL";
//        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
//            @Override
//            public void onResponse(JSONObject response) {
//                String datetime= null;
//                try {
//                    datetime = response.getString("datetime");
//                } catch (JSONException e) {
//                    throw new RuntimeException(e);
//                }
//                data.setText(datetime);
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//            }
//        });
//        Volley.newRequestQueue(this).add(request);
//
//
//
//
    }

//    private class FetchGraphUrlTask extends AsyncTask<String, Void, String> {
//
//        @Override
//        protected String doInBackground(String... params) {
//            String url = params[0];
//            String graphUrl = null;
//
//            try {
//                Document doc = Jsoup.connect(url).get();
//                Element graphImg = doc.selectFirst("img[class*=chartImg]");
//                graphUrl = graphImg.attr("src");
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//            return graphUrl;
//        }
//
//        @Override
//        protected void onPostExecute(String graphUrl) {
//            ImageView graphView = findViewById(R.id.graphView);
//            Glide.with(homepage.this).load(graphUrl).into(graphView);
//        }
//    }


}