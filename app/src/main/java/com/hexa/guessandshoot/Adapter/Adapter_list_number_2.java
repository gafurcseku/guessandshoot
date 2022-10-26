package com.hexa.guessandshoot.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hexa.guessandshoot.Modules.Db_notification;
import com.hexa.guessandshoot.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Adapter_list_number_2 extends RecyclerView.Adapter<Adapter_list_number_2.ViewHolder> {
    ArrayList<String> list = new ArrayList<String>();
    Context context;


    int postrion = -1;


    public static  String select_nubmer = "";


    public Adapter_list_number_2(ArrayList<String> list, Context context) {
        this.list = list;

        this.context = context;
    }

    public void Ref() {
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_select_number, null, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        if (postrion == position) {
            holder.text.setTextColor(context.getResources().getColor(R.color.colorAccent));
        } else {
            holder.text.setTextColor(context.getResources().getColor(R.color.white));

        }
        holder.text.setText(list.get(position)+"");


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                postrion = position;

                select_nubmer = list.get(position);
                Ref();

            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView text;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.text);


        }
    }
}
