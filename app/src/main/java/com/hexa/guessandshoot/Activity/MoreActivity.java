package com.hexa.guessandshoot.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.hexa.guessandshoot.Fragment.FragmentMore;
import com.hexa.guessandshoot.R;

public class MoreActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mare);
        getSupportFragmentManager().beginTransaction().replace(R.id.frame ,new FragmentMore()).commit() ;

     }
}