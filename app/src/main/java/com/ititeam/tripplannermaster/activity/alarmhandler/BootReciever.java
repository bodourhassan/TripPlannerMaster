package com.ititeam.tripplannermaster.activity.alarmhandler;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.ititeam.tripplannermaster.DB.TripTableOperations;
import com.ititeam.tripplannermaster.activity.AddNewTrip;
import com.ititeam.tripplannermaster.model.Trip;

import java.util.ArrayList;
import java.util.Calendar;

import static android.support.v4.content.WakefulBroadcastReceiver.startWakefulService;

public class BootReciever extends BroadcastReceiver {
    private PendingIntent pendingIntent;
    private Context context;
    @Override
    public void onReceive(Context context, Intent intent) {
        this.context = context;
        // TODO: This method is called when the BroadcastReceiver is receiving
        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
            // Set the alarm here.
            ArrayList<Trip> trips = new TripTableOperations(context).selectUpcomingTripsUsingOnlyDate();
            for (Trip trip : trips)
            {
//                Intent bootIntent=new Intent(context, AlarmScheduleService.class);
//                intent.putExtra("trip",trip);
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
//                Toast.makeText(context,trip.getTripId()+"", Toast.LENGTH_SHORT).show();
//                ComponentName comp = new ComponentName(context.getPackageName(),
//                AlarmScheduleService.class.getName());
//                 startWakefulService(context, (intent.setComponent(comp)));

                //Trip trip =  new TripTableOperations(getApplicationContext()).selectSingleTrips(trip_id+"");
                Intent alarmIntent = new Intent(context, AlarmReceiver.class);
                //alarmIntent.putExtra("trip_id" , trip_id);
                alarmIntent.putExtra("trip" , trip);
                pendingIntent = PendingIntent.getBroadcast(context, trip.getTripId(), alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);

                triggerAlarmManager(trip);
            }
        }
    }

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

        AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);//get instance of alarm manager
        manager.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pendingIntent);//set alarm manager with entered timer by converting into milliseconds

    }
}
