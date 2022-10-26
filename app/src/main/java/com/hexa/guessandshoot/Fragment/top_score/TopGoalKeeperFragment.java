package com.hexa.guessandshoot.Fragment.top_score;

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

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.hexa.guessandshoot.Adapter.AdapterTopScore;
import com.hexa.guessandshoot.Modules.newDB.DBChampionGuess;
import com.hexa.guessandshoot.R;
import com.hexa.guessandshoot.Settings.ApiService;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Map;


public class TopGoalKeeperFragment extends Fragment {
    private static final String TAG = "GoalKeeperFragment";
    RecyclerView recycler_goalKeeper;
    ArrayList<DBChampionGuess> list = new ArrayList<>();

    public TopGoalKeeperFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_top_goal_keeper, container, false);
        initViews(view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initItems();
    }

    private void initItems() {
        getGoalKeeper();
    }

    private void getGoalKeeper() {
        Log.d(TAG, "getGoalKeeper: ");
//        ApiService.loading(getActivity(), true);

        final RequestQueue queue = Volley.newRequestQueue(getActivity());
        // Tag used to cancel the request
        String url = ApiService.getChampionsGoalkeepers;


        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET, url, null,
                responseBody -> {

                    try {
                        ApiService.loading(getActivity(), false);

                        String status = responseBody.getString("status");

                        if (status.equals("true")) {
                            String champions = responseBody.getString("champions");
                            JSONArray jsonArray = new JSONArray(champions);

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JsonParser parser = new JsonParser();
                                JsonElement mJson = parser.parse(jsonArray.getString(i));
                                Gson gson = new Gson();
                                DBChampionGuess storeModules = gson.fromJson(mJson, DBChampionGuess.class);
                                list.add(storeModules);
                                Log.d(TAG, "onResponse: " + storeModules.toString());
                            }
                            AdapterTopScore adapter = new AdapterTopScore(list, TopRequestType.GOAL_KEEPER, getActivity());

                            recycler_goalKeeper.setLayoutManager(new GridLayoutManager(getContext(), 1));
                            recycler_goalKeeper.setAdapter(adapter);
                        } else {
                            Log.e(TAG, "getGoalKeeper: Error When Get Data From Server");
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
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                    .setTitle(getString(R.string.network_connection_error))
                    .setMessage("")
                    .setCancelable(false)
                    .setView(R.layout.item_connection_error)
                    .setPositiveButton(R.string.retry, (dialog, which) -> {
                        getGoalKeeper();
                    });
            builder.create().show();
        }catch (Exception exception){
           exception.printStackTrace();
        }

    }


    private void initViews(View view) {
        recycler_goalKeeper = view.findViewById(R.id.recycler_goalKeeper);
    }
}