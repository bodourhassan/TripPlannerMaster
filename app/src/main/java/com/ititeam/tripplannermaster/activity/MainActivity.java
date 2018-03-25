package com.ititeam.tripplannermaster.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.ititeam.tripplannermaster.DB.TripTableOperations;
import com.ititeam.tripplannermaster.R;
import com.ititeam.tripplannermaster.*;
import com.ititeam.tripplannermaster.model.Note;
import com.ititeam.tripplannermaster.model.Trip;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnHesham , btnBdour , btnHana , btnMark;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnHesham = findViewById(R.id.MainActivity_ButtonHesham);
        btnBdour = findViewById(R.id.MainActivity_ButtonBdour);
        btnMark = findViewById(R.id.MainActivity_ButtonMark);
        btnHana = findViewById(R.id.buttMainActivity_ButtonHana);

        btnHana.setOnClickListener(this);
        btnMark.setOnClickListener(this);
        btnBdour.setOnClickListener(this);
        btnHesham.setOnClickListener(this);
        Trip trip = new Trip();

        Note note1 = new Note();
        note1.setNoteBody("any note1");
        note1.setStatus("not determined");
        trip.getTripNodes().add(note1);
        Note note2 = new Note();
        note2.setNoteBody("any note2");
        note2.setStatus("not determined");
        trip.getTripNodes().add(note2);

        trip.setTripName("asdasd");
        trip.setTripStartPoint("12");
        trip.setTripEndPoint("13");
        trip.setTripDate("12345");
        trip.setTripTime("mon");
        trip.setTripStatus("qwertyui");
        trip.setTripDirection("qwo");
        trip.setTripDescription("asdfg");
        trip.setTripRepetition("122334");
        trip.setTripCategory("cat1");
        trip.setUserId(1);
        boolean flag = new TripTableOperations(getApplicationContext()).insertTrip(trip);
        Toast.makeText(getApplicationContext(), flag+"", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onClick(View view) {
        if(view == btnHesham)
        {
            ///////
            Toast.makeText(this, "clicked", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(this , StartActivityDrawer.class);
            startActivity(i);

        }else if(view == btnBdour)
        {
            ///////
            Intent intent=new Intent(MainActivity.this,UpdateTrip.class);
            startActivity(intent);


        }else if(view == btnMark)
        {
            //////////


        }else if(view == btnHana)
        {
            //////////
            Intent intent=new Intent(MainActivity.this,ShowHistoryActivity.class);
            startActivity(intent);

        }
    }
  /* Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button=findViewById(R.id.hanaa);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,AuthenticationActivity.class);
                startActivity(intent);
            }
        });
    }*/
}
