package com.ititeam.tripplannermaster.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.ititeam.tripplannermaster.DB.NoteTableOperations;
import com.ititeam.tripplannermaster.DB.TripTableOperations;
import com.ititeam.tripplannermaster.R;
import com.ititeam.tripplannermaster.*;
import com.ititeam.tripplannermaster.model.Note;
import com.ititeam.tripplannermaster.model.Trip;

import java.util.ArrayList;

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
//        Trip trip = new Trip();
//
//        Note note1 = new Note();
//        note1.setNoteBody("any note1");
//        note1.setStatus("not determined");
//        trip.getTripNotes().add(note1);
//        Note note2 = new Note();
//        note2.setNoteBody("any note2");
//        note2.setStatus("not determined");
//        trip.getTripNotes().add(note2);
//
//        trip.setTripName("0");
//        trip.setTripStartPoint("cairo");
//        trip.setTripEndPoint("alexandria");
//        trip.setTripDate("12345");
//        trip.setTripTime("mon");
//        trip.setTripStatus("Done");
//        trip.setTripDirection("qwo");
//        trip.setTripDescription("asdfg");
//        trip.setTripRepetition("122334");
//        trip.setTripCategory("cat1");
//        trip.setUserId(1);
//        boolean flag = new TripTableOperations(getApplicationContext()).insertTrip(trip);
//        Toast.makeText(getApplicationContext(), flag+"", Toast.LENGTH_LONG).show();
//
//        Trip trip1 = new Trip();
//        trip1.setTripName("1");
//        trip1.setTripStartPoint("cairo");
//        trip1.setTripEndPoint("alexandria");
//        trip1.setTripDate("2018-01-14");
//        trip1.setTripTime("mon");
//        trip1.setTripStatus("Done");
//        trip1.setTripDirection("qwo");
//        trip1.setTripDescription("asdfg");
//        trip1.setTripRepetition("122334");
//        trip1.setTripCategory("cat1");
//        trip1.setUserId(1);
//        boolean flag1 = new TripTableOperations(getApplicationContext()).insertTrip(trip1);
//        Toast.makeText(getApplicationContext(), flag+"", Toast.LENGTH_LONG).show();
//
//
//        Trip trip2 = new Trip();
//        trip2.setTripName("2");
//        trip2.setTripStartPoint("cairo");
//        trip2.setTripEndPoint("alexandria");
//        trip2.setTripDate("2018-01-23");
//        trip2.setTripTime("mon");
//        trip2.setTripStatus("qwertyui");
//        trip2.setTripDirection("qwo");
//        trip2.setTripDescription("asdfg");
//        trip2.setTripRepetition("122334");
//        trip2.setTripCategory("cat1");
//        trip2.setUserId(1);
//        boolean flag2 = new TripTableOperations(getApplicationContext()).insertTrip(trip2);
//        Toast.makeText(getApplicationContext(), flag+"", Toast.LENGTH_LONG).show();
//
//        Trip trip3 = new Trip();
//        trip3.setTripName("3");
//        trip3.setTripStartPoint("cairo");
//        trip3.setTripEndPoint("alexandria");
//        trip3.setTripDate("2018-02-01");
//        trip3.setTripTime("mon");
//        trip3.setTripStatus("qwertyui");
//        trip3.setTripDirection("qwo");
//        trip3.setTripDescription("asdfg");
//        trip3.setTripRepetition("122334");
//        trip3.setTripCategory("cat1");
//        trip3.setUserId(1);
//        boolean flag3 = new TripTableOperations(getApplicationContext()).insertTrip(trip3);
//        Toast.makeText(getApplicationContext(), flag+"", Toast.LENGTH_LONG).show();
//
//        Trip trip4 = new Trip();
//        trip4.setTripName("4");
//        trip4.setTripStartPoint("cairo");
//        trip4.setTripEndPoint("alexandria");
//        trip4.setTripDate("2018-02-23");
//        trip4.setTripTime("mon");
//        trip4.setTripStatus("qwertyui");
//        trip4.setTripDirection("qwo");
//        trip4.setTripDescription("asdfg");
//        trip4.setTripRepetition("122334");
//        trip4.setTripCategory("cat1");
//        trip4.setUserId(1);
//        boolean flag4 = new TripTableOperations(getApplicationContext()).insertTrip(trip4);
//        Toast.makeText(getApplicationContext(), flag+"", Toast.LENGTH_LONG).show();
//
//        Trip trip5 = new Trip();
//        trip5.setTripName("5");
//        trip5.setTripStartPoint("cairo");
//        trip5.setTripEndPoint("alexandria");
//        trip5.setTripDate("2018-04-12");
//        trip5.setTripTime("mon");
//        trip5.setTripStatus("qwertyui");
//        trip5.setTripDirection("qwo");
//        trip5.setTripDescription("asdfg");
//        trip5.setTripRepetition("122334");
//        trip5.setTripCategory("cat1");
//        trip5.setUserId(1);
//        boolean flag5 = new TripTableOperations(getApplicationContext()).insertTrip(trip5);
//        Toast.makeText(getApplicationContext(), flag+"", Toast.LENGTH_LONG).show();
//
//        Trip trip6 = new Trip();
//        trip6.setTripName("6");
//        trip6.setTripStartPoint("cairo");
//        trip6.setTripEndPoint("alexandria");
//        trip6.setTripDate("2018-05-10");
//        trip6.setTripTime("mon");
//        trip6.setTripStatus("qwertyui");
//        trip6.setTripDirection("qwo");
//        trip6.setTripDescription("asdfg");
//        trip6.setTripRepetition("122334");
//        trip6.setTripCategory("cat1");
//        trip6.setUserId(1);
//        boolean flag6 = new TripTableOperations(getApplicationContext()).insertTrip(trip6);
//        Toast.makeText(getApplicationContext(), flag+"", Toast.LENGTH_LONG).show();
//
//        ArrayList<Trip> pastTrips = new TripTableOperations(getApplicationContext()).selectPastTripsUsingDateAndStatus();
//
//        for (Trip pastTrip : pastTrips) {
//            Toast.makeText(getApplicationContext(), pastTrip.getTripName(), Toast.LENGTH_SHORT).show();
//        }

       /* Trip trip = new TripTableOperations(getApplicationContext()).selectSingleTrips(1 + "");

        for (Note note : trip.getTripNotes()) {
            Toast.makeText(getApplicationContext(), note.getNoteBody(), Toast.LENGTH_SHORT).show();
        }

        Note note2 = trip.getTripNotes().get(1);
        note2.setNoteBody("note2");
        note2.setStatus("not determined");
        new NoteTableOperations(getApplicationContext()).updateNote(note2);*/
    }

    @Override
    public void onClick(View view) {
        if(view == btnHesham)
        {
            ///////
            Toast.makeText(this, "clicked", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(this, StartActivityDrawer.class);
            startActivity(i);

        }else if(view == btnBdour)
        {
            ///////
            Intent intent = new Intent(MainActivity.this, UpdateTrip.class);
            startActivity(intent);


        }else if(view == btnMark)
        {
            //////////


        }else if(view == btnHana)
        {
            //////////


           /* Trip trip5 = new Trip();
            trip5.setTripName("5");
            trip5.setTripStartPoint("cairo");
            trip5.setTripEndPoint("Aswan");
            trip5.setTripDate("2018-05-11");
            trip5.setTripTime("mon");
            trip5.setTripStatus(TripConstant.DoneStatus);
            trip5.setTripDirection("qwo");
            trip5.setTripDescription("asdfg");
            trip5.setTripRepetition("122334");
            trip5.setTripCategory("cat1");
            trip5.setUserId(2);
            Note note=new Note();
            note.setNoteBody("kkk");
            trip5.getTripNotes().add(note);
            new TripTableOperations(MainActivity.this).insertTrip(trip5);*/
            Intent intent=new Intent(MainActivity.this,FirebaseActivity
                    .class);

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
