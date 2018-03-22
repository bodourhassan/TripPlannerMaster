package com.ititeam.tripplannermaster.activity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.MultiAutoCompleteTextView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.ititeam.tripplannermaster.R;

import java.util.ArrayList;
import java.util.Calendar;

public class AddNewTrip extends AppCompatActivity implements ConnectionCallbacks,OnConnectionFailedListener {

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
    MultiAutoCompleteTextView NoteItem;

    Calendar myCalender;
    int day,month,year;
    int hour,minute;
    String format;
    public  static final LatLngBounds LatLangBounds =new LatLngBounds(new LatLng(-40,-168),new LatLng(71,163));
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_trip);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
         mylocationStart=findViewById(R.id.autoCompleteTextView2);
        mylocationEnd=findViewById(R.id.autoCompleteTextView3);
        NoteItem =findViewById(R.id.NoteId);
        /************************** Note List Part ************************/
        MyNoteList=findViewById(R.id.NoteList);
        NoteListadapter=new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                listItems);
        MyNoteList.setAdapter(NoteListadapter);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              //  Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                String Notebody =NoteItem.getText().toString();
                if(Notebody.trim().equals(""))
                {
                    Toast.makeText(getBaseContext(), " Enter Your Note ",
                            Toast.LENGTH_SHORT).show();

                }
                else {

                   //  addItems(Notebody);
                    listItems.add(Notebody);
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
        DateView =findViewById(R.id.Date);
        TimeView =findViewById(R.id.Time);
        myCalender= Calendar.getInstance();
        day=myCalender.get(Calendar.DAY_OF_MONTH);
        month=myCalender.get(Calendar.MONTH);
        year =myCalender.get(Calendar.YEAR);
        hour=myCalender.get(Calendar.HOUR_OF_DAY);
        minute=myCalender.get(Calendar.MINUTE);
        month=month+1;

        DateView.setText(day+"/"+month+"/"+year);

        DateView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(AddNewTrip.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month=month+1;

                        DateView.setText(dayOfMonth+"/"+month+"/"+year);
                    }
                },year,month,day);
                datePickerDialog.show();
            }
        });

       hourFormat(hour);

       TimeView.setText(hour+" : "+minute+" "+format);
       TimeView.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               TimePickerDialog timePickerDialog=new TimePickerDialog(AddNewTrip.this, new TimePickerDialog.OnTimeSetListener() {
                   @Override
                   public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                       hourFormat(hour);

                       TimeView.setText(hourOfDay+" : "+minute+" "+format);
                   }
               },hour,minute,true);
               timePickerDialog.show();
           }
       });
      /******************************************Trip Catagory Part ***********************/
        //get the spinner from the xml.
        Spinner dropdown = findViewById(R.id.TripCatId);
        //create a list of items for the spinner.
        String[] items = new String[]{"Work", "School", "Shooping"};
        //create an adapter to describe how the items are displayed, adapters are used in several places in android
        // There are multiple variations of this, but this is the basic variant.
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        //set the spinners adapter to the previously created one.
        dropdown.setAdapter(adapter);



    }

    public void addItems(String newNoteList) {
        listItems.add(newNoteList);
       NoteListadapter.notifyDataSetChanged();
    }

    public void hourFormat(int hour)
    {
        if(hour==0)
        {
            hour+=12;
            format="AM";
        }
        else if(hour==12)
        {

            format="PM";
        }
        else if(hour>12)
        {
           hour-=12;
            format="PM";
        }
        else {

            format="AM";
        }
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
}
