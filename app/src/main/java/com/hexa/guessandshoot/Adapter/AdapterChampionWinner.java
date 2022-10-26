package com.hexa.guessandshoot.Adapter;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hexa.guessandshoot.Modules.last_db.Winner;
import com.hexa.guessandshoot.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterChampionWinner extends RecyclerView.Adapter<AdapterChampionWinner.MyViewHolder> {
    private static final String TAG = "AdapterChampionWinner";

    public enum WinnerType {
        CLUB, SCORER, GOALKEEPER
    }

    private Context context;
    private List<Winner> db_championWinners;
    private WinnerType winnerType;

    public AdapterChampionWinner(Context context, List<Winner> db_championWinners, WinnerType winnerType) {
        this.context = context;
        this.db_championWinners = db_championWinners;
        this.winnerType = winnerType;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_champions_winner, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Winner currentWinner = db_championWinners.get(position);

        String championImageUrl = currentWinner.getChampion().getImage();
        if (championImageUrl != null && !TextUtils.isEmpty(championImageUrl))
            Picasso.get().load(championImageUrl).into(holder.iv_championImage);
        holder.tv_championName.setText(currentWinner.getChampion().getName());

        String winnerTypeImage = "";
        String winnerTypeText = "";

        switch (winnerType) {
            case CLUB:
                Log.d(TAG, "onBindViewHolder: Club :: " + currentWinner.getClub().toString());
                winnerTypeImage = currentWinner.getClub().getImage();
                winnerTypeText = currentWinner.getClub().getName();
                ((CircleImageView)holder.iv_clubImage).setBorderWidth(0);
                break;
            case SCORER:
                Log.d(TAG, "onBindViewHolder: SCORER :: " + currentWinner.getPlayer().toString());
                winnerTypeImage = currentWinner.getPlayer().getImage();
                winnerTypeText = currentWinner.getPlayer().getName();
                break;
            case GOALKEEPER:
                Log.d(TAG, "onBindViewHolder: SCORER :: " + currentWinner.getGoalkeeper().toString());
                winnerTypeImage = currentWinner.getGoalkeeper().getImage();
                winnerTypeText = currentWinner.getGoalkeeper().getName();
                break;
        }
        if (!TextUtils.isEmpty(winnerTypeImage))
            Picasso.get().load(winnerTypeImage).into(holder.iv_clubImage);
        holder.tv_clubName.setText(winnerTypeText);

        String playerImageUrl = currentWinner.getUser().getImageProfile();
        if (playerImageUrl != null && !TextUtils.isEmpty(playerImageUrl))
            Picasso.get().load(playerImageUrl).into(holder.iv_playerImage);
        holder.tv_playerName.setText(currentWinner.getUser().getName());

    }

    @Override
    public int getItemCount() {
        return db_championWinners == null ? 0 : db_championWinners.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView iv_championImage, iv_clubImage, iv_playerImage;
        public TextView tv_championName, tv_clubName, tv_playerName;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            iv_championImage = itemView.findViewById(R.id.iv_championImage);
            iv_clubImage = itemView.findViewById(R.id.iv_clubImage);
            iv_playerImage = itemView.findViewById(R.id.iv_playerImage);
            tv_championName = itemView.findViewById(R.id.tv_championName);
            tv_clubName = itemView.findViewById(R.id.tv_clubName);
            tv_playerName = itemView.findViewById(R.id.tv_playerName);
        }
    }
}
