package com.ititeam.tripplannermaster.activity.alarmhandler;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import java.util.Calendar;


/**
 * Created by MARK on 27/03/18.
 */

public class AlarmScheduleService extends IntentService {

    //Pending intent instance
    private PendingIntent pendingIntent;
    //Alarm Request Code
    private static final int ALARM_REQUEST_CODE = 133;
    public AlarmScheduleService() {
        super("AlarmScheduleService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null)
        {
            int trip_id = intent.getIntExtra("trip_id" , 0);
            Toast.makeText(this,trip_id+"", Toast.LENGTH_SHORT).show();
            Intent alarmIntent = new Intent(AlarmScheduleService.this, AlarmReceiver.class);
            alarmIntent.putExtra("trip_id" , trip_id);
            pendingIntent = PendingIntent.getBroadcast(getBaseContext(), trip_id, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);

                triggerAlarmManager(5);


        }
    }

    //Trigger alarm manager with entered time interval
    public void triggerAlarmManager(int alarmTriggerTime) {
        // get a Calendar object with current time
        Calendar cal = Calendar.getInstance();
        // add alarmTriggerTime seconds to the calendar object
        cal.add(Calendar.SECOND, alarmTriggerTime);

        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);//get instance of alarm manager
        manager.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pendingIntent);//set alarm manager with entered timer by converting into milliseconds

        Toast.makeText(this, "Alarm Set for " + alarmTriggerTime + " seconds.", Toast.LENGTH_SHORT).show();
    }

}
