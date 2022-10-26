package com.hexa.guessandshoot.Activity.Auth;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
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
import com.getkeepsafe.taptargetview.TapTarget;
import com.getkeepsafe.taptargetview.TapTargetSequence;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.hexa.guessandshoot.Activity.SharedPrefs;
import com.hexa.guessandshoot.Activity.WithdrawalActivity;
import com.hexa.guessandshoot.Adapter.Adapter_MyExpections_In_MyAccont;
import com.hexa.guessandshoot.DCallBack;
import com.hexa.guessandshoot.Modules.Db_My_Expections_profile;
import com.hexa.guessandshoot.R;
import com.hexa.guessandshoot.Settings.ApiService;
import com.hexa.guessandshoot.Settings.Settings;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.orhanobut.hawk.Hawk;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;


public class my_account extends AppCompatActivity {
    RecyclerView recyclerView;
    Adapter adapter;
    ImageView edite_account, image_account;
    Button withdrawal;

    NestedScrollView nested;

    ImageView arrow_back;
    TextView name;
    TextView totalpoint;
    TextView totalCash;
    TextView TV_monthPoints;
    ProgressBar progress;
    ArrayList<Db_My_Expections_profile> list = new ArrayList<>();
    Adapter_MyExpections_In_MyAccont adapter2;
    int pageC = 1;
    boolean islast = false;
    private AdView mAdView;

    //@SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account);
        name = findViewById(R.id.name);
        image_account = findViewById(R.id.image_account);
        totalCash = findViewById(R.id.totalCash);
        totalpoint = findViewById(R.id.totalpoint);
        arrow_back = findViewById(R.id.arrow_back);
        nested = findViewById(R.id.nested);
        progress = findViewById(R.id.progress);
        TV_monthPoints = findViewById(R.id.TV_monthPoints);
        edite_account = findViewById(R.id.edite_account);
        checkTutorial();


        mAdView = findViewById(R.id.adView);

        if(!((Boolean) Hawk.get("status"))){
            AdRequest adRequest = new AdRequest.Builder().build();
            mAdView.loadAd(adRequest);
        }else {
            mAdView.setVisibility(View.GONE);
        }

        arrow_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        try {
            name.setText(Settings.getUser().getName());
            Picasso.get().load(Settings.getUser().getImageProfile()).error(getResources().getDrawable(R.drawable.ic_account)).into(image_account);
            System.out.println(Settings.getUser().getImageProfile());
        } catch (Exception e) {
            e.printStackTrace();
        }

        withdrawal = findViewById(R.id.withdrawal);


        edite_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(my_account.this, EditeMyAccount.class);
                startActivity(intent);
            }
        });

        recyclerView = findViewById(R.id.recyclerMyExpectionsMyAccount);
//
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));

        adapter2 = new Adapter_MyExpections_In_MyAccont(my_account.this, new DCallBack() {
            @Override
            public void Result(String obj, String fun, boolean IsSuccess) {
                getPointsWalletExpections(1);
                islast = false;
            }
        });

        recyclerView.setAdapter(adapter2);

        nested.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY == (v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight())) {
                    Log.e("scrollY", "scrollY");
                    if (!islast) {
                        getPointsWalletExpections(pageC);
                    }
                }
            }
        });

        getPointsWalletExpections(pageC);

    }




    private void checkTutorial() {
        boolean isShowProfileTutorial = SharedPrefs.getBoolean(getApplicationContext(), "is_show_profile_tut", true);
        if (isShowProfileTutorial) {
            showTutorial();
        }
    }

    private static final String TAG = "my_account";

    private void showTutorial() {
        Log.d(TAG, "showTutorial: ");
        new TapTargetSequence(this)
                .targets(createTargetView(edite_account, "", getString(R.string.tut_edit_profile_desc)))
                .considerOuterCircleCanceled(true)
                .continueOnCancel(true)
                .listener(new TapTargetSequence.Listener() {
                    @Override
                    public void onSequenceFinish() {
                        SharedPrefs.save(getApplicationContext(), "is_show_profile_tut", false);

                    }

                    @Override
                    public void onSequenceStep(TapTarget lastTarget, boolean targetClicked) {

                    }

                    @Override
                    public void onSequenceCanceled(TapTarget lastTarget) {
                        SharedPrefs.save(getApplicationContext(), "is_show_profile_tut", false);
                    }
                }).start();
    }

    TapTarget createTargetView(View view, String title, String descreption) {
        return TapTarget.forView(view, title, descreption)
                .dimColor(android.R.color.transparent)
                .tintTarget(false)
                .outerCircleColor(R.color.colorAccent)
                .targetCircleColor(R.color.colorPrimary)
                .textColor(android.R.color.white);
    }


    public void getPointsWalletExpections(int page) {

        //ApiService.loading(my_account.this, true);

        final RequestQueue queue = Volley.newRequestQueue(my_account.this);
        // Tag used to cancel the request
        String url = ApiService.getPointsWalletExpections + "?page=" + page;
        Log.e("url", url);


        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject responseBody) {
                        // pDialog.hide();

                        try {
                            //  ApiService.loading(my_account.this, false);


                            String status = responseBody.getString("status");

//                            String message = responseBody.getString("message");
                            String code = responseBody.getString("code");


                            if (status.equals("true")) {
                                list.clear();
                                pageC++;
                                String matches = responseBody.getString("matches");


                                JSONObject matches__ = new JSONObject(matches);

                                String data = matches__.getString("data");
                                JSONArray jsonArray = new JSONArray(data);

                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JsonParser parser = new JsonParser();
                                    JsonElement mJson = parser.parse(jsonArray.getString(i));
                                    Gson gson = new Gson();
                                    Db_My_Expections_profile db_my_expections_profile = gson.fromJson(mJson, Db_My_Expections_profile.class);
                                    list.add(db_my_expections_profile);
                                }
                                Log.e("list", list.size() + "");
                                if (list.size() < 7) {
                                    progress.setVisibility(View.GONE);
                                    islast = true;

                                }
                                adapter2.addItems(list);


                                //   System.out.println(matches);


                                final String wallet = responseBody.getString("wallet");
                                String points = responseBody.getString("points");
                                String monthPoints = responseBody.getString("monthPoints");
                                totalpoint.setText(points);
                                totalCash.setText(wallet + " $");
                                TV_monthPoints.setText(monthPoints + "");

                                withdrawal.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        if (Integer.valueOf(wallet + "") >= 20) {
                                            Intent intent = new Intent(my_account.this, WithdrawalActivity.class);
                                            startActivity(intent);

                                        } else {
                                            Toast.makeText(my_account.this, getResources().getString(R.string.ifmoneysent), Toast.LENGTH_LONG).show();
                                        }

                                    }
                                });


                                Hawk.put("totalCash", wallet);
                            } else {

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                //
                //  ApiService.loading(my_account.this, false);

//                webServise.loading(SplashScreenActivity.this, false);

                ApiService.ErrorResponse(my_account.this, volleyError);
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

    @Override
    protected void onResume() {
        super.onResume();
        try {

            name.setText(Settings.getUser().getName());
            Picasso.get().load(Settings.getUser().getImageProfile()).error(getResources().getDrawable(R.drawable.ic_account)).into(image_account);
            System.out.println(Settings.getUser().getImageProfile());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getPointsWalletExpectionsx() {

        ApiService.loading(my_account.this, true);

        AsyncHttpClient client = new AsyncHttpClient();

        String BASE_URL = ApiService.getPointsWalletExpections;

        final int DEFAULT_TIMEOUT = 20 * 1000;

        client.setTimeout(DEFAULT_TIMEOUT);
        ApiService.Header_Async(client);

        client.post(BASE_URL, null, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
            }


            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
            }

            @Override
            public void onUserException(Throwable error) {
                // super.onUserException(error);
//                Log.d("ttt", "onFailure: jhjj" + error.getMessage());


            }


            @Override
            public void onProgress(long bytesWritten, long totalSize) {


                try {

                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        });

    }


}

