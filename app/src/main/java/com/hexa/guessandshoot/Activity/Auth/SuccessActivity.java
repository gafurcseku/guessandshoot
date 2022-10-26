package com.hexa.guessandshoot.Activity.Auth;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.billingclient.api.AcknowledgePurchaseParams;
import com.android.billingclient.api.AcknowledgePurchaseResponseListener;
import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.BillingClientStateListener;
import com.android.billingclient.api.BillingFlowParams;
import com.android.billingclient.api.BillingResult;
import com.android.billingclient.api.ConsumeParams;
import com.android.billingclient.api.ConsumeResponseListener;
import com.android.billingclient.api.Purchase;
import com.android.billingclient.api.PurchasesResponseListener;
import com.android.billingclient.api.PurchasesUpdatedListener;
import com.android.billingclient.api.QueryProductDetailsParams;
import com.android.billingclient.api.QueryPurchasesParams;
import com.android.billingclient.api.SkuDetails;
import com.android.billingclient.api.SkuDetailsParams;
import com.android.billingclient.api.SkuDetailsResponseListener;
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
import com.google.firebase.crashlytics.internal.model.ImmutableList;
import com.google.gson.Gson;
import com.hexa.guessandshoot.R;
import com.hexa.guessandshoot.Settings.ApiService;
import com.hexa.guessandshoot.Settings.Settings;
import com.orhanobut.hawk.Hawk;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.hexa.guessandshoot.Settings.ApiService.getOrderId;

public class SuccessActivity extends AppCompatActivity implements PurchasesUpdatedListener {
    ImageView arrow_back;
    Button Continue;
    String subscribe_id = "year.subscription_1";
    BillingProcessor bp;
    boolean available = false;
    TextView title;


    BillingClient billingClient ;

    int from ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password_4);
        arrow_back = findViewById(R.id.arrow_back);
        Continue = findViewById(R.id.Continue);
        title = findViewById(R.id.title);

         from = getIntent().getIntExtra("from" , 0) ;


          billingClient = BillingClient.newBuilder(SuccessActivity.this)
                .setListener(this)
                .enablePendingPurchases()
                .build();



        boolean isAvailable = BillingProcessor.isIabServiceAvailable(this);
        if (!isAvailable) {
            available = true;
        }


        Continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                billingClient.startConnection(new BillingClientStateListener() {
                    @Override
                    public void onBillingSetupFinished(BillingResult billingResult) {
                        Log.e("onBillingService" , "billingResult") ;

                        if (billingResult.getResponseCode() ==  BillingClient.BillingResponseCode.OK) {
                            Log.e("onBillingService" , "billingResultOK") ;
                            // The BillingClient is ready. You can query purchases here.
                            List<String> skuList = new ArrayList<>();
                            skuList.add("year.subscription_1");
                            SkuDetailsParams.Builder params = SkuDetailsParams.newBuilder();
                            params.setSkusList(skuList).setType(BillingClient.SkuType.SUBS);


                            billingClient.querySkuDetailsAsync(params.build(),
                                    new SkuDetailsResponseListener() {
                                        @Override
                                        public void onSkuDetailsResponse(BillingResult billingResult,
                                                                         List<SkuDetails> skuDetailsList) {
                                            BillingFlowParams billingFlowParams = BillingFlowParams.newBuilder()
                                                    .setSkuDetails(skuDetailsList.get(0))
                                                    .build();

                                            int responseCode = billingClient.launchBillingFlow(SuccessActivity.this, billingFlowParams).getResponseCode();
                                            // Process the result.
                                        }
                                    });
                        }
                    }
                    @Override
                    public void onBillingServiceDisconnected() {
                        Log.e("onBillingService" , "onBillingServiceDisconnected") ;
                        // Try to restart the connection on the next request to
                        // Google Play by calling the startConnection() method.
                    }
                });
               // setBp();
            }
        });


    }




//    @Override
//    void onPurchasesUpdated(BillingResult billingResult, List<Purchase> purchases) {
//        if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK
//                && purchases != null) {
//            for (Purchase purchase : purchases) {
//                handlePurchase(purchase);
//            }
//        } else if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.USER_CANCELED) {
//            // Handle an error caused by a user cancelling the purchase flow.
//        } else {
//            // Handle any other error codes.
//        }
//    }
    void handlePurchase(Purchase purchase) {

        // Purchase retrieved from BillingClient#queryPurchases or your PurchasesUpdatedListener.


        // Verify the purchase.
        // Ensure entitlement was not already granted for this purchaseToken.
        // Grant entitlement to the user.
        checkPayment(purchase.getOriginalJson(),purchase.getPurchaseToken() ,purchase.getSignature());
        ConsumeParams consumeParams = ConsumeParams.newBuilder().setPurchaseToken(purchase.getPurchaseToken()).build();

        ConsumeResponseListener listener = new ConsumeResponseListener() {
            @Override
            public void onConsumeResponse(BillingResult billingResult, String purchaseToken) {
                if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK) {
                    // Handle the success of the consume operation.
                }
            }
        };

        billingClient.consumeAsync(consumeParams, listener);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (!bp.handleActivityResult(requestCode, resultCode, data)) {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    public void setBp() {

        boolean subscribe = bp.subscribe(this, subscribe_id);
        boolean Purchase = bp.consumePurchase(subscribe_id);

        Log.e("subscribe", subscribe + " " + Purchase);
    }
//    @Override
//    public void onProductPurchased(String productId,TransactionDetails details) {
//
//        Log.e("details",details.purchaseInfo.responseData);
//        Log.e("details",details.purchaseInfo.purchaseData.developerPayload);
//       //  Toast.makeText(this, details.purchaseInfo.purchaseData.purchaseState, Toast.LENGTH_SHORT).show();
//       // Toast.makeText(this, details.purchaseInfo.responseData, Toast.LENGTH_SHORT).show();
//        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        startActivity(intent);
//        finishAffinity();
//    }
//
//    @Override
//    public void onPurchaseHistoryRestored() {
//
//    }
//
//    @Override
//    public void onBillingError(int errorCode, Throwable error) {
//
//    }
//
//    @Override
//    public void onBillingInitialized() {
//
//    }

    public void checkPayment(String payment_json, String payment_token, String signature) {

        ApiService.loading(SuccessActivity.this, true);

        final RequestQueue queue = Volley.newRequestQueue(SuccessActivity.this);
        // Tag used to cancel the request
        String url = ApiService.checkPaymentNew1;

        Log.e("payment_json", payment_json);
        JSONObject object = new JSONObject();
        Log.e("payment_json" ,payment_json) ;
        try {
            object.put("payment_json", payment_json);
            object.put("payment_token", payment_token);
            object.put("signature", signature);
            object.put("gpa", getOrderId(payment_json));
            object.put("type", "android");

        } catch (Exception e) {
            e.printStackTrace();
        }

        Log.e("object12142",object.toString()+"");

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST, url, object,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject responseBody) {
                        // pDialog.hide();
                        Log.e("responseBody", responseBody.toString());
                        ApiService.loading(SuccessActivity.this, false);


                        try {

                            String status = responseBody.getString("status");

                            String message = responseBody.getString("message");
                            // String code = responseBody.getString("code");


                            if (status.equals("true")) {
                                finish();
                                Hawk.put("status", true);

//                                if (from==0){
//                                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
//                                    Hawk.put("User","");
//                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
//                                    startActivity(intent);
//                                    finishAffinity();
//                                }else {
//                                    finish();
//                                }

                            } else {
                                Settings.alertDialog(SuccessActivity.this, message);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError volleyError) {
////
//              webServise.loading(SplashScreenActivity.this, false);
                ApiService.loading(SuccessActivity.this, false);

                ApiService.ErrorResponse(SuccessActivity.this, volleyError);
            }
        }) {

            /**
             * Passing some request headers
             */

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
    public void onPurchasesUpdated(@NonNull BillingResult billingResult, @Nullable List<Purchase> purchases) {
        Log.e("onBillingService" , "purchases") ;

        if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK && purchases != null) {
            for (Purchase purchase : purchases) {
                handlePurchase(purchase);

                AcknowledgePurchaseParams acknowledgePurchaseParams =
                        AcknowledgePurchaseParams.newBuilder()
                                .setPurchaseToken(purchase.getPurchaseToken())
                                .build();

               // acknowledgePurchaseParams.getDeveloperPayload();

                AcknowledgePurchaseResponseListener acknowledgePurchaseResponseListener = new AcknowledgePurchaseResponseListener() {
                    @Override
                    public void onAcknowledgePurchaseResponse(BillingResult billingResult) {
                        BillingResult billingResult1 = billingResult;

                        Log.e("billingClient" , billingResult1.getDebugMessage()) ;
                    }

                };

                billingClient.acknowledgePurchase(acknowledgePurchaseParams, acknowledgePurchaseResponseListener);

            }
        } else if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.USER_CANCELED) {
            // Handle an error caused by a user cancelling the purchase flow.
        } else {
            // Handle any other error codes.
        }
    }
}
