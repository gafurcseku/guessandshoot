package com.hexa.guessandshoot.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.hexa.guessandshoot.Adapter.Adapter_Groups;
import com.hexa.guessandshoot.Modules.Db_championsGroup;
import com.hexa.guessandshoot.R;
import com.orhanobut.hawk.Hawk;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class GroupsActivity extends AppCompatActivity {
    Db_championsGroup champions ;
    RecyclerView list ;
    TextView rv_title , TV_date ;
    ImageView image , arrow_back ;
    LinearLayout ly_dialog ;
    private AdView mAdView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groups);

        mAdView =  findViewById(R.id.adView);

        if(!((Boolean) Hawk.get("status"))){
            AdRequest adRequest = new AdRequest.Builder().build();
            mAdView.loadAd(adRequest);
        }else {
            mAdView.setVisibility(View.GONE);
        }

        rv_title =findViewById(R.id.rv_title) ;
        TV_date =findViewById(R.id.TV_date) ;
        image =findViewById(R.id.image) ;
        arrow_back =findViewById(R.id.arrow_back) ;
        ly_dialog =findViewById(R.id.ly_dialog) ;

        champions = Hawk.get("object") ;
        rv_title.setText(champions.getName());
        Picasso.get().load(champions.getImage()).into(image);

        arrow_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ly_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show_Book(champions.rank_points, champions.rank_prize);
            }
        });
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat format_date = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
        try {
           Date date =  format.parse(champions.rank_end_date);
            TV_date.setText(format_date.format(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        list = findViewById(R.id.list) ;
        list.setLayoutManager(new GridLayoutManager(this , 2));
        Adapter_Groups adapter_groups = new Adapter_Groups(this , Integer.parseInt(champions.is_group));
        list.setAdapter(adapter_groups);
    }

    @SuppressLint("ResourceType")
    public void show_Book(String winner_text, String point_text) {

        final androidx.appcompat.app.AlertDialog alertDialog;
        LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View popupView = layoutInflater.inflate(R.layout.item_dialog_4, null);

        try {
            TextView winner = popupView.findViewById(R.id.winner);
            TextView point = popupView.findViewById(R.id.point);
            TextView point_team = popupView.findViewById(R.id.point_team);
            FrameLayout close_btn = popupView.findViewById(R.id.close_btn);
            ImageView img_close = popupView.findViewById(R.id.img_close);

            winner.setText(point_text);
            point.setText(winner_text);
            // point_team.setText(point_team_text);


            final androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this);

            builder.setView(popupView);

            alertDialog = builder.show();
            alertDialog.getWindow().setGravity(Gravity.CENTER);
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            img_close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialog.dismiss();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }


//        close_btn=popupView.findViewById(R.id.close_btn);
//        close_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                alertDialog.cancel();
//            }
//       });
    }
}