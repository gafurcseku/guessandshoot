package com.hexa.guessandshoot;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.hexa.guessandshoot.Activity.VisaOrPaybalInformationActivity;


public class Neteller_Info extends AppCompatActivity {


    Button send;
    ImageView arrow_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_neteller__info);
        arrow_back = findViewById(R.id.arrow_back);
        arrow_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Neteller_Info.this, VisaOrPaybalInformationActivity.class);
                startActivity(intent);
            }
        });



        send = findViewById(R.id.send);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Neteller_Info.this, Paypal_Info.class);
                startActivity(intent);
            }
        });

    }
}
