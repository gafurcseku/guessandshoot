package com.hexa.guessandshoot.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.hexa.guessandshoot.R;

public class Verify_2 extends AppCompatActivity {
    Button get_started;
    ImageView arrow_back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_2);

        arrow_back = findViewById(R.id.arrow_back);

        get_started=findViewById(R.id.get_started);



        get_started.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Verify_2.this,MainActivity.class);
                startActivity(intent);
            }
        });

    }
}
