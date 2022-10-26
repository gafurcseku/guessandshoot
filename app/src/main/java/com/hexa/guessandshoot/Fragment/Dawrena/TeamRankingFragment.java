package com.hexa.guessandshoot.Fragment.Dawrena;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.hexa.guessandshoot.Adapter.AdapterTeamRanking;
import com.hexa.guessandshoot.Adapter.AdapterTopScore;
import com.hexa.guessandshoot.Fragment.top_score.TopRequestType;
import com.hexa.guessandshoot.Modules.League;
import com.hexa.guessandshoot.Modules.newDB.DBChampionGuess;
import com.hexa.guessandshoot.R;
import com.hexa.guessandshoot.Settings.ApiService;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Map;

public class TeamRankingFragment extends Fragment {
    private static final String TAG = "TopScorerFragment";

    RecyclerView rv_topScorer;

    SwipeRefreshLayout refresh ;

    public TeamRankingFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_team_ranking, container, false);
        initViews(view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initItems();
    }

    private void initItems() {

        getTopScorer();

        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh.setRefreshing(true);
                getTopScorer();
            }
        });


    }

    private void getTopScorer() {
        Log.d(TAG, "getTopScorer: ");
        ApiService.loading(getActivity(), true);

        final RequestQueue queue = Volley.newRequestQueue(getActivity());
        // Tag used to cancel the request
        String url = ApiService.getLeagues;


        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET, url, null,
                responseBody -> {

                    try {
                        refresh.setRefreshing(false);
                        ApiService.loading(getActivity(), false);

                        String status = responseBody.getString("status");

                        if (status.equals("true")) {
                            String champions = responseBody.getString("Leagues");
                            JSONArray jsonArray = new JSONArray(champions);
                            ArrayList<League> list = new ArrayList<>();
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JsonParser parser = new JsonParser();
                                JsonElement mJson = parser.parse(jsonArray.getString(i));
                                Gson gson = new Gson();
                                League storeModules = gson.fromJson(mJson, League.class);
                                list.add(storeModules);
                                Log.d(TAG, "onResponse: " + storeModules.toString());
                            }
                            AdapterTeamRanking adapter = new AdapterTeamRanking(list, getActivity());

                            rv_topScorer.setLayoutManager(new GridLayoutManager(getContext(), 1));
                            rv_topScorer.setAdapter(adapter);
                        } else {
                            Log.e(TAG, "getTopScorer: Error When Get Data From Server");
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }, volleyError -> {
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

    private void showErrorDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setTitle(getString(R.string.network_connection_error))
                .setMessage("")
                .setCancelable(false)
                .setView(R.layout.item_connection_error)
                .setPositiveButton(R.string.retry, (dialog, which) -> {
                    getTopScorer();
                });
        builder.create().show();
    }


    private void initViews(View view) {
        rv_topScorer = view.findViewById(R.id.rv_topScorer);
        refresh = view.findViewById(R.id.refresh);
    }
}