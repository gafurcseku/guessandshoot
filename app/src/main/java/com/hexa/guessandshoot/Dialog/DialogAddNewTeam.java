package com.hexa.guessandshoot.Dialog;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.hexa.guessandshoot.Activity.Auth.EditeMyAccount;
import com.hexa.guessandshoot.DCallBack;
import com.hexa.guessandshoot.Modules.League;
import com.hexa.guessandshoot.R;
import com.hexa.guessandshoot.Settings.ApiService;
import com.hexa.guessandshoot.Settings.Settings;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.loopj.android.http.RequestParams;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;
import java.io.FileNotFoundException;

import de.hdodenhof.circleimageview.CircleImageView;

public class DialogAddNewTeam extends Dialog implements View.OnClickListener {

  public Activity activity;
  public Dialog d;
  ImageView img_edit_Image ;
  EditText ED_name ;
  TextView TV_Note ;
  CircleImageView image_account ;
  Button btn_submit ;
  FrameLayout close_btn ;
  File image ;
  ProgressBar progress ;
  CheckBox ch_privet ;

  League league ;
  public DialogAddNewTeam(Activity a) {
    super(a);
    // TODO Auto-generated constructor stub
    this.activity = a;
  }
  public DialogAddNewTeam(Activity a,League league ) {
    super(a);
    // TODO Auto-generated constructor stub
    this.activity = a;
    this.league = league;
  }
  public void setImage(Uri image) {
    this.image = new File(image.getPath());
    image_account.setImageURI(image);
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    //requestWindowFeature(Window.FEATURE_NO_TITLE);
    setContentView(R.layout.dialog_add_new_team);
    img_edit_Image =  findViewById(R.id.img_edit_Image);
    btn_submit =  findViewById(R.id.btn_submit);
    ED_name =  findViewById(R.id.ED_name);
    TV_Note =  findViewById(R.id.TV_Note);
    close_btn =  findViewById(R.id.close_btn);
    image_account =  findViewById(R.id.image_account);
    progress =  findViewById(R.id.progress);
    ch_privet =  findViewById(R.id.ch_privet);
    if (league!=null){
      Picasso.get().load(league.getImage()).into(image_account);
      ED_name.setText(league.getName());
      Settings.setDisable(ED_name);

      btn_submit.setText(activity.getText(R.string.edit));
    }

    TV_Note.setText(Html.fromHtml(Settings.getSettings().getNote_01()));
    img_edit_Image.setOnClickListener(this);
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
      case R.id.img_edit_Image:
        Log.e("img_edit_Image","img_edit_Image") ;
        get_cover_image();
        break;
      case R.id.btn_submit:

        sendRequest() ;
        break;
      case R.id.close_btn:
        dismiss();
        break;
      default:
        break;
    }
  }




  public void sendRequest(){
    if (image == null&& league==null){
      Settings.alertDialog(activity,activity.getResources().getString(R.string.enterImage));
      return;
    }
    if (ED_name.getText().toString().isEmpty()){
      Settings.alertDialog(activity,activity.getResources().getString(R.string.enter_name));
      return;
    }

    RequestParams requestParams = new RequestParams() ;
    try {
      requestParams.put("name",ED_name.getText().toString());
      if (image!=null){
        requestParams.put("image",image);
      }

      if (league==null){
        requestParams.put("type","1");
      }else {
        requestParams.put("league_id",league.getId());
      }
      requestParams.put("is_private",ch_privet.isChecked()?1:0);

    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    ApiService.PushRequest(activity,league==null ?ApiService.createLeague:ApiService.editLeague, requestParams, new DCallBack() {
      @Override
      public void Result(String obj, String fun, boolean IsSuccess) {
        if (IsSuccess){
          dismiss();
        }else{
          progress.setProgress(Integer.parseInt(obj));
        }

      }
    });
  }

  public void get_cover_image(){

    Dexter.withActivity(activity)
            .withPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            .withListener(new PermissionListener() {
              @Override public void onPermissionGranted(PermissionGrantedResponse response) {

                CropImage.activity().setGuidelines(CropImageView.Guidelines.ON).start(activity);

              }
              @Override public void onPermissionDenied(PermissionDeniedResponse response) {/* ... */}
              @Override public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {/* ... */}
            }).check();



  }

//  @Override
//  protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//    super.onActivityResult(requestCode, resultCode, data);
//    if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
//      CropImage.ActivityResult result = CropImage.getActivityResult(data);
//      if (resultCode == RESULT_OK) {
//        Uri resultUri = result.getUri();
//
//        logo_img = new File(resultUri.getPath());
//        image_account.setImageURI(resultUri);
//      } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
//        Exception error = result.getError();
//      }
//    }
//
//  }
}