package com.ititeam.tripplannermaster.activity;


import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.ititeam.tripplannermaster.R;
import com.ititeam.tripplannermaster.classes.TripViewHolder;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.Toast;

import java.util.ArrayList;


public class HomeFragment extends Fragment{
    FloatingActionMenu materialDesignFAM;
    FloatingActionButton floatingActionButton1, floatingActionButton2, floatingActionButton3;
    RecyclerView recyclerView;
    TripAdapterFragment myAdapter;
    private Toolbar mToolbar;
    FragmentDrawer drawerFragment;


    ArrayList<String> myTrip = new ArrayList<>();
    ArrayList<String> desc = new ArrayList<>();

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        materialDesignFAM = rootView.findViewById(R.id.material_design_android_floating_action_menu);
        floatingActionButton1 = rootView.findViewById(R.id.material_design_floating_action_menu_item1);
        floatingActionButton2 = rootView.findViewById(R.id.material_design_floating_action_menu_item2);
        floatingActionButton3 = rootView.findViewById(R.id.material_design_floating_action_menu_item3);
        recyclerView = rootView.findViewById(R.id.Activity_Show_upcoming_trips_recycleView);
        mToolbar = rootView.findViewById(R.id.toolbar);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
////////////////////////////////////////////////////////////////////////////////////////////
      //  drawerFragment = (FragmentDrawer) getActivity().getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
      //  drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) rootView.findViewById(R.id.drawer_layout), mToolbar);
      //  drawerFragment.setDrawerListener((FragmentDrawer.FragmentDrawerListener) getActivity());





        floatingActionButton1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //TODO something when floating action menu first item clicked
                // Intent i = new Intent(ShowUpcomingTrips.this , AddTrip.class);
                // startActivity(i);
                Toast.makeText(getActivity(), "clicked", Toast.LENGTH_SHORT).show();
            }
        });
        floatingActionButton2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //TODO something when floating action menu second item clicked
                Toast.makeText(getActivity(), "mashy", Toast.LENGTH_SHORT).show();

            }
        });
        floatingActionButton3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //TODO something when floating action menu third item clicked

            }
        });

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



        myAdapter = new TripAdapterFragment();
        recyclerView.setAdapter(myAdapter);
        //displayView(0);
        // Inflate the layout for this fragment
        return rootView;
    }



    public class TripAdapterFragment extends RecyclerSwipeAdapter<TripViewHolder> {

        private LayoutInflater inflater;
        TripViewHolder viewHolder;

        public TripAdapterFragment(){
            Log.e("ADAPTER", "TripAdapterFragment: Is here" );
        }
        @Override
        public TripViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.swip_layout ,null);
            viewHolder = new TripViewHolder(view);
            return  viewHolder;
        }

        @Override
        public void onBindViewHolder(TripViewHolder holder, final int position) {
            //intialize array
            //trip trip = array.get(position);

            ///////old carddddd
            /*
            holder.tvTitle.setText(myTrip.get(position));
            holder.tvTitle.setTypeface(null, Typeface.BOLD);
            holder.imgViewIcon.setImageDrawable(getResources().getDrawable(R.drawable.see));
            holder.tvDescription.setText(desc.get(position));

            holder.btnStart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(ShowUpcomingTrips.this, "position is "+myTrip.get(position).toString(), Toast.LENGTH_SHORT).show();
                }
            });
*/




            viewHolder.Name.setText(myTrip.get(position));
            viewHolder.Name.setTypeface(null, Typeface.BOLD);
            viewHolder.EmailId.setText(desc.get(position));
            holder.imgViewIcon.setImageDrawable(getResources().getDrawable(R.drawable.see));



            viewHolder.swipeLayout.setShowMode(SwipeLayout.ShowMode.PullOut);

            //dari kiri
            viewHolder.swipeLayout.addDrag(SwipeLayout.DragEdge.Left, viewHolder.swipeLayout.findViewById(R.id.bottom_wrapper1));

            //dari kanan
            viewHolder.swipeLayout.addDrag(SwipeLayout.DragEdge.Right, viewHolder.swipeLayout.findViewById(R.id.bottom_wraper));



            viewHolder.swipeLayout.addSwipeListener(new SwipeLayout.SwipeListener() {
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

            viewHolder.swipeLayout.getSurfaceView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getActivity(), "position is "+myTrip.get(position).toString(), Toast.LENGTH_SHORT).show();
                }
            });

            viewHolder.btnLocation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(v.getContext(), "Clicked on Information " + myTrip.get(position).toString(), Toast.LENGTH_SHORT).show();
                }
            });

            viewHolder.Share.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Toast.makeText(view.getContext(), "Clicked on Share " + myTrip.get(position).toString().toString(), Toast.LENGTH_SHORT).show();
                }
            });

            viewHolder.Edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Toast.makeText(view.getContext(), "Clicked on Edit  " + viewHolder.Name.getText().toString(), Toast.LENGTH_SHORT).show();
                }
            });

            viewHolder.Delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mItemManger.removeShownLayouts(viewHolder.swipeLayout);
                    desc.remove(position);
                    myTrip.remove(position);
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
            return myTrip.size();
            //return size of array  // array.size
        }


        @Override
        public int getSwipeLayoutResourceId(int position) {
            return R.id.swipe;
        }
    }



}