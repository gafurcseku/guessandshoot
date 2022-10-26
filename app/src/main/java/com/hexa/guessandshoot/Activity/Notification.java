package com.hexa.guessandshoot.Activity;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.hexa.guessandshoot.Adapter.Adapter_MyExpections_In_MyAccont;
import com.hexa.guessandshoot.Modules.Db_My_Expections_My_Account;
import com.hexa.guessandshoot.R;
import com.hexa.guessandshoot.Adapter.Adapter_notifications;
import com.hexa.guessandshoot.Fragment.FragmentExpectations;
import com.hexa.guessandshoot.Modules.Db_notification;
import com.hexa.guessandshoot.Settings.ApiService;
import com.orhanobut.hawk.Hawk;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;

public class Notification extends AppCompatActivity {
    RecyclerView recyclerView;
    Adapter adapter;
    ArrayList<Db_notification> list = new ArrayList<>();
    ImageView arrow_back;
    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        arrow_back = findViewById(R.id.arrow_back);

        recyclerView = findViewById(R.id.recyclerNotification);


        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));

        arrow_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });

        mAdView = findViewById(R.id.adView);

        if(!((Boolean) Hawk.get("status"))){
            AdRequest adRequest = new AdRequest.Builder().build();
            mAdView.loadAd(adRequest);
        }else {
            mAdView.setVisibility(View.GONE);
        }

        notifications();
    }

    public void notifications() {

        ApiService.loading(Notification.this, true);

        final RequestQueue queue = Volley.newRequestQueue(Notification.this);
        // Tag used to cancel the request
        String url = ApiService.notifications;


        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject responseBody) {
                        // pDialog.hide();

                        try {
                            ApiService.loading(Notification.this, false);

                            System.out.println(responseBody.toString());
                            String status = responseBody.getString("status");

//                            String message = responseBody.getString("message");
                            String code = responseBody.getString("code");


                            if (status.equals("true")) {

                                String news = responseBody.getString("news");
                                JSONArray jsonArray = new JSONArray(news);

                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JsonParser parser = new JsonParser();
                                    JsonElement mJson = parser.parse(jsonArray.getString(i));
                                    Gson gson = new Gson();
                                    Db_notification db_notification = gson.fromJson(mJson, Db_notification.class);

                                    list.add(db_notification);
                                }
                                Adapter_notifications adapter = new Adapter_notifications(list, Notification.this);

                                recyclerView.setAdapter(adapter);
                                //   System.out.println(matches);
                                NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                                notificationManager.cancelAll();
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
                ApiService.loading(Notification.this, false);

//                webServise.loading(SplashScreenActivity.this, false);

                ApiService.ErrorResponse(Notification.this, volleyError);
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

                return ApiService.getHeader(true);
            }

        };
        jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(
                9000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        queue.add(jsonObjReq);


    }

}
