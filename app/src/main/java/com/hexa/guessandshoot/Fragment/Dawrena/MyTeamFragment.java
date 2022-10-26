package com.hexa.guessandshoot.Fragment.Dawrena;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
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
import com.hexa.guessandshoot.Adapter.AdapterMyTeam;
import com.hexa.guessandshoot.Adapter.AdapterTopScore;
import com.hexa.guessandshoot.DCallBack;
import com.hexa.guessandshoot.Dialog.DialogAddNewTeam;
import com.hexa.guessandshoot.Dialog.DialogEnterCode;
import com.hexa.guessandshoot.Dialog.DialogEnterRandom;
import com.hexa.guessandshoot.Fragment.top_score.TopRequestType;
import com.hexa.guessandshoot.Modules.League;
import com.hexa.guessandshoot.Modules.last_db.User;
import com.hexa.guessandshoot.Modules.newDB.DBChampionGuess;
import com.hexa.guessandshoot.R;
import com.hexa.guessandshoot.Settings.ApiService;
import com.hexa.guessandshoot.Settings.Settings;
import com.loopj.android.http.RequestParams;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;

public class MyTeamFragment extends Fragment {
    private static final String TAG = "TopScorerFragment";
    LinearLayout Ly_add_new_team  ,Ly_enter_the_code ,Ly_random_entry;
    ArrayList<User> list = new ArrayList<>();
    DialogAddNewTeam dialogAddNewTeam ;
    LinearLayout profile,joinLeag;
    League league ;
    SwipeRefreshLayout refresh ;

    ImageView image_edit ;

    TextView TV_members,TV_code ,TV_name ,TV_Point ,TV_level,TV_leave;
    LinearLayout TV_copy  ,TV_share ;
    CircleImageView image ;
    RecyclerView rvPlayer ;

    public MyTeamFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_team, container, false);
        initViews(view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initItems();
        checkStatus();
    }




    public void showEditAndAdd(boolean isEdit){

        if (isEdit){
            dialogAddNewTeam =new DialogAddNewTeam(getActivity(),league);
        }else {
            dialogAddNewTeam =new DialogAddNewTeam(getActivity());
        }

        dialogAddNewTeam.show();
        dialogAddNewTeam.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                checkStatus();
            }
        });
    }
    private void initItems() {

        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                checkStatus();
                refresh.setRefreshing(false);
            }
        });
        Ly_add_new_team.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEditAndAdd(false);
            }
        });

        Ly_enter_the_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogEnterCode cdd=new DialogEnterCode(getActivity());
                cdd.show();
                cdd.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        checkStatus();
                    }
                });
            }
        });
        Ly_random_entry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogEnterRandom cdd=new DialogEnterRandom(getActivity());
                cdd.show();
                cdd.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        checkStatus();
                    }
                });
            }
        });
        image_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEditAndAdd(true);
            }
        });

        TV_copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Settings.copyText(getActivity() ,TV_code.getText().toString());
            }
        });

        TV_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Settings.shareText(getActivity() ,TV_code.getText().toString());
            }
        });
        TV_leave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogConfirm();
            }
        });
//        AdapterMyTeam adapter = new AdapterMyTeam(list, getActivity());
//        rvPlayer.setLayoutManager(new GridLayoutManager(getContext(), 1));
//        rvPlayer.setAdapter(adapter);
      //  getTopScorer();
    }



    public void dialogConfirm() {
        new android.app.AlertDialog.Builder(getActivity()).setMessage(getText(R.string.leaveConfarim))
                .setPositiveButton(getText(R.string.yes), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        if(league.getId() != null){
                            RequestParams requestParams = new RequestParams() ;
                            requestParams.put("league_id",league.getId());
                            ApiService.PushRequest(getActivity(), ApiService.leaveLeague,requestParams, new DCallBack() {
                                @Override
                                public void Result(String obj, String fun, boolean IsSuccess) {
                                    if (IsSuccess){
                                        checkStatus();
                                    }

                                }
                            });
                        }
                        dialog.cancel();
                    }
                }).setNegativeButton(getText(R.string.no), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        }).show();



    }

    public void checkStatus(){
        if (Settings.getUser().in_league.equals("0")) {
            profile.setVisibility(View.GONE);
            joinLeag.setVisibility(View.VISIBLE);
        }else{
            profile.setVisibility(View.VISIBLE);
            joinLeag.setVisibility(View.GONE);
            getData(Settings.getUser().in_league);
        }
    }
    public void getData(String in_league) {

        ApiService.loading(getActivity(), true);

        final RequestQueue queue = Volley.newRequestQueue(getActivity());
        // Tag used to cancel the request
        String url = ApiService.getLeagueDetails+"?league_id="+in_league;


        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject responseBody) {
                        // pDialog.hide();

                        try {
                            ApiService.loading(getActivity(), false);

                            System.out.println(responseBody.toString());
                            String status = responseBody.getString("status");

//                            String message = responseBody.getString("message");
                            String code = responseBody.getString("code");


//                            if (status.equals("true")) {

                                String news = responseBody.getString("Leagues");
                                JSONObject jsonArray = new JSONObject(news);
                                JsonParser parser = new JsonParser();
                                JsonElement mJson = parser.parse(jsonArray.toString());
                                Gson gson = new Gson();
                                league = gson.fromJson(mJson, League.class);
                                setDate();
//                            } else {
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
                ApiService.loading(getActivity(), false);

//                webServise.loading(SplashScreenActivity.this, false);

               // ApiService.ErrorResponse(getActivity(), volleyError);
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
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setTitle(getString(R.string.network_connection_error))
                .setMessage("")
                .setCancelable(false)
                .setView(R.layout.item_connection_error)
                .setPositiveButton(R.string.retry, (dialog, which) -> {
                  //  getTopScorer();
                });
        builder.create().show();
    }


    private void initViews(View view) {
        rvPlayer =view.findViewById(R.id.rvPlayer);
        TV_leave =view.findViewById(R.id.TV_leave);
        refresh =view.findViewById(R.id.refresh);
        image =view.findViewById(R.id.image);
        TV_members =view.findViewById(R.id.TV_members);
        TV_code =view.findViewById(R.id.TV_code);
        TV_copy =view.findViewById(R.id.TV_copy);
        TV_share =view.findViewById(R.id.TV_share);
        TV_name =view.findViewById(R.id.TV_name);
        TV_Point =view.findViewById(R.id.TV_Point);
        TV_level =view.findViewById(R.id.TV_level);
        Ly_add_new_team = view.findViewById(R.id.Ly_add_new_team) ;
        Ly_enter_the_code = view.findViewById(R.id.Ly_enter_the_code) ;
        Ly_random_entry = view.findViewById(R.id.Ly_random_entry) ;
        profile = view.findViewById(R.id.profile) ;
        joinLeag = view.findViewById(R.id.joinLeag) ;
        image_edit = view.findViewById(R.id.image_edit) ;
    }


    public void setDate(){

        TV_members.setText(league.getPlayers().size()+"");
        TV_name.setText(league.getName());
        TV_code.setText(league.getCode()+" ");
        TV_Point.setText(league.total_points);
        TV_level.setText(league.rank);

        Picasso.get().load(league.getImage()).into(image);
        image_edit.setVisibility(View.GONE);
        if (league.getUserId().equals(Settings.getUser().getId())){
            image_edit.setVisibility(View.VISIBLE);
        }
        AdapterMyTeam adapter = new AdapterMyTeam(league.getPlayers(),  getActivity(),league.getUserId());

        rvPlayer.setLayoutManager(new GridLayoutManager(getActivity(), 1));
        rvPlayer.setAdapter(adapter);
        // TV_level.setText(league.get);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        Log.e("onActivityResult" ,"onActivityResult") ;
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                dialogAddNewTeam.setImage(resultUri);
                //  logo_img = new File(resultUri.getPath());

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }
}