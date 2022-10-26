package com.hexa.guessandshoot.Activity.Auth;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


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
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.wallet.AutoResolveHelper;
import com.google.android.gms.wallet.IsReadyToPayRequest;
import com.google.android.gms.wallet.PaymentData;
import com.google.android.gms.wallet.PaymentDataRequest;
import com.google.android.gms.wallet.PaymentsClient;
import com.google.firebase.iid.FirebaseInstanceId;
import com.hexa.guessandshoot.Activity.MainActivity;
import com.hexa.guessandshoot.Activity.SharedPrefs;
import com.hexa.guessandshoot.R;
import com.hexa.guessandshoot.Settings.ApiService;
import com.hexa.guessandshoot.Settings.PaymentsUtil;
import com.hexa.guessandshoot.Settings.Settings;
import com.orhanobut.hawk.Hawk;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class SignUpActivity extends AppCompatActivity{
    Button sign_up;
    Button sign_in;
    ImageView arrow_back;
    EditText ed_name, ed_email, ed_mobile, ed_password, ed_ConfirmPassword;
    CheckBox checkAgree;
     PaymentsClient mPaymentsClient;

     boolean available =false ;
    BillingProcessor bp;
    boolean isClick = true ;
    private static final int LOAD_PAYMENT_DATA_REQUEST_CODE = 991;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_sign_up);
        arrow_back = findViewById(R.id.arrow_back);
        ed_name = findViewById(R.id.Name);
        ed_email = findViewById(R.id.EmailAddress);
        ed_mobile = findViewById(R.id.MobileNumber);
        ed_password = findViewById(R.id.password);
        ed_ConfirmPassword = findViewById(R.id.ConfirmPassword);
        checkAgree = findViewById(R.id.checkAgree);

        sign_up = findViewById(R.id.sign_up);
        sign_in = findViewById(R.id.sign_in);

        sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });


       // possiblyShowGooglePayButton();
        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isClick){
                    isClick = false ;
                    valid();
                }



               // if (available){

//                }else {
//                    Toast.makeText(SignUpActivity.this, "googlepay_status_unavailable", Toast.LENGTH_SHORT).show();
//                }

            }
        });





    }
    String user_name ;
    String email ;
    String mobile;
    String  password;
    String  ConfirmPassword ;
    public void valid() {

        String user_name = ed_name.getText().toString();
        String email = ed_email.getText().toString();
        String mobile = ed_mobile.getText().toString();
        String  password = ed_password.getText().toString();
        String  ConfirmPassword = ed_ConfirmPassword.getText().toString();



        if (user_name == null || user_name.isEmpty()) {
            isClick = true ;
            Settings.alertDialog(SignUpActivity.this,getResources().getString(R.string.enter_name));
        } else if (user_name.length()<4||user_name.length()>15) {
            isClick = true ;
            Settings.alertDialog(SignUpActivity.this,getResources().getString(R.string.message_error));
        }else if (email == null || email.isEmpty()) {
            Settings.alertDialog(SignUpActivity.this,getResources().getString(R.string.enter_email));
            isClick = true ;
        }  else if (!Settings.emailValidator(email)) {
            Settings.alertDialog(SignUpActivity.this,getResources().getString(R.string.invalid_email_address));
            isClick = true ;
        }else if (mobile == null || mobile.isEmpty()) {
            Settings.alertDialog(SignUpActivity.this,getResources().getString(R.string.enter_phone_number));
            isClick = true ;
        } else if (mobile.length()<8 || mobile.length()>12) {
            Settings.alertDialog(SignUpActivity.this,getResources().getString(R.string.Phone_number_must));
            isClick = true ;
        }  else if (password == null || password.isEmpty()) {
            Settings.alertDialog(SignUpActivity.this,getResources().getString(R.string.enter_password));
            isClick = true ;
        } else if (ConfirmPassword == null || ConfirmPassword.isEmpty()) {
            Settings.alertDialog(SignUpActivity.this,getResources().getString(R.string.enter_password));
            isClick = true ;
        }  else if (password.length()<6) {
            Settings.alertDialog(SignUpActivity.this,getResources().getString(R.string.message_password));
            isClick = true ;
        } else if (ConfirmPassword.length()<6) {
            Settings.alertDialog(SignUpActivity.this,getResources().getString(R.string.message_password));
            isClick = true ;
        }else if (!ConfirmPassword.equals(password)) {
            Settings.alertDialog(SignUpActivity.this,getResources().getString(R.string.Passwords_do_not_match));
            isClick = true ;
        }else if (!checkAgree.isChecked()) {
            Settings.alertDialog(SignUpActivity.this,getResources().getString(R.string.Please_agree));
            isClick = true ;
        } else {

            signUp(user_name,email,mobile,password,ConfirmPassword,"");


            //pay();
          //  requestPayment();
        }

    }

//    public void requestPayment() {
//        // Disables the button to prevent multiple clicks.
//        sign_up.setClickable(false);
//
//        // The price provided to the API should include taxes and shipping.
//        // This price is not displayed to the user.
//        String price ="50";
//
//        // TransactionInfo transaction = PaymentsUtil.createTransaction(price);
//        Optional<JSONObject> paymentDataRequestJson = PaymentsUtil.getPaymentDataRequest(price);
//        if (!paymentDataRequestJson.isPresent()) {
//            return;
//        }
//        PaymentDataRequest request = PaymentDataRequest.fromJson(paymentDataRequestJson.get().toString());
//
//        // Since loadPaymentData may show the UI asking the user to select a payment method, we use
//        // AutoResolveHelper to wait for the user interacting with it. Once completed,
//        // onActivityResult will be called with the result.
//        if (request != null) {
//            AutoResolveHelper.resolveTask(mPaymentsClient.loadPaymentData(request), this, LOAD_PAYMENT_DATA_REQUEST_CODE);
//        }
//    }


//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        switch (requestCode) {
//            // value passed in AutoResolveHelper
//            case LOAD_PAYMENT_DATA_REQUEST_CODE:
//                switch (resultCode) {
//                    case Activity.RESULT_OK:
//                        PaymentData paymentData = PaymentData.getFromIntent(data);
//                        handlePaymentSuccess(paymentData);
//                        break;
//                    case Activity.RESULT_CANCELED:
//                        // Nothing to here normally - the user simply cancelled without selecting a
//                        // payment method.
//                        break;
//                    case AutoResolveHelper.RESULT_ERROR:
//                        Status status = AutoResolveHelper.getStatusFromIntent(data);
//                        handleError(status.getStatusCode());
//                        break;
//                    default:
//                        // Do nothing.
//                }
//
//                // Re-enables the Google Pay payment button.
//                sign_up.setClickable(true);
//                break;
//        }
//
//    }
    private void handlePaymentSuccess(PaymentData paymentData) {
        String paymentInformation = paymentData.toJson();

        // Token will be null if PaymentDataRequest was not constructed using fromJson(String).
        if (paymentInformation == null) {
            return;
        }
        JSONObject paymentMethodData;

        try {
            paymentMethodData = new JSONObject(paymentInformation).getJSONObject("paymentMethodData");
            // If the gateway is set to "example", no payment information is returned - instead, the
            // token will only consist of "examplePaymentMethodToken".
            if (paymentMethodData
                    .getJSONObject("tokenizationData")
                    .getString("type")
                    .equals("PAYMENT_GATEWAY")
                    && paymentMethodData
                    .getJSONObject("tokenizationData")
                    .getString("token")
                    .equals("examplePaymentMethodToken")) {
                AlertDialog alertDialog =
                        new AlertDialog.Builder(this)
                                .setTitle("Warning")
                                .setMessage(
                                        "Gateway name set to \"example\" - please modify "
                                                + "Constants.java and replace it with your own gateway.")
                                .setPositiveButton("OK", null)
                                .create();
                alertDialog.show();
            }

            String billingName =
                    paymentMethodData.getJSONObject("info").getJSONObject("billingAddress").getString("name");
            Log.d("BillingName", billingName);
            Toast.makeText(this, "payments_show_name", Toast.LENGTH_LONG)
                    .show();

            // Logging token string.
            Log.d("GooglePaymentToken", paymentMethodData.getJSONObject("tokenizationData").getString("token"));
            String token = paymentMethodData.getJSONObject("tokenizationData").getString("token") ;


        } catch (JSONException e) {
            Log.e("handlePaymentSuccess", "Error: " + e.toString());
            return;
        }
    }

    /**
     * At this stage, the user has already seen a popup informing them an error occurred. Normally,
     * only logging is required.
     *
     * @param statusCode will hold the value of any constant from CommonStatusCode or one of the
     *     WalletConstants.ERROR_CODE_* constants.
     * @see <a
     *     href="https://developers.google.com/android/reference/com/google/android/gms/wallet/WalletConstants#constant-summary">
     *     Wallet Constants Library</a>
     */
    private void handleError(int statusCode) {
        Log.w("loadPaymentData failed", String.format("Error code: %d", statusCode));
    }
//    private void possiblyShowGooglePayButton() {
//        final Optional<JSONObject> isReadyToPayJson = PaymentsUtil.getIsReadyToPayRequest();
//        if (!isReadyToPayJson.isPresent()) {
//            return;
//        }
//        IsReadyToPayRequest request = IsReadyToPayRequest.fromJson(isReadyToPayJson.get().toString());
//        if (request == null) {
//            return;
//        }
//
//        // The call to isReadyToPay is asynchronous and returns a Task. We need to provide an
//        // OnCompleteListener to be triggered when the result of the call is known.
//        Task<Boolean> task = mPaymentsClient.isReadyToPay(request);
//        task.addOnCompleteListener(this,
//                new OnCompleteListener<Boolean>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Boolean> task) {
//                        if (task.isSuccessful()) {
//                            setGooglePayAvailable(task.getResult());
//                        } else {
//                            Log.w("isReadyToPay failed", task.getException());
//                        }
//                    }
//                });
//    }

    private void setGooglePayAvailable(boolean available) {
        this.available= available;
//        if (available) {
//            sign_up.setVisibility(View.GONE);
//            mGooglePayButton.setVisibility(View.VISIBLE);
//        } else {
//            mGooglePayStatusText.setText("googlepay_status_unavailable");
//        }
    }
    public void signUp(String name, String email, String mobile, String password, String ConfirmPassword,String token) {

        ApiService.loading(SignUpActivity.this, true);

        final RequestQueue queue = Volley.newRequestQueue(SignUpActivity.this);
        // Tag used to cancel the request
        String url = ApiService.signUp;

        JSONObject object = new JSONObject();

        try {
            object.put("name", name);
            object.put("email", email);
            object.put("mobile", mobile);
            object.put("password", password);
            object.put("payment_token", token);

            object.put("ConfirmPassword", ConfirmPassword);
            object.put("fcm_token", FirebaseInstanceId.getInstance().getToken());
            object.put("device_type", "android");
        } catch (Exception e) {

        }
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST, url, object,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject responseBody) {
                        // pDialog.hide();
                        isClick = true ;
                        System.out.println(responseBody);
                        ApiService.loading(SignUpActivity.this, false);


                        try {

                            String status = responseBody.getString("status");

//                            String message = responseBody.getString("message");
                             String code = responseBody.getString("code");


                            if (status.equals("true")) {
                                String user = responseBody.getString("user");
                                Hawk.put("User", user);

//                                Intent intent = new Intent(getApplicationContext(), SuccessActivity.class);
//                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                                startActivity(intent);

                                goHome();

                                System.out.println(user);

                            } else {
                                String message = responseBody.getString("message");
                                Settings.alertDialog(SignUpActivity.this,message);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                isClick = true ;
////
//                webServise.loading(SplashScreenActivity.this, false);
                ApiService.loading(SignUpActivity.this, false);

                ApiService.ErrorResponse(SignUpActivity.this, volleyError);
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
        SharedPrefs.save(SignUpActivity.this, "isLogin", true);
        finishAffinity();
    }






    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (!bp.handleActivityResult(requestCode, resultCode, data)) {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onDestroy() {
        if (bp != null) {
            bp.release();
        }
        super.onDestroy();
    }
}




