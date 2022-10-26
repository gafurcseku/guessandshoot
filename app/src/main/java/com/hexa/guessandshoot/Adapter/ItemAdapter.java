package com.hexa.guessandshoot.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.hexa.guessandshoot.Modules.Club;
import com.hexa.guessandshoot.Modules.Clubs;
import com.hexa.guessandshoot.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mohammed alnjjar on 4/26/18.
 */

public class ItemAdapter extends BaseAdapter implements SpinnerAdapter {

    private final Activity activity;
    public List<Clubs> asr = new ArrayList<>();

    public ItemAdapter(Activity context, List<Clubs> asr,String index) {
        this.asr.add(new Clubs(new Club(context.getString(R.string.selectClub) ,-1))) ;
        if (!index.equals("0")){
            for (int i = 0; i <asr.size() ; i++) {
                if (index.equals(asr.get(i).group_id)){
                    this.asr.add(asr.get(i))  ;
                }
            }
        }else {
            this.asr.addAll(asr);
        }


        activity = context;

    }


    public int getCount() {
        return asr.size();
    }

    public Object getItem(int i) {
        return asr.get(i);
    }

    public long getItemId(int i) {
        return (long) i;
    }


    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {

       LayoutInflater layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
       View view =  layoutInflater.inflate(R.layout.item_dawn_spinner,null);

       TextView text =  view.findViewById(R.id.text);
       text.setText(asr.get(position).club.getName());

//        TextView txt = new TextView(activity);
//        txt.setPadding(16, 16, 16, 16);
//        txt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
//
//        txt.setText(asr.get(position));
//        txt.setTextColor(activity.getResources().getColor(R.color.backgroundlogo));
        return view;
    }

    public View getView(int position, View view, ViewGroup viewgroup) {

        LayoutInflater layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view2 =  layoutInflater.inflate(R.layout.item_spinner,null);

        TextView text =  view2.findViewById(R.id.text);
        ImageView image_view =  view2.findViewById(R.id.image_view);

        text.setText(asr.get(position).club.getName());


        return view2;
    }

}
