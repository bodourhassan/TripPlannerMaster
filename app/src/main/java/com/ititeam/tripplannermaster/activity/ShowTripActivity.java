package com.ititeam.tripplannermaster.activity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.github.clans.fab.Label;
import com.ititeam.tripplannermaster.DB.TripTableOperations;
import com.ititeam.tripplannermaster.R;
import com.ititeam.tripplannermaster.classes.TripViewHolder;
import com.ititeam.tripplannermaster.model.Trip;

public class ShowTripActivity extends AppCompatActivity {

    Label tripName,tripStartPoint;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_trip);
        tripName=findViewById(R.id.show_trip_name);
        TripTableOperations tripTableOperations=new TripTableOperations(getBaseContext());
        int trip_id=1;
        Trip trip=tripTableOperations.selectSingleTrips(trip_id+"");

        tripName.setText(trip.getTripName());

    }

}
