package com.hexa.guessandshoot.Fragment;


import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.getkeepsafe.taptargetview.TapTarget;
import com.getkeepsafe.taptargetview.TapTargetSequence;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.firebase.messaging.FirebaseMessaging;
import com.hexa.guessandshoot.Activity.Auth.LoginActivity;
import com.hexa.guessandshoot.Activity.Auth.SignUpActivity;
import com.hexa.guessandshoot.Activity.Auth.SuccessActivity;
import com.hexa.guessandshoot.Activity.ChampionWinnersActivity;
import com.hexa.guessandshoot.Activity.FAQActivity;
import com.hexa.guessandshoot.Activity.HistoryActivity;
import com.hexa.guessandshoot.Activity.MainActivity;
import com.hexa.guessandshoot.Activity.NewsActivity;
import com.hexa.guessandshoot.Activity.RankActivity;
import com.hexa.guessandshoot.Activity.SharedPrefs;
import com.hexa.guessandshoot.Activity.StatisticsActivity;
import com.hexa.guessandshoot.Activity.StatisticsActivity2;
import com.hexa.guessandshoot.R;
import com.hexa.guessandshoot.Settings.Settings;
import com.hexa.guessandshoot.SplashActivity;
import com.orhanobut.hawk.Hawk;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentMore extends Fragment {
    LinearLayout News, Notification, AboutUs, TermsAndConditions, PrivacyPolicy, ContactUs, linear_ranking, linear_statistics,
            ShowTut, linear_championWinners, logout, linear_Change_Language, linear_history ,Statistics, ly_FAQ,linear_subscribe;
    ImageView facebook, Twitter, insta;
    TextView login_or_sing;
    Switch switch1;
    ImageView arrow_back ;
    private AdView mAdView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = getLayoutInflater().inflate(R.layout.more, container, false);

        mAdView =  view.findViewById(R.id.adView);

        if(!((Boolean) Hawk.get("status"))){
            AdRequest adRequest = new AdRequest.Builder().build();
            mAdView.loadAd(adRequest);
        }else {
            mAdView.setVisibility(View.GONE);
        }

        login_or_sing = view.findViewById(R.id.login_or_sing);
        insta = view.findViewById(R.id.insta);
        logout = view.findViewById(R.id.logout);
        News = view.findViewById(R.id.News);
        ContactUs = view.findViewById(R.id.ContactUs);
        Notification = view.findViewById(R.id.Notification);
        AboutUs = view.findViewById(R.id.AboutUs);
        facebook = view.findViewById(R.id.facebook);
        switch1 = view.findViewById(R.id.switch1);
        Statistics = view.findViewById(R.id.Statistics);
        arrow_back = view.findViewById(R.id.arrow_back);
        ly_FAQ = view.findViewById(R.id.ly_FAQ);

        linear_ranking = view.findViewById(R.id.linear_ranking);
        linear_championWinners = view.findViewById(R.id.linear_championWinners);
        linear_statistics = view.findViewById(R.id.linear_statistics);

        linear_Change_Language = view.findViewById(R.id.linear_Change_Language);
        linear_subscribe = view.findViewById(R.id.linear_subscribe);
        linear_history = view.findViewById(R.id.linear_history);

        Twitter = view.findViewById(R.id.Twitter);
        TermsAndConditions = view.findViewById(R.id.TermsAndConditions);
        PrivacyPolicy = view.findViewById(R.id.PrivacyPolicy);
        ShowTut = view.findViewById(R.id.ShowTut);


        switch1.setChecked(Settings.IsSubscribe());

        ly_FAQ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), FAQActivity.class);
                startActivity(intent);
            }
        });
        ContactUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), com.hexa.guessandshoot.Activity.StaticePage.ContactUs.class);
                startActivity(intent);
            }
        });
        PrivacyPolicy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), com.hexa.guessandshoot.Activity.StaticePage.PrivacyPolicy.class);
                startActivity(intent);
            }
        });

        TermsAndConditions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), com.hexa.guessandshoot.Activity.StaticePage.TermsAndConditions.class);
                startActivity(intent);
            }
        });
        News.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchMarket();
//                Intent intent = new Intent(getContext(), NewsActivity.class);
//                startActivity(intent);
            }
        });
        AboutUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), com.hexa.guessandshoot.Activity.StaticePage.AboutUs.class);
                startActivity(intent);
            }
        });
        Statistics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), StatisticsActivity2.class);
                startActivity(intent);
            }
        });

        Notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                switch1.setChecked(!switch1.isChecked());

                if (switch1.isChecked()) {
                    Hawk.put("notfi", true);
                    //switch_Notifications.setChecked(true);
                    FirebaseMessaging.getInstance().subscribeToTopic("guess-shoot");
                } else {
                    Hawk.put("notfi", false);
                    //switch_Notifications.setChecked(false);
                    FirebaseMessaging.getInstance().unsubscribeFromTopic("guess-shoot");
                }

//                Intent intent = new Intent(getContext(), com.hexa.guessandshoot.Activity.Notification.class);
//                startActivity(intent);
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (Hawk.contains("User")) {

                    if (Hawk.get("User").toString().equals("")) {
                        Intent intent = new Intent(getContext(), LoginActivity.class);
//                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
//                        getActivity().finishAffinity();
                    } else {

                        new AlertDialog.Builder(getContext())
                                .setMessage(getActivity().getResources().getString(R.string.are_you_logout))
                                .setCancelable(false)
                                .setPositiveButton(getActivity().getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {

                                        Hawk.put("User","");


                                        Intent intent = new Intent(getContext(), SplashActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(intent);
                                        getActivity().finishAffinity();

                                    }
                                })
                                .setNegativeButton(getActivity().getResources().getString(R.string.no), null)
                                .show();
                    }

                } else {
                    Intent intent = new Intent(getContext(), LoginActivity.class);
//                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
//                    getActivity().finishAffinity();
                }


            }
        });

        arrow_back.setOnClickListener(v -> getActivity().finish());
        ShowTut.setOnClickListener(v -> {

            getActivity().setResult(123);
            getActivity().finish();

        });


        facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String url = Settings.getSettings().getFacebook() + "";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);

            }
        });
        Twitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String url = Settings.getSettings().getTwitter() + "";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);

            }
        });
        insta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String url = Settings.getSettings().getInstagram() + "";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);

            }
        });

        linear_ranking.setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), RankActivity.class));
        });

        linear_statistics.setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), StatisticsActivity.class));
        });


        linear_championWinners.setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), ChampionWinnersActivity.class));

        });

        linear_Change_Language.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_Language();
            }
        });

        linear_subscribe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(requireActivity(), SuccessActivity.class);
                startActivity(intent);
            }
        });

        linear_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), HistoryActivity.class));
            }
        });

        if (Hawk.contains("User")) {

            if (Hawk.get("User").toString().equals("")) {
                login_or_sing.setText(getContext().getResources().getString(R.string.sign_in));//true

            } else {
                login_or_sing.setText(getContext().getResources().getString(R.string.Sign_Out));//true

            }

        } else {
            login_or_sing.setText(getContext().getResources().getString(R.string.sign_in));//true
        }


        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }

    @Override
    public void onResume() {
        super.onResume();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                boolean isShowMoreTut = SharedPrefs.getBoolean(getContext(), "is_show_more_tut", true);
                if (isShowMoreTut) {
                    showMoreTutorial();
                }
            }
        },300);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        if (isVisibleToUser)




        super.setUserVisibleHint(isVisibleToUser);
    }
    private static final String TAG = "FragmentMore";

    private void showMoreTutorial() {
        Log.d(TAG, "showMoreTutorial: ");
        new TapTargetSequence(getActivity())
                .targets(
                        createTargetView(linear_history, "", getString(R.string.tut_news_desc)),
                        createTargetView(linear_championWinners, "", getString(R.string.tut_winner_desc)),
                        createTargetView(Statistics, "", getString(R.string.tut_static_desc))
                )
                .considerOuterCircleCanceled(true)
                .continueOnCancel(true)
                .listener(new TapTargetSequence.Listener() {
                    @Override
                    public void onSequenceFinish() {
                        SharedPrefs.save(getContext(), "is_show_more_tut", false);
                    }

                    @Override
                    public void onSequenceStep(TapTarget lastTarget, boolean targetClicked) {

                    }

                    @Override
                    public void onSequenceCanceled(TapTarget lastTarget) {
                        SharedPrefs.save(getContext(), "is_show_more_tut", false);
                    }
                }).start();
    }

    TapTarget createTargetView(View view, String title, String descreption) {
        return TapTarget.forView(view, title, descreption)
                .dimColor(android.R.color.transparent)
                .tintTarget(false)
                .outerCircleColor(R.color.colorAccent)
                .targetCircleColor(R.color.colorPrimary)
                .textColor(android.R.color.white);
    }

    public void dialog_Language() {
        new AlertDialog.Builder(getActivity()).setMessage(getText(R.string.change_Lang))
                .setPositiveButton(getText(R.string.yes), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        String lang;

                        if (Hawk.get("lang").equals("en")) {
                            lang = "ar";
                        } else {
                            lang = "en";
                        }

                        Hawk.put("lang", lang);
                        Intent i = new Intent(getActivity(), SplashActivity.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i);
                        getActivity().finish();


                        dialog.cancel();
                    }
                }).setNegativeButton(getText(R.string.no), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        }).show();




    }
    private void launchMarket() {
        Uri uri = Uri.parse("market://details?id=com.hexa.guessandshoot");
        Intent myAppLinkToMarket = new Intent(Intent.ACTION_VIEW, uri);
        try {
            startActivity(myAppLinkToMarket);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(getActivity(), " unable to find market app", Toast.LENGTH_LONG).show();
        }
    }
}