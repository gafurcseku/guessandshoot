package com.hexa.guessandshoot.Settings;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.BillingClientStateListener;
import com.android.billingclient.api.BillingResult;
import com.android.billingclient.api.Purchase;
import com.android.billingclient.api.PurchasesResponseListener;
import com.android.billingclient.api.PurchasesUpdatedListener;
import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.hexa.guessandshoot.Activity.Auth.EditeMyAccount;
import com.hexa.guessandshoot.Activity.Auth.LoginActivity;
import com.hexa.guessandshoot.DCallBack;
import com.hexa.guessandshoot.Modules.Db_user;
import com.hexa.guessandshoot.Modules.League;
import com.hexa.guessandshoot.R;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.orhanobut.hawk.Hawk;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import cz.msebera.android.httpclient.Header;


public class ApiService {
    public static KProgressHUD kProgressHUD;


    public static String Domain = "https://www.guess-shoot.com/api/";

    public static String getNews = Domain + "getNews";
    public static String getWinners = Domain + "getWinners";
    public static String getMatches = Domain + "getMatches";

    public static String getChampions = Domain + "getChampions";
    public static String getChampionsRanks = Domain + "getChampionsRanks";
    public static String getChampionsGoalkeepers = Domain + "getChampionsGoalkeepers";
    public static String getTopScorer = Domain + "getChampionsPlayers";

    public static String getSetting = Domain + "getSetting";
    public static String checkPaymentStatus = Domain + "checkPaymentStatus";
    public static String pages1 = Domain + "pages/1";
    public static String pages2 = Domain + "pages/2";
    public static String pages3 = Domain + "pages/3";
    public static String getRanking = Domain + "getRanking";
    public static String getMonthlyRanking = Domain + "getMonthlyRanking";
    public static String allQuestions = Domain + "allQuestions";
    public static String contactUs = Domain + "contactUs";
    public static String getLeagueDetails = Domain + "getLeagueDetails";



    public static String signUp = Domain + "signUp";
    public static String login = Domain + "login";
    public static String newLogin = Domain + "newLogin";
    public static String androidLogin = Domain + "androidLogin";
    public static String forgotPassword = Domain + "forgotPassword";
    public static String editProfile = Domain + "editProfile";
    public static String changePassword = Domain + "changePassword";

    public static String userGuess = Domain + "userGuess";
    public static String userGuessChamp = Domain + "userGuessChamp";
    public static String userGuessGaolKeeper = Domain + "userGuessGoalkeeper";
    public static String userGuessPlayer = Domain + "userGuessPlayer";
    public static String userRankChamp = Domain + "userRankChamp";

    public static String logout = Domain + "logout";

    public static String notifications = Domain + "notifications";

    public static String getPointsWalletExpections = Domain + "getPointsWalletExpections";
    public static String paymentRequest = Domain + "paymentRequest";
    public static String checkPayment = Domain + "checkPayment";
    public static String checkPaymentNew = Domain + "checkPaymentNew";
    public static String getHistory = Domain + "getHistory";
    public static String checkPaymentNew1 = Domain + "checkPaymentNew1";
    public static String createLeague = Domain + "createLeague";
    public static String editLeague = Domain + "editLeague";
    public static String getLeagues = Domain + "getLeagues";
    public static String leaveLeague = Domain + "leaveLeague";
    public static String deleteUser = Domain + "deleteUser";
    public static String getFAQ = Domain + "getFAQ";


    public static void ErrorResponse(Activity activity, VolleyError volleyError) {
        try {

            Log.e("ErrorResponse",volleyError.toString());
            NetworkResponse networkResponse = volleyError.networkResponse;
            if (networkResponse != null && networkResponse.data != null) {
                String jsonError = new String(networkResponse.data);
                JSONObject jsonObject = new JSONObject(jsonError);
                Log.e("jsonError",jsonError);
                // Print Error!
               String cod  = jsonObject.getString("code");
                try {
                    int errorCode = 10;
                    if (volleyError instanceof TimeoutError) {
                        errorCode = -7;
                    } else if (volleyError instanceof NoConnectionError) {
                        errorCode = -1;
                    } else if (volleyError instanceof AuthFailureError) {
                        errorCode = -6;
                    } else if (volleyError instanceof ServerError) {
                        errorCode = 0;
                    } else if (volleyError instanceof NetworkError) {
                        errorCode = -1;
                    } else if (volleyError instanceof ParseError) {
                        errorCode = -8;
                    }
                    if (cod.equals("401")){
                        activity.startActivity(new Intent(activity,LoginActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP));
                    }
//
//                    if (code.equals("401")){
//                        activity.startActivity(new Intent(activity, LoginActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP));
//                    }

//                    JSONObject jsonObject1 = new JSONObject(jsonError);
//                    String message = jsonObject1.getString("message");

                    //   Make_Toast(activity, "errorCode " + errorCode + "$$" + volleyError.getMessage());

                } catch (Exception e) {
                    e.printStackTrace();
                }


            }


        } catch (Exception e) {

        }
    }

    public static void Header_Async(AsyncHttpClient client) {

        client.addHeader("Accept", "application/json");
        client.addHeader("Auth-Role", "user");
        client.addHeader("Accept-Language", Hawk.get("lang").toString());
        client.addHeader("Authorization", "Bearer " + com.hexa.guessandshoot.Settings.Settings.getUser().getAccessToken());
    }

    public static HashMap<String, String> getHeader(boolean have_header) {
        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put("Accept", "application/json");
        headers.put("Content-Type", "application/json");
        try {
            headers.put("Accept-Language", Hawk.get("lang").toString());
        }catch (Exception e){

        };

        if (have_header) {
            String token = "";
            Db_user user = Settings.getUser();
            if (user != null){
                token = user.getAccessToken();
            }
            try {
                headers.put("Authorization", "Bearer " + token);
                Log.e("Authorization", token);

            } catch (Exception e) {
                e.printStackTrace();
            }


        }

        return headers;
    }


    public static void loading(Activity activity, boolean stopOrstart) {

        try {
            if (stopOrstart) {
                if (kProgressHUD!=null && kProgressHUD.isShowing()){
                    return;
                }
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            kProgressHUD = KProgressHUD.create(activity)
                                    .setStyle(KProgressHUD.Style.PIE_DETERMINATE)
                                    .setCancellable(true)
                                    .setAnimationSpeed(2)
                                    .setBackgroundColor(activity.getResources().getColor(R.color.prawn))
                                    .setDimAmount(0.5f)
                                    .show();
                        }catch (Exception exception){
                            exception.printStackTrace();
                        }

                    }
                });



            } else {
                if (kProgressHUD != null) {
                    kProgressHUD.dismiss();

                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }



    }

    public static void isUserHasSubscription(Context context, onCheck onCheck) {
        BillingClient billingClient = BillingClient.newBuilder(context).enablePendingPurchases().setListener(new PurchasesUpdatedListener() {
            @Override
            public void onPurchasesUpdated(@NonNull BillingResult billingResult, @Nullable List<Purchase> list) {

            }
        }).build();

        billingClient.startConnection(new BillingClientStateListener() {
            @Override
            public void onBillingSetupFinished(BillingResult billingResult) {
                billingClient.queryPurchasesAsync(BillingClient.SkuType.SUBS, new PurchasesResponseListener() {
                    @Override
                    public void onQueryPurchasesResponse(@NonNull BillingResult billingResult, @NonNull List<Purchase> list) {

                        Log.d("billingprocess","purchasesResult.getPurchasesList():"+list);

                        //                        //here you can pass the user to use the app because he has an active subscription
                        //                        Intent myIntent=new Intent(context,MainActivity.class);
                        //                        startActivity(myIntent);
                        boolean isPrshees = billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK && !Objects.requireNonNull(list).isEmpty();

                        onCheck.onCheck(isPrshees,isPrshees?getOrderId(list.get(0).getOriginalJson()):"");
                    }
                });

            }
            @Override
            public void onBillingServiceDisconnected() {
                onCheck.onCheck(false,"") ;
                // Try to restart the connection on the next request to
                // Google Play by calling the startConnection() method.
                Log.d("billingprocess","onBillingServiceDisconnected");
            }
        });
    }


    public static String getOrderId(String s){
        String OrderId = "" ;
        try {
            JSONObject jsonObject = new JSONObject(s) ;
            OrderId=jsonObject.getString("orderId") ;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("getOrderId",OrderId);
        return OrderId;
    }


    public static void PushRequest(Activity activity, String url, RequestParams requestParams, DCallBack dCallBack) {

        ApiService.loading(activity, true);

        AsyncHttpClient client = new AsyncHttpClient();
        ApiService.Header_Async(client);
        client.setConnectTimeout(10*1000*60);
        client.setResponseTimeout(10*1000*60);

        client.post(url, requestParams, new JsonHttpResponseHandler() {
            @Override
            public void onProgress(long bytesWritten, long totalSize) {
                super.onProgress(bytesWritten, totalSize);
                int val =(int) ((bytesWritten* 100) / totalSize);
                dCallBack.Result(val+"","",false );
                Log.e("loading",(int) ((bytesWritten* 100) / totalSize)+"");
            }

            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, JSONObject response) {
                ApiService.loading(activity, false);
                Log.e("response", response.toString());
                try {


                    String status = response.getString("status");

//                            String message = responseBody.getString("message");
                    String code = response.getString("code");


                    if (status.equals("true")) {
                        if (url.equals(ApiService.leaveLeague)){
                            Settings.setUserStatus("0");
                        }else if (url.equals(ApiService.deleteUser)){

                        }else {
                            String news = response.getString("Leagues");
                            JSONArray jsonArray = new JSONArray(news);
                            JsonParser parser = new JsonParser();
                            JsonElement mJson = parser.parse(jsonArray.getJSONObject(0).toString());
                            Gson gson = new Gson();
                            League  league = gson.fromJson(mJson, League.class);
                            Settings.setUserStatus(league.getId()+"");

                        }


                        dCallBack.Result("100","",true );
                    }else{
                        Toast.makeText(activity, response.getString("message")+"", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Log.e("Exception",e.toString());
                    try {
                        Toast.makeText(activity, response.getString("message")+"", Toast.LENGTH_SHORT).show();
                    } catch (JSONException e1) {
                        e1.printStackTrace();
                    }
                }
            }
            @Override
            public void onFinish() {
                //  startActivity(new Intent(JoinUsActivity.this,Massege_Join_UsActivity.class));
                super.onFinish();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                dCallBack.Result("","",false );
                Log.e("JSONObject11",responseString.toString());
                //GlobalSettings.loading(getActivity(), false);
            }
            @Override
            public void onUserException(Throwable error) {
                // super.onUserException(error);
                Log.d("ttt", "onFailure: jhjj" + error.getMessage());

            }
        });
    }

}
