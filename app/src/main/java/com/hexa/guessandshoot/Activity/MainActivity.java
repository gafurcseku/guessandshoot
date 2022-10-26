package com.hexa.guessandshoot.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.github.florent37.viewanimator.ViewAnimator;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.RequestConfiguration;
import com.hexa.guessandshoot.Adapter.PagerHomeAdapter;
import com.hexa.guessandshoot.Fragment.FragmentChampions;
import com.hexa.guessandshoot.Fragment.FragmentExpectations;
import com.hexa.guessandshoot.Fragment.FragmentMore;
import com.hexa.guessandshoot.Fragment.FragmentStatistics;
import com.hexa.guessandshoot.Fragment.top_score.TheBestFragment;
import com.hexa.guessandshoot.R;
import com.hexa.guessandshoot.Settings.Settings;
import com.orhanobut.hawk.BuildConfig;
import com.orhanobut.hawk.Hawk;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import kotlin.Unit;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private AdView mAdView;

    LinearLayout expectations, topScorer, champions, statistics, more;
    ImageView arrow_back;
    public static MeowBottomNavigation bottomNavigation;
    // FrameLayout frame;
    public static ViewPager VP;
    TextView TV_Expectations, TV_TopScores, TV_Champions, TV_Statistics, TV_More;
    int pos = 0;
    public MeowBottomNavigation.Model ic_expectations = new MeowBottomNavigation.Model(0, R.drawable.ic_expectations);
    public MeowBottomNavigation.Model ic_first = new MeowBottomNavigation.Model(1, R.drawable.ic_first);
    public MeowBottomNavigation.Model ic_champions = new MeowBottomNavigation.Model(2, R.drawable.ic_champions);
    public MeowBottomNavigation.Model ic_statistics = new MeowBottomNavigation.Model(4, R.drawable.ic_trophy);
    public MeowBottomNavigation.Model ic_rank = new MeowBottomNavigation.Model(3, R.drawable.ic_rank);
    //public MeowBottomNavigation.Model ic_more = new MeowBottomNavigation.Model(4, R.drawable.ic_more);

    boolean isFirstTime = false;

    List<TextView> textViews = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        arrow_back = findViewById(R.id.arrow_back);
        TV_Expectations = findViewById(R.id.TV_Expectations);
        TV_TopScores = findViewById(R.id.TV_Ranking);
        TV_Champions = findViewById(R.id.TV_Champions);
        TV_Statistics = findViewById(R.id.TV_Statistics);
        TV_More = findViewById(R.id.TV_More);
        VP = findViewById(R.id.VP);

        Settings.setStatusBarGradiant(this);

      //  if (AdRequest.Builder){
            List<String> testDeviceIds = Arrays.asList("84CEA156EAAA3A01D0180C4CD55615FF");
            RequestConfiguration configuration =
                    new RequestConfiguration.Builder().setTestDeviceIds(testDeviceIds).build();
            MobileAds.setRequestConfiguration(configuration);

      //  }

        mAdView = findViewById(R.id.adView);

        if(!((Boolean) Hawk.get("status"))){
            AdRequest adRequest = new AdRequest.Builder().build();
            mAdView.loadAd(adRequest);
        }else {
            mAdView.setVisibility(View.GONE);
        }


        textViews.add(TV_Expectations);
        textViews.add(TV_TopScores);
        textViews.add(TV_Champions);
        textViews.add(TV_More);
        textViews.add(TV_Statistics);


        //frame = findViewById(R.id.frame);

        bottomNavigation = findViewById(R.id.bottomNavigation);

        bottomNavigation.add(ic_expectations);
        bottomNavigation.add(ic_first);
        bottomNavigation.add(ic_champions);
        bottomNavigation.add(ic_rank);
        bottomNavigation.add(ic_statistics);


        transactionToExpecationsFragment();


        setViewPager();


        expectations = findViewById(R.id.expectations);
        expectations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VP.setCurrentItem(0);
                // getSupportFragmentManager().beginTransaction().replace(R.id.frame, new FragmentExpectations()).commit();

            }
        });


        topScorer = findViewById(R.id.topScorer);
        topScorer.setOnClickListener(v -> VP.setCurrentItem(pos)
                //getSupportFragmentManager().beginTransaction().replace(R.id.frame, new TheBestFragment()).commit()
        );

        champions = findViewById(R.id.champions);
        champions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (Hawk.contains("User")) {

                    if (Hawk.get("User").toString().equals("")) {
                        Toast.makeText(MainActivity.this, getResources().getString(R.string.pleaseLogin), Toast.LENGTH_LONG).show();

                    } else {
                        VP.setCurrentItem(2);
                        // getSupportFragmentManager().beginTransaction().replace(R.id.frame, new FragmentChampions()).commit();

                    }

                } else {
                    Toast.makeText(MainActivity.this, getResources().getString(R.string.pleaseLogin), Toast.LENGTH_LONG).show();
//                    Intent intent = new Intent(, LoginActivity.class);
////                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                    startActivity(intent);
////                    getActivity().finishAffinity();
                }


            }
        });


        statistics = findViewById(R.id.statistics);
        statistics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VP.setCurrentItem(3);
                //     getSupportFragmentManager().beginTransaction().replace(R.id.frame, new FragmentStatistics()).commit();

            }
        });


        more = findViewById(R.id.more);
        more.setOnClickListener(v -> transactionToMoreFragment());


    }

    public void setViewPager() {
        VP.setOffscreenPageLimit(5);
        PagerHomeAdapter pagerHomeAdapter = new PagerHomeAdapter(getSupportFragmentManager());
        VP.setAdapter(pagerHomeAdapter);
        VP.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                bottomNavigation.show(position, true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        bottomNavigation.show(0, true);
        setpostion(0);


        bottomNavigation.setOnClickMenuListener(p1 -> {
            setpostion(p1.getId());
            Log.d(TAG, "invoke: Switch Fragment to " + p1.getId());


            switch (p1.getId()) {
                case 0:
                    pos = 0;
                    VP.setCurrentItem(0);
                    // transactionToExpecationsFragment();
                    break;
                case 1:
                    if (Hawk.contains("User")) {
                        Log.d(TAG, "invoke: Found User");
                        if (Hawk.get("User").toString().equals("")) {
                            Log.d(TAG, "invoke: Found User and it empty Back To " + pos);
                            Toast.makeText(MainActivity.this, getResources().getString(R.string.pleaseLogin), Toast.LENGTH_LONG).show();
                            new Handler().postDelayed(() -> {
                                setpostion(pos);
                                bottomNavigation.show(pos, true);
                            }, 200);

                            return Unit.INSTANCE;
                        } else {
                            Log.d(TAG, "invoke: Found User and Login");
                            pos = 1;
                            VP.setCurrentItem(1);
                            // getSupportFragmentManager().beginTransaction().replace(R.id.frame, new TheBestFragment()).commit();

                        }
                    } else {
                        Log.d(TAG, "invoke: NO User");
                        Toast.makeText(MainActivity.this, getResources().getString(R.string.pleaseLogin), Toast.LENGTH_LONG).show();
                    }
                    break;
                case 2:
                    if (Hawk.contains("User")) {
                        Log.d(TAG, "invoke: Found User");
                        if (Hawk.get("User").toString().equals("")) {
                            Log.d(TAG, "invoke: Found User and it empty Back To " + pos);
                            Toast.makeText(MainActivity.this, getResources().getString(R.string.pleaseLogin), Toast.LENGTH_LONG).show();
                            new Handler().postDelayed(() -> {
                                setpostion(pos);
                                bottomNavigation.show(pos, true);
                            }, 200);

                            return Unit.INSTANCE;
                        } else {
                            Log.d(TAG, "invoke: Found User and Login");
                            pos = 2;
                            //getSupportFragmentManager().beginTransaction().replace(R.id.frame, new FragmentChampions()).commit();
                            VP.setCurrentItem(pos);
                        }

                    } else {
                        Log.d(TAG, "invoke: NO User");
                        Toast.makeText(MainActivity.this, getResources().getString(R.string.pleaseLogin), Toast.LENGTH_LONG).show();
                    }
                    break;
                case 3:
                    pos = 3;
                    VP.setCurrentItem(pos);
                    // getSupportFragmentManager().beginTransaction().replace(R.id.frame, new FragmentStatistics()).commit();
                    break;
                case 4:
                    pos = 4;
                    VP.setCurrentItem(pos);
                    // transactionToMoreFragment();
                    break;
                case 5:
                    pos = 5;
                    VP.setCurrentItem(pos);
                    // transactionToMoreFragment();
                    break;

            }
            return Unit.INSTANCE;
        });

    }

    private void transactionToExpecationsFragment() {
//        FragmentExpectations frag = new FragmentExpectations();
//        getSupportFragmentManager().beginTransaction().replace(R.id.frame, frag).commit();
        VP.setCurrentItem(0);
    }

    private void transactionToMoreFragment() {
        VP.setCurrentItem(4);
//        FragmentMore frag = new FragmentMore();
//        getSupportFragmentManager().beginTransaction().replace(R.id.frame, frag).commit();
    }

    boolean is_back = false;

    @Override
    public void onBackPressed() {

        if (is_back) {
            finishAffinity();
        } else {
            is_back = true;
            Toast.makeText(this, getText(R.string.message_exit), Toast.LENGTH_SHORT).show();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    is_back = false;
                }
            }, 2000);


        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==123){
            SharedPrefs.save(this, "is_show_exp_tut", true);
            SharedPrefs.save(this, "is_show_more_tut", true);
            SharedPrefs.save(this, "is_show_profile_tut", true);

            FragmentExpectations frag = new FragmentExpectations();
           //   ((MainActivity) Objects.requireNonNull(getActivity())).setViewPager();
            bottomNavigation.show(0, true);
            setpostion(0);
            VP.setCurrentItem(0);
        }
        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
            fragment.onActivityResult(requestCode, resultCode, data);
        }
    }

    public void setpostion(final int position) {


        runOnUiThread(() -> {
            for (int i = 0; i < textViews.size(); i++) {
                if (i == position) {
                    ViewAnimator.animate(textViews.get(i))
                            .translationY(0, -20)
                            .duration(300).start();
                } else {
                    ViewAnimator.animate(textViews.get(i))
                            .translationY(0).duration(200).start();
                }
            }
        });

    }


}
