package com.ititeam.tripplannermaster.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.ititeam.tripplannermaster.R;
import com.ititeam.tripplannermaster.classes.TripViewHolder;
import com.ititeam.tripplannermaster.classes.User;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;


import java.util.ArrayList;

public class ShowUpcomingTrips extends AppCompatActivity implements FragmentDrawer.FragmentDrawerListener {
    FloatingActionMenu materialDesignFAM;
    FloatingActionButton floatingActionButton1, floatingActionButton2, floatingActionButton3;
    RecyclerView recyclerView;
    TripAdapter myAdapter;
    private Toolbar mToolbar;
    FragmentDrawer drawerFragment;


    ArrayList<String> myTrip = new ArrayList<>();
    ArrayList<String> desc = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_upcoming_trips);

        materialDesignFAM = findViewById(R.id.material_design_android_floating_action_menu);
        floatingActionButton1 = findViewById(R.id.material_design_floating_action_menu_item1);
        floatingActionButton2 = findViewById(R.id.material_design_floating_action_menu_item2);
        floatingActionButton3 = findViewById(R.id.material_design_floating_action_menu_item3);
        recyclerView = findViewById(R.id.Activity_Show_upcoming_trips_recycleView);
        mToolbar = findViewById(R.id.toolbar);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        /*
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
        */

//        setSupportActionBar(mToolbar);
        //       getSupportActionBar().setDisplayShowHomeEnabled(true);

        //   setSupportActionBar(mToolbar);
        //  getSupportActionBar().setDisplayShowHomeEnabled(true);

        drawerFragment = (FragmentDrawer)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), mToolbar);
        drawerFragment.setDrawerListener(this);

        // display the first navigation drawer view on app launch
        displayView(0);


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


        // myAdapter = new TripAdapter();
        // recyclerView.setAdapter(myAdapter);


    }

    @Override
    public void onDrawerItemSelected(View view, int position) {
        displayView(position);
    }


    private void displayView(int position) {
        Fragment fragment = null;
        String title = getString(R.string.app_name);
        switch (position) {
            case 0:
                fragment = new HomeFragment();
                title = getString(R.string.title_home);
                break;
            case 1:
                //  fragment = new FriendsFragment();
                // title = getString(R.string.title_friends);
                break;
            case 2:
                //fragment = new MessagesFragment();
                //title = getString(R.string.title_messages);
                break;
            default:
                break;
        }

        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_body, fragment);
            fragmentTransaction.commit();

            // set the toolbar title
            getSupportActionBar().setTitle(title);
        }
    }


    /*
    ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT | ItemTouchHelper.DOWN | ItemTouchHelper.UP) {

        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            Toast.makeText(ShowUpcomingTrips.this, "on Move", Toast.LENGTH_SHORT).show();
            return false;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
            Toast.makeText(ShowUpcomingTrips.this, "on Swiped ", Toast.LENGTH_SHORT).show();
            //Remove swiped item from list and notify the RecyclerView
            final int position = viewHolder.getAdapterPosition();
            myAdapter.notifyItemRemoved(position);
        }
    };
    */
    public class TripAdapter extends RecyclerSwipeAdapter<TripViewHolder> {

        private LayoutInflater inflater;
        TripViewHolder viewHolder;

        @Override
        public TripViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.swip_layout, null);
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
                    Toast.makeText(ShowUpcomingTrips.this, "position is " + myTrip.get(position).toString(), Toast.LENGTH_SHORT).show();
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
