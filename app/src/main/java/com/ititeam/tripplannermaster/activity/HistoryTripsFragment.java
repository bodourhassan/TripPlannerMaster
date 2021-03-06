package com.ititeam.tripplannermaster.activity;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;
import com.ititeam.tripplannermaster.DB.TripTableOperations;
import com.ititeam.tripplannermaster.R;
import com.ititeam.tripplannermaster.classes.TripViewHistoryHolder;
import com.ititeam.tripplannermaster.model.Trip;
import com.ititeam.tripplannermaster.model.User;

import java.util.ArrayList;


import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class HistoryTripsFragment extends Fragment {
    RecyclerView recyclerView;
    ArrayList<String> myTrip = new ArrayList<>();
    ArrayList<String> desc = new ArrayList<>();
    ArrayList<Trip> historyTrip;
    TripTableOperations tripTableOperations;
    TripAdapterFragmentHistory adapterHistory;



    public HistoryTripsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        historyTrip = new ArrayList<>();
        tripTableOperations = new TripTableOperations(getActivity());
        historyTrip = tripTableOperations.selectPastTripsUsingOnlyDate(User.getEmail());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_history_trips, container, false);
        recyclerView = rootView.findViewById(R.id.Activity_Show_HistoryTrips);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


        myTrip.add("hewham");
        myTrip.add("mostafa");
        myTrip.add("alyyyy");
        myTrip.add("zeinnn");
        myTrip.add("malik");
        myTrip.add("alolaaa");
        myTrip.add("kolaaa");
        myTrip.add("bolaaaa");


        desc.add("this is trip number one i am gointg to have some fun with my family");
        desc.add("this is trip number one i am gointg to have some fun with my family");
        desc.add("this is trip number one i am gointg to have some fun with my family");
        desc.add("this is trip number one i am gointg to have some fun with my family");
        desc.add("this is trip number one i am gointg to have some fun with my family");
        desc.add("this is trip number one i am gointg to have some fun with my family");
        desc.add("this is trip number one i am gointg to have some fun with my family");
        desc.add("this is trip number one i am gointg to have some fun with my family");
        // Inflate the layout for this fragment

        adapterHistory = new TripAdapterFragmentHistory();
        recyclerView.setAdapter(adapterHistory);
        return rootView;
    }


    public class TripAdapterFragmentHistory extends RecyclerSwipeAdapter<TripViewHistoryHolder> {

        private LayoutInflater inflater;
        TripViewHistoryHolder viewHolder;

        public TripAdapterFragmentHistory() {
            Log.e("ADAPTER", "TripAdapterFragment: Is here");
        }

        @Override
        public TripViewHistoryHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.swip_layout_history, null);
            viewHolder = new TripViewHistoryHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(TripViewHistoryHolder holder, final int position) {
            viewHolder.Name.setText(historyTrip.get(position).getTripName());
            viewHolder.Name.setTypeface(null, Typeface.BOLD);
            viewHolder.EmailId.setText(historyTrip.get(position).getTripDescription());
            //holder.imgViewIcon.setImageDrawable(getResources().getDrawable(R.drawable.see));
            viewHolder.started.setText(historyTrip.get(position).getTripStartPoint());
            viewHolder.started.setText(historyTrip.get(position).getTripEndPoint());



            if(historyTrip.get(position).getTripCategory().equals(TripConstant.FriendCatagory))
            {
                holder.imgViewIcon.setImageDrawable(getResources().getDrawable(R.drawable.friends1));
            }else if (historyTrip.get(position).getTripCategory().equals(TripConstant.FamilyCatagory))
            {
                holder.imgViewIcon.setImageDrawable(getResources().getDrawable(R.drawable.family));
            }else if(historyTrip.get(position).getTripCategory().equals(TripConstant.bussinessCatagory))
            {
                holder.imgViewIcon.setImageDrawable(getResources().getDrawable(R.drawable.business3));
            }else if(historyTrip.get(position).getTripCategory().equals(TripConstant.meetingCatagory))
            {
                holder.imgViewIcon.setImageDrawable(getResources().getDrawable(R.drawable.meeting));
            }else if(historyTrip.get(position).getTripCategory().equals(TripConstant.vacationCatagory))
            {
                holder.imgViewIcon.setImageDrawable(getResources().getDrawable(R.drawable.vacation));
            }else if(historyTrip.get(position).getTripCategory().equals(TripConstant.otherCatagory))
            {
                holder.imgViewIcon.setImageDrawable(getResources().getDrawable(R.drawable.other));
            }else{
                holder.imgViewIcon.setImageDrawable(getResources().getDrawable(R.drawable.other));
            }



            viewHolder.swipeLayout2.setShowMode(SwipeLayout.ShowMode.PullOut);

            //dari kiri
            viewHolder.swipeLayout2.addDrag(SwipeLayout.DragEdge.Left, viewHolder.swipeLayout2.findViewById(R.id.bottom_wrapper1));

            //dari kanan
            viewHolder.swipeLayout2.addDrag(SwipeLayout.DragEdge.Right, viewHolder.swipeLayout2.findViewById(R.id.bottom_wraper));


            viewHolder.swipeLayout2.addSwipeListener(new SwipeLayout.SwipeListener() {
                @Override
                public void onStartOpen(SwipeLayout layout) {

                }

                @Override
                public void onOpen(SwipeLayout layout) {

                }

                @Override
                public void onStartClose(SwipeLayout layout) {

                }

                @Override
                public void onClose(SwipeLayout layout) {

                }

                @Override
                public void onUpdate(SwipeLayout layout, int leftOffset, int topOffset) {

                }

                @Override
                public void onHandRelease(SwipeLayout layout, float xvel, float yvel) {

                }
            });

            viewHolder.swipeLayout2.getSurfaceView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(getActivity() , ShowTripActivity.class);
                    i.putExtra("trip_id" , String.valueOf(historyTrip.get(position).getTripId()));
                    startActivity(i);
                }
            });

            viewHolder.btnLocation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent i = new Intent(getActivity() , ShowTripActivity.class);
                    i.putExtra("trip_id" , String.valueOf(historyTrip.get(position).getTripId()));
                    startActivity(i);
                }
            });

            /*
            viewHolder.Edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Toast.makeText(view.getContext(), "Clicked on Edit  " + viewHolder.Name.getText().toString(), Toast.LENGTH_SHORT).show();
                }
            });
            */
            /*
            viewHolder.Share.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Toast.makeText(view.getContext(), "Clicked on Share " + myTrip.get(position).toString().toString(), Toast.LENGTH_SHORT).show();
                }
            });
            */
            /*
            viewHolder.Edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Toast.makeText(view.getContext(), "Clicked on Edit  " + viewHolder.Name.getText().toString(), Toast.LENGTH_SHORT).show();
                }
            });
            */
            viewHolder.Delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mItemManger.removeShownLayouts(viewHolder.swipeLayout2);

                    historyTrip.remove(position);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position, desc.size());
                    mItemManger.closeAllItems();
                    Toast.makeText(v.getContext(), "Deleted " + viewHolder.Name.getText().toString(), Toast.LENGTH_SHORT).show();
                }
            });

            mItemManger.bindView(viewHolder.itemView, position);

        }

        @Override
        public int getItemCount() {
            return historyTrip.size();
            //return size of array  // array.size
        }


        @Override
        public int getSwipeLayoutResourceId(int position) {
            return R.id.swipe;
        }
    }

}

