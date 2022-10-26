package com.hexa.guessandshoot.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.hexa.guessandshoot.Activity.Auth.LoginActivity;
import com.hexa.guessandshoot.DCallBack;
import com.hexa.guessandshoot.Modules.Db_My_Expections_profile;
import com.hexa.guessandshoot.R;
import com.hexa.guessandshoot.Modules.Db_My_Expections_My_Account;
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
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class Adapter_MyExpections_In_MyAccont extends RecyclerView.Adapter<Adapter_MyExpections_In_MyAccont.ViewHolder> {
    ArrayList<Db_My_Expections_profile> list = new ArrayList<>();
    Activity activity;
    AlertDialog alertDialog;
    PopupWindow popupWindow;

    Adapter adapter;


//  ImageView ok;
//  Button submit, confirm,close_btn,btn_Shot;
//  LinearLayout Reward;
    DCallBack dCallBack ;

    public void addItems(ArrayList<Db_My_Expections_profile> postItems) {
        int size = list.size() ;
        list.addAll(postItems);
       // list.addAll(postItems);
       //);
        if (size>0){
            notifyItemRangeChanged(size -1,list.size()-1);
        }else {
            notifyDataSetChanged();
        }

    }
    public Adapter_MyExpections_In_MyAccont( Activity activity , DCallBack dCallBack) {
        this.activity = activity;
        this.dCallBack = dCallBack;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_my_expections_on_my_account, null, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        //    holder.movie.setText(list.get(position).getMovie());


        holder.text_The_name_of_the_league.setText(list.get(position).getMatch().getChampionName());
        holder.text_Name_Of_First_Team.setText(list.get(position).getMatch().getHomeClubName());
        holder.text_Name_Of_Second_Team.setText(list.get(position).getMatch().getAwayClubName());
        holder.text_Time_The_Match.setText(list.get(position).getMatch().getMatchTime());
        holder.text_Number_Of_Targets_2.setText(list.get(position).getMatch().getAwayGoals()+"");
        holder.text_Number_Of_Targets_1.setText(list.get(position).getMatch().getHomeGoals()+"");




        holder.text_My_guess2.setText(list.get(position).getAway_goals()+"");
        holder.text_My_guess1.setText(list.get(position).getHome_goals()+"");
        holder.btn_Shot.setBackground(ContextCompat.getDrawable(activity,R.drawable.shape_btn_time_end));
        holder.btn_Shot.setText(activity.getString(R.string.SHOOT_Agein));
        holder.btn_Shot.setTextColor(Color.BLACK);


//        holder.text_Day_Mhe_Match.setText(list.get(position).get());

        Calendar calendar = Calendar.getInstance();
        try {
            String match_date_time = list.get(position).getMatch().match_date_time;
            SimpleDateFormat format_date12 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",Locale.ENGLISH);
            Date date12 = format_date12.parse(match_date_time);

            if (date12.getTime()<Settings.dateTimeGMT().getTime()){
                holder.btn_Shot.setBackground(ContextCompat.getDrawable(activity,R.drawable.shape_btn_time_end));
                holder.btn_Shot.setText(activity.getString(R.string.time_ended));
                holder.btn_Shot.setTextColor(Color.RED);
                holder.btn_Shot.setEnabled(false);
                // holder.btn_Shot.setVisibility(View.GONE);
                holder.TV_time_message.setVisibility(View.GONE);
            }else {
                holder.TV_time_message.setVisibility(View.GONE);
//                holder.btn_Shot.setBackground(ContextCompat.getDrawable(activity,R.drawable.shape_btn));
//                holder.btn_Shot.setVisibility(View.VISIBLE);
//                holder.TV_time_message.setVisibility(View.GONE);
            }
            String date22 = format_date12.format(date12);
            //Log.e("date22",date22);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        try {
            String strCurrentDate = list.get(position).getMatch().getMatchDate();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            Date newDate = format.parse(strCurrentDate);
            calendar.setTimeInMillis(newDate.getTime());
            String lang =  Hawk.get("lang");
            SimpleDateFormat format_date = new SimpleDateFormat("dd-MM-yyyy",Locale.ENGLISH);
            SimpleDateFormat format_day = new SimpleDateFormat("EEEE",lang.equals("en")?Locale.ENGLISH:new Locale("ar"));
            String date = format_date.format(newDate);
            String day = format_day.format(newDate);


         //   System.out.println("datedate" + date+" "+day);
            holder.text_Date_the_match.setText(day+" "+date);
        } catch (Exception e) {

        }
        try {
            String strCurrentDate = list.get(position).getMatch().getMatchTime();
            SimpleDateFormat format = new SimpleDateFormat("HH:mm");
            Date newDate = format.parse(strCurrentDate);

            calendar.set(Calendar.HOUR_OF_DAY,newDate.getHours());
            calendar.set(Calendar.MINUTE,newDate.getMinutes());
            calendar.set(Calendar.SECOND,0);

            format = new SimpleDateFormat("HH:mm",Locale.ENGLISH);
            String date = format.format(newDate);


//            if (calendar.getTimeInMillis()< new Date().getTime()){
//                holder.btn_Shot.setVisibility(View.GONE);
//                holder.TV_time_message.setVisibility(View.VISIBLE);
//            }else {
//                holder.btn_Shot.setVisibility(View.VISIBLE);
//                holder.TV_time_message.setVisibility(View.GONE);
//            }
            SimpleDateFormat format_date = new SimpleDateFormat("EEEE dd-MM-yyyy hh:mm",Locale.ENGLISH);

            String test_date= format_date.format(calendar.getTime());
            Log.e("test_date",test_date);
            holder.text_Time_The_Match.setText(date+ " GMT");
        } catch (Exception e) {

        }


        Picasso.get().load(list.get(position).getMatch().getClubHome().getImage()).into(holder.image_First_Team);
        Picasso.get().load(list.get(position).getMatch().getClubAway().getImage()).into(holder.image_Second_Team);


//        holder.ok = ok.findViewById(R.id.ok);
//
//       holder.ok.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                show_Book();
//
//            }
//        });


        holder.btn_Shot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show(list.get(position).getMatch().getId() + "", list.get(position).getMatch().getClubHome().getImage(), list.get(position).getMatch().getClubAway().getImage(),position);

            }
        });

        holder.Reward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show_Book(list.get(position).getMatch().getWinnerPoint(), list.get(position).getMatch().getWinnerPrize(), list.get(position).getMatch().getGuessPoint());

            }
        });


    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image_First_Team, image_Second_Team, image_medal;
        Button btn_Shot;
        LinearLayout Reward;
        FrameLayout btn_close;
        TextView text_Name_Of_First_Team, text_Number_Of_Targets_1, text_Name_Of_Second_Team, text_Number_Of_Targets_2,
                text_The_name_of_the_league, text_Day_Mhe_Match, text_Date_the_match, text_Time_The_Match, text_Reward ,TV_time_message;

        TextView text_My_guess1 ,text_My_guess2;

        FrameLayout close_btn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image_First_Team = itemView.findViewById(R.id.image_First_Team);
            image_Second_Team = itemView.findViewById(R.id.image_Second_Team);
            image_medal = itemView.findViewById(R.id.image_medal);
            btn_Shot = itemView.findViewById(R.id.btn_Shot);
            text_Name_Of_First_Team = itemView.findViewById(R.id.text_Name_Of_First_Team);
            text_Number_Of_Targets_1 = itemView.findViewById(R.id.text_Number_Of_Targets_1);
            text_Name_Of_Second_Team = itemView.findViewById(R.id.text_Name_Of_Second_Team);
            text_Number_Of_Targets_2 = itemView.findViewById(R.id.text_Number_Of_Targets_2);
            text_The_name_of_the_league = itemView.findViewById(R.id.text_The_name_of_the_league);
            text_Time_The_Match = itemView.findViewById(R.id.text_Time_The_Match);
            text_Date_the_match = itemView.findViewById(R.id.text_Date_the_match);
            text_Reward = itemView.findViewById(R.id.text_Reward);
            text_Day_Mhe_Match = itemView.findViewById(R.id.text_Day_Mhe_Match);
            Reward = itemView.findViewById(R.id.Reward);
            TV_time_message = itemView.findViewById(R.id.TV_time_message);
            text_My_guess1 = itemView.findViewById(R.id.text_My_guess1);
            text_My_guess2 = itemView.findViewById(R.id.text_My_guess2);
//            image_account=itemView.findViewById(R.id.image_account);
        }
    }
    NumberPicker.OnValueChangeListener onValueChangeListener =
            new NumberPicker.OnValueChangeListener(){
                @Override
                public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                    Toast.makeText(activity,
                            "selected number "+numberPicker.getValue(), Toast.LENGTH_SHORT);
                }
            };

    public void show(final String id, String image1, String image2,int postion) {


        LayoutInflater layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View popupView = layoutInflater.inflate(R.layout.item_dialog_3, null);

//        final Button btn_ok =  popupView.findViewById(R.id.btn_ok);
        //  RecyclerView recycler_dialog_1  =  popupView.findViewById(R.id.recycler_dialog_1);
        RecyclerView recycler_dialog_1 = popupView.findViewById(R.id.recycler_dialog_1);
        RecyclerView recycler_dialog_2 = popupView.findViewById(R.id.recycler_dialog_2);
        ImageView ok_btn = popupView.findViewById(R.id.ok_btn);
        final NumberPicker Picker1 = popupView.findViewById(R.id.Picker1);
        final NumberPicker Picker2 = popupView.findViewById(R.id.Picker2);

        ImageView team_1 = popupView.findViewById(R.id.team_1);
        ImageView team_2 = popupView.findViewById(R.id.team_2);
        ImageView img_close = popupView.findViewById(R.id.img_close);

//        final AlertDialog.Builder builder = new AlertDialog.Builder(activity);
//        builder.setView(popupView);
//
//        alertDialog = builder.show();
        //      alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
//


        recycler_dialog_1.setLayoutManager(new GridLayoutManager(activity, 1));
        recycler_dialog_2.setLayoutManager(new GridLayoutManager(activity, 1));


        Picasso.get().load(image1 + "").into(team_1);
        Picasso.get().load(image2 + "").into(team_2);


        ArrayList<String> strings = new ArrayList<>();
        strings.add("1");
        strings.add("2");
        strings.add("3");
        strings.add("4");
        strings.add("5");
        strings.add("6");
        strings.add("7");
        strings.add("8");
        strings.add("9");
        strings.add("10");

        recycler_dialog_1.setAdapter(new Adapter_list_number_1(strings, activity));
        recycler_dialog_2.setAdapter(new Adapter_list_number_2(strings, activity));

        final String[] values= {"0","1", "2" , "3", "4" , "5", "6" , "7", "8" , "9"};


        Picker1.setMinValue(0);
        Picker1.setDisplayedValues(values);
        Picker1.setMaxValue(values.length-1);

//        Picker1.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
//            @Override
//            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
//                val_1 = newVal;
//                String text = "Changed from " + oldVal + " to " + newVal;
//                Toast.makeText(activity, text, Toast.LENGTH_SHORT).show();
//            }
//        });
        Picker2.setMinValue(0);
        Picker2.setDisplayedValues(values);
        Picker2.setMaxValue(values.length-1);

        Picker1.setValue(Integer.parseInt(list.get(postion).getHome_goals()));
        Picker2.setValue(Integer.parseInt(list.get(postion).getAway_goals()));
//        Picker2.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
//            @Override
//            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
//                val_2 = newVal;
//
//                String text = "Changed from " + oldVal + " to " + newVal;
//                Toast.makeText(activity, text, Toast.LENGTH_SHORT).show();
//            }
//        });


        ok_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Picker1.getValue();
                Picker2.getValue();

                userGuess(id, Picker1.getValue()+"", Picker2.getValue()+"" , postion);



            }
        });
        img_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });

        popupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);


    }

    @SuppressLint("ResourceType")
    public void show_Book(String winner_text, String point_text, String point_team_text) {

        LayoutInflater layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View popupView = layoutInflater.inflate(R.layout.item_dialog_2, null);

        try {
            TextView winner = popupView.findViewById(R.id.winner);
            TextView point = popupView.findViewById(R.id.point);
            TextView point_team = popupView.findViewById(R.id.point_team);
            FrameLayout close_btn = popupView.findViewById(R.id.close_btn);
            LinearLayout linear_point_team = popupView.findViewById(R.id.linear_point_team);
            TextView TV_Winners = popupView.findViewById(R.id.TV_Winners);
            TV_Winners.setText(activity.getString(R.string.Winnerss));
            winner.setText(point_text);
            point.setText(winner_text);
            linear_point_team.setVisibility(View.VISIBLE);
            point_team.setText(point_team_text);
            close_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialog.dismiss();
                }
            });


            final AlertDialog.Builder builder = new AlertDialog.Builder(activity);

            builder.setView(popupView);

            alertDialog = builder.show();
            alertDialog.getWindow().setGravity(Gravity.CENTER);
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

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
    public void userGuess(String match_id, String home_goals, String away_goals,int postion) {

        ApiService.loading(activity, true);

        final RequestQueue queue = Volley.newRequestQueue(activity);
        // Tag used to cancel the request
        String url = ApiService.userGuess;


        JSONObject jsonObject = new JSONObject();


        try {
            jsonObject.put("match_id", match_id);
            jsonObject.put("home_goals", home_goals);
            jsonObject.put("away_goals", away_goals);

            System.out.println("jsonObject" + jsonObject.toString());
        } catch (Exception e) {

        }


        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST, url, jsonObject,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject responseBody) {
                        // pDialog.hide();

                        try {
                            ApiService.loading(activity, false);


                            String status = responseBody.getString("status");

//                            String message = responseBody.getString("message");
                            String code = responseBody.getString("code");


                            if (status.equals("true")) {



                                list.get(postion).setAway_goals(away_goals);
                                list.get(postion).setHome_goals(home_goals);
                                notifyDataSetChanged();
                                popupWindow.dismiss();
                                Toast.makeText(activity, activity.getResources().getString(R.string.message_dune)+"", Toast.LENGTH_SHORT).show();
                               // dCallBack.Result("","",false);
                            } else {


                                Toast.makeText(activity, responseBody.getString("message")+"", Toast.LENGTH_SHORT).show();

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError volleyError) {

                System.out.println(volleyError.getMessage());
/////
                ApiService.loading(activity, false);

//                webServise.loading(SplashScreenActivity.this, false);

                ApiService.ErrorResponse(activity, volleyError);
            }
        }) {

            /**
             * Passing some request headers
             * */

//            @Override
//            protected Map<String,String> getParams(){
//                Map<String,String> params = new HashMap<String, String>();
//                params.put("email",email);
//                params.put("name",name);
//                params.put("mobile",mobile);
//                params.put("message",message);
//
//
//                return params;
//            }
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {

                return ApiService.getHeader(true);
            }

        };

        jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(
                9000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(jsonObjReq);


    }



    public void userGuess2(String match_id, String home_goals, String away_goals) {

        ApiService.loading(activity, true);

        final RequestQueue queue = Volley.newRequestQueue(activity);
        // Tag used to cancel the request
        String url = ApiService.userGuess;


        JSONObject jsonObject = new JSONObject();


        try {
            jsonObject.put("match_id", match_id);
            jsonObject.put("home_goals", home_goals);
            jsonObject.put("away_goals", away_goals);

            System.out.println("jsonObject"+jsonObject.toString());
        } catch (Exception e) {

        }


        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST, url, jsonObject,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject responseBody) {
                        // pDialog.hide();

                        try {
                            ApiService.loading(activity, false);


                            String status = responseBody.getString("status");

//                            String message = responseBody.getString("message");
                            String code = responseBody.getString("code");


                            if (status.equals("true")) {

//                                String matches = responseBody.getString("matches");

                                popupWindow.dismiss();

                            } else {

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError volleyError) {

                System.out.println(volleyError.getMessage());
/////
                ApiService.loading(activity, false);

//                webServise.loading(SplashScreenActivity.this, false);

                ApiService.ErrorResponse(activity, volleyError);
            }
        }) {

            /**
             * Passing some request headers
             * */

//            @Override
//            protected Map<String,String> getParams(){
//                Map<String,String> params = new HashMap<String, String>();
//                params.put("email",email);
//                params.put("name",name);
//                params.put("mobile",mobile);
//                params.put("message",message);
//
//
//                return params;
//            }
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {

                return ApiService.getHeader(true);
            }

        };
        jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(
                9000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        queue.add(jsonObjReq);


    }

}
