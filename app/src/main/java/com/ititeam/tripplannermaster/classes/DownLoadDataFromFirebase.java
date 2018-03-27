package com.ititeam.tripplannermaster.classes;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ititeam.tripplannermaster.activity.FirebaseActivity;
import com.ititeam.tripplannermaster.model.Trip;

import java.util.ArrayList;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by Hanaa on 3/27/2018.
 */

public class DownLoadDataFromFirebase extends AsyncTask<String,Integer,Object> {

    private DatabaseReference databaseReference,getDatabaseReference;
    Context context;
    public DownLoadDataFromFirebase(Context context){
        this.context=context;
    }
    @Override
    protected Object doInBackground(String... strings) {
        databaseReference= FirebaseDatabase.getInstance().getReference();
        getDatabaseReference=databaseReference.child("User"+1);
        getDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<Trip> trips=new ArrayList<>();
                trips= (ArrayList<Trip>) dataSnapshot.getValue();
                Toast.makeText(getApplicationContext(), "download"+trips.size(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(context, "Download Error", Toast.LENGTH_SHORT).show();

            }
        });

        return null;
    }
}
