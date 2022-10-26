package com.hexa.guessandshoot.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
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

import com.hexa.guessandshoot.DCallBack;
import com.hexa.guessandshoot.Fragment.top_score.TopRequestType;
import com.hexa.guessandshoot.Modules.League;
import com.hexa.guessandshoot.Modules.Player;
import com.hexa.guessandshoot.Modules.last_db.User;
import com.hexa.guessandshoot.Modules.newDB.DBChampionGuess;
import com.hexa.guessandshoot.Modules.newDB.DB_Player;
import com.hexa.guessandshoot.R;
import com.hexa.guessandshoot.Settings.ApiService;
import com.hexa.guessandshoot.Settings.Settings;
import com.loopj.android.http.RequestParams;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterMyTeam extends RecyclerView.Adapter<AdapterMyTeam.ViewHolder> {
    private static final String TAG = "Adapter_GoalKeeper";

    List<Player> list;
    Activity activity;
   int ownerId ;


    public AdapterMyTeam(List<Player> list, Activity activity,int ownerId) {
        this.list = list;
        this.activity = activity;
        this.ownerId = ownerId;

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_my_team, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {


        Player player = list.get(position) ;
        holder.TV_number.setText((position+1)+"");
        Log.e("checkHamdullah",player.getUser().getName());
        holder.TV_name.setText(player.getUser().getName()+"");
        holder.TV_point.setText(player.getUser().getPoints()+"");

        Picasso.get().load(player.getUser().getImageProfile()).into(holder.image);

        if (player.getUserId() == ownerId){
            holder.img_copyright.setVisibility(View.VISIBLE);
        }else {
            holder.img_copyright.setVisibility(View.GONE);
        }
        if (Settings.getUser()!=null &&Settings.getUser().getId()==ownerId &&!Settings.getUser().getId().equals(player.getUserId()) ){
         //   holder.image_medal.setVisibility(View.VISIBLE);
        }else {
            holder.image_medal.setVisibility(View.GONE);
        }
        if (Settings.getUser()!=null && Settings.getUser().getId().equals(player.getUserId())){
            holder.background.setBackground(activity.getResources().getDrawable(R.drawable.shape_border_home_selected));
        }
    }


    @Override
    public int getItemCount() {
        return list.size();

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView  TV_name ,TV_number ,TV_point;
        CircleImageView image ;
        ImageView background ,img_copyright ,image_medal ;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            TV_name = itemView.findViewById(R.id.TV_name);
            TV_number = itemView.findViewById(R.id.TV_number);
            TV_point = itemView.findViewById(R.id.TV_point);
            image = itemView.findViewById(R.id.image);
            background = itemView.findViewById(R.id.background);
            img_copyright = itemView.findViewById(R.id.img_copyright);
            image_medal = itemView.findViewById(R.id.image_medal);
            image_medal.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialogConfirm(getAdapterPosition());
                }
            });

        }
    }
    public void dialogConfirm(int position) {
        new android.app.AlertDialog.Builder(activity).setMessage(activity.getText(R.string.delete_message))
                .setPositiveButton(activity.getText(R.string.yes), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        RequestParams requestParams = new RequestParams() ;
                        requestParams.put("user_id",list.get(position).getUserId());
                        requestParams.put("league_id",list.get(position).getLeagueId());

                        ApiService.PushRequest(activity, ApiService.deleteUser,requestParams, new DCallBack() {
                            @Override
                            public void Result(String obj, String fun, boolean IsSuccess) {
                                if (IsSuccess){
                                    removeAt(position);
                                }

                            }
                        });
                        dialog.cancel();
                    }
                }).setNegativeButton(activity.getText(R.string.no), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        }).show();



    }
    public void removeAt(int position) {
        list.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, list.size());
    }
}
