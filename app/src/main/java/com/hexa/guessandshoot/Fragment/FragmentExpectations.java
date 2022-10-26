package com.hexa.guessandshoot.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.getkeepsafe.taptargetview.TapTarget;
import com.getkeepsafe.taptargetview.TapTargetSequence;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.hexa.guessandshoot.Activity.Auth.my_account;
import com.hexa.guessandshoot.Activity.MainActivity;
import com.hexa.guessandshoot.Activity.MoreActivity;
import com.hexa.guessandshoot.Activity.Notification;
import com.hexa.guessandshoot.Activity.SharedPrefs;
import com.hexa.guessandshoot.Adapter.newApdater.PaginationAdapter;
import com.hexa.guessandshoot.Modules.Db_My_Expections_My_Account;
import com.hexa.guessandshoot.R;
import com.hexa.guessandshoot.Settings.ApiService;
import com.hexa.guessandshoot.Settings.PaginationScrollListener;
import com.hexa.guessandshoot.Settings.Settings;
import com.orhanobut.hawk.Hawk;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;
//import me.zhanghai.android.fastscroll.FastScrollerBuilder;

import static com.hexa.guessandshoot.Adapter.newApdater.PaginationListener.PAGE_SIZE;
import static com.hexa.guessandshoot.Adapter.newApdater.PaginationListener.PAGE_START;


public class FragmentExpectations extends Fragment {

    private static final String TAG = "FragmentExpectations";

    public static int ClickCount = 0;
    public static InterstitialAd mInterstitialAd;

    RecyclerView rv_test;

    FrameLayout ImageL;
    FrameLayout notifications;
    ImageView iamgenotf ,img_seen;
    CircleImageView image_account;

    PaginationAdapter adapter;
    //NestedScrollView nsv_exp;
    private int currentPage = PAGE_START;
    private boolean isLastPage = false;
    private boolean isLoading = false;
    int itemCount = 0;
    boolean isShowExpTut = false;
    ImageView image_more ;

    TextView mywidget ;
    RelativeLayout ly_news;


    SwipeRefreshLayout swipe ;

    LinearLayout item_connection ;
    TextView retry ;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = getLayoutInflater().inflate(R.layout.activity_expectations, container, false);
        //nsv_exp = view.findViewById(R.id.nsv_exp);
        rv_test = view.findViewById(R.id.recyclerMyExpectionsMyAccount);
        notifications = view.findViewById(R.id.notification);
        image_account = view.findViewById(R.id.image_account);
        ImageL = view.findViewById(R.id.ImageL);
        img_seen = view.findViewById(R.id.img_seen);
        swipe = view.findViewById(R.id.swipe);
        iamgenotf = view.findViewById(R.id.iamgenotf);
        image_more = view.findViewById(R.id.image_more);
        mywidget = view.findViewById(R.id.mywidget);
        item_connection = view.findViewById(R.id.item_connection);
        ly_news = view.findViewById(R.id.ly_news);
        retry = view.findViewById(R.id.retry);


        if (Settings.getCount()!=0){
            img_seen.setVisibility(View.VISIBLE);
        }else {
            img_seen.setVisibility(View.GONE);
        }
        if (Settings.getSettings().news.equals("")){
            ly_news.setVisibility(View.GONE);
        }else{
            mywidget.setText(Settings.getSettings().news);
            mywidget.setSelected(true);
        }


        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d(TAG, "onViewCreated: ");
        isShowExpTut = SharedPrefs.getBoolean(getContext(), "is_show_exp_tut", true);
        Log.d(TAG, "onViewCreated: SHOW Tutorial :: " + isShowExpTut);


        initAdd();

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        rv_test.setLayoutManager(layoutManager);
        adapter = new PaginationAdapter(getActivity(), new ArrayList<>());
        rv_test.setHasFixedSize(true);
        rv_test.setItemAnimator(null);
        rv_test.setAdapter(adapter);
        rv_test.setNestedScrollingEnabled(false);

        getMatches(false,true);

        rv_test.addOnScrollListener(new PaginationScrollListener(layoutManager) {
            @Override
            protected void loadMoreItems() {
                  currentPage++;
//                   isLoading = true;
//                   adapter.addLoading();
                if (!isLastPage) getMatches(false,false);
            }

            @Override
            public int getTotalPageCount() {
                return 0;
            }

            @Override
            public boolean isLastPage() {
                return isLastPage;
            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }
        });
//        rv_test.getViewTreeObserver().addOnScrollChangedListener(() -> {
//            View view2 = (View) rv_test.getChildAt(rv_test.getChildCount() - 1);
//
//            int diff = (view2.getBottom() - (rv_test.getHeight() + rv_test.getScrollY()));
//
//            if (diff == 0) {
//                Log.d(TAG, "onViewCreated: BOTTOM");
//                isLoading = true;
//                currentPage++;
//
//                if (!isLastPage)
//                    getMatches(true);
//            }
//        });

        //        rv_test.addOnScrollListener(new PaginationListener(layoutManager) {
//            @Override
//            protected void loadMoreItems() {
//                Log.d(TAG, "loadMoreItems: ");
//                isLoading = true;
//                currentPage++;
//                getMatches();
//            }
//
//            @Override
//            public boolean isLastPage() {
//                return isLastPage;
//            }
//
//            @Override
//            public boolean isLoading() {
//                return isLoading;
//            }
//        });

        if (Hawk.contains("User")) {
            if (!Hawk.get("User").toString().equals("")) {
                ImageL.setVisibility(View.VISIBLE);
                notifications.setVisibility(View.VISIBLE);
            }
        }

        ImageL.setOnClickListener(v -> {
            if (!Hawk.contains("User") || Hawk.get("User").toString().isEmpty()) {
                Toast.makeText(getContext(), R.string.profile_need_login, Toast.LENGTH_SHORT).show();
                return;
            }
            Intent intent = new Intent(getContext(), my_account.class);
            startActivity(intent);
        });

        image_more.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), MoreActivity.class);
            startActivityForResult(intent,1);
        });
        notifications.setOnClickListener(v -> {
            if (!Hawk.contains("User") || Hawk.get("User").toString().isEmpty()) {
                Toast.makeText(getContext(), R.string.notifications_need_login, Toast.LENGTH_SHORT).show();
                return;
            }
            Settings.setCount(0);
            img_seen.setVisibility(View.GONE);
            Intent intent = new Intent(getContext(), Notification.class);
            startActivity(intent);
        });

        try {
            String userImage = Settings.getUser().getImageProfile();
            if (userImage != null)
                Picasso.get().load(userImage).error(getResources().getDrawable(R.drawable.ic_account)).into(image_account);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void initAdd() {
        AdRequest adRequest = new AdRequest.Builder().build();
        InterstitialAd.load(requireActivity(), requireActivity().getString(R.string.full_ad_unit), adRequest, new InterstitialAdLoadCallback() {
            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                FragmentExpectations.mInterstitialAd = null;
            }

            @Override
            public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                FragmentExpectations.mInterstitialAd = interstitialAd;
                FragmentExpectations.mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                    @Override
                    public void onAdDismissedFullScreenContent() {
                        initAdd();
                        super.onAdDismissedFullScreenContent();

                    }
                });
            }
        });
    }

    public void getMatches(boolean showDialogLoading,boolean isFirst) {
        isLoading = true;
      //  ApiService.loading(getActivity(), showDialogLoading);
        final RequestQueue queue = Volley.newRequestQueue(requireContext());
        // Tag used to cancel the request
        swipe.setRefreshing(showDialogLoading);
        String url = ApiService.getMatches + "?page=" + currentPage;
        Log.d(TAG, "getMatches: URL :: " + url);
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET, url, null,
                responseBody -> {
                    isLoading = false;
                    if (isFirst){
                        adapter.clear();
                    }
                    ApiService.loading(getActivity(), false);
                    Log.e("responseBody", responseBody.toString());
                    try {
                        swipe.setRefreshing(false);
                        String status = responseBody.getString("status");
                        if (status.equals("true")) {
                            ArrayList<Db_My_Expections_My_Account> items = new ArrayList<>();
                            String matches = responseBody.getString("matches");
                            JSONArray jsonArray = new JSONArray(matches);

                            for (int i = 0; i < jsonArray.length(); i++) {
                                itemCount++;
                                JsonParser parser = new JsonParser();
                                JsonElement mJson = parser.parse(jsonArray.getString(i));
                                Gson gson = new Gson();
                                Db_My_Expections_My_Account storeModules = gson.fromJson(mJson, Db_My_Expections_My_Account.class);
//                                Log.d(TAG, "getMatches: storeModules :: " + storeModules.toString());
                                items.add(storeModules);
                            }
                            adapter.removeLoading();

                            adapter.addItems(items);


                            rv_test.setVisibility(View.VISIBLE);
                            item_connection.setVisibility(View.GONE);

                            if (jsonArray.length() < 10) {
                                Log.d(TAG, "getMatches: LAST PAGE " + currentPage);
                                isLastPage = true;
                            }else {
                                adapter.addLoading();
                            }
                            PAGE_SIZE = jsonArray.length();

                            Log.d(TAG, "getMatches: CURRENT PAGE " + currentPage + " PAGE_SIZE :: " + PAGE_SIZE);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                volleyError -> {
                    volleyError.printStackTrace();
                    if (showDialogLoading)
                        showErrorDialog();
                    ApiService.loading(getActivity(), false);
                    ApiService.ErrorResponse(getActivity(), volleyError);
                }) {
            @Override
            public Map<String, String> getHeaders() {
                return ApiService.getHeader(true);
            }

        };

        jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(
                9000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(jsonObjReq);


    }

    @Override
    public void onResume() {
        super.onResume();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                isShowExpTut = SharedPrefs.getBoolean(getContext(), "is_show_exp_tut", true);
                if (isShowExpTut) {
                    showTutorial();
                }

            }
        },500);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        if (isVisibleToUser)

        super.setUserVisibleHint(isVisibleToUser);
    }
    private void showErrorDialog() {

        rv_test.setVisibility(View.GONE);
        item_connection.setVisibility(View.VISIBLE);
        retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getMatches(true,true);
            }
        });
//        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
//                .setTitle(getString(R.string.network_connection_error))
//                .setMessage("")
//                .setCancelable(false)
//                .setView(R.layout.item_connection_error)
//                .setPositiveButton(R.string.retry, (dialog, which) -> {
//                    getMatches(true);
//                });
//        builder.create().show();
    }

    private void showTutorial() {
        Log.d(TAG, "showTutorial: ");
        TapTargetSequence.Listener listener = new TapTargetSequence.Listener() {
            @Override
            public void onSequenceFinish() {
                adapter.setShowTutorial(isShowExpTut);
                adapter.notifyDataSetChanged();
                isShowExpTut = false;
                SharedPrefs.save(getContext(), "is_show_exp_tut", isShowExpTut);
                Log.d(TAG, "onSequenceFinish: Change IS_SHOW_EXP_TUT TO :: "
                        + SharedPrefs.getBoolean(getContext(), "is_show_exp_tut", true));
            }

            @Override
            public void onSequenceStep(TapTarget lastTarget, boolean targetClicked) {

            }

            @Override
            public void onSequenceCanceled(TapTarget lastTarget) {
                SharedPrefs.save(getContext(), "is_show_exp_tut", false);
            }
        };

        new TapTargetSequence(requireActivity()).targets(createTargetView(iamgenotf, getString(R.string.notifications), ""),
                        createTargetView(ImageL, getString(R.string.profile), getString(R.string.tut_profile_desc)),
                        createTargetView(image_more, getString(R.string.More), ""),
                        createTargetView(((MainActivity) getActivity()).bottomNavigation.getCellById(0), getString(R.string.Expectations), getString(R.string.tut_expectaions_desc)),
                        createTargetView(((MainActivity) getActivity()).bottomNavigation.getCellById(1), getString(R.string.top_score), getString(R.string.tut_top_score_desc)),
                        createTargetView(((MainActivity) getActivity()).bottomNavigation.getCellById(2), getString(R.string.Champions), getString(R.string.tut_champion_desc)),
                        createTargetView(((MainActivity) getActivity()).bottomNavigation.getCellById(3), getString(R.string.Arrange), getString(R.string.Predict_teams)),
                        createTargetView(((MainActivity) getActivity()).bottomNavigation.getCellById(4), getString(R.string.dawrena), getString(R.string.tut_dawrtena_desc)))

                .considerOuterCircleCanceled(true)
                .continueOnCancel(true)
                .listener(listener).start();

    }

    TapTarget createTargetView(View view, String title, String descreption) {
        return TapTarget.forView(view, title, descreption)
                .dimColor(android.R.color.transparent)
                .tintTarget(false)
                .outerCircleColor(R.color.colorAccent)
                .targetCircleColor(R.color.colorPrimary)
                .textColor(android.R.color.white);
    }
}

