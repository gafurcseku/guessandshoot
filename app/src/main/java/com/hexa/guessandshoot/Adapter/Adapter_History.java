package com.hexa.guessandshoot.Adapter;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hexa.guessandshoot.Modules.Db_notification;
import com.hexa.guessandshoot.Modules.History;
import com.hexa.guessandshoot.R;
import com.orhanobut.hawk.Hawk;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class Adapter_History extends RecyclerView.Adapter<Adapter_History.ViewHolder> {
    ArrayList<History> list ;
    Activity activity;


    public Adapter_History(ArrayList<History> list, Activity activity) {
        this.list = list;
        this.activity = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

      //  holder.isRecyclable();

        //holder.message_notification.setText(list.get(position).getMessage());
        History history = list.get(position);




        holder.TV_coin.setText(history.getGuess());
        if (history.getPrize().equals("0")){
            holder.linear_cash.setVisibility(View.INVISIBLE);
        }
        if (history.getMatch() != null) {
            holder.TV_champion_name.setText(history.getMatch().getChampionName());

            if (history.getMatch().getWinnerPrize().equals("0")){
                holder.linear_cash.setVisibility(View.INVISIBLE);
            }else {
                holder.TV_cash.setText(history.getMatch().getWinnerPrize()+" $");
            }

            holder.TV_cloup1.setText(history.getMatch().getHomeClubName());
            holder.TV_cloup2.setText(history.getMatch().getAwayClubName());
            holder.TV_result_1.setText(history.getMatch().getHomeGoals());
            holder.TV_result_2.setText(history.getMatch().getAwayGoals());
            try {
                String MatchDate = history.getMatch().getMatchDateTime();

                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);

                Date newDate = format.parse(MatchDate);

                format = new SimpleDateFormat("dd-MM-yyyy",Locale.ENGLISH);
                String lang =  Hawk.get("lang");
                SimpleDateFormat format_day = new SimpleDateFormat("EEE",lang.equals("en")?Locale.ENGLISH:new Locale("ar"));
                String day = format_day.format(newDate);
                String date = format.format(newDate);
                holder.TV_date.setText(day+" "+date);

                format = new SimpleDateFormat("HH:mm",Locale.ENGLISH);
                String time = format.format(newDate);
                holder.TV_time.setText(time) ;


            } catch (Exception e) {
                Log.e("Exception",e.getMessage());

            }
        }
        else {
            if (history.getPrize().equals("0")){
                holder.linear_cash.setVisibility(View.INVISIBLE);
            }else {
                holder.TV_cash.setText(history.getChampion().getWinnerPrize()+" $");
            }
            holder.TV_cloup1.setVisibility(View.INVISIBLE);
            holder.TV_cloup2.setVisibility(View.INVISIBLE);
            holder.TV_result_1.setVisibility(View.INVISIBLE);
            holder.TV_result_2.setVisibility(View.INVISIBLE);
            holder.TV_date.setVisibility(View.INVISIBLE);
            holder.TV_time.setVisibility(View.INVISIBLE);
            holder.ly_match.setVisibility(View.INVISIBLE);
            holder.TVgmt.setVisibility(View.INVISIBLE);
            holder.TV_champion_name.setText(history.getChampion().getName());

        }
//        if (history.getMatch() != null)
//        {
//            holder.TV_champion_name.setText(history.getMatch().getChampionName());
//            holder.TV_coin.setText(history.getGuess());
//            if (history.getMatch().getWinnerPrize().equals("0")){
//                holder.linear_cash.setVisibility(View.INVISIBLE);
//            }else {
//                holder.TV_cash.setText(history.getMatch().getWinnerPrize()+" $");
//            }
//
//            holder.TV_cloup1.setText(history.getMatch().getHomeClubName());
//            holder.TV_cloup2.setText(history.getMatch().getAwayClubName());
//            holder.TV_result_1.setText(history.getMatch().getHomeGoals());
//            holder.TV_result_2.setText(history.getMatch().getAwayGoals());
//            try {
//                String MatchDate = history.getMatch().getMatchDateTime();
//
//                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
//
//                Date newDate = format.parse(MatchDate);
//
//                format = new SimpleDateFormat("dd-MM-yyyy",Locale.ENGLISH);
//                String lang =  Hawk.get("lang");
//                SimpleDateFormat format_day = new SimpleDateFormat("EEEE",lang.equals("en")?Locale.ENGLISH:new Locale("ar"));
//                String day = format_day.format(newDate);
//                String date = format.format(newDate);
//                holder.TV_date.setText(day+" "+date);
//
//                format = new SimpleDateFormat("HH:mm",Locale.ENGLISH);
//                String time = format.format(newDate);
//                holder.TV_time.setText(time) ;
//
//
//            } catch (Exception e) {
//                Log.e("Exception",e.getMessage());
//
//            }
//        }
//        else {
//            if (history.getPrize().equals("0")){
//                holder.linear_cash.setVisibility(View.INVISIBLE);
//            }else {
//                holder.TV_cash.setText(history.getChampion().getWinnerPrize()+" $");
//            }
//            holder.TV_cloup1.setVisibility(View.INVISIBLE);
//            holder.TV_cloup2.setVisibility(View.INVISIBLE);
//            holder.TV_result_1.setVisibility(View.INVISIBLE);
//            holder.TV_result_2.setVisibility(View.INVISIBLE);
//            holder.TV_date.setVisibility(View.INVISIBLE);
//            holder.TV_time.setVisibility(View.INVISIBLE);
//            holder.ly_match.setVisibility(View.INVISIBLE);
//            holder.TVgmt.setVisibility(View.INVISIBLE);
//            holder.TV_champion_name.setText(history.getChampion().getName());
//            holder.TV_coin.setText(history.getGuess());
//        }
    }

    @Override
    public int getItemCount() {
        return list.size();

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView TV_coin, TV_cash ,TV_date ,TV_time ,TV_cloup1 ,TV_cloup2 ,TV_result_1 ,TV_result_2 ,TV_champion_name ,TVgmt;
        LinearLayout linear_cash ,ly_match ;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            TV_coin = itemView.findViewById(R.id.TV_coin);
            TV_cash = itemView.findViewById(R.id.TV_cash);
            TV_date = itemView.findViewById(R.id.TV_date);
            TV_time = itemView.findViewById(R.id.TV_time);
            TV_cloup1 = itemView.findViewById(R.id.TV_cloup1);
            TV_cloup2 = itemView.findViewById(R.id.TV_cloup2);
            TV_result_1 = itemView.findViewById(R.id.TV_result_1);
            TV_result_2 = itemView.findViewById(R.id.TV_result_2);
            TV_champion_name = itemView.findViewById(R.id.TV_champion_name);
            linear_cash = itemView.findViewById(R.id.linear_cash);
            ly_match = itemView.findViewById(R.id.ly_match);
            TVgmt = itemView.findViewById(R.id.TVgmt);
        }
    }
}
