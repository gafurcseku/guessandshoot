package com.hexa.guessandshoot.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
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
import com.hexa.guessandshoot.Adapter.Adapter_History;
import com.hexa.guessandshoot.Adapter.Adapter_notifications;
import com.hexa.guessandshoot.Adapter.YearsAdapter;
import com.hexa.guessandshoot.Modules.Db_notification;
import com.hexa.guessandshoot.Modules.History;
import com.hexa.guessandshoot.R;
import com.hexa.guessandshoot.Settings.ApiService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HistoryActivity extends AppCompatActivity {
    ImageView arrow_back,img_filter;
    RecyclerView ls ;
    ArrayList<String> month = getMonth() ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        ls =findViewById(R.id.ls);
        arrow_back = findViewById(R.id.arrow_back);
        img_filter = findViewById(R.id.img_filter);
        arrow_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });
        img_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show_Book();

            }
        });

        ls.setLayoutManager(new LinearLayoutManager(this));
        getHistory("","");
    }

    public void getHistory(String year , String month) {

        ApiService.loading(HistoryActivity.this, true);

        final RequestQueue queue = Volley.newRequestQueue(HistoryActivity.this);
        // Tag used to cancel the request
        String url = ApiService.getHistory+"?year="+year+"&month="+month;

        Log.e("url",url);

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject responseBody) {
                        // pDialog.hide();

                        try {
                            ApiService.loading(HistoryActivity.this, false);

                            System.out.println(responseBody.toString());
                            String status = responseBody.getString("status");

//                            String message = responseBody.getString("message");
                            String code = responseBody.getString("code");


                            if (status.equals("true")) {

                                String news = responseBody.getString("history");
                                JSONArray jsonArray = new JSONArray(news);
                                ArrayList<History> list = new ArrayList<>();
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JsonParser parser = new JsonParser();
                                    JsonElement mJson = parser.parse(jsonArray.getString(i));
                                    Gson gson = new Gson();
                                    History db_notification = gson.fromJson(mJson, History.class);

                                    list.add(db_notification);
                                }
                                Adapter_History adapter = new Adapter_History(list, HistoryActivity.this);

                                ls.setAdapter(adapter);
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
                ApiService.loading(HistoryActivity.this, false);

//                webServise.loading(SplashScreenActivity.this, false);

                ApiService.ErrorResponse(HistoryActivity.this, volleyError);
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
    AlertDialog alertDialog = null;

    public void show_Book() {


        LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View popupView = layoutInflater.inflate(R.layout.item_filter, null);

        try {

            FrameLayout close_btn = popupView.findViewById(R.id.close_btn);

            Spinner SP_Years = popupView.findViewById(R.id.SP_Years);
            Spinner SP_month = popupView.findViewById(R.id.SP_month);
            ImageView arrow_back = popupView.findViewById(R.id.arrow_back);
            ImageView image_right = popupView.findViewById(R.id.image_right);
            ImageView arrow_back_month = popupView.findViewById(R.id.arrow_back_month);
            ImageView image_right_month = popupView.findViewById(R.id.image_right_month);
            Button btn_Filter = popupView.findViewById(R.id.btn_Filter);
//
//
            final AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setView(popupView);

            alertDialog = builder.show();
            alertDialog.getWindow().setGravity(Gravity.CENTER);
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
//

            new Handler().post(new Runnable() {
                @Override
                public void run() {

            ArrayList<String> years = getYears() ;
            YearsAdapter yearsAdapter = new YearsAdapter(HistoryActivity.this ,years);
            SP_Years.setAdapter(yearsAdapter);
            YearsAdapter monthAdapter = new YearsAdapter(HistoryActivity.this ,month);
            SP_month.setAdapter(monthAdapter);
                }
            });



            arrow_back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (SP_Years.getSelectedItemPosition()>0){
                        SP_Years.setSelection(SP_Years.getSelectedItemPosition()-1);
                    }

                }
            });

            image_right.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (SP_Years.getSelectedItemPosition()<month.size()-1){
                        SP_Years.setSelection(SP_Years.getSelectedItemPosition()+1);
                    }

                }
            });

//
//
//
//

            arrow_back_month.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (SP_month.getSelectedItemPosition()>0){
                        SP_month.setSelection(SP_month.getSelectedItemPosition()-1);
                    }

                }
            });

            image_right_month.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (SP_month.getSelectedItemPosition()<month.size()-1){
                        SP_month.setSelection(SP_month.getSelectedItemPosition()+1);
                    }

                }
            });
//
            btn_Filter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                  String year = (String) SP_Years.getSelectedItem();
                  int mont = (int) SP_month.getSelectedItemPosition();

                    getHistory(year,(mont+1)+"");
                    alertDialog.dismiss();
                }
            });
            close_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialog.dismiss();
                }
            });


        } catch (Exception e) {
            e.printStackTrace();
        }



    }

    private ArrayList<String> getMonth() {
        ArrayList<String> years = new ArrayList<>();
        DateFormatSymbols dfs = new DateFormatSymbols();
        String[] months = dfs.getMonths();
        for (int i = 0 ; i <12 ; i++) {
            years.add(months[i]);
        }

        return years ;
    }
//    String getMonthForInt(int num) {
//        String month = "wrong";
//
//
//        if (num >= 0 && num <= 11 ) {
//            month = months[num];
//        }
//        return month;
//    }

    public ArrayList<String> getYears(){

        ArrayList<String> years = new ArrayList<>();
        for (int i = 2020 ; i <2040 ; i++) {
            years.add(i+"");
        }

        return years ;
    }
}
