package com.hexa.guessandshoot;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.hexa.guessandshoot.Activity.Auth.LoginActivity;
import com.hexa.guessandshoot.Activity.MainActivity;
import com.orhanobut.hawk.Hawk;

import java.util.Locale;

public class SelectLanguageActivity extends AppCompatActivity implements View.OnClickListener {
    Button btn_English ,btn_arabic;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_language);
        btn_English =findViewById(R.id.btn_English);
        btn_arabic =findViewById(R.id.btn_arabic);
        btn_English.setOnClickListener(this);
        btn_arabic.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_English:
                setIntent("en");
                break;
            case R.id.btn_arabic:
                setIntent("ar");
                break;
        }
    }

    public void setIntent(String lang){
//        Intent intent1 ;
//        LocaleUtils.setLocale(getActivity().getApplicationContext(), lang);
//        intent1 = getActivity().getBaseContext().getPackageManager().getLaunchIntentForPackage(getActivity().getBaseContext().getPackageName());
//        intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        startActivity(intent1);
//        UserAuth.setLang(lang);


        Hawk.put("lang",lang);
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,getBaseContext().getResources().getDisplayMetrics());

        Intent intent = new Intent(SelectLanguageActivity.this, SplashActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();


//        if (Hawk.contains(Constants.KEY_Ads)){
//            if (UserAuth.getUser() != null){
//                Intent intent = new Intent(getActivity(), MainActivity.class);
//                startActivity(intent);
//                finish();
//            }else {
//                Intent intent = new Intent(getActivity(), LoginActivity.class);
//                startActivity(intent);
//                finish();
//            }
//
//        }else {
//            Intent intent = new Intent(getActivity(), AdsActivity.class);
//            startActivity(intent);
//            finishAffinity();
//        }
    }
}
