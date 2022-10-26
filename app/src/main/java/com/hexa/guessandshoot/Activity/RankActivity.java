package com.hexa.guessandshoot.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
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
import com.hexa.guessandshoot.Adapter.Adapter_Ranking;
import com.hexa.guessandshoot.Modules.Db_Ranking;
import com.hexa.guessandshoot.R;
import com.hexa.guessandshoot.Settings.ApiService;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class RankActivity extends AppCompatActivity {

    RecyclerView recyclerView;


    ArrayList<Db_Ranking> list = new ArrayList<>();
    ArrayList<Db_Ranking> list_first = new ArrayList<>();

    ImageView arrow_back2;
    TextView name_1, name_2, name_3;
    TextView point_1, point_2, point_3;
    CircleImageView image_1, image_2, image_3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rank);

        initViews();

        initItems();

    }

    private void initViews() {
        recyclerView = findViewById(R.id.recycler_Ranking);

        arrow_back2 = findViewById(R.id.arrow_back2);

        name_1 = findViewById(R.id.name_1);
        point_1 = findViewById(R.id.point_1);
        image_1 = findViewById(R.id.image_1);

        name_2 = findViewById(R.id.name_2);
        point_2 = findViewById(R.id.point_2);
        image_2 = findViewById(R.id.image_2);

        name_3 = findViewById(R.id.name_3);
        point_3 = findViewById(R.id.point_3);
        image_3 = findViewById(R.id.image_3);


    }

    private void initItems() {
        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 1));
        getRanking();

        arrow_back2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    public void getRanking() {

        ApiService.loading(this, true);

        final RequestQueue queue = Volley.newRequestQueue(this);
        // Tag used to cancel the request
        String url = ApiService.getRanking;


        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject responseBody) {
                        // pDialog.hide();

                        try {
                            ApiService.loading(RankActivity.this, false);


                            String status = responseBody.getString("status");

//                            String message = responseBody.getString("message");
                            String code = responseBody.getString("code");


                            if (status.equals("true")) {

                                String rank = responseBody.getString("rank");
                                JSONArray jsonArray = new JSONArray(rank);

                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JsonParser parser = new JsonParser();
                                    JsonElement mJson = parser.parse(jsonArray.getString(i));
                                    Gson gson = new Gson();
                                    Db_Ranking storeModules = gson.fromJson(mJson, Db_Ranking.class);


                                    if (i > 2) {
                                        list.add(storeModules);

                                    } else {
                                        list_first.add(storeModules);

                                    }


                                }
                                Adapter_Ranking adapter = new Adapter_Ranking(list, getApplicationContext());

                                recyclerView.setAdapter(adapter);
                                //   System.out.println(matches);


                                try {
                                    name_1.setText(list_first.get(0).getUser().getName());
                                    name_2.setText(list_first.get(1).getUser().getName());
                                    name_3.setText(list_first.get(2).getUser().getName());
                                }catch (Exception e){

                                }
                                try {
                                    point_1.setText(list_first.get(0).getPoints());
                                    point_2.setText(list_first.get(1).getPoints());
                                    point_3.setText(list_first.get(2).getPoints());

                                }catch (Exception e){

                                }


                                try {
                                    Picasso.get().load(list_first.get(0).getUser().getImageProfile()).into(image_1);
                                    Picasso.get().load(list_first.get(1).getUser().getImageProfile()).into(image_2);
                                    Picasso.get().load(list_first.get(2).getUser().getImageProfile()).into(image_3);
                                }catch (Exception e){

                                }



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
                ApiService.loading(RankActivity.this, false);

//                webServise.loading(SplashScreenActivity.this, false);

                ApiService.ErrorResponse(RankActivity.this, volleyError);
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