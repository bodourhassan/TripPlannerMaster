package com.ititeam.tripplannermaster.classes;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.ititeam.tripplannermaster.DB.TripTableOperations;
import com.ititeam.tripplannermaster.model.Trip;
import com.ititeam.tripplannermaster.model.User;

import java.util.ArrayList;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by Hanaa on 3/27/2018.
 */

public class UploadDataToFirebase extends AsyncTask<String, Integer, Object> {

    private DatabaseReference databaseReference, getDatabaseReference;
    Context context;
    ArrayList<Trip> trips=new ArrayList<>();

    public UploadDataToFirebase(Context context) {
        this.context = context;
    }
    @Override
    protected Object doInBackground(String... strings) {
        databaseReference = FirebaseDatabase.getInstance().getReference();
        TripTableOperations tripTableOperations = new TripTableOperations(context);
        String user_id = User.getEmail();
        trips = tripTableOperations.selectTripsUsingUserId(user_id);
        String path= User.getEmail().replace(".","_");
        path=path.replace("#","_");
        path=path.replace("$","_");
        path=path.replace("[","_");
        path=path.replace("]","_");
        User.setFirebasePath(path);
        getDatabaseReference = databaseReference.child(User.getFirebasePath());
        getDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<Trip> firebaseTrips = null;
                GenericTypeIndicator<ArrayList<Trip>> genericTypeIndicator = new GenericTypeIndicator<ArrayList<Trip>>() {
                };
                firebaseTrips = dataSnapshot.getValue(genericTypeIndicator);
                if (firebaseTrips != null) {
                    for (Trip trip : firebaseTrips) {
                        Trip trip1 = new TripTableOperations(context).selectSingleTrips(trip.getTripId() + "");
                        if (trip1 == null) {
                            trips.add(trip1);
                        }

                    }
                }
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(context, "Download Error", Toast.LENGTH_SHORT).show();

            }
        });
        if (trips != null) {
            if (trips.size() > 0) {
                databaseReference = FirebaseDatabase.getInstance().getReference();
                databaseReference.child(User.getFirebasePath()).setValue(trips);
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
        }
        return null;
    }
    protected void onPostExecute(Object result) {
        //Toast.makeText(context, "done", Toast.LENGTH_SHORT).show();

    }
}
