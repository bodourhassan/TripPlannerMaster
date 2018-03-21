package com.ititeam.tripplannermaster.activity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ititeam.tripplannermaster.R;
import com.ititeam.tripplannermaster.classes.TripViewHolder;

public class ShowTripActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_trip);
        TripAdapter adapter=new TripAdapter();
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
            return 1;
            //return size of array  // array.size
        }


    }
}
