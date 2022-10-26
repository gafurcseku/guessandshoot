package com.hexa.guessandshoot.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.ImageView;


import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.hexa.guessandshoot.Adapter.AdabteNews2;
import com.hexa.guessandshoot.R;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.hexa.guessandshoot.Adapter.AdapterNews;
import com.hexa.guessandshoot.Modules.Db_News;
import com.hexa.guessandshoot.Settings.ApiService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;


public class NewsActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    Adapter adapter;
    ImageView arrow_back;
    RequestQueue queue;
    ArrayList<Db_News> list = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_news);


        recyclerView = findViewById(R.id.recyclerNews);
        arrow_back = findViewById(R.id.arrow_back);

        arrow_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



       getNews();
    }

    public void getNews() {

        ApiService.loading(NewsActivity.this, true);

        final RequestQueue queue = Volley.newRequestQueue(NewsActivity.this);
        // Tag used to cancel the request
        String url = ApiService.getNews;


        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject responseBody) {
                        // pDialog.hide();

                        try {
                            ApiService.loading(NewsActivity.this, false);

                            String status = responseBody.getString("status");

                            String code = responseBody.getString("code");


                            if (status.equals("true")) {
                                String news = responseBody.getString("news");
                                JSONArray jsonArray = new JSONArray(news);

                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JsonParser parser = new JsonParser();
                                    JsonElement mJson = parser.parse(jsonArray.getString(i));
                                    Gson gson = new Gson();
                                    Db_News storeModules = gson.fromJson(mJson, Db_News.class);
                                    list.add(storeModules);

                                }
                                recyclerView.setLayoutManager(new LinearLayoutManager(NewsActivity.this));
                                AdabteNews2 adapter = new AdabteNews2(list, NewsActivity.this);
                                recyclerView.setAdapter(adapter);

                                System.out.println(news);


                            } else {

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError volleyError) {
/////
                ApiService.loading(NewsActivity.this, false);

//                webServise.loading(SplashScreenActivity.this, false);

                ApiService.ErrorResponse(NewsActivity.this, volleyError);
            }
        }) {

            /**
             * Passing some request headers
             * */

//            @Override
//            protected Map<String,String> getParams(){
//                Map<String,String> params = new HashMap<String, String>();
//                params.put("email",email);
//                params.put("name",name);
//                params.put("mobile",mobile);
//                params.put("message",message);
//
//
//                return params;
//            }
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {

                return ApiService.getHeader(false);
            }

        };
        jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(
                9000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        queue.add(jsonObjReq);


    }

}


