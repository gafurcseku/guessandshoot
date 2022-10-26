package com.hexa.guessandshoot.Adapter;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;


import com.hexa.guessandshoot.Fragment.ArrangeFragment;
import com.hexa.guessandshoot.Fragment.Dawrena.DawrenaFragment;
import com.hexa.guessandshoot.Fragment.FragmentChampions;
import com.hexa.guessandshoot.Fragment.FragmentExpectations;
import com.hexa.guessandshoot.Fragment.top_score.TheBestFragment;

import java.util.ArrayList;


public class PagerHomeAdapter extends FragmentPagerAdapter {
    ArrayList<String> amgs;

    public PagerHomeAdapter(FragmentManager fm) {
        super( fm );

    }

    @Override
    public Fragment getItem(int position) {


        Fragment fragment =null ;
        Bundle bundle=new Bundle();
        switch (position){
            case 0 :
                fragment = new FragmentExpectations();
                break;
            case 1:
                fragment = new TheBestFragment();
                break;
            case 2:
                fragment = new FragmentChampions();
                break;
            case 3:
                fragment = new ArrangeFragment();
                break;
            case 4:

                fragment = new DawrenaFragment();
               // fragment = new FragmentStatistics();
               // fragment = new FragmentMore();
                break;

        }



            return fragment;

    }


    @Override
    public int getCount() {
        // Show 3 total pages.
        return 5;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return "ma";
    }
}
