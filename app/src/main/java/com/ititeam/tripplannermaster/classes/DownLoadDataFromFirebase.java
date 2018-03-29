package com.ititeam.tripplannermaster.classes;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.ititeam.tripplannermaster.DB.TripTableOperations;
import com.ititeam.tripplannermaster.activity.StartActivityDrawer;
import com.ititeam.tripplannermaster.activity.login.OnTaskComplete;
import com.ititeam.tripplannermaster.model.Trip;
import com.ititeam.tripplannermaster.model.User;

import java.util.ArrayList;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by Hanaa on 3/27/2018.
 */

public class DownLoadDataFromFirebase extends AsyncTask<String, Integer, Object> {

    private DatabaseReference databaseReference, getDatabaseReference;
    Context context;
    OnTaskComplete onTaskComplete=null;

    public DownLoadDataFromFirebase(Context context) {
        this.context=context;
    }

    @Override
    protected void onPostExecute(Object o){
    }

    @Override
    protected Object doInBackground(String... strings) {
        databaseReference = FirebaseDatabase.getInstance().getReference();
        String path= User.getEmail().replace(".","_");
         path=path.replace("#","_");
        path=path.replace("$","_");
        path=path.replace("[","_");
        path=path.replace("]","_");
        User.setFirebasePath(path);
        if ((getDatabaseReference = databaseReference.child(User.getFirebasePath())) != null) {
            getDatabaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    ArrayList<Trip> trips = null;
                    GenericTypeIndicator<ArrayList<Trip>> genericTypeIndicator = new GenericTypeIndicator<ArrayList<Trip>>() {
                    };
                    trips = dataSnapshot.getValue(genericTypeIndicator);
                    if (trips != null) {
                        Toast.makeText(getApplicationContext(), "download" + trips.size(), Toast.LENGTH_SHORT).show();
                        new TripTableOperations(getApplicationContext()).getTripFromFirebase(trips);

                        Intent intent = new Intent(context, StartActivityDrawer.class);
                        intent.putExtra("login_user_email", User.getEmail());
                        context.startActivity(intent);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Toast.makeText(context, "Download Error", Toast.LENGTH_SHORT).show();

                }

            });
        }
            return null;
        }
    }
