package com.hexa.guessandshoot.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.hexa.guessandshoot.R;
import com.hexa.guessandshoot.Activity.ActivityEditPassword;

public class forget_password_2 extends AppCompatActivity {
    Button submit;
    ImageView arrow_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forget_password_2);
        arrow_back = findViewById(R.id.arrow_back);

        submit = findViewById(R.id.submit);


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(forget_password_2.this, ActivityEditPassword.class);
                startActivity(intent);
            }
        });
    }

    public void submit(View view) {


    }
}
