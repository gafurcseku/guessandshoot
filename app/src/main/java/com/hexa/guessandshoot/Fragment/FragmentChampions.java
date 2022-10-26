package com.hexa.guessandshoot.Fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.hexa.guessandshoot.Adapter.Adapter_champions;
import com.hexa.guessandshoot.Modules.Db_champions;
import com.hexa.guessandshoot.R;
import com.hexa.guessandshoot.Settings.ApiService;
import com.hexa.guessandshoot.Settings.FixedRecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentChampions extends Fragment {
    FixedRecyclerView recyclerView;
    SwipeRefreshLayout swipe ;
    NestedScrollView nested ;
    LinearLayout item_connection ;
    TextView retry ;

    ArrayList<Db_champions> list = new ArrayList<>();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = getLayoutInflater().inflate(R.layout.champions, container, false);
        recyclerView = view.findViewById(R.id.recycler_champions);
        swipe = view.findViewById(R.id.swipe);
        nested = view.findViewById(R.id.nested);
        item_connection = view.findViewById(R.id.item_connection);
        retry = view.findViewById(R.id.retry);


        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1));

        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getChampions();
            }
        });
        swipe.setEnabled(false);
        getChampions();
        return view;

    }


    public void getChampions() {
        swipe.setRefreshing(true);
        // ApiService.loading(getActivity(), true);

        final RequestQueue queue = Volley.newRequestQueue(getActivity());
        // Tag used to cancel the request
        String url = ApiService.getChampions;


        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject responseBody) {
                        // pDialog.hide();
                        swipe.setRefreshing(false);
                        try {
                            ApiService.loading(getActivity(), false);


                            String status = responseBody.getString("status");

//                            String message = responseBody.getString("message");
                            String code = responseBody.getString("code");


                            if (status.equals("true")) {
                                nested.setVisibility(View.VISIBLE);
                                item_connection.setVisibility(View.GONE);
                                String champions = responseBody.getString("champions");
                                JSONArray jsonArray = new JSONArray(champions);
                                list.clear();
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JsonParser parser = new JsonParser();
                                    JsonElement mJson = parser.parse(jsonArray.getString(i));
                                    Gson gson = new Gson();
                                    Db_champions storeModules = gson.fromJson(mJson, Db_champions.class);


                                    list.add(storeModules);


                                }
                                Adapter_champions adapter = new Adapter_champions(list, getActivity());

                                recyclerView.setAdapter(adapter);
                                //   System.out.println(matches);


                            } else {

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError volleyError) {
/////
                ApiService.loading(getActivity(), false);
                swipe.setRefreshing(false);
//                webServise.loading(SplashScreenActivity.this, false);
                showErrorDialog();
                ApiService.ErrorResponse(getActivity(), volleyError);
            }
        }) {

            /**
             * Passing some request headers
             * */

//            @Override
//            protected Map<String,String> getParams(){
//                Map<String,String> params = new HashMap<String, String>();
//                params.put("email",email);
//                params.put("name",name);
//                params.put("mobile",mobile);
//                params.put("message",message);
//
//
//                return params;
//            }
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {

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
        nested.setVisibility(View.GONE);
        item_connection.setVisibility(View.VISIBLE);
        retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getChampions();
            }
        });
//        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
//                .setTitle(getString(R.string.network_connection_error))
//                .setMessage("")
//                .setCancelable(false)
//                .setView(R.layout.item_connection_error)
//                .setPositiveButton(R.string.retry, (dialog, which) -> {
//                    getChampions();
//                });
//        builder.create().show();
    }
}