package com.hexa.guessandshoot.Activity.Auth;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.BillingClientStateListener;
import com.android.billingclient.api.BillingFlowParams;
import com.android.billingclient.api.BillingResult;
import com.android.billingclient.api.Purchase;
import com.android.billingclient.api.PurchaseHistoryRecord;
import com.android.billingclient.api.PurchaseHistoryResponseListener;
import com.android.billingclient.api.PurchasesUpdatedListener;
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
import com.google.firebase.iid.FirebaseInstanceId;
import com.hexa.guessandshoot.Activity.MainActivity;
import com.hexa.guessandshoot.Activity.SharedPrefs;
import com.hexa.guessandshoot.Activity.forget_password_1;
import com.hexa.guessandshoot.R;
import com.hexa.guessandshoot.Settings.ApiService;
import com.hexa.guessandshoot.Settings.Settings;
import com.hexa.guessandshoot.Settings.onCheck;
import com.orhanobut.hawk.Hawk;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.hexa.guessandshoot.Settings.ApiService.androidLogin;
import static com.hexa.guessandshoot.Settings.ApiService.login;
import static com.hexa.guessandshoot.Settings.ApiService.newLogin;

public class LoginActivity extends AppCompatActivity implements PurchasesUpdatedListener {
    Button sign_in;
    ImageView arrow_back;
    EditText EmailAddress, password2;
    TextView visitor;
    TextView forget_password;
    Activity activity = this;
    public static String subscribe_id = "year.subscription_1";
    BillingProcessor bp;
    BillingClient billingClient ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_activity);
        sign_in = findViewById(R.id.sign_in);
        arrow_back = findViewById(R.id.arrow_back);
        EmailAddress = findViewById(R.id.EmailAddress);
        password2 = findViewById(R.id.password);
        visitor = findViewById(R.id.visitor);
        forget_password = findViewById(R.id.forget_password);

        billingClient = BillingClient.newBuilder(LoginActivity.this)
                .setListener(this)
                .enablePendingPurchases()
                .build();


        bp = new BillingProcessor(this, "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAhwFoP6qpluB6FWAojAjx8ZY4vMnI5MSnvoUUwGMwcls4GeyPO2+qbeRKlS/5BFEHupY/j/v9ldxaW9t370Y/pEYP51CrjQYo81DcaZEDleL/TzpOqWuX6zWMze1sTuWY4FfvFSUPnBJCunhnzBdvufEVdZGX6QBLbGUCn9IYAqvKGTwct45FnJcQ658PhiqA7uYZeV/UepMnpmpv2T7vDxWBIo4lK/WTmv8UA8bdtq6QqyDwNXbl1zUyb4Mx4a9qn8dWU2Q9q7dsQncLW9XIO+6EbM8UlAZoEAb9PvJ2vp4gM2hkrjS3way0tqKudYYu3jEkhVbODrWFr6s5qVsonwIDAQAB", subscribe_id, new BillingProcessor.IBillingHandler() {
            @Override
            public void onProductPurchased(String productId, TransactionDetails details) {
                Log.e("status", "onProductPurchased");
            }


            @Override
            public void onPurchaseHistoryRestored() {
                Log.e("status", "onPurchaseHistoryRestored");

            }

            @Override
            public void onBillingError(int errorCode, Throwable error) {
                Log.e("status", "onBillingError");
            }

            @Override
            public void onBillingInitialized() {
                Log.e("status", "onBillingInitialized");
            }
        });
        bp.initialize();


        forget_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(activity, forget_password_1.class));
            }
        });

        sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                valid();
            }
        });
        visitor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Hawk.put("User", "");
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
//                finishAffinity();

            }
        });
        forget_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, forget_password_1.class);
                startActivity(intent);
            }
        });
    }


    public void valid() {

        Log.e("getDeviceName" , getDeviceName()) ;



        String email = EmailAddress.getText().toString();
        String password = password2.getText().toString();


        if (email == null || email.isEmpty()) {
            Settings.alertDialog(activity, getString(R.string.enter_email));

        } else if (!Settings.emailValidator(email)) {
            Settings.alertDialog(activity, getString(R.string.invalid_email_address));

        } else if (password == null || password.isEmpty()) {
            Settings.alertDialog(activity, getString(R.string.enter_password));

        } else if (password.length() < 6) {
            Settings.alertDialog(activity, getString(R.string.enter_password));

        } else {
            ApiService.isUserHasSubscription(activity, new onCheck() {
                @Override
                public void onCheck(boolean isSubscribe,String orderId) {
                    // if (b){
                    Log.e("type",new Time().toString()) ;
                    Login_method(email, password ,isSubscribe,orderId);
//                    }else {
//                        showNotActive("");
//                    }

                }
            }) ;

        }

    }

    public void sgin_up(View view) {
        Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
        startActivity(intent);

//        new AlertDialog.Builder(this).setTitle(getString(R.string.message_Subscribe))
//                .setPositiveButton(getString(R.string.continues), new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
//                        startActivity(intent);
//                    }
//                }).setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.cancel();
//            }
//        }).show();
    }

    public void forget_password(View view) {

    }

    public void Login_method(String email, String password,boolean isSubscribe,String orderId) {

        ApiService.loading(LoginActivity.this, true);

        final RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
        // Tag used to cancel the request
        String url = androidLogin;

        JSONObject object = new JSONObject();
        String token = FirebaseInstanceId.getInstance().getToken();
        try {
            object.put("email", email);
            object.put("password", password);
            object.put("fcm_token", token);
            object.put("payment_status", isSubscribe);
            object.put("device_type", "android");
            object.put("device_name", getDeviceName());
            object.put("device_id", getDiviceId());
            object.put("gpa", orderId);
        } catch (Exception e) {

        }
        Log.e("object",object.toString()) ;

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST, url, object,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject responseBody) {
                        // pDialog.hide();

                        Log.e("responseBody", responseBody.toString());
                        try {
                            ApiService.loading(LoginActivity.this, false);


                            String status = responseBody.getString("status");


                            String code = responseBody.getString("code");


                            if (status.equals("true")) {
                                String user = responseBody.getString("user");
                                Hawk.put("User", user);
                                Hawk.put("status", true);
                                if (Settings.getUser().getStatus().equals("active")) {
                                    goHome();
                                }else if (!bp.isSubscribed(subscribe_id)){
                                    Hawk.put("status", false);
                                }
                                System.out.println(user);
                            } else {
                                if (code.equals("220")) {
                                    String user = responseBody.getString("user");
                                    Hawk.put("User", user);
                                    Hawk.put("status", false);
                                    goHome();
                                    //Account Not Active
                                    Log.d("TAG NOT ACTIVE", "onResponse: ");
//                                    new AlertDialog.Builder(activity).setTitle(getString(R.string.message_activated))
//                                            .setCancelable(false)
//                                            .setPositiveButton(getString(R.string.continues), new DialogInterface.OnClickListener() {
//                                                @Override
//                                                public void onClick(DialogInterface dialog, int which) {
//                                                    Intent intent = new Intent(LoginActivity.this, SuccessActivity.class);
//                                                    startActivity(intent);
//                                                }
//                                            }).setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
//                                        @Override
//                                        public void onClick(DialogInterface dialog, int which) {
//                                            dialog.cancel();
//                                            Hawk.put("User", "");
//                                        }
//                                    }).show();
                                } else {
                                    String message = responseBody.getString("message");
                                    Settings.alertDialog(activity, message);
                                }
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError volleyError) {
/////
                ApiService.loading(LoginActivity.this, false);

//                webServise.loading(SplashScreenActivity.this, false);

                ApiService.ErrorResponse(LoginActivity.this, volleyError);
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
    public void goHome(){
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        SharedPrefs.save(LoginActivity.this, "isLogin", true);
        finishAffinity();
    }



    @Override
    public void onPurchasesUpdated(@NonNull BillingResult billingResult, @Nullable List<Purchase> list) {

    }

    public String getDiviceId(){
       return android.provider.Settings.Secure.getString(getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);

    }
    public String getDeviceName() {

        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        if (model.toLowerCase().startsWith(manufacturer.toLowerCase())) {
            return capitalize(model);
        } else {
            return capitalize(manufacturer) + " " + model;
        }
    }
    private String capitalize(String s) {
        if (s == null || s.length() == 0) {
            return "";
        }
        char first = s.charAt(0);
        if (Character.isUpperCase(first)) {
            return s;
        } else {
            return Character.toUpperCase(first) + s.substring(1);
        }
    }
}
