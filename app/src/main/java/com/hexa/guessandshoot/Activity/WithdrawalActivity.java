package com.hexa.guessandshoot.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.hexa.guessandshoot.R;

public class WithdrawalActivity extends AppCompatActivity {
    Button submit;
    ImageView arrow_back;


    LinearLayout paypal;
    LinearLayout netwer;
    LinearLayout westren;
    LinearLayout stc;
    RadioButton radioButtonPayPal;
    RadioButton radioButtonnnetwer;
    RadioButton radioButtonWestar;
    RadioButton radioButtonstc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdrawal);
        submit = findViewById(R.id.submit);
        arrow_back = findViewById(R.id.arrow_back);


        paypal = findViewById(R.id.paypal);
        netwer = findViewById(R.id.netwer);
        westren = findViewById(R.id.westren);
        stc = findViewById(R.id.stc);

        radioButtonPayPal = findViewById(R.id.radioButtonPayPal);
        radioButtonnnetwer = findViewById(R.id.radioButtonnnetwer);
        radioButtonWestar = findViewById(R.id.radioButtonWestar);
        radioButtonstc = findViewById(R.id.radioButtonstc);


        arrow_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        paypal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radioButtonPayPal.setChecked(true);
                radioButtonnnetwer.setChecked(false);
                radioButtonWestar.setChecked(false);
                radioButtonstc.setChecked(false);
            }
        });
        netwer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radioButtonPayPal.setChecked(false);
                radioButtonnnetwer.setChecked(true);
                radioButtonWestar.setChecked(false);
                radioButtonstc.setChecked(false);
            }
        });
        westren.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radioButtonPayPal.setChecked(false);
                radioButtonnnetwer.setChecked(false);
                radioButtonWestar.setChecked(true);
                radioButtonstc.setChecked(false);
            }
        });
        stc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radioButtonPayPal.setChecked(false);
                radioButtonnnetwer.setChecked(false);
                radioButtonWestar.setChecked(false);
                radioButtonstc.setChecked(true);
            }
        });

        radioButtonPayPal.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    radioButtonPayPal.setChecked(true);
                    radioButtonnnetwer.setChecked(false);
                    radioButtonWestar.setChecked(false);
                    radioButtonstc.setChecked(false);
                }
            }
        });
        radioButtonnnetwer.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    radioButtonPayPal.setChecked(false);
                    radioButtonnnetwer.setChecked(true);
                    radioButtonWestar.setChecked(false);
                    radioButtonstc.setChecked(false);
                }
            }
        });
        radioButtonWestar.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    radioButtonPayPal.setChecked(false);
                    radioButtonnnetwer.setChecked(false);
                    radioButtonWestar.setChecked(true);
                    radioButtonstc.setChecked(false);
                }
            }
        });

        radioButtonstc.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    radioButtonPayPal.setChecked(false);
                    radioButtonnnetwer.setChecked(false);
                    radioButtonWestar.setChecked(false);
                    radioButtonstc.setChecked(true);
                }
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (radioButtonPayPal.isChecked()|radioButtonnnetwer.isChecked()|radioButtonWestar.isChecked()|radioButtonstc.isChecked()){
                    if (radioButtonPayPal.isChecked()) {
                        Intent intent = new Intent(WithdrawalActivity.this, VisaOrPaybalInformationActivity.class);
                        intent.putExtra("select", "paypal");
                        startActivity(intent);
                    }
                    if (radioButtonnnetwer.isChecked()) {
                        Intent intent = new Intent(WithdrawalActivity.this, VisaOrPaybalInformationActivity.class);
                        intent.putExtra("select", "Netelller");
                        startActivity(intent);
                    }
                    if (radioButtonWestar.isChecked()) {
                        Intent intent = new Intent(WithdrawalActivity.this, VisaOrPaybalInformationActivity.class);
                        intent.putExtra("select", "westren");
                        startActivity(intent);
                    }
                    if (radioButtonstc.isChecked()) {
                        Intent intent = new Intent(WithdrawalActivity.this, VisaOrPaybalInformationActivity.class);
                        intent.putExtra("select", "stc");
                        startActivity(intent);
                    }
                }else {
                    Toast.makeText(WithdrawalActivity.this, getString(R.string.payment_message), Toast.LENGTH_SHORT).show();
                }
              

            }
        });

    }
}
