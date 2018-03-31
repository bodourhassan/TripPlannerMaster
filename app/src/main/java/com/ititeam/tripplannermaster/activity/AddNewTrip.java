package com.ititeam.tripplannermaster.activity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.MultiAutoCompleteTextView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.ititeam.tripplannermaster.DB.TripTableOperations;
import com.ititeam.tripplannermaster.R;
import com.ititeam.tripplannermaster.activity.alarmhandler.AlarmScheduleService;
import com.ititeam.tripplannermaster.model.User;
import com.ititeam.tripplannermaster.model.Note;
import com.ititeam.tripplannermaster.model.Trip;

import java.util.ArrayList;
import java.util.Calendar;

public class AddNewTrip extends AppCompatActivity implements ConnectionCallbacks,OnConnectionFailedListener {

    private GeoDataClient geoDataClient ;
    ArrayList<String> listItems=new ArrayList<String>();
    ArrayAdapter<String> NoteListadapter;
    AutoCompleteTextView TripNameView;
    AutoCompleteTextView mylocationStart;
    AutoCompleteTextView mylocationEnd;
    EditText DescriptipnView;
    TextView DateView;
    TextView TimeView;
    ListView MyNoteList;
    RadioButton myRadiobutton;
    MultiAutoCompleteTextView NoteItem;
    RadioGroup mytripGroup;
    // Spinner Tripcatagory;
    ArrayList<Note> myNoteList = new ArrayList<>();
    Calendar myCalender;
    int day,month,year;
    int hour,minute;
    Spinner dropdown;
    //String format;
    String Notebody;
    public  static final LatLngBounds LatLangBounds =new LatLngBounds(new LatLng(-40,-168),new LatLng(71,163));
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_trip);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mylocationStart = findViewById(R.id.AutoStart);
        mylocationEnd=findViewById(R.id.AutoEnd);
        NoteItem =findViewById(R.id.NoteId);
        TripNameView = findViewById(R.id.AutTripName);
        DescriptipnView=findViewById(R.id.EditDescription);
        mytripGroup= findViewById(R.id.GroupType);
        // Tripcatagory= findViewById(R.id.TripCatId);
        /************************** Note List Part ************************/
        MyNoteList=findViewById(R.id.NoteList);
        NoteListadapter=new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                listItems);

        MyNoteList.setOnTouchListener(new View.OnTouchListener() {
            // Setting on Touch Listener for handling the touch inside ScrollView
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // Disallow the touch request for parent scroll on touch of child view
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //  Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                Notebody = NoteItem.getText().toString();
                if(Notebody.trim().equals(""))
                {
                    Toast.makeText(getBaseContext(), " Enter Your Note ",
                            Toast.LENGTH_SHORT).show();

                }
                else {

                    //  addItems(Notebody);
                    Log.e("Add", Notebody);
                    listItems.add(Notebody);
                    NoteListadapter.notifyDataSetChanged();
                    NoteItem.setText("".toString());
                    // NoteListadapter.setNotifyOnChange(true);
                    // MyNoteList.notifyAll();

                }
            }
        });

        MyNoteList.setAdapter(NoteListadapter);


        geoDataClient = Places.getGeoDataClient(this, null);
        MapPlacesAdapter mapPlacesAdapter=new MapPlacesAdapter(this , geoDataClient , LatLangBounds,null);
        mylocationStart.setAdapter(mapPlacesAdapter);
        mylocationEnd.setAdapter(mapPlacesAdapter);
        DateView =findViewById(R.id.Date);
        TimeView =findViewById(R.id.Time);
        myCalender= Calendar.getInstance();
        day=myCalender.get(Calendar.DAY_OF_MONTH);
        month=myCalender.get(Calendar.MONTH);
        year =myCalender.get(Calendar.YEAR);
        hour=myCalender.get(Calendar.HOUR_OF_DAY);
        minute=myCalender.get(Calendar.MINUTE);
        month=month+1;

       // DateView.setText(day+"-"+month+"-"+year);
        String Smonth=month+"";
        String sDay =day+"";
        if (month < 10) {
            Smonth = "0" + month;
        }
        if (day < 10) {
            sDay = "0" + day;

        }
        DateView.setText(year+"-"+Smonth+"-"+sDay);

        DateView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(AddNewTrip.this, R.style.TimePickerTheme, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month=month+1;

                       // DateView.setText(dayOfMonth+"-"+month+"-"+year);
                        String Smonthin=month+"";
                        String sDayin =dayOfMonth+"";
                        if (month < 10) {
                            Smonthin = "0" + month;
                        }
                        if (dayOfMonth < 10) {
                            sDayin = "0" + dayOfMonth;

                        }
                        DateView.setText(year+"-"+Smonthin+"-"+sDayin);

                    }
                },year,month,day);
                datePickerDialog.show();
            }
        });

        //  hourFormat(hour);
        String myhour = "" + hour;
        String myminute = "" + minute;
        if (hour < 10) {
            myhour = "0" + hour;
        }
        if (minute < 10) {
            myminute = "0" + minute;

        }
        TimeView.setText(myhour + " : " + myminute);
        TimeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(AddNewTrip.this, R.style.TimePickerTheme, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        //hourFormat(hour);
                        String myhourin = "" + hourOfDay;
                        String myminutein = "" + minute;
                        if (hourOfDay < 10) {
                            myhourin = "0" + hourOfDay;
                        }
                        if (minute < 10) {
                            myminutein = "0" + minute;

                        }
                        TimeView.setText(myhourin + " : " + myminutein);
                    }
                }, hour, minute, true);
                timePickerDialog.show();
            }
        });
        /******************************************Trip Catagory Part ***********************/
        //get the spinner from the xml.
        dropdown = findViewById(R.id.TripCatId);
        //create a list of items for the spinner.
        String[] items = new String[]{TripConstant.FriendCatagory,TripConstant.FamilyCatagory, TripConstant.bussinessCatagory,TripConstant.meetingCatagory,TripConstant.vacationCatagory,TripConstant.otherCatagory};
        //create an adapter to describe how the items are displayed, adapters are used in several places in android
        // There are multiple variations of this, but this is the basic variant.
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        //set the spinners adapter to the previously created one.
        dropdown.setAdapter(adapter);
        dropdown.setSelection(adapter.getPosition(TripConstant.otherCatagory));


    }

    public void addItems(String newNoteList) {
        listItems.add(newNoteList);
        NoteListadapter.notifyDataSetChanged();
    }

//    public void hourFormat(int hour)
//    {
//        if(hour==0)
//        {
//            hour+=12;
//            format="AM";
//        }
//        else if(hour==12)
//        {
//
//            format="PM";
//        }
//        else if(hour>12)
//        {
//           hour-=12;
//            format="PM";
//        }
//        else {
//
//            format="AM";
//        }
//    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    public void onClicksave(View view) {
        if (TripNameView.getText().toString().trim().equals(""))
        {
            Toast.makeText(getBaseContext(), " Enter Trip Name ",
                    Toast.LENGTH_SHORT).show();
        }
        else if(mylocationStart.getText().toString().trim().equals(""))
        {
            Toast.makeText(getBaseContext(), " Enter Your Start Location ",
                    Toast.LENGTH_SHORT).show();

        }
        else if(mylocationEnd.getText().toString().trim().equals(""))
        {
            Toast.makeText(getBaseContext(), " Enter Your End Location ",
                    Toast.LENGTH_SHORT).show();

        }
        else if(DescriptipnView.getText().toString().trim().equals(""))
        {
            Toast.makeText(getBaseContext(), " Enter Your Description  ",
                    Toast.LENGTH_SHORT).show();

        }
//          else if(getLatLongFromGivenAddress(mylocationStart.getText().toString())==null){
//
//              Toast.makeText(getBaseContext(), " Enter a valid Start point ",
//                      Toast.LENGTH_SHORT).show();
//          }
//          else if(getLatLongFromGivenAddress(mylocationEnd.getText().toString())==null){
//
//              Toast.makeText(getBaseContext(), " Enter a valid End point ",
//                      Toast.LENGTH_SHORT).show();
//          }
        else {
            String NameofTrip = TripNameView.getText().toString();
            String StartLoc = mylocationStart.getText().toString();
            String Endloc = mylocationEnd.getText().toString();
            String Desc = DescriptipnView.getText().toString();
            String Date = DateView.getText().toString();
            String Time = TimeView.getText().toString();
            // get selected radio button from radioGroup
            int selectedId = mytripGroup.getCheckedRadioButtonId();
            // find the radiobutton by returned id
            myRadiobutton = findViewById(selectedId);
            String TripDirection = myRadiobutton.getText().toString();
            myNoteList.clear();
            for (int i = 0; i < listItems.size(); i++) {
                Log.e("When Add", listItems.get(i));
                Note note = new Note(listItems.get(i), TripConstant.NoteLater);
                myNoteList.add(note);
            }
            String TripCatagory = dropdown.getSelectedItem().toString();
            Trip NewTrip = new Trip(NameofTrip, StartLoc, Endloc, Date, Time, TripConstant.UpcomingStatus, TripDirection, Desc, "none", TripCatagory, User.getEmail(), myNoteList);
            TripTableOperations myTripTable = new TripTableOperations(this);
            boolean test = myTripTable.insertTrip(NewTrip);
            if(test){
                Trip lastTrip=myTripTable.selectAllTripsForGettingLastId();
                Intent intent=new Intent(AddNewTrip.this, AlarmScheduleService.class);
                intent.putExtra("trip_id",lastTrip.getTripId());
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startService(intent);

            }
            for (int i = 0; i < NewTrip.getTripNotes().size(); i++) {
                Log.e("Trip before", NewTrip.getTripNotes().get(i).getNoteBody());
            }
            ArrayList<Trip> all = myTripTable.selectAllTrips();
            Toast.makeText(getBaseContext(), all.size() + "," + test,
                    Toast.LENGTH_SHORT).show();
            Log.e("My Data:", "Name : " + NameofTrip);
            Log.e("My Data:", "start : " + StartLoc);
            Log.e("My Data:", "end : " + Endloc);
            Log.e("My Data:", "desc : " + Desc);
            Log.e("My Data:", "date : " + Date);
            Log.e("My Data:", "time : " + Time);
            Log.e("My Data:", "direction : " + TripDirection);
            Log.e("My Data:", "cate : " + TripCatagory);

            Trip my = myTripTable.selectSingleTrips(1 + "");
            Log.e("dataaaaaaaaaaaaa:", "Name : " + my.getTripName());
            Log.e("dataaaaaaaaaaaaaa:", "start : " + my.getTripStartPoint());
            Log.e("dataaaaaaaaaaaaaa:", "end : " + my.getTripEndPoint());
            Log.e("dataaaaaaaaaaaaaa:", "desc : " + my.getTripDescription());
            Log.e("dataaaaaaaaaaaaaa:", "date : " + my.getTripDate());
            Log.e("dataaaaaaaaaaaaaa:", "time : " + my.getTripTime());
            Log.e("dataaaaaaaaaaaaaa:", "direction : " + my.getTripDirection());
            Log.e("dataaaaaaaaaaaaaa:", "cate : " + my.getTripCategory());
            for (int i = 0; i < my.getTripNotes().size(); i++) {
                Log.e("Trip after", my.getTripNotes().get(i).getNoteBody());
            }
//            Intent intent = new Intent(AddNewTrip.this, StartActivityDrawer.class);
//            startActivity(intent);

            finish();
        }

    }

    public void cancleClick(View view) {
//        Intent intent = new Intent(AddNewTrip.this, StartActivityDrawer.class);
//        startActivity(intent);
        finish();
    }
  /*  public class TripAdapterFragment extends RecyclerSwipeAdapter<ViewHolder> {

        private LayoutInflater inflater;
        ViewHolder viewHolder;

        public TripAdapterFragment() {
            Log.e("ADAPTER", "TripAdapterFragment: Is here");
        }
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            inflater = (LayoutInflater) AddNewTrip.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.item_layout, null);
            viewHolder = new ViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {
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
/*

            viewHolder.getheader().setText(listItems.get(position));


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
                    Toast.makeText(getActivity(), "position is " + upcommingTrips.get(position).toString(), Toast.LENGTH_SHORT).show();
                }
            });

            viewHolder.btnLocation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(v.getContext(), "Clicked on Information " + upcommingTrips.get(position).toString(), Toast.LENGTH_SHORT).show();
                }
            });

            viewHolder.Share.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Toast.makeText(view.getContext(), "Clicked on Share " + upcommingTrips.get(position).toString().toString(), Toast.LENGTH_SHORT).show();
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
                    AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                    alert.setTitle("Delete Trip " + upcommingTrips.get(position).getTripName());
                    alert.setMessage("Are you sure you want to delete " + upcommingTrips.get(position).getTripName() + " ?");

                    alert.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {


                        public void onClick(DialogInterface dialog, int which) {
                            // continue with delete
                            tripTableOperations.deleteTrip(String.valueOf(upcommingTrips.get(position).getTripId()));
                            mItemManger.removeShownLayouts(viewHolder.swipeLayout);
                            upcommingTrips.remove(position);
                            notifyItemRemoved(position);
                            notifyItemRangeChanged(position, upcommingTrips.size());
                            mItemManger.closeAllItems();
                            Toast.makeText(getActivity(), "here in delete", Toast.LENGTH_SHORT).show();
                        }
                    });

                    alert.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // close dialog
                            dialog.cancel();
                        }
                    });
                    alert.show();



                    //Toast.makeText(v.getContext(), "Deleted " + upcommingTrips.get(position).getTripId(), Toast.LENGTH_SHORT).show();
                    //Toast.makeText(getActivity(), "array size"+ upcommingTrips.size(), Toast.LENGTH_SHORT).show();
                }
            });

            mItemManger.bindView(viewHolder.itemView, position);


        }

        @Override
        public int getItemCount() {
            return upcommingTrips.size();
            //return size of array  // array.size
        }


        @Override
        public int getSwipeLayoutResourceId(int position) {
            return R.id.swipe;
        }
    }*/
}
