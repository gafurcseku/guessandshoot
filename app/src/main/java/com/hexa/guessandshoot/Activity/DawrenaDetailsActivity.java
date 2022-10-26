package com.hexa.guessandshoot.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
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
import com.hexa.guessandshoot.Adapter.AdapterMyTeam;
import com.hexa.guessandshoot.Adapter.AdapterTeamRanking;
import com.hexa.guessandshoot.Adapter.Adapter_notifications;
import com.hexa.guessandshoot.Modules.Db_notification;
import com.hexa.guessandshoot.Modules.League;
import com.hexa.guessandshoot.R;
import com.hexa.guessandshoot.Settings.ApiService;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class DawrenaDetailsActivity extends AppCompatActivity {
    League league ;
    TextView TV_members,TV_code ,TV_copy ,TV_share ,TV_name ,TV_Point ,TV_level;
    CircleImageView image ;
    RecyclerView rvPlayer ;
    ImageView arrow_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dawrena_details);
        rvPlayer = findViewById(R.id.rvPlayer);
        image = findViewById(R.id.image);
        TV_members = findViewById(R.id.TV_members);
        TV_code = findViewById(R.id.TV_code);
        TV_copy = findViewById(R.id.TV_copy);
        TV_share = findViewById(R.id.TV_share);
        TV_name = findViewById(R.id.TV_name);
        TV_Point = findViewById(R.id.TV_Point);
        TV_level = findViewById(R.id.TV_level);
        arrow_back = findViewById(R.id.arrow_back);

        arrow_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

       int id =  getIntent().getExtras().getInt("id");
        getData(id+"");
    }



    public void setDate(){

        TV_members.setText(league.getPlayers().size()+"");
        TV_name.setText(league.getName());
        TV_code.setText(league.getCode()+" ");
        TV_Point.setText(league.total_points);
        TV_level.setText(league.rank);
        Picasso.get().load(league.getImage()).into(image);
        AdapterMyTeam adapter = new AdapterMyTeam(league.getPlayers(),  getActivity(),league.getUserId());

        rvPlayer.setLayoutManager(new GridLayoutManager(getActivity(), 1));
        rvPlayer.setAdapter(adapter);
       // TV_level.setText(league.get);
    }
   Activity getActivity(){
        return this ;
    }
    public void getData(String id) {

        ApiService.loading(getActivity(), true);

        final RequestQueue queue = Volley.newRequestQueue(getActivity());
        // Tag used to cancel the request
        String url =  ApiService.getLeagueDetails+"?league_id="+id;;


        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject responseBody) {
                        // pDialog.hide();

                        try {
                            ApiService.loading(getActivity(), false);

                            System.out.println(responseBody.toString());
                            String status = responseBody.getString("status");

//                            String message = responseBody.getString("message");
                            String code = responseBody.getString("code");


                            if (status.equals("true")) {

                                String news = responseBody.getString("Leagues");
                                JSONObject jsonArray = new JSONObject(news);


                                    JsonParser parser = new JsonParser();
                                    JsonElement mJson = parser.parse(jsonArray.toString());
                                    Gson gson = new Gson();
                                    league = gson.fromJson(mJson, League.class);
                                    setDate();


                                //   System.out.println(matches);


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