package com.ititeam.tripplannermaster.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.ititeam.tripplannermaster.R;
import com.ititeam.tripplannermaster.classes.TripViewHolder;

public class ShowUpcomingTrips extends Activity {
    FloatingActionMenu materialDesignFAM;
    FloatingActionButton floatingActionButton1, floatingActionButton2, floatingActionButton3;

    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_upcoming_trips);

        materialDesignFAM = findViewById(R.id.material_design_android_floating_action_menu);
        floatingActionButton1 = findViewById(R.id.material_design_floating_action_menu_item1);
        floatingActionButton2 = findViewById(R.id.material_design_floating_action_menu_item2);
        floatingActionButton3 = findViewById(R.id.material_design_floating_action_menu_item3);
        recyclerView = findViewById(R.id.Activity_Show_upcoming_trips_recycleView);

        floatingActionButton1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //TODO something when floating action menu first item clicked
               // Intent i = new Intent(ShowUpcomingTrips.this , AddTrip.class);
               // startActivity(i);
                Toast.makeText(ShowUpcomingTrips.this, "clicked", Toast.LENGTH_SHORT).show();
            }
        });
        floatingActionButton2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //TODO something when floating action menu second item clicked
                Toast.makeText(ShowUpcomingTrips.this, "mashy", Toast.LENGTH_SHORT).show();

            }
        });
        floatingActionButton3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //TODO something when floating action menu third item clicked

            }
        });
    }

    public class TripAdapter extends RecyclerView.Adapter<TripViewHolder> {

        private LayoutInflater inflater;
        TripViewHolder viewHolder;

        @Override
        public TripViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.list_layout_upcoming_trip ,null);
            viewHolder = new TripViewHolder(view);
            return  viewHolder;
        }

        @Override
        public void onBindViewHolder(TripViewHolder holder, int position) {
            //intialize array
            //trip trip = array.get(position);
        }

        @Override
        public int getItemCount() {
            return 0;
            //return size of array  // array.size
        }


    }
}
