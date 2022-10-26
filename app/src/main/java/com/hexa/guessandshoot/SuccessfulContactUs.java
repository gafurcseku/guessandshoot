package com.hexa.guessandshoot;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.hexa.guessandshoot.Activity.StaticePage.ContactUs;

public class SuccessfulContactUs extends AppCompatActivity {
    Button get_started;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_successful_contact_us);
        get_started = findViewById(R.id.get_started);


        get_started.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SuccessfulContactUs.this, ContactUs.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
