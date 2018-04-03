package com.ititeam.tripplannermaster.activity.alarmhandler;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.ititeam.tripplannermaster.R;
import com.ititeam.tripplannermaster.model.ParcelableUtil;
import com.ititeam.tripplannermaster.model.Trip;

/**
 * Created by MARK on 27/03/18.
 */

public class AlarmNotificationService extends IntentService {
    private NotificationManager alarmNotificationManager;

    //Notification ID for Alarm
    public static final int NOTIFICATION_ID = 1;
    Trip trip = null;
    public AlarmNotificationService() {
        super("AlarmNotificationService");
    }

    @Override
    public void onHandleIntent(Intent intent) {

        //Send notification
        sendNotification("Wake Up! Wake Up! Alarm started!!" , intent);
    }

    //handle notification
    private void sendNotification(String msg , Intent intent) {
        alarmNotificationManager = (NotificationManager) this
                .getSystemService(Context.NOTIFICATION_SERVICE);

        if(trip == null)
        {
            trip =(Trip) ParcelableUtil.unmarshall(intent.getByteArrayExtra("trip") , Trip.CREATOR);
        }
        Intent returningIntent = new Intent(this, AlarmActivity.class);
        byte[] last = ParcelableUtil.marshall(trip);
        returningIntent.putExtra("trip",last);
        //get pending intent
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,returningIntent
                , PendingIntent.FLAG_UPDATE_CURRENT);

        //Create notification
        NotificationCompat.Builder alamNotificationBuilder = new NotificationCompat.Builder(
                this).setContentTitle("Alarm").setSmallIcon(R.mipmap.ic_launcher)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(msg))
                .setContentText(msg).setAutoCancel(false);
        alamNotificationBuilder.setContentIntent(contentIntent);

        //notiy notification manager about new notification
        Notification notification = alamNotificationBuilder.build();
        // this is the main thing to do to make a non removable notification
        notification.flags |= Notification.FLAG_NO_CLEAR;
        alarmNotificationManager.notify(NOTIFICATION_ID, notification);
    }


}
