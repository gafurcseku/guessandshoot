package com.hexa.guessandshoot.Activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.hexa.guessandshoot.Activity.Auth.my_account;
import com.hexa.guessandshoot.Activity.StaticePage.ContactUs;
import com.hexa.guessandshoot.R;
import com.hexa.guessandshoot.Neteller_Info;
import com.hexa.guessandshoot.Settings.ApiService;
import com.hexa.guessandshoot.Settings.Settings;
import com.orhanobut.hawk.Hawk;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

public class VisaOrPaybalInformationActivity extends AppCompatActivity {
    Button send;
    ImageView arrow_back;

    LinearLayout emaillayout;
    LinearLayout westren_layout;

    EditText EmailAddress;

    AlertDialog alertDialog;

    EditText full_name, mobile, country, city;

    TextView title, smalltitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visa__info);
        arrow_back = findViewById(R.id.arrow_back);
        emaillayout = findViewById(R.id.emaillayout);
        EmailAddress = findViewById(R.id.EmailAddress);
        westren_layout = findViewById(R.id.westren_layout);
        title = findViewById(R.id.title);
        smalltitle = findViewById(R.id.smalltitle);


        full_name = findViewById(R.id.full_name);
        mobile = findViewById(R.id.mobile);
        country = findViewById(R.id.country);
        city = findViewById(R.id.city);
        send = findViewById(R.id.send);


        arrow_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        try {

            String select = getIntent().getStringExtra("select");


            if (select.equals("paypal")) {
                emaillayout.setVisibility(View.VISIBLE);
                westren_layout.setVisibility(View.GONE);

                title.setText(getResources().getString(R.string.payPal));

                send.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (!EmailAddress.getText().toString().equals("")) {


                            try {

                                JSONObject jsonObject = new JSONObject();

                                jsonObject.put("payment_method", "1");
                                jsonObject.put("email", EmailAddress.getText().toString());
                                jsonObject.put("amount", Hawk.get("totalCash").toString());

                                show_alert(jsonObject, Hawk.get("totalCash").toString());


                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }else {
                            Toast.makeText(VisaOrPaybalInformationActivity.this, getString(R.string.enter_email), Toast.LENGTH_SHORT).show();
                        }


                    }
                });

            }
            if (select.equals("Netelller")) {
                emaillayout.setVisibility(View.VISIBLE);
                westren_layout.setVisibility(View.GONE);
                title.setText("Netelller");

                send.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (!EmailAddress.getText().toString().equals("")) {
                            try {

                                JSONObject jsonObject = new JSONObject();

                                jsonObject.put("payment_method", "2");
                                jsonObject.put("email", EmailAddress.getText().toString());
                                jsonObject.put("amount", Hawk.get("totalCash").toString());

                                show_alert(jsonObject, Hawk.get("totalCash").toString());


                            } catch (Exception e) {

                            }
                        }else {
                            Toast.makeText(VisaOrPaybalInformationActivity.this, getString(R.string.enter_email), Toast.LENGTH_SHORT).show();
                        }


                    }
                });
            }
            if (select.equals("westren")) {
                westren_layout.setVisibility(View.VISIBLE);
                emaillayout.setVisibility(View.GONE);
                title.setText(getResources().getString(R.string.western_union));

                send.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!full_name.getText().toString().equals("") |
                                !mobile.getText().toString().equals("") |

                                !country.getText().toString().equals("") |
                                !city.getText().toString().equals("")) {

                            try {

                                JSONObject jsonObject = new JSONObject();

                                jsonObject.put("payment_method", "3");
                                jsonObject.put("full_name", full_name.getText().toString());
                                jsonObject.put("mobile", mobile.getText().toString());
                                jsonObject.put("country", country.getText().toString());
                                jsonObject.put("city", city.getText().toString());
                                jsonObject.put("amount", Hawk.get("totalCash").toString());

                                show_alert(jsonObject, Hawk.get("totalCash").toString());


                            } catch (Exception e) {

                            }
                        }else {
                            Toast.makeText(VisaOrPaybalInformationActivity.this, getString(R.string.message), Toast.LENGTH_SHORT).show();
                        }


                    }
                });
            }
            if (select.equals("stc")) {
                westren_layout.setVisibility(View.VISIBLE);
                emaillayout.setVisibility(View.GONE);
                country.setVisibility(View.GONE);
                city.setVisibility(View.GONE);
                title.setText(getResources().getString(R.string.stc));

                send.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!full_name.getText().toString().equals("") |
                                !mobile.getText().toString().equals("") ) {

                            try {
                                JSONObject jsonObject = new JSONObject();

                                jsonObject.put("payment_method", "4");
                                jsonObject.put("full_name", full_name.getText().toString());
                                jsonObject.put("mobile", mobile.getText().toString());
                                jsonObject.put("amount", Hawk.get("totalCash").toString());

                                show_alert(jsonObject, Hawk.get("totalCash").toString());


                            } catch (Exception e) {

                            }
                        }else {
                            Toast.makeText(VisaOrPaybalInformationActivity.this, getString(R.string.message), Toast.LENGTH_SHORT).show();
                        }


                    }
                });
            }


        } catch (Exception e) {

        }


    }

    public void show_alert(final JSONObject jsonObject, String money) {
        try {
            LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final View popupView = layoutInflater.inflate(R.layout.item_dialog_visa, null);


            TextView text_money_will_be_send = popupView.findViewById(R.id.text_money_will_be_send);
            Button confirm = popupView.findViewById(R.id.confirm);
            text_money_will_be_send.setText(money);

            confirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    contactUs(jsonObject);

                }
            });

            FrameLayout close_btn = popupView.findViewById(R.id.close_btn);


            close_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialog.dismiss();
                }
            });


            final AlertDialog.Builder builder = new AlertDialog.Builder(VisaOrPaybalInformationActivity.this);

            builder.setView(popupView);

            alertDialog = builder.show();
            alertDialog.getWindow().setGravity(Gravity.CENTER);
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void contactUs(JSONObject jsonObject) {

        ApiService.loading(VisaOrPaybalInformationActivity.this, true);

        final RequestQueue queue = Volley.newRequestQueue(VisaOrPaybalInformationActivity.this);
        // Tag used to cancel the request
        String url = ApiService.paymentRequest;


        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST, url, jsonObject,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject responseBody) {
                        // pDialog.hide();
                        System.out.println(responseBody);
                        ApiService.loading(VisaOrPaybalInformationActivity.this, false);


                        try {

                            String status = responseBody.getString("status");

//                            String message = responseBody.getString("message");
                            String code = responseBody.getString("code");


                            if (status.equals("true")) {
                                alertDialog.dismiss();
                                Toast.makeText(VisaOrPaybalInformationActivity.this, getResources().getString(R.string.moneysent), Toast.LENGTH_LONG).show();

                                finish();

                            } else {

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

                ApiService.ErrorResponse(VisaOrPaybalInformationActivity.this, volleyError);
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

}
