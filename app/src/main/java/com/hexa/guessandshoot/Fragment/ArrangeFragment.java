package com.hexa.guessandshoot.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.hexa.guessandshoot.Adapter.Adapter_Arrange;
import com.hexa.guessandshoot.Adapter.Adapter_champions;
import com.hexa.guessandshoot.Modules.Db_champions;
import com.hexa.guessandshoot.Modules.Db_championsGroup;
import com.hexa.guessandshoot.R;
import com.hexa.guessandshoot.Settings.ApiService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ArrangeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ArrangeFragment extends Fragment {

    RecyclerView adapterlist ;
   // SwipeRefreshLayout swipe ;
    ArrayList<Db_championsGroup> list = new ArrayList<>();

    public ArrangeFragment() {
        // Required empty public constructor
    }

    public static ArrangeFragment newInstance() {
        ArrangeFragment fragment = new ArrangeFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_arrange, container, false);
        adapterlist = view.findViewById(R.id.list) ;
       // swipe = view.findViewById(R.id.swipe) ;
        adapterlist.setLayoutManager(new GridLayoutManager(getActivity() , 3));
        return view ;
    }

    @Override
    public void onResume() {
        super.onResume();
        getChampionsRanks() ;
    }

    public void getChampionsRanks() {


        final RequestQueue queue = Volley.newRequestQueue(getActivity());
        // Tag used to cancel the request
        String url = ApiService.getChampionsRanks;


        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject responseBody) {
                        // pDialog.hide();
                   //     swipe.setRefreshing(false);
                        try {
                            ApiService.loading(getActivity(), false);


                            String status = responseBody.getString("status");

//                            String message = responseBody.getString("message");
                            String code = responseBody.getString("code");


                            if (status.equals("true")) {
                                String champions = responseBody.getString("champions");
                                JSONArray jsonArray = new JSONArray(champions);
                                list.clear();
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JsonParser parser = new JsonParser();
                                    JsonElement mJson = parser.parse(jsonArray.getString(i));
                                    Gson gson = new Gson();
                                    Db_championsGroup storeModules = gson.fromJson(mJson, Db_championsGroup.class);


                                    list.add(storeModules);


                                }
                                Adapter_Arrange adapter_arrange = new Adapter_Arrange(list ,getActivity()) ;
                                adapterlist.setAdapter(adapter_arrange);
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
            //    swipe.setRefreshing(false);
                ApiService.loading(getActivity(), false);

//                webServise.loading(SplashScreenActivity.this, false);

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
}