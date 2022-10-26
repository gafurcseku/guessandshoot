package com.hexa.guessandshoot.Activity.Auth;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ContentUris;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.hexa.guessandshoot.Activity.ChangePassword;
import com.hexa.guessandshoot.Adapter.Adapter_MyExpections_In_MyAccont;
import com.hexa.guessandshoot.Modules.Db_My_Expections_profile;
import com.hexa.guessandshoot.Modules.Db_user;
import com.hexa.guessandshoot.R;
import com.hexa.guessandshoot.Settings.ApiService;
import com.hexa.guessandshoot.Settings.Settings;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.orhanobut.hawk.Hawk;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Map;

import cz.msebera.android.httpclient.Header;
import gun0912.tedbottompicker.TedBottomPicker;
import gun0912.tedbottompicker.TedBottomSheetDialogFragment;


public class EditeMyAccount extends AppCompatActivity {
    TextView change_password;
    Button confirm;
    ImageView arrow_back, image_account;
    EditText name, email, mobile;

    File logo_img;
    ImageView edite_account;
    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edite_my_account);
        change_password = findViewById(R.id.change_password);
        confirm = findViewById(R.id.confirm);
        edite_account = findViewById(R.id.edite_account);
        image_account = findViewById(R.id.image_account);

        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        mobile = findViewById(R.id.mobile);

        mAdView =  findViewById(R.id.adView);

        if(!((Boolean) Hawk.get("status"))){
            AdRequest adRequest = new AdRequest.Builder().build();
            mAdView.loadAd(adRequest);
        }else {
            mAdView.setVisibility(View.GONE);
        }

        try {


            name.setText(Settings.getUser().getName());
            email.setText(Settings.getUser().getEmail());
            mobile.setText(Settings.getUser().getMobile());
            Picasso.get().load(Settings.getUser().getImageProfile()).error(getResources().getDrawable(R.drawable.ic_account)).into(image_account);

        } catch (Exception e) {

        }

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                valid();
            }


        });

        edite_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                    if (ContextCompat.checkSelfPermission(EditeMyAccount.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
//
//
//                        // No explanation needed, we can request the permission.
//
//                        ActivityCompat.requestPermissions(EditeMyAccount.this,
//                                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
//                                10);
//
//                        // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
//                        // app-defined int constant. The callback method gets the
//                        // result of the request.
//
//                    } else {
//
//                        CropImage.activity()
//                                .setGuidelines(CropImageView.Guidelines.ON)
//                                .start(EditeMyAccount.this);
//
//
//                    }
//                } else {
//                    CropImage.activity()
//                            .setGuidelines(CropImageView.Guidelines.ON)
//                            .start(EditeMyAccount.this);
//
//                }

                get_cover_image();

            }
        });


        change_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditeMyAccount.this, ChangePassword.class);
                startActivity(intent);
            }
        });
        arrow_back = findViewById(R.id.arrow_back);
        arrow_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }


    public void valid() {

        String mname = name.getText().toString();
        String memail = email.getText().toString();
        String mmobile = mobile.getText().toString();
        if (mname == null || mname.isEmpty()) {
            Settings.alertDialog(EditeMyAccount.this,getString(R.string.enter_name));
        } else if (mname.length()<4||mname.length()>15) {
            Settings.alertDialog(EditeMyAccount.this,getResources().getString(R.string.message_error));
        }else if (memail == null || memail.isEmpty()) {
           Settings.alertDialog(EditeMyAccount.this,getString(R.string.enter_email));

        }  else if (!Settings.emailValidator(memail)) {
            Settings.alertDialog(EditeMyAccount.this,getString(R.string.invalid_email_address));

        }else if (mmobile == null || mmobile.isEmpty()) {
            Settings.alertDialog(EditeMyAccount.this,getString(R.string.enter_phone_number));

        } else if (mmobile.length()<8 || mmobile.length()>12) {
            Settings.alertDialog(EditeMyAccount.this,getString(R.string.Phone_number_must));

        } else{

            editUser(mname,memail,mmobile);
        }
    }
    public void editUser(
            String name, String email, String mobile
    ) {

        ApiService.loading(EditeMyAccount.this, true);
        String BASE_URL= ApiService.editProfile;
        RequestParams requestParams=new RequestParams();
        try{
            requestParams.put("name", name);
            requestParams.put("mobile", mobile);
            if (logo_img!=null){
                requestParams.put("image_profile", logo_img);
            }



        }catch (Exception e){

        }
        AsyncHttpClient client = new AsyncHttpClient();
        ApiService.Header_Async(client);
        client.setConnectTimeout(10*1000*60);
        client.setResponseTimeout(10*1000*60);

        client.post(BASE_URL, requestParams, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, JSONObject response) {
                ApiService.loading(EditeMyAccount.this, false);
                Log.e("response", response.toString());
                try {


                    String status = response.getString("status");

//                            String message = responseBody.getString("message");
                    String code = response.getString("code");


                    if (status.equals("true")) {
                        String user = response.getString("user");
                        Hawk.put("User", user);
                        finish();
                    }



                       ;
                } catch (Exception e) {
                    Log.e("Exception",e.toString());
                    try {
                        Toast.makeText(EditeMyAccount.this, response.getString("message")+"", Toast.LENGTH_SHORT).show();
                    } catch (JSONException e1) {
                        e1.printStackTrace();
                    }
                }
            }
            @Override
            public void onFinish() {
                //  startActivity(new Intent(JoinUsActivity.this,Massege_Join_UsActivity.class));
                super.onFinish();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                Log.e("JSONObject11",responseString.toString());
                //GlobalSettings.loading(getActivity(), false);
            }
            @Override
            public void onUserException(Throwable error) {
                // super.onUserException(error);
                Log.d("ttt", "onFailure: jhjj" + error.getMessage());

            }
        });
    }


    public void editProfile(String name, String email, String mobile) {

    //     webServise.loading(SplashScreenActivity.this, true);

        final RequestQueue queue = Volley.newRequestQueue(EditeMyAccount.this);
        // Tag used to cancel the request
        String url = ApiService.editProfile;

        JSONObject object = new JSONObject();

        try {
            object.put("name", name);
            object.put("email", email);
            object.put("mobile", mobile);


        } catch (Exception e) {

        }


        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST, url, object,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject responseBody) {
                        // pDialog.hide();
                        System.out.println(responseBody);


                        try {

                            String status = responseBody.getString("status");

//                            String message = responseBody.getString("message");
                            String code = responseBody.getString("code");


                            if (status.equals("true")) {
                                String user = responseBody.getString("user");
                                Hawk.put("User", user);
                                finish();
                                System.out.println(user);


                            } else {

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError volleyError) {
////
//                webServise.loading(SplashScreenActivity.this, false);

                ApiService.ErrorResponse(EditeMyAccount.this, volleyError);
            }
        }) {

            /**
             * Passing some request headers
             */

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

                return ApiService.getHeader(false);
            }

        };
        jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(
                9000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        queue.add(jsonObjReq);


    }

    public void update_image_profile(File file) {

        ApiService.loading(EditeMyAccount.this, true);

        AsyncHttpClient client = new AsyncHttpClient();

        String BASE_URL = ApiService.editProfile;

        final int DEFAULT_TIMEOUT = 20 * 1000;

        client.setTimeout(DEFAULT_TIMEOUT);
        ApiService.Header_Async(client);

        RequestParams requestParams = new RequestParams();
        try {
            requestParams.put("image_profile", file);


        } catch (Exception e) {

        }


        client.post(BASE_URL, requestParams, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);

                ApiService.loading(EditeMyAccount.this, false);


                try {


                    String status = response.getString("status");

//                            String message = responseBody.getString("message");
                    String code = response.getString("code");


                    if (status.equals("true")) {

                        String user = response.getString("user");
                        Hawk.put("User", user);

                        System.out.println("done_djgkjdkfdkfj");


                    } else {

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }


            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                System.out.println(errorResponse.toString());
            }

            @Override
            public void onUserException(Throwable error) {
                // super.onUserException(error);
//                Log.d("ttt", "onFailure: jhjj" + error.getMessage());


            }


            @Override
            public void onProgress(long bytesWritten, long totalSize) {


                try {

                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();

                logo_img = new File(resultUri.getPath());
                image_account.setImageURI(resultUri);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }

    }

    //    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
//            CropImage.ActivityResult result = CropImage.getActivityResult(data);
//            if (resultCode == RESULT_OK) {
//                Uri resultUri = result.getUri();
//
//                File file = new File(getPath(resultUri));
////                image = file;
////                Bitmap myBitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
////                logo.setImageBitmap(myBitmap);
////                image_select = true;
//
//                update_image_profile(file);
//                Bitmap myBitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
//                image_account.setImageBitmap(myBitmap);
//
//
////                update_image(type_image_profile_or_cover, image_profile);
//
//                try {
//
//
//                    RequestParams requestParams = new RequestParams();
//                    requestParams.put("kind", "travilling");//travilling/tour
//
//                    requestParams.put("logo", file);//travilling/tour
//
//
////                    send_EditProfile(requestParams);
//
//                } catch (Exception e) {
//
//                }
//
//            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
//                Exception error = result.getError();
//            }
//        }
//    }
    private String getPath(final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
        if (isKitKat) {
            // MediaStore (and general)
            return getForApi19(uri);
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {

            // Return the remote address
            if (isGooglePhotosUri(uri))
                return uri.getLastPathSegment();

            return getDataColumn(uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }






    @TargetApi(19)
    private String getForApi19(Uri uri) {
        // Log.e(tag, "+++ API 19 URI :: " + uri);
        if (DocumentsContract.isDocumentUri(this, uri)) {
            //   Log.e(tag, "+++ Document URI");
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                // Log.e(tag, "+++ External Document URI");
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    //       Log.e(tag, "+++ Primary External Document URI");
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }

                // TODO handle non-primary volumes
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {
                //    Log.e(tag, "+++ Downloads External Document URI");
                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                //  Log.e(tag, "+++ Media Document URI");
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    //   Log.e(tag, "+++ Image Media Document URI");
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    //    Log.e(tag, "+++ Video Media Document URI");
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    //     Log.e(tag, "+++ Audio Media Document URI");
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{
                        split[1]
                };

                return getDataColumn(contentUri, selection, selectionArgs);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            // Log.e(tag, "+++ No DOCUMENT URI :: CONTENT ");

            // Return the remote address
            if (isGooglePhotosUri(uri))
                return uri.getLastPathSegment();

            return getDataColumn(uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            //   Log.e(tag, "+++ No DOCUMENT URI :: FILE ");
            return uri.getPath();
        }
        return null;
    }

    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     *
     * @param uri           The Uri to query.
     * @param selection     (Optional) Filter used in the query.
     * @param selectionArgs (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     */
    public String getDataColumn(Uri uri, String selection,
                                String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };

        try {
            cursor = getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }


    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is Google Photos.
     */
    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }

    public void get_cover_image(){

        Dexter.withActivity(this)
                .withPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new PermissionListener() {
                    @Override public void onPermissionGranted(PermissionGrantedResponse response) {

                        CropImage.activity()
                                .setGuidelines(CropImageView.Guidelines.ON)
                                .start(EditeMyAccount.this);



//                        TedBottomPicker.with(EditeMyAccount.this)
//                                .setPeekHeight(1600)
//                                .showTitle(false)
//                                .setCompleteButtonText("Done")
//                                .setEmptySelectionText("No Select")
//                                .show(new TedBottomSheetDialogFragment.OnImageSelectedListener() {
//                                    @Override
//                                    public void onImageSelected(Uri uri) {
//                                         logo_img = new File(uri.getPath());
//                                         image_account.setImageURI(uri);
//
//                                    }
//                                });
                    }
                    @Override public void onPermissionDenied(PermissionDeniedResponse response) {/* ... */}
                    @Override public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {/* ... */}
                }).check();



    }



}
