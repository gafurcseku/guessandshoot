package com.hexa.guessandshoot.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hexa.guessandshoot.Modules.Faq;
import com.hexa.guessandshoot.R;


import java.util.ArrayList;
import java.util.List;


public class FAQRvAdabter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<Faq> questionList;
    Activity activity;
    int i;
    int nowclicked = 0;
    boolean show = false;


    ArrayList<Integer> integers;

    public FAQRvAdabter(Activity activity, ArrayList<Integer> integers, List<Faq> questionList) {

        this.activity = activity;
        this.integers = integers;
        this.questionList = questionList;

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // viewholder root= data.get( viewType ).viewh(parent);

        return new viewholderq(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_faq, parent, false));


    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {

        int viewType = getItemViewType(position);


        final viewholderq item = (viewholderq) holder;

        Faq data = questionList.get(position);
        item.TV_ansur.setText(data.answer);
        item.TV_question.setText(data.question);

        item.LY_Ansor.setVisibility(show & nowclicked == position ? View.VISIBLE : View.GONE);

     //   item.Ly_qoustion.setBackground(activity.getResources().getDrawable(show & nowclicked == position ? R.drawable.ic_open_faq : R.drawable.ic_close_faq));

        if (show && nowclicked == position) {
            if (integers.get(position) == 0) {
               // ObjectAnimator colorFade = ObjectAnimator.ofObject(item.Ly_qoustion, "backgroundColor", new ArgbEvaluator(), Color.argb(255, 255, 255, 255), Color.argb(255, 250, 250, 250));
                //colorFade.setDuration(500);
               // colorFade.start();
                item.img_Open.setImageResource(R.drawable.ic_shem_open);
                item.img_Open.setAlpha(0.0f);
                item.img_Open.animate().alpha(1.0f).setDuration(500).start();
                integers.set(position, 1);
            }

        } else {
            if (integers.get(position) == 1) {
                //ObjectAnimator colorFade = ObjectAnimator.ofObject(item.Ly_qoustion, "backgroundColor", new ArgbEvaluator(), Color.argb(255, 186, 241, 240), Color.argb(255, 255, 255, 255));
               // colorFade.setDuration(500);
               // colorFade.start();
                integers.set(position, 0);
                item.img_Open.setAlpha(0.0f);
                item.img_Open.setImageResource(R.drawable.ic_shem_down);
                item.img_Open.animate().alpha(1.0f).setDuration(500).start();
            }
        }

        item.Ly_qoustion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (nowclicked != position) {
                    show = false;
                }
                show = !show;
                nowclicked = position;

                for (int j = 0; j < getItemCount(); j++) {
                    notifyItemChanged(j);
                }

            }
        });
    }


    @Override
    public int getItemCount() {
        return questionList.size();
    }


    public class viewholderq extends RecyclerView.ViewHolder {

        TextView TV_ansur, TV_question;
        ImageView img_Open;
        LinearLayout LY_Ansor;
        LinearLayout Ly_qoustion;

        public viewholderq(View itemView) {
            super(itemView);
            LY_Ansor = itemView.findViewById(R.id.LY_Ansor);
            img_Open = itemView.findViewById(R.id.img_Open);
            Ly_qoustion = itemView.findViewById(R.id.Ly_qoustion);
            TV_ansur = itemView.findViewById(R.id.TV_ansur);
            TV_question = itemView.findViewById(R.id.TV_question);

            img_Open.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (nowclicked != getAdapterPosition()) {
                        show = false;
                    }
                    show = !show;
                    nowclicked = getAdapterPosition();
                    notifyDataSetChanged();

                }
            });


        }

    }


}
