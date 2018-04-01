package com.ititeam.tripplannermaster.activity.alarmhandler;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;
import com.ititeam.tripplannermaster.DB.*;
import com.ititeam.tripplannermaster.model.ParcelableUtil;
import com.ititeam.tripplannermaster.model.Trip;

import java.util.Calendar;
import java.util.GregorianCalendar;


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
//            Trip trip = (Trip) intent.getSerializableExtra("trip");
            byte[] last = intent.getByteArrayExtra("trip");
            Trip trip =(Trip) ParcelableUtil.unmarshall(last , Trip.CREATOR);

            Log.i("Mark" , trip.getTripDate()+"");

            //Trip trip =  new TripTableOperations(getApplicationContext()).selectSingleTrips(trip_id+"");
            Toast.makeText(this,trip.getTripId()+"", Toast.LENGTH_SHORT).show();
            Intent alarmIntent = new Intent(AlarmScheduleService.this, AlarmReceiver.class);
            //alarmIntent.putExtra("trip_id" , trip_id);

            alarmIntent.putExtra("trip" , last);
            pendingIntent = PendingIntent.getBroadcast(getBaseContext(), trip.getTripId(), alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);

                triggerAlarmManager(trip);


        }
    }

    //Trigger alarm manager with entered time interval
    public void triggerAlarmManager(Trip trip) {
        // get a Calendar object with current time
       // Log.i("Mark" , trip.getTripDate()+"");
        String [] date = trip.getTripDate().split("-");
//        for (String s : date)
//        {
//            Log.i("jiji" , s);
//        }
        String [] time = trip.getTripTime().split(":" +
                "");
//        for (String s : time)
//        {
//            Log.i("jiji" , s);
//        }
        Calendar cal = Calendar.getInstance();
        //Calendar cal =  new GregorianCalendar();
        // add alarmTriggerTime seconds to the calendar object
//        cal.add(Calendar.DATE, Integer.parseInt(trip.getTripDate()));
//        cal.set(Calendar.YEAR ,2018);
//        cal.add(Calendar.HOUR_OF_DAY , 23);
//        cal.add(Calendar.MINUTE , 15);
//        cal.add(Calendar.SECOND, 0);
        int month = 0;
        cal.setTimeInMillis(System.currentTimeMillis());
        cal.clear();
        switch (Integer.parseInt(date[1]))
        {
            case 1:
                month = Calendar.JANUARY;
                break;
            case 2:
                month = Calendar.FEBRUARY;
                break;
            case 3:
                month = Calendar.MARCH;
                break;
            case 4:
                month = Calendar.APRIL;
                break;
            case 5:
                month = Calendar.MAY;
                break;
            case 6:
                month = Calendar.JUNE;
                break;
            case 7:
                month = Calendar.JULY;
                break;
            case 8:
                month = Calendar.AUGUST;
                break;
            case 9:
                month = Calendar.SEPTEMBER;
                break;
            case 10:
                month = Calendar.OCTOBER;
                break;
            case 11:
                month = Calendar.NOVEMBER;
                break;
            case 12:
                month = Calendar.DECEMBER;
                break;
        }
        cal.set(Integer.parseInt(date[0]),month,Integer.parseInt(date[2]),Integer.parseInt(time[0]),Integer.parseInt(time[1]));
        //cal.set(2018,3,1,2,58 ,0);

        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);//get instance of alarm manager
        manager.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pendingIntent);//set alarm manager with entered timer by converting into milliseconds

        Toast.makeText(this, "Alarm Set for " + 1 + " seconds.", Toast.LENGTH_SHORT).show();
    }

}
