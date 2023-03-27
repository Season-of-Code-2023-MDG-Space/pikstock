package com.example.pikstock;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import android.view.inputmethod.EditorInfo;
import android.view.KeyEvent;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

//import com.android.volley.Request;
//import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.android.volley.RequestQueue;

//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import java.util.concurrent.TimeUnit;
import java.io.IOException;








public class Searchtab extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    private EditText searchbar;
    private String POST="POST";
    private String GET="GET";
    TextView textView_search;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchtab);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setTitle("PikStock");
        getSupportActionBar().setHomeAsUpIndicator(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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









    }

    void sendRequest(String param){

        /* if url is of our get request, it should not have parameters according to our implementation.
         * But our post request should have 'name' parameter. */
//        TODO: replace url with proper uri
        String url="";
        String fullURL=url+"/"+GET+(param==null?"":"/"+param);
        Request request;

        OkHttpClient client = new OkHttpClient().newBuilder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS).build();


            /*If it's our get request, it doesn't require parameters, hence just sending with the url*/
        request = new Request.Builder()
                .url(fullURL)
                .build();

        /* this is how the callback get handled */
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {

                // Read data on the worker thread
                final String responseData = response.body().string();

                // Run view-related code back on the main thread.
                // Here we display the response message in our text view
                Searchtab.this.runOnUiThread(() -> textView_search.setText(responseData));
            }
        });
    }


    private void performSearch() {
        // This is where you would perform the search function
        // For example, you could get the search text from the EditText and search for it
        String searchText = searchbar.getText().toString();
        Toast.makeText(this, "Searching for " + searchText, Toast.LENGTH_SHORT).show();
        sendRequest(searchText);








//        RequestQueue requestQueue;
//        requestQueue= Volley.newRequestQueue(this);
//        JsonObjectRequest jsonObjectRequest= new JsonObjectRequest(Request.Method.GET, "https://192.168.121.119:8000/stockname/2", null, new Response.Listener<JSONObject>() {
//            @Override
//            public void onResponse(JSONObject response) {
//                try {
//                    Log.d("my app","The response is:" + response.getString("id"));
//                } catch (JSONException e) {
//                    throw new RuntimeException(e);
//                }
//
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//                Log.d("my app", String.valueOf(error));
//
//            }
//        });
//        requestQueue.add(jsonObjectRequest);
    }



}