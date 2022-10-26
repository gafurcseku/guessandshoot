package com.hexa.guessandshoot.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hexa.guessandshoot.Activity.ArrangeChampionshipActivity;
import com.hexa.guessandshoot.R;

import java.util.ArrayList;

public class Adapter_Groups extends RecyclerView.Adapter<Adapter_Groups.ViewHolder> {
    ArrayList<String> list =new ArrayList<>();
    Activity activity;

    int index ;
    public Adapter_Groups(Activity activity , int index) {
        for(char c = 'A'; c <= 'Z'; ++c){
            list.add(c + "");
        }


        this.activity = activity;
        this.index = index;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_groups, null, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

        holder.tv_name.setText(list.get(position));
    }

    @Override
    public int getItemCount() {
        return index;

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
                    Intent i = new Intent(activity , ArrangeChampionshipActivity.class) ;
                    i.putExtra("index" ,getAdapterPosition()+1);
                    i.putExtra("char" ,list.get(getAdapterPosition()));
                    activity.startActivity(i);
                }
            });
        }
    }

}
