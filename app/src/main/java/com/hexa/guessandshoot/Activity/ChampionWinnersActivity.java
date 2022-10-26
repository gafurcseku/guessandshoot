package com.hexa.guessandshoot.Activity;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.hexa.guessandshoot.Adapter.AdapterChampionWinner;
import com.hexa.guessandshoot.Adapter.YearsAdapter;
import com.hexa.guessandshoot.Modules.last_db.Winner;
import com.hexa.guessandshoot.R;
import com.hexa.guessandshoot.Settings.ApiService;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Map;


public class ChampionWinnersActivity extends AppCompatActivity {

    private static final String TAG = "ChampionWinnersActivity";

    ImageView iv_backArrow, img_filter;
    RecyclerView rv_championWinner;
    TextView tv_sortType ;
    int currentWinnerPosition = 0;
    int currentYearPosition = 0;
    ArrayList<Winner> list = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_champion_winners);


        initViews();

        initItems();

        initClicks();
    }

    private void initViews() {
        iv_backArrow = findViewById(R.id.iv_backArrow);
        img_filter = findViewById(R.id.img_filter);
        rv_championWinner = findViewById(R.id.rv_championWinner);
        tv_sortType = findViewById(R.id.tv_sortType);
    }

    private void initItems() {
        getWinnerByTypeAndYear(AdapterChampionWinner.WinnerType.CLUB, "-1");
    }

    private void initClicks() {
        img_filter.setOnClickListener(v -> show_Book());
        iv_backArrow.setOnClickListener(v -> onBackPressed());
    }

    String sortTypeString ="";
    private void getWinnerByTypeAndYear(AdapterChampionWinner.WinnerType winnerType, String year) {
        list.clear();
        Log.d(TAG, "getWinnerByTypeAndYear: Type :: " + winnerType.name() + " Year :: " + year);
        ApiService.loading(ChampionWinnersActivity.this, true);
        StringBuilder stringBuilder = new StringBuilder();
        final RequestQueue queue = Volley.newRequestQueue(ChampionWinnersActivity.this);
        // Tag used to cancel the request
        String type = "0";
        switch (winnerType) {
            case CLUB:
                type = "0";
                sortTypeString = getString(R.string.club);
                break;
            case SCORER:
                type = "1";
                sortTypeString = getString(R.string.scorers_title);
                break;
            case GOALKEEPER:
                type = "2";
                sortTypeString = getString(R.string.goal_keeper);
                break;
        }
        stringBuilder.append(ApiService.getWinners);
        if (!year.equals("-1")) {
            stringBuilder.append("?year=");
            stringBuilder.append(year);

            stringBuilder.append("&type=");
            stringBuilder.append(type);
        } else {
            stringBuilder.append("?type");
            stringBuilder.append(type);
        }

        String url = stringBuilder.toString();
        Log.d(TAG, "getWinnerByType: URL :: " + url);

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET, url, null,
                responseBody -> {
                    try {
                        ApiService.loading(ChampionWinnersActivity.this, false);
                        String status = responseBody.getString("status");
                        String code = responseBody.getString("code");
                        if (status.equals("true")) {
                            tv_sortType.setText(sortTypeString);
                            String winners = responseBody.getString("winners");
                            JSONArray jsonArray = new JSONArray(winners);

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JsonParser parser = new JsonParser();
                                JsonElement mJson = parser.parse(jsonArray.getString(i));
                                Gson gson = new Gson();
                                Winner winner = gson.fromJson(mJson, Winner.class);
                                switch (winnerType) {
                                    case CLUB:
                                        if (winner.getClub() != null) {
                                            Log.d(TAG, "getWinnerByType: " + winner.getClub().toString());
                                            list.add(winner);
                                        }
                                        break;
                                    case SCORER:
                                        if (winner.getPlayer() != null) {
                                            Log.d(TAG, "getWinnerByType: " + winner.getPlayer().toString());
                                            list.add(winner);
                                        }
                                        break;
                                    case GOALKEEPER:
                                        if (winner.getGoalkeeper() != null) {
                                            Log.d(TAG, "getWinnerByType: " + winner.getGoalkeeper().toString());
                                            list.add(winner);
                                        }
                                        break;
                                }

                            }
                            rv_championWinner.setLayoutManager(new LinearLayoutManager(ChampionWinnersActivity.this));
                            AdapterChampionWinner adapter = new AdapterChampionWinner(ChampionWinnersActivity.this, list, winnerType);
                            rv_championWinner.setAdapter(adapter);
                        } else {

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }, volleyError -> {
            ApiService.loading(ChampionWinnersActivity.this, false);
            ApiService.ErrorResponse(ChampionWinnersActivity.this, volleyError);
        }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {

                return ApiService.getHeader(false);
            }

        };
        jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(
                9000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(jsonObjReq);
    }

    AlertDialog alertDialog = null;
    AdapterChampionWinner.WinnerType winnerType = AdapterChampionWinner.WinnerType.CLUB;
    ArrayList<TextView> typeBtns;

    public void show_Book() {

        LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View popupView = layoutInflater.inflate(R.layout.item_filter_winner, null);

        try {
            FrameLayout close_btn = popupView.findViewById(R.id.close_btn);

            Spinner SP_Years = popupView.findViewById(R.id.SP_Years);
            ImageView arrow_back = popupView.findViewById(R.id.arrow_back);
            ImageView image_right = popupView.findViewById(R.id.image_right);
            Button btn_Filter = popupView.findViewById(R.id.btn_Filter);

            TextView btn_club = popupView.findViewById(R.id.btn_club);
            TextView btn_scorer = popupView.findViewById(R.id.btn_scorer);
            TextView btn_goalKeeper = popupView.findViewById(R.id.btn_goalKeeper);
            typeBtns = new ArrayList<>();
            typeBtns.add(btn_club);
            typeBtns.add(btn_scorer);
            typeBtns.add(btn_goalKeeper);

            final AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setView(popupView);

            alertDialog = builder.show();
            alertDialog.getWindow().setGravity(Gravity.CENTER);
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));



            btn_club.setOnClickListener(v -> {
                winnerType = AdapterChampionWinner.WinnerType.CLUB;
                currentWinnerPosition = 0;
                changeSelectedType(currentWinnerPosition);
            });

            btn_scorer.setOnClickListener(v -> {
                winnerType = AdapterChampionWinner.WinnerType.SCORER;
                currentWinnerPosition = 1;
                changeSelectedType(currentWinnerPosition);
            });

            btn_goalKeeper.setOnClickListener(v -> {
                winnerType = AdapterChampionWinner.WinnerType.GOALKEEPER;
                currentWinnerPosition = 2;
                changeSelectedType(currentWinnerPosition);
            });

            new Handler().post(() -> {
                ArrayList<String> years = getYears();
                YearsAdapter yearsAdapter = new YearsAdapter(ChampionWinnersActivity.this, years);
                SP_Years.setAdapter(yearsAdapter);
            });


            //Init Last Selection
            Log.d(TAG, "show_Book: Last Selection Position Type :: " + currentWinnerPosition + "\nLast Selection Position Year :: " + currentYearPosition);
            changeSelectedType(currentWinnerPosition);
            SP_Years.setSelection(currentYearPosition);

            arrow_back.setOnClickListener(v -> {
                if (SP_Years.getSelectedItemPosition() > 0) {
                    SP_Years.setSelection(SP_Years.getSelectedItemPosition() - 1);
                    Log.d(TAG, "show_Book: Change Current Year Position From " + currentYearPosition);
                    currentYearPosition = SP_Years.getSelectedItemPosition();
                    Log.d(TAG, "show_Book: Change Current Year Position TO " + currentYearPosition);
                }
            });

            image_right.setOnClickListener(v -> {
                if (SP_Years.getSelectedItemPosition() < getYears().size() - 1) {
                    SP_Years.setSelection(SP_Years.getSelectedItemPosition() + 1);
                    Log.d(TAG, "show_Book: Change Current Year Position From " + currentYearPosition);
                    currentYearPosition = SP_Years.getSelectedItemPosition();
                    Log.d(TAG, "show_Book: Change Current Year Position TO " + currentYearPosition);
                }
            });

            btn_Filter.setOnClickListener(v -> {
                String year = (String) SP_Years.getSelectedItem();
                getWinnerByTypeAndYear(winnerType, year);
                alertDialog.dismiss();
            });
            close_btn.setOnClickListener(v -> alertDialog.dismiss());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void changeSelectedType(int position) {

        Log.d(TAG, "changeSelectedType: TO :: " + position);
        runOnUiThread(() -> {
            for (TextView button : typeBtns) {
                button.setBackgroundResource(R.drawable.shape_btn_white);
                button.setTextColor(getResources().getColor(R.color.colorAccent));
            }
            typeBtns.get(position).setBackgroundResource(R.drawable.shape_btn);
            typeBtns.get(position).setTextColor(getResources().getColor(R.color.white));
        });
    }

    public ArrayList<String> getYears() {

        ArrayList<String> years = new ArrayList<>();
        for (int i = 2020; i < 2030; i++) {
            years.add(String.valueOf(i));
        }

        return years;
    }

}