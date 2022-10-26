package com.hexa.guessandshoot.Fragment;

import android.app.AlertDialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

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
import com.hexa.guessandshoot.Activity.NewsActivity;
import com.hexa.guessandshoot.Adapter.AdapterNews;
import com.hexa.guessandshoot.Modules.Db_News;
import com.hexa.guessandshoot.Modules.Db_RankingModules;
import com.hexa.guessandshoot.Modules.MonthlyRank;
import com.hexa.guessandshoot.R;
import com.hexa.guessandshoot.Settings.ApiService;
import com.hexa.guessandshoot.Settings.Settings;
import com.orhanobut.hawk.Hawk;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentStatistics extends Fragment {


    private AdView mAdView;

    TextView name_first_year, point_first_year ;
    TextView name_first_year2, point_first_year2 ;
    TextView name_first_year3, point_first_year3 ;
    AlertDialog alertDialog;


    TextView name_first_month, point_first_month;
    TextView name_secand_month, point_secand_month;
    TextView name_thaird_month, point_third_month;


   // List<MonthlyRank> db_rankingModuless = new ArrayList<>();

    CircleImageView image_account_year ,image_account_year2 ,image_account_year3;
    CircleImageView image_account_1;
    CircleImageView image_account_2;
    CircleImageView image_account_3;




    LinearLayout Linear_account_year ,Linear_account_year2 ,Linear_account_year3,Linear_account_1 ,Linear_account_2 ,Linear_account_3;
    LinearLayout Linrear_account_1_this ,Linrear_account_2_this ,Linrear_account_3_this ;


    CircleImageView imagethis_account_1 ,imagethis_account_2 ,imagethis_account_3;

    TextView namethis_first_month ,namethis_secand_month ,name_this_thaird_month;
    TextView pointthis_first_month ,pointthis_secand_month ,point_this_third_month;


    ImageView arrow_back ;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = getLayoutInflater().inflate(R.layout.activity_statistics, container, false);
        name_first_year = view.findViewById(R.id.name_first_year);
        name_first_year2 = view.findViewById(R.id.name_first_year2);
        name_first_year3 = view.findViewById(R.id.name_first_year3);
        point_first_year2 = view.findViewById(R.id.point_first_year2);
        point_first_year3 = view.findViewById(R.id.point_first_year3);
        point_first_year = view.findViewById(R.id.point_first_year);
        image_account_year = view.findViewById(R.id.image_account_year);
        image_account_year2 = view.findViewById(R.id.image_account_year2);
        image_account_year3 = view.findViewById(R.id.image_account_year3);
        Linear_account_year = view.findViewById(R.id.Linear_account_year);
        Linear_account_year2 = view.findViewById(R.id.Linear_account_year2);
        Linear_account_year3 = view.findViewById(R.id.Linear_account_year3);
        Linear_account_1 = view.findViewById(R.id.Linrear_account_1);
        Linear_account_2 = view.findViewById(R.id.Linrear_account_2);
        Linear_account_3 = view.findViewById(R.id.Linrear_account_3);
        Linrear_account_1_this = view.findViewById(R.id.Linrear_account_1_this);
        Linrear_account_2_this = view.findViewById(R.id.Linrear_account_2_this);
        Linrear_account_3_this = view.findViewById(R.id.Linrear_account_3_this);
        arrow_back = view.findViewById(R.id.arrow_back);



        imagethis_account_1 = view.findViewById(R.id.imagethis_account_1);
        imagethis_account_2 = view.findViewById(R.id.imagethis_account_2);
        imagethis_account_3 = view.findViewById(R.id.imagethis_account_3);

        namethis_first_month = view.findViewById(R.id.namethis_first_month);
        namethis_secand_month = view.findViewById(R.id.namethis_secand_month);
        name_this_thaird_month = view.findViewById(R.id.name_this_thaird_month);

        pointthis_first_month = view.findViewById(R.id.pointthis_first_month);
        pointthis_secand_month = view.findViewById(R.id.pointthis_secand_month);
        point_this_third_month = view.findViewById(R.id.point_this_third_month);



        name_first_month = view.findViewById(R.id.name_first_month);
        point_first_month = view.findViewById(R.id.point_first_month);
        image_account_1 = view.findViewById(R.id.image_account_1);

        name_secand_month = view.findViewById(R.id.name_secand_month);
        point_secand_month = view.findViewById(R.id.point_secand_month);
        image_account_2 = view.findViewById(R.id.image_account_2);


        name_thaird_month = view.findViewById(R.id.name_thaird_month);
        point_third_month = view.findViewById(R.id.point_third_month);
        image_account_3 = view.findViewById(R.id.image_account_3);

        getMonthlyRanking();

        arrow_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });

        mAdView =  view.findViewById(R.id.adView);

        if(!((Boolean) Hawk.get("status"))){
            AdRequest adRequest = new AdRequest.Builder().build();
            mAdView.loadAd(adRequest);
        }else {
            mAdView.setVisibility(View.GONE);
        }

        return view;
    }

    public void getMonthlyRanking() {

        ApiService.loading(getActivity(), true);

        final RequestQueue queue = Volley.newRequestQueue(getContext());
        // Tag used to cancel the request
        String url = ApiService.getMonthlyRanking;


        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject responseBody) {
                        // pDialog.hide();

                        try {
                            ApiService.loading(getActivity(), false);

                            String status = responseBody.getString("status");

                            String code = responseBody.getString("code");


                            if (status.equals("true")) {
                                String current_rank = responseBody.getString("current_rank");
                                JSONArray current_rankArray = new JSONArray(current_rank);

                                for (int i = 0; i < current_rankArray.length(); i++) {
                                    JsonParser parser = new JsonParser();
                                    JsonElement mJson = parser.parse(current_rankArray.getString(i));
                                    Gson gson = new Gson();
                                    Db_RankingModules db_rankingModules = gson.fromJson(mJson, Db_RankingModules.class);
                                  //  db_rankingModuless.add(db_rankingModules);

                                    if (i == 0) {
                                        namethis_first_month.setText(db_rankingModules.getUser().getName() + "");
                                        pointthis_first_month.setText(db_rankingModules.getPoints() + "");
                                        Picasso.get().load(db_rankingModules.getUser().getImageProfile()).error(R.color.blue).into(imagethis_account_1);
                                        Log.e("getImageProfile1",db_rankingModules.getUser().getImageProfile());
                                        Linrear_account_1_this.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {


                                                LayoutInflater layoutInflater = (LayoutInflater) getActivity().getSystemService(getActivity().LAYOUT_INFLATER_SERVICE);
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

                                                final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

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


                                                LayoutInflater layoutInflater = (LayoutInflater) getActivity().getSystemService(getActivity().LAYOUT_INFLATER_SERVICE);
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
                                                final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

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


                                                LayoutInflater layoutInflater = (LayoutInflater) getActivity().getSystemService(getActivity().LAYOUT_INFLATER_SERVICE);
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
                                                final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

//            alertDialog_country =
                                                builder.setView(popupView);


                                                alertDialog = builder.show();

                                                alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

                                            }
                                        });
                                    }

                                }

                           //     String rank = responseBody.getString("rank");
                                String rank = responseBody.getString("monthlyRanks");
                                JSONArray jsonArray = new JSONArray(rank);
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JsonParser parser = new JsonParser();
                                    JsonElement mJson = parser.parse(jsonArray.getString(i));
                                    Gson gson = new Gson();
                                    MonthlyRank db_rankingModules = gson.fromJson(mJson, MonthlyRank.class);
                                   // db_rankingModuless.add(db_rankingModules);

                                    if (i == 0) {
                                        name_first_month.setText(db_rankingModules.getUser().getName() + "");
                                        point_first_month.setText(db_rankingModules.getPoints() + "");
                                        Picasso.get().load(db_rankingModules.getUser().getImageProfile()).error(R.color.blue).into(image_account_1);
                                        Log.e("getImageProfile1",db_rankingModules.getUser().getImageProfile());
                                        Linear_account_1.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {


                                                LayoutInflater layoutInflater = (LayoutInflater) getActivity().getSystemService(getActivity().LAYOUT_INFLATER_SERVICE);
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

                                                final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

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


                                                LayoutInflater layoutInflater = (LayoutInflater) getActivity().getSystemService(getActivity().LAYOUT_INFLATER_SERVICE);
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
                                                final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

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


                                                LayoutInflater layoutInflater = (LayoutInflater) getActivity().getSystemService(getActivity().LAYOUT_INFLATER_SERVICE);
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
                                                final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

//            alertDialog_country =
                                                builder.setView(popupView);


                                                alertDialog = builder.show();

                                                alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

                                            }
                                        });
                                    }

                                }


                              //  JSONArray year_champ = responseBody.getJSONArray("year_champ");
                                JSONArray year_champ = responseBody.getJSONArray("all_year_champ");

                                for (int i = 0; i < year_champ.length(); i++) {
                                    JsonParser parser = new JsonParser();
                                    JsonElement mJson = parser.parse(year_champ.getString(i));
                                    Gson gson = new Gson();
                                    Db_RankingModules db_rankingModules = gson.fromJson(mJson, Db_RankingModules.class);

                                    if (i==0){
                                        setData(db_rankingModules ,name_first_year,point_first_year,image_account_year,Linear_account_year ,Settings.getSettings().getYearlyPrize() + " $ ");
                                    }else if (i == 1){
                                        setData(db_rankingModules ,name_first_year2,point_first_year2,image_account_year2,Linear_account_year2 ,Settings.getSettings().yearlyPrize2 + " $ ");
                                    }else if (i==2){
                                        setData(db_rankingModules ,name_first_year3,point_first_year3,image_account_year3,Linear_account_year3 ,Settings.getSettings().yearlyPrize3+ " $ ");

                                    }
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
              //  showErrorDialog();
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

    private void showErrorDialog() {
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(getActivity())
                .setTitle(getString(R.string.network_connection_error))
                .setMessage("")
                .setCancelable(false)
                .setView(R.layout.item_connection_error)
                .setPositiveButton(R.string.retry, (dialog, which) -> {
                    getMonthlyRanking();
                });
        builder.create().show();
    }


    public void show_notfy(){
        LayoutInflater layoutInflater = (LayoutInflater) getActivity().getSystemService(getActivity().LAYOUT_INFLATER_SERVICE);
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

        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());


        builder.setView(popupView);


        alertDialog = builder.show();

        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
    }

    public void setData(Db_RankingModules db_rankingModules , TextView name , TextView point , ImageView image , LinearLayout linearLayout , String text){
        try {
            name.setText(db_rankingModules.getUser().getName() + "");
            point.setText(db_rankingModules.getPoints() + "");
            Picasso.get().load(db_rankingModules.getUser().getImageProfile()).error(R.color.blue).into(image);
            Log.e("getImageProfile4",db_rankingModules.getUser().getImageProfile());
            linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    LayoutInflater layoutInflater = (LayoutInflater) getActivity().getSystemService(getActivity().LAYOUT_INFLATER_SERVICE);
                    final View popupView = layoutInflater.inflate(R.layout.alert_dialog_staticis, null);

                    FrameLayout close = popupView.findViewById(R.id.close_btn);
                    TextView cost = popupView.findViewById(R.id.cost);

                    close.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            alertDialog.dismiss();
                        }
                    });
                   // cost.setText(Settings.getSettings().getYearlyPrize() + " $ ");
                    cost.setText(text);
                    final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

//            alertDialog_country =
                    builder.setView(popupView);


                    alertDialog = builder.show();

                    alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

                }
            });
        }catch (Exception e){
            Log.e("Exception",e.toString());
        }
    }
   //  try {
//                                            name_first_year.setText(db_rankingModules.getUser().getName() + "");
//                                            point_first_year.setText(db_rankingModules.getPoints() + "");
//                                            Picasso.get().load(db_rankingModules.getUser().getImageProfile()).error(R.color.blue).into(image_account_year);
//                                            Log.e("getImageProfile4",db_rankingModules.getUser().getImageProfile());
//                                            Linear_account_year.setOnClickListener(new View.OnClickListener() {
//                                                @Override
//                                                public void onClick(View v) {
//
//
//                                                    LayoutInflater layoutInflater = (LayoutInflater) getActivity().getSystemService(getActivity().LAYOUT_INFLATER_SERVICE);
//                                                    final View popupView = layoutInflater.inflate(R.layout.alert_dialog_staticis, null);
//
//                                                    FrameLayout close = popupView.findViewById(R.id.close_btn);
//                                                    TextView cost = popupView.findViewById(R.id.cost);
//
//                                                    close.setOnClickListener(new View.OnClickListener() {
//                                                        @Override
//                                                        public void onClick(View v) {
//                                                            alertDialog.dismiss();
//                                                        }
//                                                    });
//                                                    cost.setText(Settings.getSettings().getYearlyPrize() + " $ ");
//                                                    final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
//
////            alertDialog_country =
//                                                    builder.setView(popupView);
//
//
//                                                    alertDialog = builder.show();
//
//                                                    alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
//
//                                                }
//                                            });
//                                        }catch (Exception e){
//
//                                        }
}