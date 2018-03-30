package com.ititeam.tripplannermaster.activity;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
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

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.ititeam.tripplannermaster.DB.NoteTableOperations;
import com.ititeam.tripplannermaster.DB.TripTableOperations;
import com.ititeam.tripplannermaster.R;
import com.ititeam.tripplannermaster.activity.alarmhandler.AlarmActivity;
import com.ititeam.tripplannermaster.activity.alarmhandler.AlarmScheduleService;
import com.ititeam.tripplannermaster.classes.UploadDataToFirebase;
import com.ititeam.tripplannermaster.model.Note;
import com.ititeam.tripplannermaster.model.Trip;

import java.util.ArrayList;
import java.util.Calendar;

public class UpdateTrip extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks,GoogleApiClient.OnConnectionFailedListener {
    private GoogleApiClient googleApiClient;
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
    Spinner dropdown;
    ArrayList<Note> NmyNoteList =new ArrayList<>();
    Calendar myCalender;
    int day,month,year;
    int hour,minute;
    //String format;
    int userid=1;
    Trip UpdateTrip;
    TripTableOperations tripTableOperations;
    NoteTableOperations noteTableOperations;

    //Pending intent instance
    private PendingIntent pendingIntent;

    //Alarm Request Code
    private static final int ALARM_REQUEST_CODE = 133;

    public  static final LatLngBounds LatLangBounds =new LatLngBounds(new LatLng(-40,-168),new LatLng(71,163));
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_trip);
        Toolbar toolbar = findViewById(R.id.Utoolbar);
        setSupportActionBar(toolbar);
        mylocationStart=findViewById(R.id.UAutoStart);
        mylocationEnd=findViewById(R.id.UAutoEnd);
        NoteItem =findViewById(R.id.UNoteId);
        TripNameView = findViewById(R.id.UAutTripName);
        DescriptipnView=findViewById(R.id.UEditDescription);
        mytripGroup= findViewById(R.id.UGroupType);
        // Tripcatagory= findViewById(R.id.UTripCatId);
        DateView =findViewById(R.id.UDate);
        TimeView =findViewById(R.id.UTime);
        MyNoteList=findViewById(R.id.UNoteList);
        dropdown = findViewById(R.id.UTripCatId);
        Intent intent = this.getIntent();
        //String TripId = 1+"";
        String TripId = intent.getStringExtra("trip_id");
        Toast.makeText(this, "in update   " + TripId, Toast.LENGTH_SHORT).show();
        /***************************Get TRip Data***************************/
        tripTableOperations = new TripTableOperations(this);
        noteTableOperations=new NoteTableOperations(this);
        UpdateTrip = tripTableOperations.selectSingleTrips(TripId);
        //UpdateTrip = tripTableOperations.selectSingleTrips(1 + "");
        TripNameView.setText(UpdateTrip.getTripName());
        // TripNameView.setText("My Trip");
        mylocationStart.setText(UpdateTrip.getTripStartPoint());
        //   mylocationStart.setText("cairo");
        mylocationEnd.setText(UpdateTrip.getTripEndPoint());
        //     mylocationEnd .setText("Alex");
        DescriptipnView.setText(UpdateTrip.getTripDescription());
        //    DescriptipnView.setText("My first Trip");
        DateView.setText(UpdateTrip.getTripDate());
        //   DateView.setText("17/5/1994");
        TimeView.setText(UpdateTrip.getTripTime());
        //    TimeView.setText("15.30 Am");
        String RadiobuttonValue = UpdateTrip.getTripDirection();
        RadioButton RadiobuttonGet;
        for(int i=0;i<mytripGroup.getChildCount();i++)
        {

            RadiobuttonGet = (RadioButton) mytripGroup.getChildAt(i);
            if (RadiobuttonGet.getText().toString().equals(RadiobuttonValue))
            //  if(RadiobuttonGet.getText().toString().equals("Round Trip"))
            {
                mytripGroup.check(RadiobuttonGet.getId());
                //  RadiobuttonGet.setChecked(true);
            }

        }
        NmyNoteList = UpdateTrip.getTripNotes();
        for (int i = 0; i < NmyNoteList.size(); i++)
        //  for (int i=0 ;i<5 ;i++)
        {
            listItems.add(NmyNoteList.get(i).getNoteBody());
            // listItems.add("listItem");
        }
        NoteListadapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,listItems);
        MyNoteList.setAdapter(NoteListadapter);
        MyNoteList.setOnTouchListener(new View.OnTouchListener() {
            // Setting on Touch Listener for handling the touch inside ScrollView
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // Disallow the touch request for parent scroll on touch of child view
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });


        //get the spinner from the xml.
        //create a list of items for the spinner.
        String[] items = new String[]{"Work", "School", "Shopping"};
        //create an adapter to describe how the items are displayed, adapters are used in several places in android
        // There are multiple variations of this, but this is the basic variant.
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        //set the spinners adapter to the previously created one.
        dropdown.setAdapter(adapter);
        dropdown.setSelection(adapter.getPosition(UpdateTrip.getTripCategory()));
        // dropdown.setSelection(adapter.getPosition("Shooping"));
        /************************** Note List Part ************************/

        FloatingActionButton fab =  findViewById(R.id.Ufab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Notebody =NoteItem.getText().toString();
                if(Notebody.trim().equals(""))
                {
                    Toast.makeText(getBaseContext(), " Enter Your Note ",
                            Toast.LENGTH_SHORT).show();

                }
                else {

                    //  addItems(Notebody);
                    listItems.add(Notebody);
                    Note myNewNote= new Note();
                    myNewNote.setNoteBody(Notebody);
                    myNewNote.setStatus(TripConstant.NoteLater);
                    myNewNote.setTripIdFk(Integer.parseInt(TripId));
                    noteTableOperations.insertNote(myNewNote);
                    NoteListadapter.notifyDataSetChanged();
                    NoteItem.setText("".toString());
                    // NoteListadapter.setNotifyOnChange(true);
                    // MyNoteList.notifyAll();

                }
            }
        });
         geoDataClient = Places.getGeoDataClient(this, null);
         MapPlacesAdapter mapPlacesAdapter=new MapPlacesAdapter(this , geoDataClient , LatLangBounds,null);
          mylocationStart.setAdapter(mapPlacesAdapter);
          mylocationEnd.setAdapter(mapPlacesAdapter);
        myCalender= Calendar.getInstance();
        day=myCalender.get(Calendar.DAY_OF_MONTH);
        month=myCalender.get(Calendar.MONTH);
        year =myCalender.get(Calendar.YEAR);
        hour=myCalender.get(Calendar.HOUR_OF_DAY);
        minute=myCalender.get(Calendar.MINUTE);
        month=month+1;

        //  DateView.setText(day+"/"+month+"/"+year);

        DateView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(UpdateTrip.this, R.style.TimePickerTheme, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month=month+1;

                        DateView.setText(dayOfMonth+"/"+month+"/"+year);
                    }
                },year,month,day);
                datePickerDialog.show();
            }
        });


        TimeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(UpdateTrip.this, R.style.TimePickerTheme, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        // hourFormat(hour);

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
//            hour-=12;
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


    public void UpdateClick(View view) {
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
            NmyNoteList.clear();
            for (int i = 0; i < listItems.size(); i++) {
                Note note = new Note(listItems.get(i), TripConstant.NoteLater);

                NmyNoteList.add(note);

            }
            String TripCatagory = dropdown.getSelectedItem().toString();

            // Toast.makeText(getBaseContext(),all.size(),
            //        Toast.LENGTH_SHORT).show();
            UpdateTrip.setTripName(NameofTrip);
            UpdateTrip.setTripStartPoint(StartLoc);
            UpdateTrip.setTripEndPoint(Endloc);
            UpdateTrip.setTripDate(Date);
            UpdateTrip.setTripTime(Time);
            UpdateTrip.setTripDirection(TripDirection);
            UpdateTrip.setTripDescription(Desc);
            UpdateTrip.setTripCategory(TripCatagory);
            for (int i = 0; i < NmyNoteList.size(); i++) {
                Log.e("uuuuuuuuuuuuuuu", "Note" + i + NmyNoteList.get(i).getNoteBody());

            }
            UpdateTrip.setTripNotes(NmyNoteList);
            boolean test = tripTableOperations.updateTrip(UpdateTrip);
            if(test){
                AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                Intent alarmIntent = new Intent(UpdateTrip.this, MainActivity.class);
                pendingIntent = PendingIntent.getBroadcast(getBaseContext(),UpdateTrip.getTripId() , alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);

                manager.cancel(pendingIntent);//cancel the alarm manager of the pending intent

                Trip lastTrip=tripTableOperations.selectAllTripsForGettingLastId();
                Intent intent=new Intent(UpdateTrip.this, AlarmScheduleService.class);
                intent.putExtra("trip_id",lastTrip.getTripId());
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startService(intent);
            }
            Toast.makeText(getBaseContext(), test + "",
                    Toast.LENGTH_SHORT).show();
            Trip my = tripTableOperations.selectSingleTrips(1 + "");
            for (int i = 0; i < my.getTripNotes().size(); i++) {
                Log.e("afteruuuuu", "Note" + i + my.getTripNotes().get(i).getNoteBody());

            }

//            Intent intent = new Intent(UpdateTrip.this, StartActivityDrawer.class);
//            startActivity(intent);
                 finish();
        }

    }

    public void cancel(View view) {
//        Intent intent = new Intent(UpdateTrip.this, StartActivityDrawer.class);
//        startActivity(intent);
        finish();
    }
}
