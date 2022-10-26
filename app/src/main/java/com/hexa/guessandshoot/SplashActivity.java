package com.hexa.guessandshoot;

import static com.hexa.guessandshoot.Settings.ApiService.isUserHasSubscription;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.BillingClientStateListener;
import com.android.billingclient.api.BillingResult;
import com.android.billingclient.api.Purchase;
import com.android.billingclient.api.PurchaseHistoryRecord;
import com.android.billingclient.api.PurchaseHistoryResponseListener;
import com.android.billingclient.api.PurchasesUpdatedListener;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.anjlab.android.iab.v3.BillingProcessor;
import com.anjlab.android.iab.v3.TransactionDetails;
import com.google.firebase.messaging.FirebaseMessaging;
import com.hexa.guessandshoot.Activity.Auth.LoginActivity;
import com.hexa.guessandshoot.Activity.MainActivity;
import com.hexa.guessandshoot.Settings.ApiService;
import com.hexa.guessandshoot.Settings.Settings;
import com.hexa.guessandshoot.Settings.onCheck;
import com.orhanobut.hawk.Hawk;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;


public class SplashActivity extends AppCompatActivity {
    Handler handler;
    BillingClient billingClient ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_activity);

        if (Hawk.contains("notfi")) {
        } else {
            Hawk.put("notfi", true);
        }

        if (!Hawk.contains("User")) {
            Hawk.put("User", "");
        }

        if (!Hawk.contains("status")) {
            Hawk.put("status", true);
        }

        Hawk.put("status", false);

        if (Settings.IsSubscribe()) {
            FirebaseMessaging.getInstance().subscribeToTopic("guess-shoot");
        } else {
            FirebaseMessaging.getInstance().unsubscribeFromTopic("guess-shoot");
        }

        getSettings();
    }

    public boolean valid(){
        BillingProcessor bp = new BillingProcessor(this, "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAhwFoP6qpluB6FWAojAjx8ZY4vMnI5MSnvoUUwGMwcls4GeyPO2+qbeRKlS/5BFEHupY/j/v9ldxaW9t370Y/pEYP51CrjQYo81DcaZEDleL/TzpOqWuX6zWMze1sTuWY4FfvFSUPnBJCunhnzBdvufEVdZGX6QBLbGUCn9IYAqvKGTwct45FnJcQ658PhiqA7uYZeV/UepMnpmpv2T7vDxWBIo4lK/WTmv8UA8bdtq6QqyDwNXbl1zUyb4Mx4a9qn8dWU2Q9q7dsQncLW9XIO+6EbM8UlAZoEAb9PvJ2vp4gM2hkrjS3way0tqKudYYu3jEkhVbODrWFr6s5qVsonwIDAQAB", LoginActivity.subscribe_id, new BillingProcessor.IBillingHandler() {
            @Override
            public void onProductPurchased(String productId, TransactionDetails details) {
                Log.e("BillingProcessor", "onProductPurchased");
            }


            @Override
            public void onPurchaseHistoryRestored() {
                Log.e("BillingProcessor", "onPurchaseHistoryRestored");

            }

            @Override
            public void onBillingError(int errorCode, Throwable error) {
                Log.e("BillingProcessor", "onBillingError");
            }

            @Override
            public void onBillingInitialized() {
                Log.e("BillingProcessor", "onBillingInitialized");
            }
        });
        bp.initialize();

        return bp.isPurchased(LoginActivity.subscribe_id) ;
    }

    public void getSettings() {


        final RequestQueue queue = Volley.newRequestQueue(SplashActivity.this);
        // Tag used to cancel the request
        String url = ApiService.getSetting;


        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject responseBody) {
                        // pDialog.hide();

                        try {
                            ApiService.loading(SplashActivity.this, false);


                            String status = responseBody.getString("status");

//                            String message = responseBody.getString("message");
                            String code = responseBody.getString("code");


                            if (status.equals("true")) {
                                String settings = responseBody.getString("settings");
                                Hawk.put("Settings", settings);


                                handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    private boolean isNetworkConnected() {
                                        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

                                        return cm.getActiveNetworkInfo() != null;
                                    }

                                    @Override
                                    public void run() {

                                        if(!valid()){
                                            Hawk.put("status", false);
                                        }

                                        if (Hawk.contains("lang")) {

                                            if (Hawk.contains("User") && !Hawk.get("User").toString().equals("")) {

                                                isUserHasSubscription(SplashActivity.this, SplashActivity.this::pushStatus);
                                                chang_lang(Hawk.get("lang"), MainActivity.class);
                                            } else {
                                                chang_lang(Hawk.get("lang"), LoginActivity.class);

                                            }
                                        } else {
                                            Intent intent = new Intent(SplashActivity.this, SelectLanguageActivity.class);
                                            startActivity(intent);
                                            finish();
                                        }

                                    }
                                }, 2000);


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

                ApiService.loading(SplashActivity.this, false);
                showErrorDialog();
//                webServise.loading(SplashScreenActivity.this, false);

                ApiService.ErrorResponse(SplashActivity.this, volleyError);
            }
        }) {

            /**
             * Passing some request headers
             * */


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
    public void pushStatus(boolean isActive,String orderId) {
        final RequestQueue queue = Volley.newRequestQueue(SplashActivity.this);
        // Tag used to cancel the request
        String url = ApiService.checkPaymentStatus;
        JSONObject jsonObject = new JSONObject() ;
        Log.e("payment_jsonSplash" ,isActive+"");
        Log.e("payment_jsonOrderId" ,orderId+"  kugufuyf");
        try {
            jsonObject.put("payment_status",isActive+"") ;
            jsonObject.put("type","android") ;
            jsonObject.put("gpa",orderId) ;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST, url, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject responseBody) {
                        // pDialog.hide();

                        Log.e("payment_responseBody" ,responseBody.toString()) ;
                        try {
                            ApiService.loading(SplashActivity.this, false);
                            String status = responseBody.getString("status");

//                            String message = responseBody.getString("message");
                              String code = responseBody.getString("code");


                            if (status.equals("true")) {


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

                ApiService.loading(SplashActivity.this, false);
                showErrorDialog();
//                webServise.loading(SplashScreenActivity.this, false);

                ApiService.ErrorResponse(SplashActivity.this, volleyError);
            }
        }) {
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
    private void showErrorDialog() {
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(SplashActivity.this)
                    .setTitle(getString(R.string.network_connection_error))
                    .setMessage("")
                    .setCancelable(false)
                    .setView(R.layout.item_connection_error)
                    .setPositiveButton(R.string.retry, (dialog, which) -> {
                        getSettings();
                    });
            builder.create().show();
        }catch (Exception exception){
            exception.printStackTrace();
        }

    }


    public void chang_lang(String lang, Class<?> cls) {
        Resources res = getResources();
        // Change locale settings in the app.
        DisplayMetrics dm = res.getDisplayMetrics();
        android.content.res.Configuration conf = res.getConfiguration();
        conf.setLocale(new Locale(lang)); // API 17+ only.
        // Use conf.locale = new Locale(...) if targeting lower versions
        res.updateConfiguration(conf, dm);
        Intent intent = new Intent(SplashActivity.this, cls);
        startActivity(intent);

        finish();

    }

}