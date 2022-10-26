package com.hexa.guessandshoot.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hexa.guessandshoot.Fragment.top_score.TopRequestType;
import com.hexa.guessandshoot.Modules.newDB.DBChampionGuess;
import com.hexa.guessandshoot.Modules.newDB.DB_Player;
import com.hexa.guessandshoot.R;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterTopScore extends RecyclerView.Adapter<AdapterTopScore.ViewHolder> {
    private static final String TAG = "Adapter_GoalKeeper";

    ArrayList<DBChampionGuess> list;
    Activity activity;
    TopRequestType topRequestType;


    public AdapterTopScore(ArrayList<DBChampionGuess> list, TopRequestType topRequestType, Activity activity) {
        this.list = list;
        this.activity = activity;
        this.topRequestType = topRequestType;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chosen_top_player, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

        DBChampionGuess db_championWinner = list.get(position);
        Log.d(TAG, "onBindViewHolder: " + db_championWinner.toString());

        //Init Champion Name
        String championName = db_championWinner.getName();
        if (championName != null && !TextUtils.isEmpty(championName))
            holder.text_Champions.setText(championName);

        try {
            String endGuess = db_championWinner.getEndGuess();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            Date newDate = format.parse(endGuess);

            format = new SimpleDateFormat("dd-MM-yyyy",Locale.ENGLISH);
            String date = format.format(newDate);

            if (!TextUtils.isEmpty(date))
                holder.dueTime.setText(activity.getString(R.string.dueDate) + " " + date);
        } catch (Exception e) {
            String endGuess = db_championWinner.getEndGuess();
            if (endGuess != null && !TextUtils.isEmpty(endGuess))
                holder.dueTime.setText(activity.getString(R.string.dueDate) + " " + endGuess);
        }




        //Init Champion Image
        String championImage = db_championWinner.getImage();
        if (championImage != null && !TextUtils.isEmpty(championImage))
            Picasso.get().load(championImage).into(holder.image_champions);

        //Init Card Title
        holder.tv_cardTitle.setText(topRequestType == TopRequestType.GOAL_KEEPER ? activity.getString(R.string.GoalKeeper_title) : activity.getString(R.string.Scorer_title));
        List<DB_Player> db_playerList = topRequestType == TopRequestType.GOAL_KEEPER ? list.get(position).getGoalkeepers() : list.get(position).getPlayers();

        //Init Champion Players IN RecyclerView
        AdapterTopPlayer adapterTopPlayer = new AdapterTopPlayer(db_playerList, topRequestType, activity);
        holder.rv_topPlayer.setLayoutManager(new GridLayoutManager(activity, 4));
        holder.rv_topPlayer.setAdapter(adapterTopPlayer);
        holder.linear_Reward.setOnClickListener(v -> {
            if (topRequestType == TopRequestType.GOAL_KEEPER) {
                show_Book(db_championWinner.getGoalkeeperPoint(), db_championWinner.getGoalkeeperPrize());
            } else
                show_Book(db_championWinner.getPlayerPoint(), db_championWinner.getPlayerPrize());
        });

        //Init Header Click
        holder.linear_header.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                if (holder.more.getVisibility() == View.VISIBLE) {
                    holder.more.setVisibility(View.GONE);
                    holder.arrow_down.setImageDrawable(activity.getDrawable(R.drawable.ic_keyboard_arrow_down_black));
                } else {
                    holder.arrow_down.setImageDrawable(activity.getDrawable(R.drawable.ic_keyboard_arrow_up_black_24dp));
                    holder.more.setVisibility(View.VISIBLE);
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return list.size();

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView text_Champions, dueTime, tv_cardTitle;
        CircleImageView image_champions;
        ImageView image_medal, arrow_down;
        LinearLayout more, linear_header, linear_Reward;

        RecyclerView rv_topPlayer;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            text_Champions = itemView.findViewById(R.id.text_Champions);
            dueTime = itemView.findViewById(R.id.dueTime);
            tv_cardTitle = itemView.findViewById(R.id.tv_cardTitle);
            image_champions = itemView.findViewById(R.id.image_champions);

            arrow_down = itemView.findViewById(R.id.arrow_down);
            image_medal = itemView.findViewById(R.id.image_medal);

            more = itemView.findViewById(R.id.more);
            linear_header = itemView.findViewById(R.id.linear_header);
            linear_Reward = itemView.findViewById(R.id.linear_Reward);

            rv_topPlayer = itemView.findViewById(R.id.rv_topPlayer);
        }
    }

    public void show() {
        LayoutInflater layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View popupView = layoutInflater.inflate(R.layout.item_dialog_2, null);


        final AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setView(popupView);

        AlertDialog alertDialog = builder.show();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
    }

    @SuppressLint("ResourceType")
    public void show_Book(String winner_text, String point_text) {

        final AlertDialog alertDialog;
        LayoutInflater layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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


            final androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(activity);

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
