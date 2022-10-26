package com.hexa.guessandshoot.Adapter;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.hexa.guessandshoot.Modules.Clubs;
import com.hexa.guessandshoot.R;
import com.hexa.guessandshoot.Settings.ApiService;
import com.hexa.guessandshoot.Settings.Settings;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;

public class Adapter_team extends RecyclerView.Adapter<Adapter_team.ViewHolder> {
    List<Clubs> list;
    Activity activity;

    public Adapter_team(List<Clubs> list, Activity activity) {
        this.list = list;
        this.activity = activity;
    }

    public void REF() {
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_choose_team, null, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {


        Picasso.get().load(list.get(position).club.getImage()).into(holder.image_club);


        if (list.get(position).getIsChoose().equals("1")) {
            Log.e("selected", list.get(position).getChampionId() + "  ----   " + list.get(position).club.getId());
            holder.selected.setVisibility(View.VISIBLE);
        } else {
            holder.selected.setVisibility(View.GONE);

        }


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("selected", list.get(position).getChampionId() + " ----  " + list.get(position).club.getId());
                userGuessChamp(list.get(position).getChampionId() + "", list.get(position).club.getId() + "", position);
            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image_club;
        ImageView selected;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            selected = itemView.findViewById(R.id.selected);
            image_club = itemView.findViewById(R.id.image_club);
        }
    }

    public void userGuessChamp(String champion_id, String club_id, final int postion) {
        ApiService.loading((Activity) activity, true);

        final RequestQueue queue = Volley.newRequestQueue((Activity) activity);
        // Tag used to cancel the request
        String url = ApiService.userGuessChamp;


        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("champion_id", champion_id);
            jsonObject.put("club_id", club_id);
        } catch (Exception e) {

        }


        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST, url, jsonObject,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject responseBody) {
                        // pDialog.hide();
                        Log.e("responseBody", responseBody.toString());

                        try {
                            ApiService.loading((Activity) activity, false);

                            System.out.println(responseBody.toString());
                            String status = responseBody.getString("status");

                            String message = responseBody.getString("message");
                            String code = responseBody.getString("code");


//                            if (status.equals("true")) {


                                for (int i = 0; i < list.size(); i++) {


                                    list.get(i).setIsChoose("0");


                                }

                                list.get(postion).setIsChoose("1");

                                REF();

                               // Toast.makeText(activity, message, Toast.LENGTH_LONG).show();

//                            } else {
//                               // Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
//                                Settings.alertDialog(activity, message);
//
//
//                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError volleyError) {
/////
                ApiService.loading((Activity) activity, false);

//                webServise.loading(SplashScreenActivity.this, false);

                ApiService.ErrorResponse((Activity) activity, volleyError);
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
