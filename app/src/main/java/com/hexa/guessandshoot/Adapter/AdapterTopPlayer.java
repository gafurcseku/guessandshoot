package com.hexa.guessandshoot.Adapter;

import android.app.Activity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.hexa.guessandshoot.Fragment.top_score.TopRequestType;
import com.hexa.guessandshoot.Modules.newDB.DB_Player;
import com.hexa.guessandshoot.R;
import com.hexa.guessandshoot.Settings.ApiService;
import com.hexa.guessandshoot.Settings.Settings;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;

public class AdapterTopPlayer extends RecyclerView.Adapter<AdapterTopPlayer.ViewHolder> {

    private static final String TAG = "AdapterTopPlayer";
    List<DB_Player> list;
    Activity activity;
    TopRequestType scorerPlayer;

    public AdapterTopPlayer(List<DB_Player> list, TopRequestType scorerPlayer, Activity activity) {
        this.list = list;
        this.scorerPlayer = scorerPlayer;
        this.activity = activity;
    }


    public void REF() {
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_choose_player, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        DB_Player currentPlayer = list.get(position);
        //Init Player Image
        String playerImage = currentPlayer.getPlayer().getImage();
        if (playerImage != null && !TextUtils.isEmpty(playerImage))
            Picasso.get().load(playerImage).into(holder.iv_playerImage);

        //Init Player name
        String playerName = currentPlayer.getPlayer().getName();
        if (playerName != null && !TextUtils.isEmpty(playerName))
            holder.tv_playerName.setText(playerName);

        //Init Club name
        String clubName = currentPlayer.getPlayer().getClub().getName();
        if (clubName != null && !TextUtils.isEmpty(clubName))
            holder.tv_playerClub.setText(clubName);

        //Init last chosen
//        if (scorerPlayer == TopRequestType.GOAL_KEEPER){
//
//        }
        if (currentPlayer.getIsPlayerChoose().equals("1")) {
            Log.e(TAG, "Champion Selected :: " + currentPlayer.getChampionId() + ", And Player " + currentPlayer.getPlayer().getId());
            holder.selected.setVisibility(View.VISIBLE);
        } else {
            holder.selected.setVisibility(View.GONE);
        }

        //Init New Chosen
        holder.itemView.setOnClickListener(v -> {
            Log.e(TAG, "Selected Champion ID :: " + currentPlayer.getChampionId() + ", And Player ID :: " + currentPlayer.getId());
            userGuessChamp(currentPlayer.getChampionId(), String.valueOf(currentPlayer.getPlayer().getId()), position);
        });
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_playerImage, selected;
        TextView tv_playerName,tv_playerClub;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            selected = itemView.findViewById(R.id.selected);
            iv_playerImage = itemView.findViewById(R.id.iv_playerImage);
            tv_playerName = itemView.findViewById(R.id.tv_playerName);
            tv_playerClub = itemView.findViewById(R.id.tv_playerClub);
        }
    }

    public void userGuessChamp(String champion_id, String player_id, final int position) {
        ApiService.loading((Activity) activity, true);

        final RequestQueue queue = Volley.newRequestQueue((Activity) activity);
        //Tag used to cancel the request
        //Get Request Type

        String url = scorerPlayer == TopRequestType.GOAL_KEEPER ? ApiService.userGuessGaolKeeper : ApiService.userGuessPlayer;

        //Prepare JSON Request
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("champion_id", champion_id /*"8"*/);
            jsonObject.put("player_id", player_id /*"1"*/);
        } catch (Exception e) {
            Log.e(TAG, "userGuessChamp: Error When Prepare request", e);
        }

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST, url, jsonObject, (Response.Listener<JSONObject>) responseBody -> {
            Log.e(TAG, responseBody.toString());

            try {
                ApiService.loading((Activity) activity, false);
                String status = responseBody.getString("status");
                String message = responseBody.getString("message");
                if (status.equals("true")) {
                    //Init New Chosen
                    initNewChosen(position, message);
                } else {
                   // Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
                    Settings.alertDialog(activity, message);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, (Response.ErrorListener) volleyError -> {
            ApiService.loading((Activity) activity, false);
            ApiService.ErrorResponse((Activity) activity, volleyError);
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
        //Send Request To Server
        queue.add(jsonObjReq);

    }

    private void initNewChosen(int position, String message) {
        //Cancel All Chosen
        for (int i = 0; i < list.size(); i++) {
            list.get(i).setIsPlayerChoose("0");
        }
        //Set new Chosen
        list.get(position).setIsPlayerChoose("1");
        //Refresh RecycleView
        REF();
        //Show Message to User
        Toast.makeText(activity, message, Toast.LENGTH_LONG).show();
    }

}
