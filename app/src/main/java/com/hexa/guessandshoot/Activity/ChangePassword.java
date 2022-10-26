package com.hexa.guessandshoot.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.hexa.guessandshoot.R;
import com.hexa.guessandshoot.Settings.ApiService;
import com.hexa.guessandshoot.Settings.Settings;
import com.orhanobut.hawk.Hawk;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

public class ChangePassword extends AppCompatActivity {
    Button confirm;
    ImageView arrow_back;
    TextView password, confirm_password, confirm_new_password;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        confirm = findViewById(R.id.confirm);
        password = findViewById(R.id.password);
        arrow_back = findViewById(R.id.arrow_back);
        confirm_password = findViewById(R.id.confirm_password);
        confirm_new_password = findViewById(R.id.confirm_new_password);

        arrow_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String old_password = password.getText().toString();
                String password = confirm_password.getText().toString();
                String confirm_password = confirm_new_password.getText().toString();

                if (old_password==null || old_password.isEmpty()){
                    Settings.alertDialog(ChangePassword.this,getString(R.string.enter_old_password));
                }else if (password==null || password.isEmpty()){
                    Settings.alertDialog(ChangePassword.this,getString(R.string.enter_new_password));
                }else if (confirm_password==null || confirm_password.isEmpty()){
                    Settings.alertDialog(ChangePassword.this,getString(R.string.enter_confirm_password));
                }else if (!confirm_password.equals(password)){
                    Settings.alertDialog(ChangePassword.this,getString(R.string.message_password2));
                }else {

                    changePassword(old_password,password);

                    //changePassword(old_password,password,confirm_password);
                }





//                if ( !confirm_password.getText().toString().equals("") & !confirm_new_password.getText().toString().equals("")) {
//                    if (confirm_password.getText().toString().equals(confirm_new_password.getText().toString())) {
//
//                        Intent intent = new Intent(ChangePassword.this, com.hexa.guessandshoot.Activity.Verify.class);
//                        startActivity(intent);
//
//
//                    }}


//
            }
        });

    }

//                Intent intent = new Intent(ChangePassword.this, com.hexa.guessandshoot.Activity.Auth.EditeMyAccount.class);
//                startActivity(intent);


    public void changePassword( String Password, String confirmNewPassword) {


         ApiService.loading(ChangePassword.this, true);

        final RequestQueue queue = Volley.newRequestQueue(ChangePassword.this);
        // Tag used to cancel the request
        String url = ApiService.changePassword;

        JSONObject object = new JSONObject();

        try {
              object.put("old_password", Password);
              object.put("password", confirmNewPassword);
              object.put("confirm_password", confirmNewPassword);

        } catch (Exception e) {

        }
            JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST, url, object,
                    new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject responseBody) {
                            // pDialog.hide();
                            ApiService.loading(ChangePassword.this, false);
                            Log.e("responseBody",responseBody.toString()+"");
                            try {
                                String status = responseBody.getString("status");
//                            String message = responseBody.getString("message");
                                String code = responseBody.getString("code");
                                if (status.equals("true")) {

                                    finish();
                                  //  System.out.println(user);
                                } else {
                                    Settings.alertDialog(ChangePassword.this,responseBody.getString("message"));
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError volleyError) {
////
                    ApiService.loading(ChangePassword.this, false);
//                webServise.loading(SplashScreenActivity.this, false);

                    ApiService.ErrorResponse(ChangePassword.this, volleyError);
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


    }}