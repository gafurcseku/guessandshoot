package com.hexa.guessandshoot.Adapter.newApdater;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.getkeepsafe.taptargetview.TapTarget;
import com.getkeepsafe.taptargetview.TapTargetSequence;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.hexa.guessandshoot.Activity.MainActivity;
import com.hexa.guessandshoot.Activity.SharedPrefs;
import com.hexa.guessandshoot.Modules.Db_My_Expections_My_Account;
import com.hexa.guessandshoot.R;
import com.hexa.guessandshoot.Settings.ApiService;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Map;

import static com.hexa.guessandshoot.Adapter.newApdater.PaginationListener.PAGE_START;

public class TestPaginationActivity extends AppCompatActivity {

    private static final String TAG = "TestPaginationActivity";

    private PaginationAdapter adapter;

    private int currentPage = PAGE_START;
    private boolean isLastPage = false;
    private int totalPage = 10;
    private boolean isLoading = false;
    int itemCount = 0;

    boolean isShowExpTut = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_paginiation);

        isShowExpTut = SharedPrefs.getBoolean(getApplicationContext(), "is_show_exp_tut", true);
        Log.d(TAG, "onViewCreated: SHOW Tutorial :: " + isShowExpTut);

        RecyclerView rv_test = findViewById(R.id.rv_test);

        rv_test.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rv_test.setLayoutManager(layoutManager);
        adapter = new PaginationAdapter(this, new ArrayList<>());
        rv_test.setAdapter(adapter);

        getMatches();

        rv_test.addOnScrollListener(new PaginationListener(layoutManager) {
            @Override
            protected void loadMoreItems() {
                isLoading = true;
                currentPage++;
                getMatches();
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
    }


    public void getMatches() {
        Log.d(TAG, "getMatches: FIRST FRAGMENT");
        final RequestQueue queue = Volley.newRequestQueue(this);
        // Tag used to cancel the request
        String url = ApiService.getMatches + "?" + currentPage;
        final ArrayList<Db_My_Expections_My_Account> items = new ArrayList<>();
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET, url, null,
                responseBody -> {
                    Log.e("responseBody", responseBody.toString());
                    try {
                        String status = responseBody.getString("status");
                        if (status.equals("true")) {
                            String matches = responseBody.getString("matches");
                            JSONArray jsonArray = new JSONArray(matches);
                            for (int i = 0; i < jsonArray.length(); i++) {
                                itemCount++;
                                JsonParser parser = new JsonParser();
                                JsonElement mJson = parser.parse(jsonArray.getString(i));
                                Gson gson = new Gson();
                                Db_My_Expections_My_Account storeModules = gson.fromJson(mJson, Db_My_Expections_My_Account.class);
                                items.add(storeModules);
                            }

                            if (isShowExpTut && currentPage == PAGE_START) {
                                showTutorial();
                            }
                            if (currentPage != PAGE_START) adapter.removeLoading();
                            adapter.addItems(items);

                            // check weather is last page or not
                            if (currentPage < totalPage) {
                                adapter.addLoading();
                            } else {
                                isLastPage = true;
                            }
                            isLoading = false;

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, volleyError -> {
            ApiService.loading(this, false);
            ApiService.ErrorResponse(this, volleyError);
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

    private void showTutorial() {
        Log.d(TAG, "showTutorial: ");
        TapTargetSequence.Listener listener = new TapTargetSequence.Listener() {
            @Override
            public void onSequenceFinish() {
                adapter.setShowTutorial(isShowExpTut);
                isShowExpTut = false;
                SharedPrefs.save(getApplicationContext(), "is_show_exp_tut", isShowExpTut);
                Log.d(TAG, "onSequenceFinish: Change IS_SHOW_EXP_TUT TO :: " + SharedPrefs.getBoolean(getApplicationContext(), "is_show_exp_tut", true));
            }

            @Override
            public void onSequenceStep(TapTarget lastTarget, boolean targetClicked) {

            }

            @Override
            public void onSequenceCanceled(TapTarget lastTarget) {
                SharedPrefs.save(getApplicationContext(), "is_show_exp_tut", false);
            }
        };

        new TapTargetSequence(this)
                .targets()


//                        createTargetView(notifications, getString(R.string.notifications), ""),
//                        createTargetView(ImageL, getString(R.string.profile), getString(R.string.tut_profile_desc)),
////
//                        createTargetView(((MainActivity) getActivity()).bottomNavigation.getCellById(0), getString(R.string.Expectations), getString(R.string.tut_expectaions_desc)),
//                        createTargetView(((MainActivity) getActivity()).bottomNavigation.getCellById(1), getString(R.string.top_score), getString(R.string.tut_top_score_desc)),
//                        createTargetView(((MainActivity) getActivity()).bottomNavigation.getCellById(2), getString(R.string.Champions), getString(R.string.tut_champion_desc)),
//                        createTargetView(((MainActivity) getActivity()).bottomNavigation.getCellById(3), getString(R.string.Statistics), getString(R.string.tut_static_desc)),
//                        createTargetView(((MainActivity) getActivity()).bottomNavigation.getCellById(4), getString(R.string.More), ""))
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