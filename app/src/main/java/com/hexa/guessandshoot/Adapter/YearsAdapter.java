package com.hexa.guessandshoot.Adapter;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.hexa.guessandshoot.R;
import com.hexa.guessandshoot.Settings.Settings;

import com.orhanobut.hawk.Hawk;

import java.util.List;


public class YearsAdapter extends BaseAdapter implements SpinnerAdapter {

    private final Activity activity;
    private List<String> asr;

    public YearsAdapter(Activity context, List<String> asr) {
        this.asr = asr;
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
        TextView txt = new TextView(activity);
        txt.setPadding(50, 16, 50, 16);
        txt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
        //txt.setCompoundDrawablePadding(25);

        txt.setTypeface(txt.getTypeface(), Typeface.BOLD);
        txt.setGravity(Gravity.CENTER);



        txt.setText(asr.get(position));
        txt.setTextColor(activity.getResources().getColor(R.color.prawn));
        return txt;
    }

    public View getView(int i, View view, ViewGroup viewgroup) {
        TextView txt = new TextView(activity);
        txt.setPadding(50, 16, 50, 16);
        txt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
        //txt.setCompoundDrawablePadding(25);
        txt.setTypeface(txt.getTypeface(), Typeface.BOLD);
        txt.setGravity(Gravity.CENTER);



        txt.setText(asr.get(i));
        txt.setTextColor(activity.getResources().getColor(R.color.prawn));
        return txt;
    }

}
