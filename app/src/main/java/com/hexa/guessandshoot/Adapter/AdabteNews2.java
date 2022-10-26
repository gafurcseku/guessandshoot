package com.hexa.guessandshoot.Adapter;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.hexa.guessandshoot.Modules.Db_News;
import com.hexa.guessandshoot.R;
import com.squareup.picasso.Picasso;

import java.util.List;


public class AdabteNews2 extends RecyclerView.Adapter<AdabteNews2.viewholderq> {
    List<Db_News> data;
    Activity activity;
    int i = -1;

    public AdabteNews2(List<Db_News> data, Activity activity) {
        this.data = data;
        this.activity = activity;
    }

    public AdabteNews2(Activity activity) {
        this.data = data;
        this.activity = activity;
    }

    @NonNull
    @Override
    public viewholderq onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        viewholderq root = new viewholderq(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rec_news, parent, false));
       // setmargin(viewType, root);
        Log.e("root", "viewType()" + viewType);

        return root;
    }

    @Override
    public void onBindViewHolder(@NonNull final viewholderq holder, final int position) {
        // System.out.println(data.get( position ).getClass());
        int viewType = getItemViewType(position);
         viewholderq item = holder;

        holder.text_news.setText(data.get(position).getName());


        Picasso.get().load(data.get(position).getImage()).into(holder.image_news);

    }


    @Override
    public int getItemViewType(int position) {

        super.getItemViewType(position);
        return position;
    }

    @Override
    public int getItemCount() {
         return data.size();
       // return 5;
    }


    public class viewholderq extends RecyclerView.ViewHolder {

        TextView text_news;
        ImageView image_news;


        public viewholderq(View itemView) {
            super(itemView);
            text_news=itemView.findViewById(R.id.text_news);
            image_news=itemView.findViewById(R.id.image_news);

       }

    }

//    public void setmargin(int position, viewholderq holder) {
//        if (position == (4)) {
//            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
//                    LinearLayout.LayoutParams.MATCH_PARENT,
//                    LinearLayout.LayoutParams.WRAP_CONTENT
//            );
//            params.setMargins((int) activity.getResources().getDimension(R.dimen._5sdp), (int) activity.getResources().getDimension(R.dimen._5sdp), (int) activity.getResources().getDimension(R.dimen._5sdp), (int) activity.getResources().getDimension(R.dimen._65sdp));
//            holder.linear.setLayoutParams(params);
//        }
//    }
}
