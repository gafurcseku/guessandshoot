package com.hexa.guessandshoot.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.hexa.guessandshoot.R;

public class Verify extends AppCompatActivity {
    Button submit;
    ImageView arrow_back;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify);
        arrow_back = findViewById(R.id.arrow_back);

        submit=findViewById(R.id.submit);



        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Verify.this, com.hexa.guessandshoot.Activity.Verify_2.class);
                startActivity(intent);
            }
        });





    }
}
