package com.hexa.guessandshoot.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.hexa.guessandshoot.R;
import com.hexa.guessandshoot.Modules.Db_Ranking;
import com.hexa.guessandshoot.Settings.Settings;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Adapter_Ranking extends RecyclerView.Adapter<Adapter_Ranking.ViewHolder> {
    ArrayList<Db_Ranking> list = new ArrayList<>();
    Context context;


    int number = 3;

    public Adapter_Ranking(ArrayList<Db_Ranking> list, Context context) {
        this.list = list;

        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ranking, null, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        number = number + 1;
        holder.text_Center_number.setText(number + "");

        holder.text_Name.setText(list.get(position).getUser().getName() + "");
        holder.text_Points.setText(list.get(position).getPoints() + " ");


        Picasso.get().load(list.get(position).getUser().getImageProfile()).into(holder.image_Ranking);

    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView text_Center_number, text_Name, text_Points;
        ImageView image_Ranking;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image_Ranking = itemView.findViewById(R.id.image_Ranking);
            text_Center_number = itemView.findViewById(R.id.text_Center_number);
            text_Name = itemView.findViewById(R.id.text_Name);
            text_Points = itemView.findViewById(R.id.text_Points);
        }
    }
}
