package com.ititeam.tripplannermaster.classes;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ititeam.tripplannermaster.DB.TripTableOperations;
import com.ititeam.tripplannermaster.activity.FirebaseActivity;
import com.ititeam.tripplannermaster.model.Trip;

import java.util.ArrayList;

/**
 * Created by Hanaa on 3/27/2018.
 */

public class UploadDataToFirebase extends AsyncTask<String, Integer, Object> {

    private DatabaseReference databaseReference, getDatabaseReference;
    Context context;

    public UploadDataToFirebase(Context context) {
        this.context = context;
    }

    @Override
    protected Object doInBackground(String... strings) {
        databaseReference = FirebaseDatabase.getInstance().getReference();
        TripTableOperations tripTableOperations = new TripTableOperations(context);
        ArrayList<Trip> trips = tripTableOperations.selectAllTrips();
        if (trips.size() > 0) {
            databaseReference.child("User" + trips.get(0).getUserId()).setValue(trips);
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Toast.makeText(context, "done" + trips.size(), Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
            //Toast.makeText(context, "done"+trips.size(), Toast.LENGTH_SHORT).show();

        }

        return null;
    }

    protected void onPostExecute(Object result) {
        Toast.makeText(context, "done", Toast.LENGTH_SHORT).show();

    }
}
