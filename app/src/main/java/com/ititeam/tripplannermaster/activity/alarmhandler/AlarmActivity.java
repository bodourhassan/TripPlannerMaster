package com.ititeam.tripplannermaster.activity.alarmhandler;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.ititeam.tripplannermaster.R;
import com.ititeam.tripplannermaster.activity.MainActivity;
import com.ititeam.tripplannermaster.activity.ShowTripActivity;
import com.ititeam.tripplannermaster.activity.StartTripActivity;
import com.ititeam.tripplannermaster.model.ParcelableUtil;
import com.ititeam.tripplannermaster.model.Trip;

public class AlarmActivity extends AppCompatActivity {

    //Pending intent instance
    private PendingIntent pendingIntent;
    Trip trip = null;
    //Alarm Request Code
    private static final int ALARM_REQUEST_CODE = 133;
    private Intent openingIntent = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         this.supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_alarm);
        this.openingIntent = getIntent();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            this.setFinishOnTouchOutside(false);
        }

        //Set On CLick over start alarm button
        findViewById(R.id.start_alarm_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Trip trip = (Trip) getIntent().getSerializableExtra("trip");
                //Trip trip= (Trip) openingIntent.getSerializableExtra("trip");

                trip =(Trip) ParcelableUtil.unmarshall(openingIntent.getByteArrayExtra("trip") , Trip.CREATOR);
                Intent intent =new Intent(AlarmActivity.this,StartTripActivity.class);
                intent.putExtra("trip_id",trip.getTripId()+"");
                stopAlarmManager();
                startActivity(intent);

            }
        });

        //set on click over stop alarm button
        findViewById(R.id.stop_alarm_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Stop alarm manager
                stopAlarmManager();
                finish();
            }
        });

        findViewById(R.id.later_alarm_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Later alarm manager
                 trip =(Trip) ParcelableUtil.unmarshall(openingIntent.getByteArrayExtra("trip") , Trip.CREATOR);
                Intent intent = new Intent(AlarmActivity.this , AlarmNotificationService.class);
                byte[] last = ParcelableUtil.marshall(trip);
                intent.putExtra("trip",last);
                startService(intent);
                //Stop the Media Player Service to stop sound
                stopService(new Intent(AlarmActivity.this, AlarmSoundService.class));
                finish();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
    }

    //Stop/Cancel alarm manager
    public void stopAlarmManager() {

        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent alarmIntent = new Intent(AlarmActivity.this, MainActivity.class);
        //Trip trip = (Trip) getIntent().getSerializableExtra("trip");
        //Trip trip= (Trip) openingIntent.getSerializableExtra("trip");
        if(trip == null)
        {
            trip =(Trip) ParcelableUtil.unmarshall(openingIntent.getByteArrayExtra("trip") , Trip.CREATOR);
        }
        Toast.makeText(getApplicationContext(), trip.getTripId()+"", Toast.LENGTH_SHORT).show();
        pendingIntent = PendingIntent.getBroadcast(getBaseContext(), trip.getTripId(), alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        manager.cancel(pendingIntent);//cancel the alarm manager of the pending intent


        //Stop the Media Player Service to stop sound
        stopService(new Intent(getBaseContext(), AlarmSoundService.class));

        //remove the notification from notification tray
        NotificationManager notificationManager = (NotificationManager) this
                .getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancel(AlarmNotificationService.NOTIFICATION_ID);

        Toast.makeText(this, "Alarm Canceled/Stop by User.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {

    }

}
