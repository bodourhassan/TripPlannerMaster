package com.ititeam.tripplannermaster.activity.alarmhandler;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by MARK on 27/03/18.
 */

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "ALARM!! ALARM!!", Toast.LENGTH_SHORT).show();

        //Stop sound service to play sound for alarm
        context.startService(new Intent(context, AlarmSoundService.class));

        //This will send a notification message and show notification in notification tray
//        ComponentName comp = new ComponentName(context.getPackageName(),
//                AlarmNotificationService.class.getName());
//        startWakefulService(context, (intent.setComponent(comp)));
        int trip_id = intent.getIntExtra("trip_id" , 0);
        Intent alarmActivityIntent = new Intent(context, AlarmActivity.class);
        alarmActivityIntent.putExtra("trip_id" , trip_id);
        context.startActivity(alarmActivityIntent);

    }


}
