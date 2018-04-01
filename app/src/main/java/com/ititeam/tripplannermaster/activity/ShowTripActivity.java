package com.ititeam.tripplannermaster.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.internal.Utility;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.github.clans.fab.Label;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.ititeam.tripplannermaster.DB.TripTableOperations;
import com.ititeam.tripplannermaster.R;
import com.ititeam.tripplannermaster.classes.DirectionsParser;
import com.ititeam.tripplannermaster.classes.TripViewHolder;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class ShowTripActivity extends FragmentActivity implements OnMapReadyCallback {
    FloatingActionMenu materialDesignFAM;
    FloatingActionButton floatingActionButton1, floatingActionButton2, floatingActionButton3,floatingActionButton4,floatingActionButton5;
    Label tripName, tripStartPoint, tripEndPoint, tripDirection, tripDescription,tripStatus;
    TextView tripDate, tripTime;
    private GoogleMap mMap;
    private static final int LOCATION_REQUEST = 500;
    ArrayList<LatLng> listPoints;
    ArrayList<MarkerOptions> markers;
    SupportMapFragment mapFragment;
    ArrayList<Note> notes = new ArrayList<>();
    Trip trip = null;
    TripTableOperations tripTableOperations=null;
    //Pending intent instance
    private PendingIntent pendingIntent;
    boolean flag=false;
    boolean flag1=false;
    boolean flag3=false;
    //Alarm Request Code
    private static final int ALARM_REQUEST_CODE = 133;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_trip);
        tripName = findViewById(R.id.show_trip_name);
        tripStartPoint = findViewById(R.id.show_trip_start_point);
        tripEndPoint = findViewById(R.id.show_trip_end_point);
        tripDescription = findViewById(R.id.show_trip_description);
        tripDirection = findViewById(R.id.show_trip_direction);
        tripStatus = findViewById(R.id.show_trip_status);
        tripDate = findViewById(R.id.show_trip_date);
        tripTime = findViewById(R.id.show_trip_time);
        materialDesignFAM = findViewById(R.id.show_trip_material_design_android_floating_action_menu);
        floatingActionButton1 = findViewById(R.id.show_trip_start);
        floatingActionButton2 = findViewById(R.id.show_trip_edit);
        floatingActionButton3 = findViewById(R.id.show_trip_done);
        floatingActionButton4 = findViewById(R.id.show_trip_cancel);
        floatingActionButton5 = findViewById(R.id.show_trip_delete);
        tripTableOperations = new TripTableOperations(getBaseContext());
        Intent intent = getIntent();
        String trip_id = intent.getStringExtra("trip_id");
        trip = tripTableOperations.selectSingleTrips(trip_id);
        if (trip != null) {
            notes = trip.getTripNotes();
            ListView myList = findViewById(R.id.show_trip_note_custom_list);
            ShowTripNotesAdapter myadapter = new ShowTripNotesAdapter(this, R.layout.show_trip_notes_layout, notes);
            myList.setAdapter(myadapter);
            myList.setOnTouchListener(new View.OnTouchListener() {
                // Setting on Touch Listener for handling the touch inside ScrollView
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    // Disallow the touch request for parent scroll on touch of child view
                    v.getParent().requestDisallowInterceptTouchEvent(true);
                    return false;
                }
            });
            tripName.setText(trip.getTripName());
            tripStartPoint.setText(trip.getTripStartPoint());
            tripEndPoint.setText(trip.getTripEndPoint());
            tripTime.setText(trip.getTripTime());
            tripDate.setText(trip.getTripDate());
            tripDirection.setText(trip.getTripDirection());
            tripDescription.setText(trip.getTripDescription());
            tripStatus.setText(trip.getTripStatus());
            Date nowDate=null ;
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat TimeFormat = new SimpleDateFormat("HH:mm");
            Date userDate=null;
            Date myTime=null;
            Date CurrentTime=null;
            try {
                userDate = sdf.parse(trip.getTripDate());
                nowDate = sdf.parse(sdf.format(new Date()));
                myTime = TimeFormat.parse(trip.getTripTime());
                CurrentTime = TimeFormat.parse(TimeFormat.format(new Date()));
            }
            catch (ParseException e) {
                e.printStackTrace();
            }
            

                if ((userDate.compareTo(nowDate)==0 && myTime.compareTo(CurrentTime)<=0) ||userDate.compareTo(nowDate)<0|| trip.getTripStatus().equals(TripConstant.DoneStatus) || trip.getTripStatus().equals(TripConstant.CancelledStatus)) {
                    materialDesignFAM.removeMenuButton(floatingActionButton1);
                    materialDesignFAM.removeMenuButton(floatingActionButton2);
                    flag3=true;
                }
                if (trip.getTripStatus().equals(TripConstant.DoneStatus) || trip.getTripStatus().equals(TripConstant.CancelledStatus)) {
                    materialDesignFAM.removeMenuButton(floatingActionButton3);
                    materialDesignFAM.removeMenuButton(floatingActionButton4);
                    flag=true;
                } else if (trip.getTripStatus().equals(TripConstant.halfTripStatus)) {
                    materialDesignFAM.removeMenuButton(floatingActionButton3);
                    flag1=true;
                }

            listPoints = new ArrayList<>();
            markers = new ArrayList<>();
            mapFragment = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.map);
            if (!isConnected()) {
/*            LinearLayout mapLayout = findViewById(R.id.map_layout);
            LinearLayout.LayoutParams mapLayoutParams = new LinearLayout.LayoutParams(0, 0);
            mapLayoutParams.setMargins(0,0,0,100);
            mapLayout.setLayoutParams(mapLayoutParams);

*/


            } else {

                mapFragment.getMapAsync(this);
            }

            floatingActionButton1.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    //TODO something when floating action menu first item clicked
                    //go to start Activity
                    Intent intent = new Intent(ShowTripActivity.this, StartTripActivity.class);
                    intent.putExtra("trip_id", trip_id);
                    startActivity(intent);

                }
            });
            floatingActionButton2.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    //TODO something when floating action menu second item clicked
                    Intent intent = new Intent(ShowTripActivity.this, UpdateTrip.class);
                    intent.putExtra("trip_id", trip_id);
                    startActivity(intent);

                }
            });
            floatingActionButton3.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    //TODO something when floating action menu third item clicked
                    AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                    Intent alarmIntent = new Intent(ShowTripActivity.this, MainActivity.class);
                    pendingIntent = PendingIntent.getBroadcast(getBaseContext(), trip.getTripId(), alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                    manager.cancel(pendingIntent);
                    trip.setTripStatus(TripConstant.DoneStatus);
                    tripTableOperations.updateTrip(trip);
                    tripStatus.setText(TripConstant.DoneStatus);
                    if(!flag3) {
                     materialDesignFAM.removeMenuButton(floatingActionButton1);
                     materialDesignFAM.removeMenuButton(floatingActionButton2);
}
                    if(!flag) {
                        materialDesignFAM.removeMenuButton(floatingActionButton3);
                        materialDesignFAM.removeMenuButton(floatingActionButton4);
                    }else{
                        if(!flag1){
                            materialDesignFAM.removeMenuButton(floatingActionButton4);
                        }
                    }
                    materialDesignFAM.close(false);
                    Toast.makeText(ShowTripActivity.this, "trip has Done", Toast.LENGTH_SHORT).show();

                }
            });
            floatingActionButton4.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    //TODO something when floating action menu third item clicked
                    AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                    Intent alarmIntent = new Intent(ShowTripActivity.this, MainActivity.class);
                    pendingIntent = PendingIntent.getBroadcast(getBaseContext(), trip.getTripId(), alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                    manager.cancel(pendingIntent);
                    trip.setTripStatus(TripConstant.CancelledStatus);
                    tripTableOperations.updateTrip(trip);

                    tripStatus.setText(TripConstant.CancelledStatus);
                    if(!flag3) {
                        materialDesignFAM.removeMenuButton(floatingActionButton1);
                        materialDesignFAM.removeMenuButton(floatingActionButton2);
                    }
                    if(!flag) {
                        materialDesignFAM.removeMenuButton(floatingActionButton3);
                        materialDesignFAM.removeMenuButton(floatingActionButton4);
                    }else{
                        if(!flag1){
                            materialDesignFAM.removeMenuButton(floatingActionButton4);
                        }
                    }
                    materialDesignFAM.close(false);
                    Toast.makeText(ShowTripActivity.this, "trip has Cancelled", Toast.LENGTH_SHORT).show();
                }
            });
            floatingActionButton5.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    //TODO something when floating action menu third item clicked

                    AlertDialog.Builder alert = new AlertDialog.Builder(ShowTripActivity.this);
                    alert.setTitle("Delete Trip " + trip.getTripName());
                    alert.setMessage("Are you sure you want to delete " + trip.getTripName() + " ?");

                    alert.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {


                        public void onClick(DialogInterface dialog, int which) {
                            // continue with delete
                            AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                            Intent alarmIntent = new Intent(ShowTripActivity.this, MainActivity.class);
                            pendingIntent = PendingIntent.getBroadcast(getBaseContext(), trip.getTripId(), alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);

                            //cancel the alarm manager of the pending intent
                            tripTableOperations.deleteTrip(String.valueOf(trip.getTripId()));
                            manager.cancel(pendingIntent);
                        /*mItemManger.removeShownLayouts(viewHolder.swipeLayout);
                        upcommingTrips.remove(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, upcommingTrips.size());
                        mItemManger.closeAllItems();*/
                            //   Toast.makeText(getApplicationContext(), "here in delete", Toast.LENGTH_SHORT).show();
                        /*Intent intent = new Intent(ShowTripActivity.this, StartActivityDrawer.class);

                        startActivity(intent);*/
                            finish();
                        }
                    });

                    alert.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // close dialog
                            dialog.cancel();
                        }
                    });
                    alert.show();
               /* Intent intent =new Intent(ShowTripActivity.this,UpdateTrip.class);
                intent.putExtra("trip_id",trip_id);
                startActivity(intent);*/
                }
            });
        }
    }
    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera.
     * In this case, we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device.
     * This method will only be triggered once the user has installed
     * Google Play services and returned to the app.
     */

    @Override
    public void onMapReady(GoogleMap googleMap) {
        if (isConnected()) {
            LatLng latLng1 = getLatLongFromGivenAddress(trip.getTripStartPoint());
            LatLng latLng2 = getLatLongFromGivenAddress(trip.getTripEndPoint());
            if (latLng1 != null && latLng2!=null) {
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
            ShowTripActivity.TaskRequestDirections taskRequestDirections = new ShowTripActivity.TaskRequestDirections();
            taskRequestDirections.execute(url);


            //googleMap.animateCamera(cu);
            mMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
                @Override
                public void onMapLoaded() {

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
        else
            {
                Toast.makeText(getBaseContext(),"Your Satart Or End Location isn’t on Map",Toast.LENGTH_SHORT).show();

            }


    }
        else
        {
            Toast.makeText(getBaseContext(),"connect To Network",Toast.LENGTH_SHORT).show();

        }
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
            ShowTripActivity.TaskParser taskParser = new ShowTripActivity.TaskParser();
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
    public boolean isConnected() {
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Activity.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected())
            return true;
        else
            return false;
    }
}