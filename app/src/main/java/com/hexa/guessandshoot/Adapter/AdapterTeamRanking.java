package com.hexa.guessandshoot.Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hexa.guessandshoot.Activity.DawrenaDetailsActivity;
import com.hexa.guessandshoot.Modules.Db_Settings;
import com.hexa.guessandshoot.Modules.League;
import com.hexa.guessandshoot.Modules.newDB.DBChampionGuess;
import com.hexa.guessandshoot.R;
import com.hexa.guessandshoot.Settings.Settings;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterTeamRanking extends RecyclerView.Adapter<AdapterTeamRanking.ViewHolder> {
    private static final String TAG = "Adapter_GoalKeeper";

    ArrayList<League> list;
    Activity activity;



    public AdapterTeamRanking(ArrayList<League> list, Activity activity) {
        this.list = list;
        this.activity = activity;

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_team_ranking, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

        League league = list.get(position) ;
        holder.TV_number.setText((position+1)+"");
        holder.TV_name.setText(league.getName()+"");
        holder.TV_memburCount.setText(league.getUserCount()+"");
        holder.TV_point.setText(league.total_points+"");

        Picasso.get().load(league.getImage()).into(holder.image);

        if (position>2){
            holder.background.setAlpha(0.4f);
            holder.image_medal.setVisibility(View.GONE);
        }else{
            holder.image_medal.setVisibility(View.VISIBLE);
            holder.background.setAlpha(0.70f);
        }

        if (Settings.getUser()!=null&&Settings.getUser().in_league.equals(league.getId()+"")){
            holder.background.setBackground(activity.getResources().getDrawable(R.drawable.shape_border_home_selected));
        }


    }


    @Override
    public int getItemCount() {
        return list.size();

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView TV_number , TV_name ,TV_memburCount ,TV_point;
        CircleImageView image ;
        ImageView background ,image_medal ;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            TV_number = itemView.findViewById(R.id.TV_number);
            TV_name = itemView.findViewById(R.id.TV_name);
            TV_memburCount = itemView.findViewById(R.id.TV_memburCount);
            TV_point = itemView.findViewById(R.id.TV_point);
            image = itemView.findViewById(R.id.image);
            image_medal = itemView.findViewById(R.id.image_medal);
            background = itemView.findViewById(R.id.background);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent =new Intent(activity , DawrenaDetailsActivity.class);
                    intent.putExtra("id" , list.get(getAdapterPosition()).getId());
                    activity.startActivity(intent);
                }
            });


            image_medal.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Db_Settings settings = Settings.getSettings();
                    if (getAdapterPosition()==0){
                        showDialog(settings.leage_1_prize,settings.leage_prizae_text_1);
                    }else if (getAdapterPosition()==1){
                        showDialog(settings.leage_2_prize,settings.leage_prizae_text_2);
                    }else if (getAdapterPosition()==2){
                        showDialog(settings.leage_3_prize,settings.leage_prizae_text_3);
                    }

                }
            });
        }
    }


    public void showDialog(String Prise,String text){
        AlertDialog alertDialog = null;
        LayoutInflater layoutInflater = (LayoutInflater) activity.getSystemService(activity.LAYOUT_INFLATER_SERVICE);
        final View popupView = layoutInflater.inflate(R.layout.alert_dialog_staticis2, null);

        FrameLayout close = popupView.findViewById(R.id.close_btn);
        TextView cost = popupView.findViewById(R.id.cost);
        TextView TVtext = popupView.findViewById(R.id.text);



        // cost.setText(Settings.getSettings().getYearlyPrize() + " $ ");
         cost.setText(Prise+" $ ");
        TVtext.setText(Html.fromHtml(text));
        final AlertDialog.Builder builder = new AlertDialog.Builder(activity);

//            alertDialog_country =
        builder.setView(popupView);


        alertDialog = builder.show();

        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        AlertDialog finalAlertDialog = alertDialog;
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finalAlertDialog.dismiss();
            }
        });
    }

}
