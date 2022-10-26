package com.hexa.guessandshoot.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
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
import com.hexa.guessandshoot.Modules.Db_RankingModules;
import com.hexa.guessandshoot.R;
import com.hexa.guessandshoot.Settings.ApiService;
import com.hexa.guessandshoot.Settings.Settings;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class StatisticsActivity extends AppCompatActivity {

    TextView name_first_year, point_first_year;
    AlertDialog alertDialog;


    TextView name_first_month, point_first_month;
    TextView name_secand_month, point_secand_month;
    TextView name_thaird_month, point_third_month;


    List<Db_RankingModules> db_rankingModuless = new ArrayList<>();

    CircleImageView image_account_year;
    CircleImageView image_account_1;
    CircleImageView image_account_2;
    CircleImageView image_account_3;


    LinearLayout Linear_account_year ,Linear_account_1 ,Linear_account_2 ,Linear_account_3;
    LinearLayout Linrear_account_1_this ,Linrear_account_2_this ,Linrear_account_3_this ;


    CircleImageView imagethis_account_1 ,imagethis_account_2 ,imagethis_account_3;

    TextView namethis_first_month ,namethis_secand_month ,name_this_thaird_month;
    TextView pointthis_first_month ,pointthis_secand_month ,point_this_third_month;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);
        
        initViews();
        
        initItems();
    }

    private void initViews() {
        name_first_year = findViewById(R.id.name_first_year);
        point_first_year = findViewById(R.id.point_first_year);
        image_account_year = findViewById(R.id.image_account_year);
        Linear_account_year = findViewById(R.id.Linear_account_year);
        Linear_account_1 = findViewById(R.id.Linrear_account_1);
        Linear_account_2 = findViewById(R.id.Linrear_account_2);
        Linear_account_3 = findViewById(R.id.Linrear_account_3);
        Linrear_account_1_this = findViewById(R.id.Linrear_account_1_this);
        Linrear_account_2_this = findViewById(R.id.Linrear_account_2_this);
        Linrear_account_3_this = findViewById(R.id.Linrear_account_3_this);



        imagethis_account_1 = findViewById(R.id.imagethis_account_1);
        imagethis_account_2 = findViewById(R.id.imagethis_account_2);
        imagethis_account_3 = findViewById(R.id.imagethis_account_3);

        namethis_first_month = findViewById(R.id.namethis_first_month);
        namethis_secand_month = findViewById(R.id.namethis_secand_month);
        name_this_thaird_month = findViewById(R.id.name_this_thaird_month);

        pointthis_first_month = findViewById(R.id.pointthis_first_month);
        pointthis_secand_month = findViewById(R.id.pointthis_secand_month);
        point_this_third_month = findViewById(R.id.point_this_third_month);



        name_first_month = findViewById(R.id.name_first_month);
        point_first_month = findViewById(R.id.point_first_month);
        image_account_1 = findViewById(R.id.image_account_1);

        name_secand_month = findViewById(R.id.name_secand_month);
        point_secand_month = findViewById(R.id.point_secand_month);
        image_account_2 = findViewById(R.id.image_account_2);


        name_thaird_month = findViewById(R.id.name_thaird_month);
        point_third_month = findViewById(R.id.point_third_month);
        image_account_3 = findViewById(R.id.image_account_3);

    }

    private void initItems() {
        getMonthlyRanking();
    }

    public void getMonthlyRanking() {

        ApiService.loading(StatisticsActivity.this, true);

        final RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        // Tag used to cancel the request
        String url = ApiService.getMonthlyRanking;


        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject responseBody) {
                        // pDialog.hide();

                        try {
                            ApiService.loading(StatisticsActivity.this, false);

                            String status = responseBody.getString("status");

                            String code = responseBody.getString("code");


                            if (status.equals("true")) {
                                String rank = responseBody.getString("rank");
                                String current_rank = responseBody.getString("current_rank");
                                JSONArray jsonArray = new JSONArray(rank);
                                JSONArray current_rankArray = new JSONArray(current_rank);

                                for (int i = 0; i < current_rankArray.length(); i++) {
                                    JsonParser parser = new JsonParser();
                                    JsonElement mJson = parser.parse(current_rankArray.getString(i));
                                    Gson gson = new Gson();
                                    Db_RankingModules db_rankingModules = gson.fromJson(mJson, Db_RankingModules.class);
                                    db_rankingModuless.add(db_rankingModules);

                                    if (i == 0) {
                                        namethis_first_month.setText(db_rankingModules.getUser().getName() + "");
                                        pointthis_first_month.setText(db_rankingModules.getPoints() + "");
                                        Picasso.get().load(db_rankingModules.getUser().getImageProfile()).error(R.color.blue).into(imagethis_account_1);
                                        Log.e("getImageProfile1",db_rankingModules.getUser().getImageProfile());
                                        Linrear_account_1_this.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {


                                                LayoutInflater layoutInflater = (LayoutInflater) StatisticsActivity.this.getSystemService(StatisticsActivity.this.LAYOUT_INFLATER_SERVICE);
                                                final View popupView = layoutInflater.inflate(R.layout.alert_dialog_staticis, null);

                                                FrameLayout close = popupView.findViewById(R.id.close_btn);
                                                TextView cost = popupView.findViewById(R.id.cost);

                                                close.setOnClickListener(new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View v) {
                                                        alertDialog.dismiss();
                                                    }
                                                });
                                                cost.setText(Settings.getSettings().getMonthlyPrize1() + " $ ");

                                                final AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());

//            alertDialog_country =
                                                builder.setView(popupView);


                                                alertDialog = builder.show();

                                                alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

                                            }
                                        });


                                    }
                                    if (i == 1) {
                                        namethis_secand_month.setText(db_rankingModules.getUser().getName() + "");
                                        pointthis_secand_month.setText(db_rankingModules.getPoints() + "");
                                        Picasso.get().load(db_rankingModules.getUser().getImageProfile()).error(R.color.blue).into(imagethis_account_2);
                                        Log.e("getImageProfile2",db_rankingModules.getUser().getImageProfile());
                                        Linrear_account_2_this.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {


                                                LayoutInflater layoutInflater = (LayoutInflater) StatisticsActivity.this.getSystemService(StatisticsActivity.this.LAYOUT_INFLATER_SERVICE);
                                                final View popupView = layoutInflater.inflate(R.layout.alert_dialog_staticis, null);

                                                FrameLayout close = popupView.findViewById(R.id.close_btn);
                                                TextView cost = popupView.findViewById(R.id.cost);

                                                close.setOnClickListener(new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View v) {
                                                        alertDialog.dismiss();
                                                    }
                                                });
                                                cost.setText(Settings.getSettings().getMonthlyPrize2() + " $ ");
                                                final AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());

//            alertDialog_country =
                                                builder.setView(popupView);


                                                alertDialog = builder.show();

                                                alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

                                            }
                                        });
                                    }
                                    if (i == 2) {
                                        name_this_thaird_month.setText(db_rankingModules.getUser().getName() + "");
                                        point_this_third_month.setText(db_rankingModules.getPoints() + "");
                                        Picasso.get().load(db_rankingModules.getUser().getImageProfile()).error(R.color.blue).into(imagethis_account_3);
                                        Log.e("getImageProfile3",db_rankingModules.getUser().getImageProfile());
                                        Linrear_account_3_this.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {


                                                LayoutInflater layoutInflater = (LayoutInflater) StatisticsActivity.this.getSystemService(StatisticsActivity.this.LAYOUT_INFLATER_SERVICE);
                                                final View popupView = layoutInflater.inflate(R.layout.alert_dialog_staticis, null);

                                                FrameLayout close = popupView.findViewById(R.id.close_btn);
                                                TextView cost = popupView.findViewById(R.id.cost);

                                                close.setOnClickListener(new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View v) {
                                                        alertDialog.dismiss();
                                                    }
                                                });
                                                cost.setText(Settings.getSettings().getMonthlyPrize3() + " $ ");
                                                final AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());

//            alertDialog_country =
                                                builder.setView(popupView);


                                                alertDialog = builder.show();

                                                alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

                                            }
                                        });
                                    }

                                }


                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JsonParser parser = new JsonParser();
                                    JsonElement mJson = parser.parse(jsonArray.getString(i));
                                    Gson gson = new Gson();
                                    Db_RankingModules db_rankingModules = gson.fromJson(mJson, Db_RankingModules.class);
                                    db_rankingModuless.add(db_rankingModules);

                                    if (i == 0) {
                                        name_first_month.setText(db_rankingModules.getUser().getName() + "");
                                        point_first_month.setText(db_rankingModules.getPoints() + "");
                                        Picasso.get().load(db_rankingModules.getUser().getImageProfile()).error(R.color.blue).into(image_account_1);
                                        Log.e("getImageProfile1",db_rankingModules.getUser().getImageProfile());
                                        Linear_account_1.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {


                                                LayoutInflater layoutInflater = (LayoutInflater) StatisticsActivity.this.getSystemService(StatisticsActivity.this.LAYOUT_INFLATER_SERVICE);
                                                final View popupView = layoutInflater.inflate(R.layout.alert_dialog_staticis, null);

                                                FrameLayout close = popupView.findViewById(R.id.close_btn);
                                                TextView cost = popupView.findViewById(R.id.cost);

                                                close.setOnClickListener(new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View v) {
                                                        alertDialog.dismiss();
                                                    }
                                                });
                                                cost.setText(Settings.getSettings().getMonthlyPrize1() + " $ ");

                                                final AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());

//            alertDialog_country =
                                                builder.setView(popupView);


                                                alertDialog = builder.show();

                                                alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

                                            }
                                        });


                                    }
                                    if (i == 1) {
                                        name_secand_month.setText(db_rankingModules.getUser().getName() + "");
                                        point_secand_month.setText(db_rankingModules.getPoints() + "");
                                        Picasso.get().load(db_rankingModules.getUser().getImageProfile()).error(R.color.blue).into(image_account_2);
                                        Log.e("getImageProfile2",db_rankingModules.getUser().getImageProfile());
                                        Linear_account_2.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {


                                                LayoutInflater layoutInflater = (LayoutInflater) StatisticsActivity.this.getSystemService(StatisticsActivity.this.LAYOUT_INFLATER_SERVICE);
                                                final View popupView = layoutInflater.inflate(R.layout.alert_dialog_staticis, null);

                                                FrameLayout close = popupView.findViewById(R.id.close_btn);
                                                TextView cost = popupView.findViewById(R.id.cost);

                                                close.setOnClickListener(new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View v) {
                                                        alertDialog.dismiss();
                                                    }
                                                });
                                                cost.setText(Settings.getSettings().getMonthlyPrize2() + " $ ");
                                                final AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());

//            alertDialog_country =
                                                builder.setView(popupView);


                                                alertDialog = builder.show();

                                                alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

                                            }
                                        });
                                    }
                                    if (i == 2) {
                                        name_thaird_month.setText(db_rankingModules.getUser().getName() + "");
                                        point_third_month.setText(db_rankingModules.getPoints() + "");
                                        Picasso.get().load(db_rankingModules.getUser().getImageProfile()).error(R.color.blue).into(image_account_3);
                                        Log.e("getImageProfile3",db_rankingModules.getUser().getImageProfile());
                                        Linear_account_3.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {


                                                LayoutInflater layoutInflater = (LayoutInflater) StatisticsActivity.this.getSystemService(StatisticsActivity.this.LAYOUT_INFLATER_SERVICE);
                                                final View popupView = layoutInflater.inflate(R.layout.alert_dialog_staticis, null);

                                                FrameLayout close = popupView.findViewById(R.id.close_btn);
                                                TextView cost = popupView.findViewById(R.id.cost);

                                                close.setOnClickListener(new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View v) {
                                                        alertDialog.dismiss();
                                                    }
                                                });
                                                cost.setText(Settings.getSettings().getMonthlyPrize3() + " $ ");
                                                final AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());

//            alertDialog_country =
                                                builder.setView(popupView);


                                                alertDialog = builder.show();

                                                alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

                                            }
                                        });
                                    }

                                }


                                String year_champ = responseBody.getString("year_champ");

                                JsonParser parser = new JsonParser();
                                JsonElement mJson = parser.parse(year_champ);
                                Gson gson = new Gson();
                                Db_RankingModules db_rankingModules = gson.fromJson(mJson, Db_RankingModules.class);

                                try {
                                    name_first_year.setText(db_rankingModules.getUser().getName() + "");
                                    point_first_year.setText(db_rankingModules.getPoints() + "");
                                    Picasso.get().load(db_rankingModules.getUser().getImageProfile()).error(R.color.blue).into(image_account_year);
                                    Log.e("getImageProfile4",db_rankingModules.getUser().getImageProfile());
                                    Linear_account_year.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {


                                            LayoutInflater layoutInflater = (LayoutInflater) StatisticsActivity.this.getSystemService(StatisticsActivity.this.LAYOUT_INFLATER_SERVICE);
                                            final View popupView = layoutInflater.inflate(R.layout.alert_dialog_staticis, null);

                                            FrameLayout close = popupView.findViewById(R.id.close_btn);
                                            TextView cost = popupView.findViewById(R.id.cost);

                                            close.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    alertDialog.dismiss();
                                                }
                                            });
                                            cost.setText(Settings.getSettings().getYearlyPrize() + " $ ");
                                            final AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());

//            alertDialog_country =
                                            builder.setView(popupView);


                                            alertDialog = builder.show();

                                            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

                                        }
                                    });
                                }catch (Exception e){

                                }


                            } else {

                            }

                        } catch (JSONException e) {
                            Log.e("JSONException",e.toString());
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError volleyError) {
/////
                ApiService.loading(StatisticsActivity.this, false);

//                webServise.loading(SplashScreenActivity.this, false);

                ApiService.ErrorResponse(StatisticsActivity.this, volleyError);
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