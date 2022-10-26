package com.hexa.guessandshoot.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hexa.guessandshoot.Modules.Db_Ranking;
import com.hexa.guessandshoot.R;
import com.hexa.guessandshoot.Modules.Db_News;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdapterNews extends RecyclerView.Adapter<AdapterNews.ViewHolder> {
    ArrayList<Db_News> list=new ArrayList<>() ;
    Context context ;


    public AdapterNews(ArrayList<Db_News> list, Context context) {
        this.list = list;
        this.context = context;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rec_news,null,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.text_news.setText(list.get(position).getName());


        Picasso.get().load(list.get(position).getImage()).into(holder.image_news);


        //   Picasso.get().load("https://www.google.com/url?sa=i&source=images&cd=&ved=2ahUKEwjuufz6w6_mAhXZDmMBHRAEAhcQjRx6BAgBEAQ&url=https%3A%2F%2Ffootytimes.com%2F2018%2F06%2F10%2Ffootball-enjoy%2F&psig=AOvVaw31Reuq5WbO5un7yVbL3Bbj&ust=1576219526762586").placeholder(R.drawable.download).into(holder.image_news);

    }

    @Override
    public int getItemCount() {
        return list.size();

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView text_news;
        ImageView image_news;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            text_news=itemView.findViewById(R.id.text_news);
            image_news=itemView.findViewById(R.id.image_news);
        }
    }
}
