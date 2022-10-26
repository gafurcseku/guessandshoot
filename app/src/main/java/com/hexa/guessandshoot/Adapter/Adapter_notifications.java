package com.hexa.guessandshoot.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hexa.guessandshoot.R;
import com.hexa.guessandshoot.Modules.Db_notification;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class Adapter_notifications extends RecyclerView.Adapter<Adapter_notifications.ViewHolder> {
    ArrayList<Db_notification> list ;
    Activity activity;


    public Adapter_notifications(ArrayList<Db_notification> list, Activity activity) {
        this.list = list;

        this.activity = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notifications, null, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {


        holder.message_notification.setText(list.get(position).getMessage());
        holder.title_notification.setText(list.get(position).getTitle());



        switch (list.get(position).getMessag_type()){
            case "0":
                holder.notification_image.setImageResource(R.drawable.appicon);
                break;
            case "1":
                holder.notification_image.setImageResource(R.drawable.ic_medal_for_notfy);
                break;
            case "2":
                holder.notification_image.setImageResource(R.drawable.ic_money_for_notfy);

                break;
        }


        try {
            String strCurrentDate = list.get(position).getCreated_at();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
            Date newDate = format.parse(strCurrentDate);

            format = new SimpleDateFormat("yyyy-MM-dd   HH:mm",Locale.ENGLISH);
            String date = format.format(newDate);


            System.out.println("datedate" + date);
            holder.date_notification.setText(date);
        } catch (Exception e) {

        }
    }

    @Override
    public int getItemCount() {
        return list.size();

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView date_notification, message_notification ,title_notification;
        ImageView notification_image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            date_notification = itemView.findViewById(R.id.date_notification);
            message_notification = itemView.findViewById(R.id.message_notification);
            notification_image = itemView.findViewById(R.id.notification_image);
            title_notification = itemView.findViewById(R.id.title_notification);
        }
    }
}
