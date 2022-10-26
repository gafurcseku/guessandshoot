package com.hexa.guessandshoot.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.hexa.guessandshoot.Activity.Auth.EditeMyAccount;
import com.hexa.guessandshoot.R;
import com.hexa.guessandshoot.Fragment.FragmentExpectations;

public class ActivityEditPassword extends AppCompatActivity {
    Button submit;
    ImageView arrow_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_password);
        submit = findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityEditPassword.this, FragmentExpectations.class);
                startActivity(intent);
            }
        });
        arrow_back = findViewById(R.id.arrow_back);
        arrow_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityEditPassword.this, EditeMyAccount.class);
                startActivity(intent);
            }
        });

    }
}
