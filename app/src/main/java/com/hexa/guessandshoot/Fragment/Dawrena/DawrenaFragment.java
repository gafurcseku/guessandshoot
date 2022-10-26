package com.hexa.guessandshoot.Fragment.Dawrena;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.hexa.guessandshoot.Activity.Auth.EditeMyAccount;
import com.hexa.guessandshoot.Dialog.DialogAddNewTeam;
import com.hexa.guessandshoot.Dialog.DialogEnterCode;
import com.hexa.guessandshoot.Dialog.DialogEnterRandom;
import com.hexa.guessandshoot.Fragment.NoUserFaindFragment;
import com.hexa.guessandshoot.Fragment.top_score.TheBestFragment;
import com.hexa.guessandshoot.Fragment.top_score.TopGoalKeeperFragment;
import com.hexa.guessandshoot.Fragment.top_score.TopScorerFragment;
import com.hexa.guessandshoot.R;
import com.hexa.guessandshoot.Settings.Settings;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.orhanobut.hawk.Hawk;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;


public class DawrenaFragment extends Fragment {



    ViewPager viewPager ;
    TabLayout tab ;
    View indicator ;
    private int indicatorWidth;
    String lang ;
    TextView textSettings ;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dawrena, container, false);

        viewPager = view.findViewById(R.id.viewPager) ;
        tab = view.findViewById(R.id.tab) ;
        indicator = view.findViewById(R.id.indicator) ;
        textSettings = view.findViewById(R.id.textSettings) ;
        lang = Hawk.get("lang");

        textSettings.setText(getText(R.string.join_message) +" "+Settings.getSettings().textDateEnd);
        setupViewPager(viewPager) ;


        return view ;
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }


    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        if (Settings.getUser()==null){
            adapter.addFragment(new NoUserFaindFragment(), getString(R.string.Your_Team));
        }else {
            adapter.addFragment(new MyTeamFragment(), getString(R.string.Your_Team));
        }

        adapter.addFragment(new TeamRankingFragment(), getString(R.string.Team_Ranking));
        viewPager.setAdapter(adapter);
        tab.setupWithViewPager(viewPager);
        tab.post(new Runnable() {
            @Override
            public void run() {
                indicatorWidth = tab.getWidth() / tab.getTabCount();

                //Assign new width
                FrameLayout.LayoutParams indicatorParams = (FrameLayout.LayoutParams) indicator.getLayoutParams();
                indicatorParams.width = indicatorWidth;
                indicator.setLayoutParams(indicatorParams);
            }
        });
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float positionOffset, int positionOffsetPx) {
                FrameLayout.LayoutParams params = (FrameLayout.LayoutParams)indicator.getLayoutParams();

                //Multiply positionOffset with indicatorWidth to get translation
                float translationOffset =  (positionOffset+i) * indicatorWidth ;
                if (lang.equals("en")){
                    params.leftMargin = (int) translationOffset;
                }else{
                    params.rightMargin = (int) translationOffset;
                }

                indicator.setLayoutParams(params);
            }

            @Override
            public void onPageSelected(int i) {

            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {

            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        Log.e("onActivityResult" ,"onActivityResult") ;
        super.onActivityResult(requestCode, resultCode, data);
        for (Fragment fragment : getChildFragmentManager().getFragments()) {
            fragment.onActivityResult(requestCode, resultCode, data);
        }
    }


    //    @Override
//    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
//            CropImage.ActivityResult result = CropImage.getActivityResult(data);
//            if (resultCode == RESULT_OK) {
//                Uri resultUri = result.getUri();
//                dialogAddNewTeam.setImage(resultUri);
//              //  logo_img = new File(resultUri.getPath());
//
//            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
//                Exception error = result.getError();
//            }
//        }
//
//    }


}