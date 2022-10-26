package com.hexa.guessandshoot.FCM;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.hexa.guessandshoot.R;
import com.hexa.guessandshoot.Settings.Settings;
import com.hexa.guessandshoot.SplashActivity;

import com.orhanobut.hawk.Hawk;


public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        Settings.setCount(1);
       // Log.e(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());

        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.e(TAG, "From: " + remoteMessage.getFrom());




        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {

            Log.e("Message_payload",remoteMessage.getData().toString());

                    if (Hawk.get("notfi")){
                        showNotification(remoteMessage.getData().get("body"), "guessandshoot", remoteMessage, remoteMessage.getData().get("typeMsg"));
                    }


            }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            showNotification(remoteMessage.getNotification().getBody(), remoteMessage.getNotification().getTitle(), remoteMessage, "2");
            Log.e(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());


        }


        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
    }




    private void showNotification(String body, String Title, RemoteMessage remoteMessage, String type) {
//        Intent intent ;
//        intent = new Intent(this, HomeActivity.class);
//        System.out.println("typetype"+type);

//
            Intent intent;

            intent = new Intent(this, SplashActivity.class);
            intent.putExtra("type",10) ;



        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        int color = getResources().getColor(R.color.colorPrimary);
        long[] v = {500, 1000};
        Bitmap icon = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);



        //pendingIntent
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 , intent, PendingIntent.FLAG_ONE_SHOT| PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_MUTABLE);





//        Log.d("Hay8","DCM8");
//        Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
//        Log.d("Hay9","DCM9");
//        PendingIntent pendingIntent = PendingIntent.getActivity(context,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
//        Log.d("Hay10","DCM10");

        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), "default")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(Title)
                .setContentText(body)
                .setSound(defaultSoundUri)

                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent);
        Log.d("Hay11", "DCM11");


        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        Log.d("Hay12", "DCM12");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            builder.setChannelId("com.myApp");
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    "com.myApp",
                    "My App",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }

        notificationManager.notify(2, builder.build());
    }







    @Override
    public void onNewToken(String token) {
        Log.e(TAG, "Refreshed token: " + token);

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        sendRegistrationToServer(token);
    }




    private void sendRegistrationToServer(String token) {
        // TODO: Implement this method to send token to your app server.
    }



}
