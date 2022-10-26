package com.hexa.guessandshoot.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hexa.guessandshoot.Activity.ArrangeChampionshipActivity;
import com.hexa.guessandshoot.Activity.GroupsActivity;
import com.hexa.guessandshoot.Activity.MainActivity;
import com.hexa.guessandshoot.Modules.Db_champions;
import com.hexa.guessandshoot.Modules.Db_championsGroup;
import com.hexa.guessandshoot.R;
import com.hexa.guessandshoot.Settings.FixedRecyclerView;
import com.orhanobut.hawk.Hawk;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class Adapter_Arrange extends RecyclerView.Adapter<Adapter_Arrange.ViewHolder> {
    ArrayList<Db_championsGroup> list;
    Activity activity;


    public Adapter_Arrange(ArrayList<Db_championsGroup> list, Activity activity) {
        this.list = list;

        this.activity = activity;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_arrange, null, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

        Picasso.get().load(list.get(position).getImage()).into(holder.image);
        holder.tv_name.setText(list.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return list.size();

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_name ;
        ImageView image ;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_name = itemView.findViewById(R.id.tv_name);
            image = itemView.findViewById(R.id.image);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (Hawk.get("User").toString().equals("")) {
                        Toast.makeText(activity, activity.getResources().getString(R.string.pleaseLogin), Toast.LENGTH_LONG).show();
                        return;
                    }
                    Hawk.put("object",list.get(getAdapterPosition())) ;
                    if (list.get(getAdapterPosition()).is_group.equals("0")){
                        Intent i = new Intent(activity , ArrangeChampionshipActivity.class) ;
                        activity.startActivity(i);
                    }else {
                        Intent i = new Intent(activity , GroupsActivity.class) ;
                        activity.startActivity(i);
                    }
                }
            });
        }
    }


}
