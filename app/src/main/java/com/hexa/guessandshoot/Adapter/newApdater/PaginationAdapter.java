package com.hexa.guessandshoot.Adapter.newApdater;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.format.Time;
import android.util.Log;
import android.util.Patterns;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.getkeepsafe.taptargetview.TapTarget;
import com.getkeepsafe.taptargetview.TapTargetSequence;
import com.hexa.guessandshoot.Activity.Auth.LoginActivity;
import com.hexa.guessandshoot.Activity.Auth.SuccessActivity;
import com.hexa.guessandshoot.Adapter.Adapter_list_number_1;
import com.hexa.guessandshoot.Adapter.Adapter_list_number_2;
import com.hexa.guessandshoot.Fragment.FragmentExpectations;
import com.hexa.guessandshoot.Modules.Db_My_Expections_My_Account;
import com.hexa.guessandshoot.R;
import com.hexa.guessandshoot.Settings.ApiService;
import com.hexa.guessandshoot.Settings.Settings;
import com.hexa.guessandshoot.Settings.onCheck;
import com.orhanobut.hawk.Hawk;
import com.squareup.picasso.Picasso;

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
import java.util.Timer;
import java.util.regex.Pattern;

public class PaginationAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private static final String TAG = "PaginationAdapter";

    private Activity activity;
    private List<Db_My_Expections_My_Account> items;
    private final ColorDrawable placeholderDrawable;
    private static final int VIEW_TYPE_LOADING = 0;
    private static final int VIEW_TYPE_NORMAL = 1;
    private boolean isLoaderVisible = false;
    private boolean showTutorial;

    private AlertDialog alertDialog;
    private PopupWindow popupWindow;

    public boolean isFirst = true ;
    public PaginationAdapter(Activity activity, List<Db_My_Expections_My_Account> postItems) {
        this.activity = activity;
        this.items = postItems;
        this.placeholderDrawable = new ColorDrawable(ContextCompat.getColor(activity, R.color.black));
    }

    public boolean isShowTutorial() {
        return showTutorial;
    }

    public void setShowTutorial(boolean showTutorial) {
        this.showTutorial = showTutorial;
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case VIEW_TYPE_NORMAL:
                return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_my_expections_on_my_account, parent, false));
            case VIEW_TYPE_LOADING:
                return new ProgressHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loading, parent, false));
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        if (holder instanceof ViewHolder){

            holder.onBind(position);
        }

    }

    @Override
    public int getItemViewType(int position) {

      return (position == items.size() - 1 && isLoaderVisible) ? VIEW_TYPE_LOADING : VIEW_TYPE_NORMAL;
    }

    @Override
    public int getItemCount() {
        return items == null ? 0 : items.size();
    }
    public void clear() {
        items.clear();
        notifyDataSetChanged();
    }
    public void addItems(List<Db_My_Expections_My_Account> postItems) {
        items.addAll(postItems);
        notifyDataSetChanged();
    }

    public void addLoading() {
        Log.d(TAG, "addLoading: ");
        isLoaderVisible = true;
        items.add(new Db_My_Expections_My_Account());
        notifyItemInserted(items.size() - 1);
    }

    public void removeLoading() {
        if (items.size()>0){
            Log.d(TAG, "removeLoading: ");
            isLoaderVisible = false;
            int position = items.size() - 1;
            Db_My_Expections_My_Account item = getItem(position);
            if (item != null) {
                items.remove(position);
                notifyItemRemoved(position);
            }
        }

    }

//    public void clear() {
//        items.clear();
//        notifyDataSetChanged();
//    }

    Db_My_Expections_My_Account getItem(int position) {
        return items.get(position);
    }


    public class ProgressHolder extends BaseViewHolder {
        ProgressHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void clear() {

        }
    }

    public class ViewHolder extends BaseViewHolder {
        ImageView image_First_Team, image_Second_Team, image_medal;
        Button btn_Shot;
        LinearLayout Reward;
        FrameLayout btn_close;
        TextView text_Name_Of_First_Team, text_Number_Of_Targets_1, text_Name_Of_Second_Team, text_Number_Of_Targets_2,
                text_The_name_of_the_league, text_Day_Mhe_Match, text_Date_the_match, text_Time_The_Match, text_Reward, TV_time_message;

        TextView text_My_guess1, text_My_guess2 ,TV_matche_text_name;

        ViewHolder(View itemView) {
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
            TV_matche_text_name = itemView.findViewById(R.id.TV_matche_text_name);
            text_Time_The_Match = itemView.findViewById(R.id.text_Time_The_Match);
            text_Date_the_match = itemView.findViewById(R.id.text_Date_the_match);
            text_Reward = itemView.findViewById(R.id.text_Reward);
            text_Day_Mhe_Match = itemView.findViewById(R.id.text_Day_Mhe_Match);
            Reward = itemView.findViewById(R.id.Reward);
            TV_time_message = itemView.findViewById(R.id.TV_time_message);
            text_My_guess1 = itemView.findViewById(R.id.text_My_guess1);
            text_My_guess2 = itemView.findViewById(R.id.text_My_guess2);
        }

        protected void clear() {

        }

        public void onBind(int position) {
            super.onBind(position);

            Db_My_Expections_My_Account item = items.get(position);

            if (item.getAwayClub()==null){
                Log.e("holder" ,"return"+position);
                return;
            }
            text_The_name_of_the_league.setText(item.getChampionName());
            text_Name_Of_First_Team.setText(item.getHomeClubName());
            text_Name_Of_Second_Team.setText(item.getAwayClubName());
            text_Time_The_Match.setText(item.getMatchTime());
            text_Number_Of_Targets_2.setText(item.getAwayGoals() + "");
            text_Number_Of_Targets_1.setText(item.getHomeGoals() + "");
            btn_Shot.setEnabled(true);

            if (item.getUser_guess().size() > 0) {
                text_My_guess2.setText(item.getUser_guess().get(1));
                text_My_guess1.setText(item.getUser_guess().get(0));
                btn_Shot.setBackground(ContextCompat.getDrawable(activity, R.drawable.shape_btn_time_end));
                btn_Shot.setText(activity.getString(R.string.SHOOT_Agein));
                btn_Shot.setTextColor(Color.BLACK);
            } else {
                text_My_guess2.setText("-");
                text_My_guess1.setText("-");
                btn_Shot.setBackground(ContextCompat.getDrawable(activity, R.drawable.shape_btn));
                btn_Shot.setText(activity.getString(R.string.shot));
                btn_Shot.setTextColor(Color.WHITE);
            }

            try {
                String match_date_time = item.match_date_time;
                SimpleDateFormat format_date12 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
                Date date12 = format_date12.parse(match_date_time);

                if (date12.getTime() < Settings.dateTimeGMT().getTime()) {
                    btn_Shot.setBackground(ContextCompat.getDrawable(activity, R.drawable.shape_btn_time_end));
                    btn_Shot.setText(activity.getString(R.string.time_ended));
                    btn_Shot.setTextColor(Color.RED);
                    btn_Shot.setEnabled(false);
                    TV_time_message.setVisibility(View.GONE);
                } else {
                    TV_time_message.setVisibility(View.GONE);
                }
                String date22 = format_date12.format(date12);
                Log.e("date22", date22);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            Calendar calendar = Calendar.getInstance();
            try {
                String strCurrentDate = item.getMatchDate();
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                Date newDate = format.parse(strCurrentDate);
                calendar.setTimeInMillis(newDate.getTime());
                String lang = Hawk.get("lang");
                SimpleDateFormat format_date = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
                SimpleDateFormat format_day = new SimpleDateFormat("EEEE", lang.equals("en") ? Locale.ENGLISH : new Locale("ar"));
                String date = format_date.format(newDate);
                String day = format_day.format(newDate);


                System.out.println("datedate" + date + " " + day);
                text_Date_the_match.setText(day + " " + date);
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {

                String strCurrentDate = item.getMatchTime();


                SimpleDateFormat format = new SimpleDateFormat("HH:mm");
                Date newDate = format.parse(strCurrentDate);
                calendar.set(Calendar.HOUR_OF_DAY, newDate.getHours());
                calendar.set(Calendar.MINUTE, newDate.getMinutes());
                calendar.set(Calendar.SECOND, 0);

                format = new SimpleDateFormat("HH:mm", Locale.ENGLISH);
                String date = format.format(newDate);
                SimpleDateFormat format_date = new SimpleDateFormat("EEEE dd-MM-yyyy hh:mm", Locale.ENGLISH);

                String test_date = format_date.format(calendar.getTime());
                Log.e("test_date", test_date);
                text_Time_The_Match.setText(date + " GMT");
            } catch (Exception e) {
                e.printStackTrace();
            }


            try {
                if (!item.matche_text_name.equals("")){
                    TV_matche_text_name.setText("("+item.matche_text_name+")");
                    TV_matche_text_name.setVisibility(View.VISIBLE);
                }else {
                    Log.e("matche_text_name" ,"matche_text_name") ;
                    TV_matche_text_name.setVisibility(View.GONE);
                }

            }catch (Exception e){
                Log.e("matche_text_name" ,"matche_text_name2") ;
                TV_matche_text_name.setVisibility(View.GONE);
            }
                Picasso.get().load(item.getClubHome().getImage()).into(image_First_Team);
                Picasso.get().load(item.getClubAway().getImage()).into(image_Second_Team);

    Log.e("image",item.getClubHome().getImage());
            btn_Shot.setOnClickListener(v -> {
                Log.e("btn_Shot","btn_Shot");
               // getEmails();
                if (!Hawk.get("User").toString().equals("")) {
                    if(!((Boolean) Hawk.get("status"))){
                        FragmentExpectations.ClickCount += 1;
                    }
                    if(FragmentExpectations.ClickCount ==6){
                        if (FragmentExpectations.mInterstitialAd != null) {
                            FragmentExpectations.mInterstitialAd .show(activity);
                        }
                        FragmentExpectations.ClickCount = 0;
                    }else{
                        show(item.getId() + "", item.getClubHome().getImage(), item.getClubAway().getImage(), item.getUser_guess(), position, this);
                    }
                } else {
                    Toast.makeText(activity, activity.getResources().getString(R.string.pleaseLogin), Toast.LENGTH_SHORT).show();
                }

            });
            Reward.setOnClickListener(v -> show_Book(item.getWinnerPoint(), item.getWinnerPrize(), item.getGuessPoint()));

            if (showTutorial && position == 0) {
                Log.d(TAG, "onBindViewHolder: showTutorial " + showTutorial);
                showTutorial(this);
            }
        }
    }

    private void showTutorial(ViewHolder holder) {
        Log.d(TAG, "showTutorial: ");
        new TapTargetSequence(activity).targets(
                        createTargetView(holder.Reward, activity.getString(R.string.rewards), activity.getString(R.string.tut_rewards_desc)),
                        createTargetView(holder.btn_Shot, "", activity.getString(R.string.tut_shot_desc))
                )
                .considerOuterCircleCanceled(true)
                .continueOnCancel(true)
                .listener(new TapTargetSequence.Listener() {
                    @Override
                    public void onSequenceFinish() {
                        showTutorial = false ;
                    }

                    @Override
                    public void onSequenceStep(TapTarget lastTarget, boolean targetClicked) {

                    }

                    @Override
                    public void onSequenceCanceled(TapTarget lastTarget) {

                    }
                }).start();
    }

    TapTarget createTargetView(View view, String title, String descreption) {
        return TapTarget.forView(view, title, descreption)
                .dimColor(android.R.color.transparent)
                .tintTarget(false)
                .outerCircleColor(R.color.colorAccent)
                .targetCircleColor(R.color.colorPrimary)
                .textColor(android.R.color.white);
    }


    public void show(final String id, String image1, String image2, List<String> gussUser, int postion, ViewHolder holder) {

        LayoutInflater layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View popupView = layoutInflater.inflate(R.layout.item_dialog_3, null);

        RecyclerView recycler_dialog_1 = popupView.findViewById(R.id.recycler_dialog_1);
        RecyclerView recycler_dialog_2 = popupView.findViewById(R.id.recycler_dialog_2);
        ImageView ok_btn = popupView.findViewById(R.id.ok_btn);
        final NumberPicker Picker1 = popupView.findViewById(R.id.Picker1);
        final NumberPicker Picker2 = popupView.findViewById(R.id.Picker2);

        ImageView team_1 = popupView.findViewById(R.id.team_1);
        ImageView team_2 = popupView.findViewById(R.id.team_2);
        ImageView img_close = popupView.findViewById(R.id.img_close);


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

        final String[] values = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};


        Picker1.setMinValue(0);
        Picker1.setDisplayedValues(values);
        Picker1.setMaxValue(values.length - 1);

        Picker2.setMinValue(0);
        Picker2.setDisplayedValues(values);
        Picker2.setMaxValue(values.length - 1);


        if (gussUser.size() > 0) {
            Picker1.setValue(Integer.parseInt(gussUser.get(0)));
            Picker2.setValue(Integer.parseInt(gussUser.get(1)));
        }


        ok_btn.setOnClickListener(v -> {
            Picker1.getValue();
            Picker2.getValue();

            userGuess(id, Picker1.getValue() + "", Picker2.getValue() + "", postion, holder ,true);

            Log.e("type",new Time().toString()) ;
//            ApiService.isUserHasSubscription(activity, new onCheck() {
//                @Override
//                public void onCheck(boolean b,String orderId) {
//                   // if (b){
//                        Log.e("type",new Time().toString()) ;
//
////                    }else {
////                        showNotActive("");
////                    }
//
//                }
//            }) ;

        });
        img_close.setOnClickListener(v -> popupWindow.dismiss());

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

    }

    public void userGuess(String match_id, String home_goals, String away_goals, int postion, ViewHolder holder,boolean isActive) {

        ApiService.loading(activity, true);

        final RequestQueue queue = Volley.newRequestQueue(activity);
        // Tag used to cancel the request
        String url = ApiService.userGuess;


        JSONObject jsonObject = new JSONObject();


        try {
            jsonObject.put("match_id", match_id);
            jsonObject.put("home_goals", home_goals);
            jsonObject.put("away_goals", away_goals);
            jsonObject.put("status", isActive+"");
            jsonObject.put("type", "android");

            System.out.println("jsonObject" + jsonObject.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST, url, jsonObject,
                responseBody -> {
                    // pDialog.hide();
                    Log.e("responseBody", responseBody.toString());
                    try {
                        ApiService.loading(activity, false);

                        String status = responseBody.getString("status");
                        String code = responseBody.getString("code");


//                        if (status.equals("true")) {
                            items.get(postion).getUser_guess().clear();
                            items.get(postion).getUser_guess().add(home_goals);
                            items.get(postion).getUser_guess().add(away_goals);

                            holder.text_My_guess2.setText(items.get(postion).getUser_guess().get(1));
                            holder.text_My_guess1.setText(items.get(postion).getUser_guess().get(0));
                            holder.btn_Shot.setBackground(ContextCompat.getDrawable(activity, R.drawable.shape_btn_time_end));
                            holder.btn_Shot.setText(activity.getString(R.string.SHOOT_Agein));
                            holder.btn_Shot.setTextColor(Color.BLACK);
                            popupWindow.dismiss();
                            Toast.makeText(activity, activity.getResources().getString(R.string.message_dune) + "", Toast.LENGTH_SHORT).show();
//                        } else {
//                            if (code.equals("203")){
//                               showNotActive(responseBody.getString("message"));
//                            }else {
//                                Toast.makeText(activity, responseBody.getString("message"), Toast.LENGTH_SHORT).show();
//
//                            }
//                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }, volleyError -> {
            System.out.println(volleyError.getMessage());
            ApiService.loading(activity, false);
            ApiService.ErrorResponse(activity, volleyError);
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

        queue.add(jsonObjReq);
    }


    public void getEmails(){
        String email ;
        Pattern emailPattern = Patterns.EMAIL_ADDRESS; // API level 8+
        Account[] accounts = AccountManager.get(activity).getAccounts();
        for (Account account : accounts) {
            if (emailPattern.matcher(account.name).matches()) {
                String possibleEmail = account.name;
                Log.e("possibleEmail" ,possibleEmail) ;
            }
        }
    }
    public void showNotActive(String message){
        try {
            new android.app.AlertDialog.Builder(activity).setTitle(message)
                    .setCancelable(false)
                    .setPositiveButton(activity.getString(R.string.continues), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(activity, SuccessActivity.class);
                            intent.putExtra("from",1) ;
                            activity.startActivity(intent);
                        }
                    }).setNegativeButton(activity.getString(R.string.cancel), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                            // Hawk.put("User", "");
                        }
                    }).show();
        }catch (Exception exception){
          exception.printStackTrace();
        }

    }

}
