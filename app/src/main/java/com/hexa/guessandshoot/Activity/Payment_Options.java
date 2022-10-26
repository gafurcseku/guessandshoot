package com.hexa.guessandshoot.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;


import com.hexa.guessandshoot.R;
import com.hexa.guessandshoot.Paypal_Info;

public class Payment_Options extends AppCompatActivity {
    ImageView arrow_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment__options);
        arrow_back = findViewById(R.id.arrow_back);
        arrow_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Payment_Options.this, Paypal_Info.class);
                startActivity(intent);
            }
        });

    }
}
