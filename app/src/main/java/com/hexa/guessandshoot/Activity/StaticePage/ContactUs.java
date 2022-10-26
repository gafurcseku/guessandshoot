package com.hexa.guessandshoot.Activity.StaticePage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;


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
import com.hexa.guessandshoot.Modules.Db_ContactUs;
import com.hexa.guessandshoot.Activity.MainActivity;
import com.hexa.guessandshoot.R;
import com.hexa.guessandshoot.Settings.ApiService;
import com.hexa.guessandshoot.Settings.Settings;
import com.hexa.guessandshoot.SuccessfulContactUs;
import com.orhanobut.hawk.Hawk;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

public class ContactUs extends AppCompatActivity {
    Button send;
    ImageView arrow_back;
    EditText name, email, mobile, massage;
    TextView text_email, mobile_phone, phone;
    PopupWindow popupWindow;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);
        arrow_back = findViewById(R.id.arrow_back);
        name = findViewById(R.id.name);
        send = findViewById(R.id.send);
        email = findViewById(R.id.email);
        mobile = findViewById(R.id.mobile);
        massage = findViewById(R.id.massage);
        text_email = findViewById(R.id.text_email);
        mobile_phone = findViewById(R.id.mobile_phone);
        phone = findViewById(R.id.phone);


        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String edNameln = name.getText().toString();
                String edEmailln = email.getText().toString();
                String edPhoneln = mobile.getText().toString();
                String message = massage.getText().toString();


                if (edNameln.isEmpty()) {
                    Settings.alertDialog(ContactUs.this, getString(R.string.enter_name));
                    return;
                }


                if (edEmailln.isEmpty()) {
                    Settings.alertDialog(ContactUs.this, getString(R.string.enter_email));
                    return;
                }
                if (!Settings.emailValidator(edEmailln)) {
                    Settings.alertDialog(ContactUs.this, getString(R.string.invalid_email_address));
                    return;
                }

                if (edPhoneln.isEmpty()) {
                    Settings.alertDialog(ContactUs.this, getString(R.string.enter_phone_number));
                    return;
                }

                if (edPhoneln.length() < 8 || edPhoneln.length() > 12) {
                    Settings.alertDialog(ContactUs.this, getString(R.string.Phone_number_must));
                    return;
                }

                if (message.isEmpty()) {
                    Settings.alertDialog(ContactUs.this, getString(R.string.enter_message));
                    return;
                }


                contactUs(name.getText().toString(), email.getText().toString(), mobile.getText().toString(), massage.getText().toString());

            }


        });

        arrow_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        try {
            text_email.setText((Settings.getSettings().getInfoEmail()));
            mobile_phone.setText((Settings.getSettings().getMobile()));
            phone.setText((Settings.getSettings().getPhone()));

            phone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Uri uri = Uri.parse("https://wa.me/" + Settings.getSettings().getPhone());
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
                }
            });
            text_email.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
                    emailIntent.setType("text/plain");
                    emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{Settings.getSettings().getInfoEmail()});
                    emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Hello There");



                    try {
                        startActivity(Intent.createChooser(emailIntent,
                                "Send email using..."));
                    } catch (android.content.ActivityNotFoundException ex) {
                        Toast.makeText(ContactUs.this,
                                "No email clients installed.",
                                Toast.LENGTH_SHORT).show();
                    }

                }
            });
            mobile_phone.setOnClickListener(new View.OnClickListener() {
                @TargetApi(Build.VERSION_CODES.M)
                @Override
                public void onClick(View v) {





                    if (checkSelfPermission(Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(ContactUs.this,
                                new String[]{Manifest.permission.CALL_PHONE},
                                1);
                        return;
                    }else {
                        Intent intent = new Intent(Intent.ACTION_CALL);

                        intent.setData(Uri.parse("tel:" + Settings.getSettings().getPhone()));
                        startActivity(intent);
                    }

                }
            });
            mobile_phone.setOnClickListener(new View.OnClickListener() {
                @TargetApi(Build.VERSION_CODES.M)
                @Override
                public void onClick(View v) {





                    if (checkSelfPermission(Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(ContactUs.this,
                                new String[]{Manifest.permission.CALL_PHONE},
                                1);
                        return;
                    }else {
                        Intent intent = new Intent(Intent.ACTION_CALL);

                        intent.setData(Uri.parse("tel:" + Settings.getSettings().getMobile()));
                        startActivity(intent);
                    }

                }
            });
        } catch (Exception e) {

        }


    }

    public void contactUs(String name_, String email_, String mobile_, String massage_) {

        ApiService.loading(ContactUs.this, true);

        final RequestQueue queue = Volley.newRequestQueue(ContactUs.this);
        // Tag used to cancel the request
        String url = ApiService.contactUs;

        JSONObject object = new JSONObject();

        try {
            object.put("name", name_);
            object.put("email", email_);
            object.put("mobile", mobile_);
            object.put("message", massage_);
            object.put("type", "0");


        } catch (Exception e) {

        }


        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST, url, object,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject responseBody) {
                        // pDialog.hide();
                        System.out.println(responseBody);
                        ApiService.loading(ContactUs.this, false);


                        try {

                            String status = responseBody.getString("status");

                           String validator = responseBody.getString("message");
                            String code = responseBody.getString("code");


                            if (status.equals("true")) {

                                name.setText("");
                                email.setText("");
                                mobile.setText("");
                                massage.setText("");
                                View view = getLayoutInflater().inflate(R.layout.activity_forget_password_4, null, false);


                                Button Continue = view.findViewById(R.id.Continue);
                                TextView title = view.findViewById(R.id.title);
                                title.setText(getResources().getString(R.string.ReplyThankYou));
                                Continue.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        popupWindow.dismiss();
                                        finish();
                                    }
                                });


                                popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

                                popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);


//                                Toast.makeText(ContactUs.this, getResources().getString(R.string.ThankYou), Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(ContactUs.this, validator, Toast.LENGTH_LONG).show();

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError volleyError) {
////
//                webServise.loading(SplashScreenActivity.this, false);

                ApiService.ErrorResponse(ContactUs.this, volleyError);
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
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Intent intent = new Intent(Intent.ACTION_CALL);
                    intent.setData(Uri.parse("tel:" + Settings.getSettings().getPhone()));
                    startActivity(intent);

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(ContactUs.this, "Permission denied to read your External storage", Toast.LENGTH_SHORT).show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

}