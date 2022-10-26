package com.hexa.guessandshoot;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.hexa.guessandshoot.R;
import com.hexa.guessandshoot.Activity.Payment_Options;


public class Paypal_Info extends AppCompatActivity {
    Button submit, confirm;
    ImageView arrow_back;

    FrameLayout close_btn;
    TextView text_mas, text_mass, text_money_will_be_send;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paypal__info);
        arrow_back = findViewById(R.id.arrow_back);
        arrow_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Paypal_Info.this, Neteller_Info.class);
                startActivity(intent);
            }
        });


        submit =findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show_Book();

            }
        });

    }

    @SuppressLint("ResourceType")
    public void show_Book() {
        final AlertDialog alertDialog;

        LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View popupView = layoutInflater.inflate(R.layout.item_dialog_1, null);

        text_mas = popupView.findViewById(R.id.text_mas);
        text_mass = popupView.findViewById(R.id.text_mass);
        text_money_will_be_send = popupView.findViewById(R.id.text_money_will_be_send);

        confirm=popupView.findViewById(R.id.confirm);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Paypal_Info.this, Payment_Options.class);
                startActivity(i);
            }
        });
//        text_mas.setText();
//        text_mass.setText(R.id.text_mass);
//        text_money_will_be_send.setText(R.id.text_money_will_be_send);


        final AlertDialog.Builder builder = new AlertDialog.Builder(Paypal_Info.this);

        builder.setView(popupView);

        alertDialog = builder.show();
        alertDialog.getWindow().setGravity(Gravity.BOTTOM);
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));


        close_btn=popupView.findViewById(R.id.close_btn);
        close_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                alertDialog.cancel();
           }
        });
    }
}
