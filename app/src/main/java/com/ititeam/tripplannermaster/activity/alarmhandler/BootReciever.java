package com.ititeam.tripplannermaster.activity.alarmhandler;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.ititeam.tripplannermaster.DB.TripTableOperations;
import com.ititeam.tripplannermaster.activity.AddNewTrip;
import com.ititeam.tripplannermaster.model.Trip;

import java.util.ArrayList;

import static android.support.v4.content.WakefulBroadcastReceiver.startWakefulService;

public class BootReciever extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
            // Set the alarm here.
            ArrayList<Trip> trips = new TripTableOperations(context).selectAllTrips();
            for (Trip trip : trips)
            {
                Intent bootIntent=new Intent(context, AlarmScheduleService.class);
                intent.putExtra("trip",trip);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                Toast.makeText(context,trip.getTripId()+"", Toast.LENGTH_SHORT).show();
                ComponentName comp = new ComponentName(context.getPackageName(),
                AlarmScheduleService.class.getName());
                 startWakefulService(context, (intent.setComponent(comp)));
            }
        }
    }
}
