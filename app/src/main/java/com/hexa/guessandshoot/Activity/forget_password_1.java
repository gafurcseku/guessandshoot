package com.hexa.guessandshoot.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
import com.hexa.guessandshoot.Activity.Auth.SuccessActivity;
import com.hexa.guessandshoot.R;
import com.hexa.guessandshoot.Settings.ApiService;
import com.hexa.guessandshoot.Settings.Settings;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

public class forget_password_1 extends AppCompatActivity {
    ImageView arrow_back;
    EditText email;
    Button submit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password_1);
        submit = findViewById(R.id.submit);
        email = findViewById(R.id.email);


        arrow_back = findViewById(R.id.arrow_back);
        arrow_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!email.getText().toString().equals("")) {
                    forgetPassword(email.getText().toString());

                }else {
                    Toast.makeText(forget_password_1.this, getString(R.string.enter_email), Toast.LENGTH_SHORT).show();
                }
                //                Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                //                startActivity(intent);
            }
        });

    }


//        Intent intent = new Intent(forget_password_1.this, com.hexa.guessandshoot.Activity.forget_password_2.class);
//        startActivity(intent);

    public void forgetPassword(String email) {

        ApiService.loading(forget_password_1.this, true);

        final RequestQueue queue = Volley.newRequestQueue(forget_password_1.this);
        // Tag used to cancel the request
        String url = ApiService.forgotPassword;

        JSONObject object = new JSONObject();

        try {
            object.put("email", email);

        } catch (Exception e) {

        }


        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST, url, object,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject responseBody) {
                        // pDialog.hide();
                        System.out.println(responseBody.toString());

                        try {
                            ApiService.loading(forget_password_1.this, false);


                            String status = responseBody.getString("status");

//                            String message = responseBody.getString("message");
                            String code = responseBody.getString("code");

                            String message = responseBody.getString("message");
                            if (status.equals("true")) {

                                finish();
                                Toast.makeText(forget_password_1.this, message, Toast.LENGTH_SHORT).show();
                            } else {

                                Settings.alertDialog(forget_password_1.this,message);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError volleyError) {
/////
                ApiService.loading(forget_password_1.this, false);
                Toast.makeText(forget_password_1.this, volleyError.getMessage(), Toast.LENGTH_LONG).show();
//                webServise.loading(SplashScreenActivity.this, false);

                ApiService.ErrorResponse(forget_password_1.this, volleyError);
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


