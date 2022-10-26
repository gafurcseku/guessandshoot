package com.hexa.guessandshoot.Settings;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.hexa.guessandshoot.Modules.Db_Settings;
import com.hexa.guessandshoot.Modules.Db_user;
import com.hexa.guessandshoot.R;
import com.orhanobut.hawk.Hawk;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.content.Context.CLIPBOARD_SERVICE;


public class Settings {
    public static void alertDialog(Activity activity, String msg) {
        if (activity != null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(
                    activity);
//        builder.setTitle(getActivity().getResources().getString(R.string.delete_mo3da));
            builder.setMessage(msg);
            builder.setCancelable(true);
            builder.setPositiveButton(activity.getResources().getString(R.string.Ok),
                    new DialogInterface.OnClickListener() {
                        public void onClick(
                                DialogInterface dialog,
                                int id) {
                            //Todo code here
                            dialog.cancel();

                        }
                    });
            builder.show();
        }


    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static void setStatusBarGradiant(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
            Drawable background = activity.getResources().getDrawable(R.color.blue);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(activity.getResources().getColor(android.R.color.transparent));
            window.setNavigationBarColor(activity.getResources().getColor(android.R.color.transparent));
            window.setBackgroundDrawable(background);
        }
    }

    public static boolean IsSubscribe() {

        return Hawk.get("notfi");
    }
    public static Db_Settings getSettings() {

        Db_Settings settingsModules = null;
        try {

            String settings = Hawk.get("Settings").toString();
            System.out.println(settings);
            JsonParser parser = new JsonParser();
            JsonElement mJson = parser.parse(settings);

            Gson gson = new Gson();
            settingsModules = gson.fromJson(mJson, Db_Settings.class);

        } catch (Exception e) {

        }


        return settingsModules;


    }
    public static Date gettimeGMT(){
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
        Date currentLocalTime = cal.getTime();

        Log.e("formatlong",cal.getTime()+"");
        SimpleDateFormat format_date12 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
        format_date12.setTimeZone(TimeZone.getTimeZone("GMT"));

        Log.e("format",format_date12.format(cal.getTimeInMillis()));
       // date();
        return currentLocalTime ;
    }
    public static Date dateTimeGMT()  {
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
        Date currentLocalTime = cal.getTime();
        DateFormat date = new SimpleDateFormat("dd-MM-yyy HH:mm:ss",Locale.ENGLISH);
        date.setTimeZone(TimeZone.getTimeZone("GMT"));
        String localTime = date.format(currentLocalTime);
        DateFormat datef = new SimpleDateFormat("dd-MM-yyy HH:mm:ss",Locale.ENGLISH);
        datef.setTimeZone(TimeZone.getDefault());
        Date date1 = new Date() ;
        try {

             date1 = datef.parse(localTime);




        }catch (Exception e){

        }

        return date1;

    }

    public static int getCount(){
      return Hawk.contains("seen")? Hawk.get("seen"):0;
    }

    public static void setCount(int count){
        Hawk.put("seen" ,count);
    }
    public static Db_user setUserStatus(String status) {

        Db_user userModules = null;
        try {

            String User = Hawk.get("User").toString();
            JsonParser parser = new JsonParser();
            JsonElement mJson = parser.parse(User);

            Gson gson = new Gson();
            userModules = gson.fromJson(mJson, Db_user.class);

            userModules.in_league = status ;
            Hawk.put("User", gson.toJson(userModules));
        } catch (Exception e) {

        }


        return userModules;
    }
    public static Db_user getUser() {

        Db_user userModules = null;
        try {

            String User = Hawk.get("User").toString();
            JsonParser parser = new JsonParser();
            JsonElement mJson = parser.parse(User);

            Gson gson = new Gson();
            userModules = gson.fromJson(mJson, Db_user.class);


        } catch (Exception e) {

        }


        return userModules;
    }

    public static boolean emailValidator(String email)
    {
        Pattern pattern;
        Matcher matcher;
       // final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();
    }

  public static void  copyText(Activity activity,String text){
      ClipboardManager clipboard = (ClipboardManager) activity.getSystemService(CLIPBOARD_SERVICE);
      ClipData clip = ClipData.newPlainText("label", text);
      clipboard.setPrimaryClip(clip);
      Toast.makeText(activity, activity.getText(R.string.messageCopy), Toast.LENGTH_SHORT).show();
    }


    public static void shareText(Activity activity , String text){
        Intent intent = new Intent(android.content.Intent.ACTION_SEND);
        /*This will be the actual content you wish you share.*/
        /*The type of the content is text, obviously.*/
        intent.setType("text/plain");
        /*Applying information Subject and Body.*/
        intent.putExtra(android.content.Intent.EXTRA_SUBJECT, activity.getString(R.string.share_subject));
        intent.putExtra(android.content.Intent.EXTRA_TEXT, text);
        /*Fire!*/
        activity.startActivity(Intent.createChooser(intent, activity.getString(R.string.share_using)));
    }
    
    public static void setDisable(EditText view){
        view.setFocusable(false);
        view.setClickable(false);
        view.setLongClickable(false);
        view.setAlpha(0.4f);
    }
}
