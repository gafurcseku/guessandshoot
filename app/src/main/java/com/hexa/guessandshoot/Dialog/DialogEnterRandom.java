package com.hexa.guessandshoot.Dialog;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.hexa.guessandshoot.DCallBack;
import com.hexa.guessandshoot.R;
import com.hexa.guessandshoot.Settings.ApiService;
import com.hexa.guessandshoot.Settings.Settings;
import com.loopj.android.http.RequestParams;

public class DialogEnterRandom extends Dialog implements View.OnClickListener {

  public Activity c;
  public Dialog d;
  EditText ED_name ;
  TextView TV_Note ;
  Button btn_submit ;
  FrameLayout close_btn ;
  ProgressBar progress ;

  public DialogEnterRandom(Activity a) {
    super(a);
    // TODO Auto-generated constructor stub
    this.c = a;
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    //requestWindowFeature(Window.FEATURE_NO_TITLE);
    setContentView(R.layout.dialog_random);

    btn_submit =  findViewById(R.id.btn_submit);
    ED_name =  findViewById(R.id.ED_name);
    TV_Note =  findViewById(R.id.TV_Note);
    close_btn =  findViewById(R.id.close_btn);
    progress =  findViewById(R.id.progress);
    TV_Note.setText(Html.fromHtml(Settings.getSettings().getNote_03()));
    btn_submit.setOnClickListener(this);
    close_btn.setOnClickListener(this);

  }


  @Override
  public void onStart() {
    super.onStart();
    Dialog dialog = this;
    if (dialog != null) {
      dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
      dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }
  }

  @Override
  public void onClick(View v) {
    switch (v.getId()) {
      case R.id.btn_submit:
        sendRequest();
        break;
      case R.id.close_btn:
        dismiss();
        break;
      default:
        break;
    }
  }

  public void sendRequest(){
    RequestParams requestParams = new RequestParams() ;
    try {
      requestParams.put("type","3");
    } catch (Exception e) {
      e.printStackTrace();
    }
    progress.setVisibility(View.VISIBLE);
    ApiService.PushRequest(c, ApiService.createLeague, requestParams, new DCallBack() {
      @Override
      public void Result(String obj, String fun, boolean IsSuccess) {
        if (IsSuccess){
          dismiss();
        }else{
          progress.setVisibility(View.GONE);
        }

      }
    });
  }
}