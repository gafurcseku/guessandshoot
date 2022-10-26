package com.hexa.guessandshoot.Activity.StaticePage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.TextView;


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
import com.hexa.guessandshoot.Activity.MainActivity;
import com.hexa.guessandshoot.Modules.DbPrivacyPolicy;
import com.hexa.guessandshoot.R;
import com.hexa.guessandshoot.Settings.ApiService;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

public class PrivacyPolicy extends AppCompatActivity {


    ImageView arrow_back, image_Privacy_Policy;
    TextView text_Invite_Friends;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy_policy);
//        recyclerView = findViewById(R.id.recyclerPrivacyPolicy);
        arrow_back = findViewById(R.id.arrow_back);
        image_Privacy_Policy = findViewById(R.id.image_Privacy_Policy);

        text_Invite_Friends = findViewById(R.id.text_Invite_Friends);
//        ArrayList<DbPrivacyPolicy> list =new ArrayList<>();
//
//        list.add(new DbPrivacyPolicy());
//        list.add(new DbPrivacyPolicy());
//        list.add(new DbPrivacyPolicy());
//        list.add(new DbPrivacyPolicy());

        pages2();


//        AdapterPrivacyPolicy adapter = new AdapterPrivacyPolicy(list,PrivacyPolicy.this);
//
//
//        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
//        recyclerView.setAdapter(adapter);

        arrow_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

    public void pages2() {

        ApiService.loading(PrivacyPolicy.this, true);

        final RequestQueue queue = Volley.newRequestQueue(PrivacyPolicy.this);
        // Tag used to cancel the request
        String url = ApiService.pages2;

        JSONObject object = new JSONObject();


        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET, url, object,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject responseBody) {
                        // pDialog.hide();
                        Log.e("responseBody", responseBody.toString());
                        try {
                            ApiService.loading(PrivacyPolicy.this, false);

                            String status = responseBody.getString("status");

                            String code = responseBody.getString("code");


                            if (status.equals("true")) {
                                String pages1 = responseBody.getString("page");
                                // JSONArray jsonArray = new JSONArray(pages1);

                                JsonParser page = new JsonParser();
                                JsonElement mJson = page.parse(pages1);
                                //   Log.e("fnnfnfnf",page+"rgrgrg");
                                System.out.println(page);

                                Gson gson = new Gson();


                                DbPrivacyPolicy storeModules = gson.fromJson(mJson, DbPrivacyPolicy.class);
//                                Picasso.get().load(storeModules.getImage()).into(image_Privacy_Policy);

                                text_Invite_Friends.setText(Html.fromHtml(storeModules.getDescription()));


                            } else {

                            }

                        } catch (JSONException e) {
                            Log.e("JSONException", e.getMessage());
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError volleyError) {
/////
                ApiService.loading(PrivacyPolicy.this, false);

//                webServise.loading(SplashScreenActivity.this, false);

                ApiService.ErrorResponse(PrivacyPolicy.this, volleyError);
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
