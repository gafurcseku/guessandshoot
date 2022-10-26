package com.hexa.guessandshoot.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.hexa.guessandshoot.Adapter.AdabteNews2;
import com.hexa.guessandshoot.Adapter.FAQRvAdabter;
import com.hexa.guessandshoot.Modules.Db_News;
import com.hexa.guessandshoot.Modules.Faq;
import com.hexa.guessandshoot.R;
import com.hexa.guessandshoot.Settings.ApiService;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class FAQActivity extends AppCompatActivity {

    RecyclerView rv;
    FAQRvAdabter rvAdabter;
    ArrayList<Integer> integers = new ArrayList<>();
    ImageView arrow_back ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq);





        arrow_back = findViewById(R.id.arrow_back);
        rv = findViewById(R.id.rv);


        arrow_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        LinearLayoutManager llm = new LinearLayoutManager(this);
        ((SimpleItemAnimator) rv.getItemAnimator()).setSupportsChangeAnimations(false);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(llm);
        rv.setHasFixedSize(true);

        getFAQ();


    }

    public void getFAQ() {

        ApiService.loading(this, true);

        final RequestQueue queue = Volley.newRequestQueue(this);
        // Tag used to cancel the request
        String url = ApiService.allQuestions;


        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject responseBody) {
                        // pDialog.hide();

                        try {
                            ApiService.loading(FAQActivity.this, false);

                            String status = responseBody.getString("status");

                            String code = responseBody.getString("code");


                            List<Faq> list = new ArrayList<>() ;
                            if (status.equals("true")) {
                                String news = responseBody.getString("questions");
                                JSONArray jsonArray = new JSONArray(news);

                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JsonParser parser = new JsonParser();
                                    JsonElement mJson = parser.parse(jsonArray.getString(i));
                                    Gson gson = new Gson();
                                    Faq storeModules = gson.fromJson(mJson, Faq.class);
                                    list.add(storeModules);
                                    integers.add(0) ;
                                }

                                rvAdabter = new FAQRvAdabter(getActivity(), integers, list);
                                rv.setAdapter(rvAdabter);
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
                ApiService.loading(getActivity(), false);

//                webServise.loading(SplashScreenActivity.this, false);

                ApiService.ErrorResponse(getActivity(), volleyError);
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


    public Activity getActivity(){
        return this ;
    }



}
