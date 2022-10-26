package com.hexa.guessandshoot.Activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.hexa.guessandshoot.Adapter.Adapter_Groups;
import com.hexa.guessandshoot.Adapter.ItemAdapter;
import com.hexa.guessandshoot.Fragment.top_score.TopRequestType;
import com.hexa.guessandshoot.Modules.Club;
import com.hexa.guessandshoot.Modules.Clubs;
import com.hexa.guessandshoot.Modules.Db_My_Expections_profile;

import com.hexa.guessandshoot.Modules.Db_championsGroup;
import com.hexa.guessandshoot.R;
import com.hexa.guessandshoot.Settings.ApiService;
import com.hexa.guessandshoot.Settings.Settings;
import com.orhanobut.hawk.Hawk;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

public class ArrangeChampionshipActivity extends AppCompatActivity {
    Db_championsGroup champions ;
    Spinner sp1,sp2 ,sp3 ,sp4 ;
    TextView rv_title ,TV_date ;
    ImageView image ,arrow_back;
    ArrayList<Spinner> spinners = new ArrayList<>() ;
    String charSrt ;
    int index ;
    LinearLayout ly_dialog ;
    Button send;
     static final String TAG = "ArrangeChampionship";
    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arrange_championship);

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
        send =findViewById(R.id.send) ;
        sp1 =findViewById(R.id.sp1) ;
        sp2 =findViewById(R.id.sp2) ;
        sp3 =findViewById(R.id.sp3) ;
        sp4 =findViewById(R.id.sp4) ;

        spinners.add(sp1);
        spinners.add(sp2);
        spinners.add(sp3);
        spinners.add(sp4);
        champions = Hawk.get("object") ;

        try {
             charSrt =  getIntent().getStringExtra("char") ;
             index =  getIntent().getIntExtra("index" ,0) ;
        }catch (Exception e){
            e.printStackTrace();
        }

        rv_title.setText(champions.getName());
        Picasso.get().load(champions.getImage()).into(image);


        arrow_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat format_date = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
        try {
            Log.e("rank_end_date" ,champions.rank_end_date);
            Date date =  format.parse(champions.rank_end_date);
            TV_date.setText(format_date.format(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        ItemAdapter adapter = new ItemAdapter(this ,champions.getClub() ,index+"");
        for (int i = 0; i <spinners.size() ; i++) {
            spinners.get(i).setAdapter(adapter);
            handelSp(spinners.get(i) ,i) ;
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (champions.isRanks.size() > 0){
                    for (int i = 0; i < champions.isRanks.size(); i++) {
                        if (champions.isRanks.get(i).getGroupId().equals(index+"")){
                            String ranks= champions.isRanks.get(i).getRanks();
                            String[] ids = ranks.split(",") ;
                            for (int j = 0; j <ids.length ; j++) {
                                for (int k = 0; k <adapter.asr.size() ; k++) {
                                    if (adapter.asr.get(k).club.getId().equals(Integer.parseInt(ids[j]))){
                                        spinners.get(j).setSelection(k);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        },500) ;


        ly_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show_Book(champions.rank_points, champions.rank_prize);
            }
        });

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isValid()){
                    userGuessChamp() ;
                }else {
                    Toast.makeText(ArrangeChampionshipActivity.this, getString(R.string.All_clubs_identified), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    public boolean isValid(){
        boolean isValid = true ;
        for (int i = 0; i <spinners.size() ; i++) {
            Clubs clubs = (Clubs) spinners.get(i).getSelectedItem();
            if (clubs.club.getId()<0){
                isValid = false ;
                break;
            }
        }
        return isValid ;
    }
    public String getIds(){
        StringBuilder ids = new StringBuilder();
        for (int i = 0; i <spinners.size() ; i++) {
            Clubs clubs = (Clubs) spinners.get(i).getSelectedItem();
            ids.append(clubs.club.getId()).append(",");
        }
        return ids.toString();
    }
    public void handelSp(Spinner sp ,int index){
        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (check(index)){
                }else {
                   // sp.setSelection(0);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public boolean check(int index){
        boolean isCheck =true;
        Clubs clubsCheck = (Clubs) spinners.get(index).getSelectedItem();
        for (int i = 0; i <spinners.size() ; i++) {
            if (i!=index){
               Clubs clubs = (Clubs) spinners.get(i).getSelectedItem();
               if (clubsCheck.club.getId().equals(clubs.club.getId())){
                   spinners.get(i).setSelection(0);
                   isCheck =false ;
                   break;
               }
            }
        }
        return isCheck ;
    }
    @SuppressLint("ResourceType")
    public void show_Book(String winner_text, String point_text) {

        final androidx.appcompat.app.AlertDialog alertDialog;
        LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View popupView = layoutInflater.inflate(R.layout.item_dialog_2, null);

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

    public void userGuessChamp() {
        ApiService.loading(this, true);

        final RequestQueue queue = Volley.newRequestQueue(this);
        //Tag used to cancel the request
        //Get Request Type

        String url =  ApiService.userRankChamp;

        //Prepare JSON Request
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("champion_id", champions.getId() /*"8"*/);
            jsonObject.put("group_id", index /*"1"*/);
            Log.e("ranks",getIds()) ;
            jsonObject.put("ranks", getIds());
        } catch (Exception e) {
            Log.e(TAG, "userGuessChamp: Error When Prepare request", e);
        }

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST, url, jsonObject, (Response.Listener<JSONObject>) responseBody -> {
            Log.e(TAG, responseBody.toString());

            try {
                ApiService.loading(this, false);
                String status = responseBody.getString("status");
                String message = responseBody.getString("message");

//                if (status.equals("true")) {
                    //Init New Chosen
                if(responseBody.has("champion")){
                    String champions = responseBody.getString("champion");
                    JsonParser parser = new JsonParser();
                    JsonElement mJson = parser.parse(champions);
                    Gson gson = new Gson();
                    Db_championsGroup db_champions = gson.fromJson(mJson, Db_championsGroup.class);
                    Hawk.put("object",db_champions) ;
                }
                onBackPressed();
                      // Toast.makeText(this, message, Toast.LENGTH_SHORT).show();



//                } else {
//                   // Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
//                    Settings.alertDialog(this, message);
//                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, (Response.ErrorListener) volleyError -> {
            ApiService.loading(this, false);
            ApiService.ErrorResponse(this, volleyError);
        }) {
            @Override
            public Map<String, String> getHeaders() {
                return ApiService.getHeader(true);
            }
        };
        jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(
                9000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //Send Request To Server
        queue.add(jsonObjReq);

    }
}