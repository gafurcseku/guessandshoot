package com.hexa.guessandshoot.Settings;


import com.android.billingclient.api.BillingClient;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.RequestConfiguration;
import com.google.firebase.FirebaseApp;
import com.orhanobut.hawk.BuildConfig;
import com.orhanobut.hawk.Hawk;

import java.util.Arrays;
import java.util.List;

public class Application extends android.app.Application {


    @Override
    public void onCreate() {
        super.onCreate();

         FirebaseApp.initializeApp(this);
       Hawk.init(this).build();


    }


}
