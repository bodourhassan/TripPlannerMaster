package com.ititeam.tripplannermaster.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.ititeam.tripplannermaster.DB.TripTableOperations;
import com.ititeam.tripplannermaster.R;
import com.ititeam.tripplannermaster.classes.DirectionsParser;
import com.ititeam.tripplannermaster.model.Note;
import com.ititeam.tripplannermaster.model.Trip;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class StartTripActivity extends FragmentActivity implements OnMapReadyCallback {
    ListView myList;
    private GoogleMap mMap;
    private static final int LOCATION_REQUEST = 500;
    ArrayList<LatLng> listPoints;
    ArrayList<MarkerOptions> markers;
    SupportMapFragment mapFragment;
    String startplace;
    String EndPlace;
    FloatingActionButton fab;
    ImageView mycheckedImage;
    FloatingActionButton mycancel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_trip);
        //  RelativeLayout myLayout= findViewById(R.id.mystartRoot);
        myList = findViewById(R.id.MyNoteCustomList);
        fab = findViewById(R.id.MyDoneTripButton);
        mycancel = findViewById(R.id.Cancelbutton);
//        Intent intent =getIntent();
//        int TripId= intent.getIntExtra("MyTripId",1);
        Intent intent = this.getIntent();
        //String email=intent.getStringExtra("login_user_email");
        String TripId = intent.getStringExtra("trip_id");
        Toast.makeText(this, "in update   " + TripId, Toast.LENGTH_SHORT).show();
        TripTableOperations myOperation = new TripTableOperations(this);
        Trip myTrip = myOperation.selectSingleTrips(TripId);
        ArrayList<Note> myNotes = myTrip.getTripNotes();

        startplace = myTrip.getTripStartPoint();

        EndPlace = myTrip.getTripEndPoint();
        MyNoteAdapter myadapter = new MyNoteAdapter(this, R.layout.ech_item_note, myNotes);

        listPoints = new ArrayList<>();
        markers = new ArrayList<>();
        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.Mymap);
        mapFragment.getMapAsync(this);
        myList.setOnTouchListener(new View.OnTouchListener() {
            // Setting on Touch Listener for handling the touch inside ScrollView
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // Disallow the touch request for parent scroll on touch of child view
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });
        myList.setAdapter(myadapter);
        myList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CheckedTextView ctv = view.findViewById(R.id.MycheckedTextView);
                mycheckedImage = view.findViewById(R.id.imageCheck);
                if (ctv.isChecked()) {
                    // Toast.makeText(StartTripActivity.this, "now it is unchecked"+position, Toast.LENGTH_SHORT).show();
                    ctv.setChecked(false);
                    ctv.setEnabled(true);
                    mycheckedImage.setVisibility(View.INVISIBLE);
                    myNotes.get(position).setStatus(TripConstant.NoteLater);

                } else {
                    // Toast.makeText(StartTripActivity.this, "now it is checked"+position, Toast.LENGTH_SHORT).show();
                    ctv.setChecked(true);
                    ctv.setEnabled(false);
                    mycheckedImage.setVisibility(View.VISIBLE);
                    myNotes.get(position).setStatus(TripConstant.NoteDone);
                }

            }
        });

        fab.setOnClickListener(new View.OnClickListener() {

            /**
             * Called when a view has been clicked.
             *
             * @param v The view that was clicked.
             */
            @Override
            public void onClick(View v) {
                myTrip.setTripNotes(myNotes);
                String allnote = "";
                String Status = myTrip.getTripStatus();
                if (Status.equals(TripConstant.UpcomingStatus)) {
                    if (myTrip.getTripDirection().equals(TripConstant.RoundTrip)) {
                        myTrip.setTripStatus(TripConstant.halfTripStatus);
                    } else if (myTrip.getTripDirection().equals(TripConstant.OneDirection)) {
                        myTrip.setTripStatus(TripConstant.DoneStatus);
                    }

                } else if (Status.equals(TripConstant.halfTripStatus)) {

                    myTrip.setTripStatus(TripConstant.DoneStatus);

                }

                boolean tset = myOperation.updateTrip(myTrip);
                Trip myTrip2 = myOperation.selectSingleTrips(TripId);
                for (int i = 0; i < myTrip2.getTripNotes().size(); i++) {
                    allnote += i + ":" + myTrip2.getTripNotes().get(i).getStatus() + ",";
                }
                Toast.makeText(StartTripActivity.this, allnote, Toast.LENGTH_LONG).show();
            }
        });
        mycancel.setOnClickListener(new View.OnClickListener() {

            /**
             * Called when a view has been clicked.
             *
             * @param v The view that was clicked.
             */
            @Override
            public void onClick(View v) {
                myTrip.setTripStatus(TripConstant.CancelledStatus);
                Toast.makeText(StartTripActivity.this, myTrip.getTripStatus(), Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng latLng1 = getLatLongFromGivenAddress(startplace);
        LatLng latLng2 = getLatLongFromGivenAddress(EndPlace);
//        LatLng latLng1 = getLatLongFromGivenAddress("Cairo,Egypt");
//        LatLng latLng2 = getLatLongFromGivenAddress("Tanata,Egypt");
        mMap = googleMap;
        mMap.getUiSettings().setZoomControlsEnabled(true);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_REQUEST);
            return;
        }
        mMap.setMyLocationEnabled(true);
        listPoints.add(latLng1);
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng1);
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
        markers.add(markerOptions);
        mMap.addMarker(markerOptions);
        listPoints.add(latLng2);
        // markerOptions = new MarkerOptions();
        MarkerOptions markerOptions2 = new MarkerOptions();
        markerOptions2.position(latLng2);
        markerOptions2.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
        mMap.addMarker(markerOptions2);
        markers.add(markerOptions2);
        String url = getRequestUrl(listPoints.get(0), listPoints.get(1));
        StartTripActivity.TaskRequestDirections taskRequestDirections = new StartTripActivity.TaskRequestDirections();
        taskRequestDirections.execute(url);
        /*mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                //Reset marker when already 2
                if (listPoints.size() == 2) {
                    listPoints.clear();
                    mMap.clear();
                }
                //Save first point select
                listPoints.add(latLng);
                //Create marker
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(latLng);

                if (listPoints.size() == 1) {
                    //Add first marker to the map
                    markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                } else {
                    //Add second marker to the map
                    markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                }
                mMap.addMarker(markerOptions);

                if (listPoints.size() == 2) {
                    //Create the URL to get request from first marker to second marker
                    String url = getRequestUrl(listPoints.get(0), listPoints.get(1));
                    TaskRequestDirections taskRequestDirections = new TaskRequestDirections();
                    taskRequestDirections.execute(url);
                }
            }
        });*/

        //googleMap.animateCamera(cu);
        mMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {
               /* if (mMap != null) {
                    LatLngBounds.Builder builder = new LatLngBounds.Builder();
                    for (MarkerOptions marker : markers) {
                        builder.include(marker.getPosition());
                    }
                    // builder.include(markerOptions.getPosition());
                    LatLngBounds bounds = builder.build();
                    int padding = 50; // offset from edges of the map in pixels
                    int width = getResources().getDisplayMetrics().widthPixels;
                    int height = getResources().getDisplayMetrics().heightPixels;
                    CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);
                    mMap.animateCamera(cu);

                }*/
            }
        });
        mapFragment.getView().post(new Runnable() {
            @Override
            public void run() {
                if (mMap != null) {
                    LatLngBounds.Builder builder = new LatLngBounds.Builder();
                    for (MarkerOptions marker : markers) {
                        builder.include(marker.getPosition());
                    }
                    // builder.include(markerOptions.getPosition());
                    LatLngBounds bounds = builder.build();
                    int padding = 50; // offset from edges of the map in pixels
                    int width = getResources().getDisplayMetrics().widthPixels;
                    int height = getResources().getDisplayMetrics().heightPixels;
                    CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);
                    mMap.animateCamera(cu);
                }
            }
        });
    }
    private String getRequestUrl(LatLng origin, LatLng dest) {
        //Value of origin
        String str_org = "origin=" + origin.latitude + "," + origin.longitude;
        //Value of destination
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;
        //Set value enable the sensor
        String sensor = "sensor=false";
        //Mode for find direction
        String mode = "mode=driving";
        //Build the full param
        String param = str_org + "&" + str_dest + "&" + sensor + "&" + mode;
        //Output format
        String output = "json";
        //Create url to request
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + param;
        return url;
    }

    private String requestDirection(String reqUrl) throws IOException {
        String responseString = "";
        InputStream inputStream = null;
        HttpURLConnection httpURLConnection = null;
        try {
            URL url = new URL(reqUrl);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.connect();

            //Get the response result
            inputStream = httpURLConnection.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            StringBuffer stringBuffer = new StringBuffer();
            String line = "";
            while ((line = bufferedReader.readLine()) != null) {
                stringBuffer.append(line);
            }

            responseString = stringBuffer.toString();
            bufferedReader.close();
            inputStreamReader.close();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
            httpURLConnection.disconnect();
        }
        return responseString;
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case LOCATION_REQUEST:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mMap.setMyLocationEnabled(true);
                }
                break;
        }
    }

    public class TaskRequestDirections extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            String responseString = "";
            try {
                responseString = requestDirection(strings[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return responseString;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            //Parse json here
            StartTripActivity.TaskParser taskParser = new StartTripActivity.TaskParser();
            taskParser.execute(s);
        }
    }

    public class TaskParser extends AsyncTask<String, Void, List<List<HashMap<String, String>>>> {

        @Override
        protected List<List<HashMap<String, String>>> doInBackground(String... strings) {
            JSONObject jsonObject = null;
            List<List<HashMap<String, String>>> routes = null;
            try {
                jsonObject = new JSONObject(strings[0]);
                DirectionsParser directionsParser = new DirectionsParser();
                routes = directionsParser.parse(jsonObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return routes;
        }

        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> lists) {
            //Get list route and display it into the map

            ArrayList points = null;

            PolylineOptions polylineOptions = null;

            for (List<HashMap<String, String>> path : lists) {
                points = new ArrayList();
                polylineOptions = new PolylineOptions();

                for (HashMap<String, String> point : path) {
                    double lat = Double.parseDouble(point.get("lat"));
                    double lon = Double.parseDouble(point.get("lon"));

                    points.add(new LatLng(lat, lon));
                }

                polylineOptions.addAll(points);
                polylineOptions.width(7);
                polylineOptions.color(Color.RED);
                polylineOptions.geodesic(true);
            }

            if (polylineOptions != null) {
                mMap.addPolyline(polylineOptions);
            } else {
                Toast.makeText(getApplicationContext(), "Direction not found!", Toast.LENGTH_SHORT).show();
            }

        }
    }

    public LatLng getLatLongFromGivenAddress(String address) {
        double lat = 0.0, lng = 0.0;
        LatLng latLng = null;

        Geocoder geoCoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geoCoder.getFromLocationName(address, 1);
            if (addresses.size() > 0) {
                latLng = new LatLng(
                        (addresses.get(0).getLatitude()),
                        (addresses.get(0).getLongitude()));

                lat = latLng.latitude;
                lng = latLng.longitude;

                Log.d("Latitude", "" + lat);
                Log.d("Longitude", "" + lng);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return latLng;
    }

}
