package com.acrs.buddies.di.service;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;

import com.acrs.buddies.R;
import com.acrs.buddies.data.AppDataManager;
import com.acrs.buddies.ui.dashboard.DashBoardActvity;
import com.acrs.buddies.ui.viewrequest.ViewRequestFromNotify;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.Random;

/**
 * Created by soorya on 31-05-18.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "firebase";
    private AppDataManager appDataManager;


    // two conditions

    // emergency push // patient not responding
    String PATIENT_NOT_RESPONDING_MSG = "Patient not responding";
    String PATIENT_NOT_RESPONDING_TITLE = "";


    // panic push // patient triggering
    String PATIENT_PANIC_MSG = "";
    String PATIENT_PANIC_TITLE = "";


    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {


        if (remoteMessage.getData() != null && remoteMessage.getData().size() > 0) {

            try {
                sendNotification(remoteMessage.getData());

            } catch (Exception e) {
                e.printStackTrace();
            }

        }


    }


    private void sendNotification(Map<String, String> data) throws Exception {


        String type = data.get("type");
        if (type.equalsIgnoreCase("emergency"))

        {
            String url = data.get("image_url");
            String username = data.get("who");
            String location = data.get("location");

            String title = "Your patient "+username+" is not responding";
            String message = PATIENT_NOT_RESPONDING_MSG;

            Bitmap bitmap = getBitmapfromUrl(url);

            Intent intent = new Intent(this, ViewRequestFromNotify.class);

            intent.putExtra("imageUrl", url);
            intent.putExtra("username", username);
            intent.putExtra("userlocation", location);
            TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
            stackBuilder.addParentStack(DashBoardActvity.class);

            stackBuilder.addNextIntentWithParentStack(intent);
            //intent.putExtra(FIREBASE_DATA, remoteMessage);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);


            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                    PendingIntent.FLAG_ONE_SHOT);


            Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                    .setLargeIcon(bitmap)/*Notification icon image*/
                    .setSmallIcon(android.R.drawable.btn_star)
                    .setContentTitle(message)
                    .setContentText(title)
                    .setStyle(new NotificationCompat.BigPictureStyle()
                            .bigPicture(bitmap))/*Notification with Image*/
                    .setAutoCancel(true)
                    .setSound(defaultSoundUri)
                    .setContentIntent(pendingIntent);

            NotificationManager notificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

            notificationManager.notify(new Random().nextInt(61) + 20 /* ID of notification */, notificationBuilder.build());


        } else if (type.equalsIgnoreCase("panic")) {

            String username = data.get("who");
            String location = data.get("location");

            String title = "Panic Alert";
            String message = "Your patient "+username + "is in a panic situation at "+location;
            Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle(title)
                    .setContentText(message)
                    .setAutoCancel(true)
                    .setSound(defaultSoundUri);
            NotificationManager notificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

            notificationManager.notify(new Random().nextInt(61) + 20 /* ID of notification */, notificationBuilder.build());


        }


    }

    /*
          }

        /*
         *To get a Bitmap image from the URL received
         * */
    public Bitmap getBitmapfromUrl(String imageUrl) {
        try {
            URL url = new URL(imageUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap bitmap = BitmapFactory.decodeStream(input);
            return bitmap;

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;

        }
    }


    private void sendNotificationNotification(String remoteMessage, String message, Map<String, String> data, int[] typeuser) throws Exception {

        //JSONObject jsonObject = new JSONObject()
        int type = 0;
        if (data.containsKey("type")) {
            type = Integer.parseInt(data.get("type"));
        }

//        int type = !data.isNull("type") ? data.get("type") : 0;

        // TODO: 18/7/18   type 1 for trainer and type 0 for citizen

        /*f (type == 1 && typeuser[0] != 0) {

            Intent intent = new Intent(this, ScheduleListActivity.class);
            TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
            stackBuilder.addParentStack(DashScheduleActivity.class);

            stackBuilder.addNextIntentWithParentStack(intent);
            //intent.putExtra(FIREBASE_DATA, remoteMessage);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            PendingIntent pendingIntent =
                    stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
            notifyMsg(remoteMessage, message,pendingIntent);


        } else if (type == 0) {

            // TODO: 18/7/18 citizen logined
            if (typeuser[1] != 0) {
                Intent intent = new Intent(this, CitizenContentActivity.class);
                TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
                stackBuilder.addParentStack(PublicDashActivity.class);
                stackBuilder.addNextIntentWithParentStack(intent);


                //        intent.putExtra(FIREBASE_DATA, remoteMessage);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                PendingIntent pendingIntent =
                        stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
                notifyMsg(remoteMessage, message,pendingIntent);


            }
            // TODO: 18/7/18 trainer logined
            else if (typeuser[0] != 0) {
                Intent intent = new Intent(this, ContentsActivity.class);
                TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
                stackBuilder.addParentStack(DashScheduleActivity.class);
                stackBuilder.addNextIntentWithParentStack(intent);
                // intent.putExtra(FIREBASE_DATA, remoteMessage);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                PendingIntent pendingIntent =
                        stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
                notifyMsg(remoteMessage, message,pendingIntent);


            }


        }*/


    }




    /*private void sendNotification(RemoteMessage remoteMessage) throws Exception {

        String messagebody = "";
        String data = null;

        if (remoteMessage != null && remoteMessage.getNotification() != null && remoteMessage.getNotification().getBody() != null) {
            messagebody = remoteMessage.getNotification().getBody().toString();
            if (remoteMessage.getData() != null && remoteMessage.getData().size() > 0) {
                data = remoteMessage.getData().toString();


              //  sendNotificationNotification(data);
                return;


            }




          *//*  Intent pintent = new Intent (this, Splash.class);
            TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
            stackBuilder.addParentStack(NotificationActivity.class);



            Intent intent1 = new Intent(this, DashScheduleActivity.class);
            Intent intent2 = new Intent(this, Splash.class);
            Intent intent = new Intent(this, NotificationActivity.class);
            intent.putExtra(FIREBASE_DATA, data);
            stackBuilder.addNextIntent(intent2);
            stackBuilder.addNextIntent(intent1);
            stackBuilder.addNextIntent(intent);*//*

            Intent start = new Intent();





           *//* TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
            stackBuilder.addNextIntentWithParentStack(intent);*//*

            Intent notifyIntent = new Intent(this, NotificationActivity.class);
            Intent dashIntent = new Intent(this, DashScheduleActivity.class);
            Intent splashIntent = new Intent(this, Splash.class);

            TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);

            stackBuilder.addNextIntent(splashIntent);
            stackBuilder.addNextIntentWithParentStack(dashIntent);

            dashIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            notifyIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            PendingIntent pendingIntent =
                    *//* stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
     *//*

                    PendingIntent.getActivity(this, 0, dashIntent,
                            PendingIntent.FLAG_UPDATE_CURRENT);

            Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                    .setSmallIcon(android.R.drawable.btn_star)
                    .setContentTitle("Digital literacy")
                    .setContentText(messagebody)
                    .setAutoCancel(true)
                    .setSound(defaultSoundUri)
                    .setContentIntent(pendingIntent);

            NotificationManager notificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

            notificationManager.notify(0 *//* ID of notification *//*, notificationBuilder.build());
        }
    }*/


  /*  public void notifyMsg(String title, String message,PendingIntent pendingIntent) throws Exception{
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_notification_small_one)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(),
                        R.mipmap.ic_launcher))
                .setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0 *//* ID of notification *//*, notificationBuilder.build());
    }*/
}
