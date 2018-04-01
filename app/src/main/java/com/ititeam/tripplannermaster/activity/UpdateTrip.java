package com.ititeam.tripplannermaster.activity;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.LauncherActivity;
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
import android.widget.AdapterView;
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
import com.ititeam.tripplannermaster.model.ParcelableUtil;
import com.ititeam.tripplannermaster.model.Trip;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class UpdateTrip extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks,GoogleApiClient.OnConnectionFailedListener {
    private GoogleApiClient googleApiClient;
    private GeoDataClient geoDataClient ;
    ArrayList<String> listItems=new ArrayList<String>();
    ArrayAdapter<String> NoteListadapter;
    MyUpdateAdapter myUpdateAdapter;
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
        DateView =findViewById(R.id.UDate);
        TimeView =findViewById(R.id.UTime);
        MyNoteList=findViewById(R.id.UNoteList);
        dropdown = findViewById(R.id.UTripCatId);
        Intent intent = this.getIntent();
        String TripId = intent.getStringExtra("trip_id");
       Toast.makeText(this, "in update   " + TripId, Toast.LENGTH_SHORT).show();
        /***************************Get TRip Data***************************/
        tripTableOperations = new TripTableOperations(this);
        noteTableOperations=new NoteTableOperations(this);
        UpdateTrip = tripTableOperations.selectSingleTrips(TripId);
        TripNameView.setText(UpdateTrip.getTripName());
        mylocationStart.setText(UpdateTrip.getTripStartPoint());
        mylocationEnd.setText(UpdateTrip.getTripEndPoint());
        DescriptipnView.setText(UpdateTrip.getTripDescription());
        DateView.setText(UpdateTrip.getTripDate());
        TimeView.setText(UpdateTrip.getTripTime());
        String RadiobuttonValue = UpdateTrip.getTripDirection();
        RadioButton RadiobuttonGet;
        for(int i=0;i<mytripGroup.getChildCount();i++)
        {

            RadiobuttonGet = (RadioButton) mytripGroup.getChildAt(i);
            if (RadiobuttonGet.getText().toString().equals(RadiobuttonValue))
            {
                mytripGroup.check(RadiobuttonGet.getId());
            }

        }
        NmyNoteList = UpdateTrip.getTripNotes();
        for (int i = 0; i < NmyNoteList.size(); i++)

        {
            listItems.add(NmyNoteList.get(i).getNoteBody());
        }
        myUpdateAdapter=new MyUpdateAdapter(this,R.layout.my_update_item,listItems,TripId);
        MyNoteList.setItemsCanFocus(true);
        MyNoteList.setAdapter(myUpdateAdapter);
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
        String[] items = new String[]{TripConstant.FriendCatagory,TripConstant.FamilyCatagory, TripConstant.bussinessCatagory,TripConstant.meetingCatagory,TripConstant.vacationCatagory,TripConstant.otherCatagory};

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


                    listItems.add(Notebody);
                    Note myNewNote= new Note();
                    myNewNote.setNoteBody(Notebody);
                    myNewNote.setStatus(TripConstant.NoteLater);
                    myNewNote.setTripIdFk(Integer.parseInt(TripId));
                    noteTableOperations.insertNote(myNewNote);
                    myUpdateAdapter.notifyDataSetChanged();
                    NoteItem.setText("".toString());


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



        DateView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(UpdateTrip.this, R.style.TimePickerTheme, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month=month+1;
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
                        TimeView.setText(myhourin + ":" + myminutein);
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
        Date nowDate=null ;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat TimeFormat = new SimpleDateFormat("HH:mm");
        Date userDate=null;
        Date myTime=null;
        Date CurrentTime=null;
        try {
            userDate = sdf.parse(DateView.getText().toString());
            nowDate = sdf.parse(sdf.format(new Date()));
            myTime = TimeFormat.parse(TimeView.getText().toString());
            CurrentTime = TimeFormat.parse(TimeFormat.format(new Date()));
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
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

        }   else if (userDate.compareTo(nowDate)==0 && myTime.compareTo(CurrentTime)<=0) {


            Toast.makeText(getBaseContext(), " Enter Upcomming Time ",
                    Toast.LENGTH_SHORT).show();

        }
        else if(userDate.compareTo(nowDate)<0 ){
            Toast.makeText(getBaseContext(), " Enter Upcomming Date ",
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
            UpdateTrip.setTripName(NameofTrip);
            UpdateTrip.setTripStartPoint(StartLoc);
            UpdateTrip.setTripEndPoint(Endloc);
            UpdateTrip.setTripDate(Date);
            UpdateTrip.setTripTime(Time);
            UpdateTrip.setTripDirection(TripDirection);
            UpdateTrip.setTripDescription(Desc);
            UpdateTrip.setTripCategory(TripCatagory);
            UpdateTrip.setTripNotes(NmyNoteList);
            boolean test = tripTableOperations.updateTrip(UpdateTrip);
            if(test){
                AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                Intent alarmIntent = new Intent(UpdateTrip.this, MainActivity.class);
                pendingIntent = PendingIntent.getBroadcast(getBaseContext(),UpdateTrip.getTripId() , alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);

                manager.cancel(pendingIntent);//cancel the alarm manager of the pending intent

                Trip lastTrip=tripTableOperations.selectAllTripsForGettingLastId();
                byte[] last = ParcelableUtil.marshall(lastTrip);
                Intent intent=new Intent(UpdateTrip.this, AlarmScheduleService.class);
                intent.putExtra("trip",last);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startService(intent);
            }

                 finish();
        }

    }

    public void cancel(View view) {

        finish();
    }
}
