package com.ititeam.tripplannermaster.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.ititeam.tripplannermaster.R;

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
    }

    @Override
    public void onClick(View view) {
        if(view == btnHesham)
        {
            ///////
            Toast.makeText(this, "clicked", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(this , ShowUpcomingTrips.class);
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
            Intent intent=new Intent(MainActivity.this,ShowTripActivity.class);
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
