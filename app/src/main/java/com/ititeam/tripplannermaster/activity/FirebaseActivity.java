package com.ititeam.tripplannermaster.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ititeam.tripplannermaster.DB.TripTableOperations;
import com.ititeam.tripplannermaster.R;
import com.ititeam.tripplannermaster.classes.DownLoadDataFromFirebase;
import com.ititeam.tripplannermaster.classes.UploadDataToFirebase;
import com.ititeam.tripplannermaster.model.Trip;
import com.ititeam.tripplannermaster.model.User;

import java.util.ArrayList;

public class FirebaseActivity extends AppCompatActivity {

    private DatabaseReference databaseReference, getDatabaseReference;
    Button uploadBtn, downlaodBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firebase);

        // databaseReference= FirebaseDatabase.getInstance().getReference().child("user1");
        uploadBtn = findViewById(R.id.uploadBtn);
        downlaodBtn = findViewById(R.id.downloadBtn);
        uploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UploadDataToFirebase uploadDataToFirebase = new UploadDataToFirebase(FirebaseActivity.this);
                uploadDataToFirebase.execute();
            }
        });
        downlaodBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
/*
                DownLoadDataFromFirebase downLoadDataFromFirebase = new DownLoadDataFromFirebase(FirebaseActivity.this);
                downLoadDataFromFirebase.execute();*/
            }
        });
    }

}
