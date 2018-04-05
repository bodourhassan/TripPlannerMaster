package com.ititeam.tripplannermaster.DB;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.util.Log;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.ititeam.tripplannermaster.activity.AddNewTrip;
import com.ititeam.tripplannermaster.activity.MainActivity;
import com.ititeam.tripplannermaster.activity.UpdateTrip;
import com.ititeam.tripplannermaster.activity.alarmhandler.AlarmScheduleService;
import com.ititeam.tripplannermaster.model.*;
import com.ititeam.tripplannermaster.activity.TripConstant;
/**
 * Created by MARK on 3/18/2018.
 */

public class TripTableOperations {

    Context context;
    String TAG = "MY TAG";
    public TripTableOperations(Context context)
    {
        this.context = context;
    }

    public ArrayList<Trip> selectAllTrips ()
    {
        String [] result_columns = {AdapterDba.DbOpenHelper.TRIP_ID,
                AdapterDba.DbOpenHelper.TRIP_NAME,
                AdapterDba.DbOpenHelper.TRIP_START_POINT,
                AdapterDba.DbOpenHelper.TRIP_END_POINT,
                AdapterDba.DbOpenHelper.TRIP_DATE,
                AdapterDba.DbOpenHelper.TRIP_TIME,
                AdapterDba.DbOpenHelper.TRIP_STATUS,
                AdapterDba.DbOpenHelper.TRIP_DIRECTION,
                AdapterDba.DbOpenHelper.TRIP_DESCRIPTION,
                AdapterDba.DbOpenHelper.TRIP_REPITITION,
                AdapterDba.DbOpenHelper.TRIP_CATEGORY,
                AdapterDba.DbOpenHelper.USER_ID};
        String whereClause = null;
        String [] selectArgs = null;
        String groupBy  = null;
        String having = null;
        String orderBy = null;
        Cursor cursor = AdapterDba.getAdapterDbaInstance(context)._select(AdapterDba.DbOpenHelper.TRIP_TABLE ,result_columns , whereClause, selectArgs, groupBy , having , orderBy);
        ArrayList<Trip> returnedData = new ArrayList<>();
        while (cursor.moveToNext())
        {
            Trip trip = new Trip();
            trip.setTripId(cursor.getInt(0));
            trip.setTripName(cursor.getString(1));
            trip.setTripStartPoint(cursor.getString(2));
            trip.setTripEndPoint(cursor.getString(3));
            trip.setTripDate(cursor.getString(4));
            trip.setTripTime(cursor.getString(5));
            trip.setTripStatus(cursor.getString(6));
            trip.setTripDirection(cursor.getString(7));
            trip.setTripDescription(cursor.getString(8));
            trip.setTripRepetition(cursor.getString(9));
            trip.setTripCategory(cursor.getString(10));
            trip.setUserId(cursor.getString(11));
            ArrayList<Note> notes = new NoteTableOperations(context).selectNoteWithTripFk(trip.getTripId() + "");
            for (Note note : notes) {
                trip.getTripNotes().add(note);
            }
            returnedData.add(trip);
        }
        return returnedData;
    }

    public ArrayList<Trip> selectPastTripsUsingOnlyDate(String user_id) {
        String[] result_columns = {AdapterDba.DbOpenHelper.TRIP_ID,
                AdapterDba.DbOpenHelper.TRIP_NAME,
                AdapterDba.DbOpenHelper.TRIP_START_POINT,
                AdapterDba.DbOpenHelper.TRIP_END_POINT,
                AdapterDba.DbOpenHelper.TRIP_DATE,
                AdapterDba.DbOpenHelper.TRIP_TIME,
                AdapterDba.DbOpenHelper.TRIP_STATUS,
                AdapterDba.DbOpenHelper.TRIP_DIRECTION,
                AdapterDba.DbOpenHelper.TRIP_DESCRIPTION,
                AdapterDba.DbOpenHelper.TRIP_REPITITION,
                AdapterDba.DbOpenHelper.TRIP_CATEGORY,
                AdapterDba.DbOpenHelper.USER_ID};
        String whereClause = "(date(" + AdapterDba.DbOpenHelper.TRIP_DATE + ") < date('now') OR "+AdapterDba.DbOpenHelper.TRIP_STATUS+"=? OR "+AdapterDba.DbOpenHelper.TRIP_STATUS+"=?)AND "+AdapterDba.DbOpenHelper.USER_ID+"=?";
        String[] selectArgs = {TripConstant.DoneStatus,TripConstant.CancelledStatus,user_id};
        String groupBy = null;
        String having = null;
        String orderBy = null;
        Cursor cursor = AdapterDba.getAdapterDbaInstance(context)._select(AdapterDba.DbOpenHelper.TRIP_TABLE, result_columns, whereClause, selectArgs, groupBy, having, orderBy);
        ArrayList<Trip> returnedData = new ArrayList<>();
        while (cursor.moveToNext()) {
            Trip trip = new Trip();
            trip.setTripId(cursor.getInt(0));
            trip.setTripName(cursor.getString(1));
            trip.setTripStartPoint(cursor.getString(2));
            trip.setTripEndPoint(cursor.getString(3));
            trip.setTripDate(cursor.getString(4));
            trip.setTripTime(cursor.getString(5));
            trip.setTripStatus(cursor.getString(6));
            trip.setTripDirection(cursor.getString(7));
            trip.setTripDescription(cursor.getString(8));
            trip.setTripRepetition(cursor.getString(9));
            trip.setTripCategory(cursor.getString(10));
            trip.setUserId(cursor.getString(11));

            ArrayList<Note> notes = new NoteTableOperations(context).selectNoteWithTripFk(trip.getTripId() + "");
            for (Note note : notes) {
                trip.getTripNotes().add(note);
            }
            returnedData.add(trip);
        }
        return returnedData;
    }

    public ArrayList<Trip> selectPastTripsUsingDateAndStatus(String userId) {
        String[] result_columns = {AdapterDba.DbOpenHelper.TRIP_ID,
                AdapterDba.DbOpenHelper.TRIP_NAME,
                AdapterDba.DbOpenHelper.TRIP_START_POINT,
                AdapterDba.DbOpenHelper.TRIP_END_POINT,
                AdapterDba.DbOpenHelper.TRIP_DATE,
                AdapterDba.DbOpenHelper.TRIP_TIME,
                AdapterDba.DbOpenHelper.TRIP_STATUS,
                AdapterDba.DbOpenHelper.TRIP_DIRECTION,
                AdapterDba.DbOpenHelper.TRIP_DESCRIPTION,
                AdapterDba.DbOpenHelper.TRIP_REPITITION,
                AdapterDba.DbOpenHelper.TRIP_CATEGORY,
                AdapterDba.DbOpenHelper.USER_ID};
        String whereClause = "date(" + AdapterDba.DbOpenHelper.TRIP_DATE + ") < date('now') AND " + AdapterDba.DbOpenHelper.TRIP_STATUS + "=? AND "+AdapterDba.DbOpenHelper.USER_ID+"=?";
        String[] selectArgs = {TripConstant.DoneStatus,userId};
        String groupBy = null;
        String having = null;
        String orderBy = null;
        Cursor cursor = AdapterDba.getAdapterDbaInstance(context)._select(AdapterDba.DbOpenHelper.TRIP_TABLE, result_columns, whereClause, selectArgs, groupBy, having, orderBy);
        ArrayList<Trip> returnedData = new ArrayList<>();
        while (cursor.moveToNext()) {
            Trip trip = new Trip();
            trip.setTripId(cursor.getInt(0));
            trip.setTripName(cursor.getString(1));
            trip.setTripStartPoint(cursor.getString(2));
            trip.setTripEndPoint(cursor.getString(3));
            trip.setTripDate(cursor.getString(4));
            trip.setTripTime(cursor.getString(5));
            trip.setTripStatus(cursor.getString(6));
            trip.setTripDirection(cursor.getString(7));
            trip.setTripDescription(cursor.getString(8));
            trip.setTripRepetition(cursor.getString(9));
            trip.setTripCategory(cursor.getString(10));
            trip.setUserId(cursor.getString(11));

            ArrayList<Note> notes = new NoteTableOperations(context).selectNoteWithTripFk(trip.getTripId() + "");
            for (Note note : notes) {
                trip.getTripNotes().add(note);
            }
            returnedData.add(trip);
        }
        return returnedData;
    }

    public ArrayList<Trip> selectUpcomingTripsUsingOnlyDate(String userId) {
        String[] result_columns = {AdapterDba.DbOpenHelper.TRIP_ID,
                AdapterDba.DbOpenHelper.TRIP_NAME,
                AdapterDba.DbOpenHelper.TRIP_START_POINT,
                AdapterDba.DbOpenHelper.TRIP_END_POINT,
                AdapterDba.DbOpenHelper.TRIP_DATE,
                AdapterDba.DbOpenHelper.TRIP_TIME,
                AdapterDba.DbOpenHelper.TRIP_STATUS,
                AdapterDba.DbOpenHelper.TRIP_DIRECTION,
                AdapterDba.DbOpenHelper.TRIP_DESCRIPTION,
                AdapterDba.DbOpenHelper.TRIP_REPITITION,
                AdapterDba.DbOpenHelper.TRIP_CATEGORY,
                AdapterDba.DbOpenHelper.USER_ID};
        String whereClause = "date(" + AdapterDba.DbOpenHelper.TRIP_DATE + ") >= date('now') AND "+AdapterDba.DbOpenHelper.TRIP_STATUS+"=? AND "+AdapterDba.DbOpenHelper.USER_ID+"=?";
        String[] selectArgs = {TripConstant.UpcomingStatus,userId};
        String groupBy = null;
        String having = null;
        String orderBy = AdapterDba.DbOpenHelper.TRIP_ID;
        Cursor cursor = AdapterDba.getAdapterDbaInstance(context)._select(AdapterDba.DbOpenHelper.TRIP_TABLE, result_columns, whereClause, selectArgs, groupBy, having, orderBy);
        ArrayList<Trip> returnedData = new ArrayList<>();
        while (cursor.moveToNext()) {
            Trip trip = new Trip();
            trip.setTripId(cursor.getInt(0));
            trip.setTripName(cursor.getString(1));
            trip.setTripStartPoint(cursor.getString(2));
            trip.setTripEndPoint(cursor.getString(3));
            trip.setTripDate(cursor.getString(4));
            trip.setTripTime(cursor.getString(5));
            trip.setTripStatus(cursor.getString(6));
            trip.setTripDirection(cursor.getString(7));
            trip.setTripDescription(cursor.getString(8));
            trip.setTripRepetition(cursor.getString(9));
            trip.setTripCategory(cursor.getString(10));
            trip.setUserId(cursor.getString(11));

            ArrayList<Note> notes = new NoteTableOperations(context).selectNoteWithTripFk(trip.getTripId() + "");
            for (Note note : notes) {
                trip.getTripNotes().add(note);
            }
            returnedData.add(trip);
        }
        return returnedData;
    }

    public ArrayList<Trip> selectUpcomingTripsUsingOnlyDate() {
        String[] result_columns = {AdapterDba.DbOpenHelper.TRIP_ID,
                AdapterDba.DbOpenHelper.TRIP_NAME,
                AdapterDba.DbOpenHelper.TRIP_START_POINT,
                AdapterDba.DbOpenHelper.TRIP_END_POINT,
                AdapterDba.DbOpenHelper.TRIP_DATE,
                AdapterDba.DbOpenHelper.TRIP_TIME,
                AdapterDba.DbOpenHelper.TRIP_STATUS,
                AdapterDba.DbOpenHelper.TRIP_DIRECTION,
                AdapterDba.DbOpenHelper.TRIP_DESCRIPTION,
                AdapterDba.DbOpenHelper.TRIP_REPITITION,
                AdapterDba.DbOpenHelper.TRIP_CATEGORY,
                AdapterDba.DbOpenHelper.USER_ID};
        String whereClause = "date(" + AdapterDba.DbOpenHelper.TRIP_DATE + ") >= date('now')";
        String[] selectArgs = null;
        String groupBy = null;
        String having = null;
        String orderBy = AdapterDba.DbOpenHelper.TRIP_ID;
        Cursor cursor = AdapterDba.getAdapterDbaInstance(context)._select(AdapterDba.DbOpenHelper.TRIP_TABLE, result_columns, whereClause, selectArgs, groupBy, having, orderBy);
        ArrayList<Trip> returnedData = new ArrayList<>();
        while (cursor.moveToNext()) {
            Trip trip = new Trip();
            trip.setTripId(cursor.getInt(0));
            trip.setTripName(cursor.getString(1));
            trip.setTripStartPoint(cursor.getString(2));
            trip.setTripEndPoint(cursor.getString(3));
            trip.setTripDate(cursor.getString(4));
            trip.setTripTime(cursor.getString(5));
            trip.setTripStatus(cursor.getString(6));
            trip.setTripDirection(cursor.getString(7));
            trip.setTripDescription(cursor.getString(8));
            trip.setTripRepetition(cursor.getString(9));
            trip.setTripCategory(cursor.getString(10));
            trip.setUserId(cursor.getString(11));

            ArrayList<Note> notes = new NoteTableOperations(context).selectNoteWithTripFk(trip.getTripId() + "");
            for (Note note : notes) {
                trip.getTripNotes().add(note);
            }
            returnedData.add(trip);
        }
        return returnedData;
    }

    public Trip selectAllTripsForGettingLastId ()
    {
        String[] result_columns = {AdapterDba.DbOpenHelper.TRIP_ID,
                AdapterDba.DbOpenHelper.TRIP_NAME,
                AdapterDba.DbOpenHelper.TRIP_START_POINT,
                AdapterDba.DbOpenHelper.TRIP_END_POINT,
                AdapterDba.DbOpenHelper.TRIP_DATE,
                AdapterDba.DbOpenHelper.TRIP_TIME,
                AdapterDba.DbOpenHelper.TRIP_STATUS,
                AdapterDba.DbOpenHelper.TRIP_DIRECTION,
                AdapterDba.DbOpenHelper.TRIP_DESCRIPTION,
                AdapterDba.DbOpenHelper.TRIP_REPITITION,
                AdapterDba.DbOpenHelper.TRIP_CATEGORY,
                AdapterDba.DbOpenHelper.USER_ID};
        String whereClause = null;
        String [] selectArgs = null;
        String groupBy  = null;
        String having = null;
        String orderBy = null;
        Cursor cursor = AdapterDba.getAdapterDbaInstance(context)._select(AdapterDba.DbOpenHelper.TRIP_TABLE ,result_columns , whereClause, selectArgs, groupBy , having , orderBy);
        Trip trip = new Trip();
        while (cursor.moveToNext())
        {
            trip.setTripId(cursor.getInt(0));
            trip.setTripName(cursor.getString(1));
            trip.setTripStartPoint(cursor.getString(2));
            trip.setTripEndPoint(cursor.getString(3));
            trip.setTripDate(cursor.getString(4));
            trip.setTripTime(cursor.getString(5));
            trip.setTripStatus(cursor.getString(6));
            trip.setTripDirection(cursor.getString(7));
            trip.setTripDescription(cursor.getString(8));
            trip.setTripRepetition(cursor.getString(9));
            trip.setTripCategory(cursor.getString(10));
            trip.setUserId(cursor.getString(11));
        }
        return trip;
    }

    public Trip selectSingleTrips (String id) {
        String[] result_columns = {AdapterDba.DbOpenHelper.TRIP_ID,
                AdapterDba.DbOpenHelper.TRIP_NAME,
                AdapterDba.DbOpenHelper.TRIP_START_POINT,
                AdapterDba.DbOpenHelper.TRIP_END_POINT,
                AdapterDba.DbOpenHelper.TRIP_DATE,
                AdapterDba.DbOpenHelper.TRIP_TIME,
                AdapterDba.DbOpenHelper.TRIP_STATUS,
                AdapterDba.DbOpenHelper.TRIP_DIRECTION,
                AdapterDba.DbOpenHelper.TRIP_DESCRIPTION,
                AdapterDba.DbOpenHelper.TRIP_REPITITION,
                AdapterDba.DbOpenHelper.TRIP_CATEGORY,
                AdapterDba.DbOpenHelper.USER_ID};
        String whereClause = AdapterDba.DbOpenHelper.TRIP_ID + "=?";
        String[] selectArgs = {id};
        String groupBy = null;
        String having = null;
        String orderBy = null;
        Cursor cursor = AdapterDba.getAdapterDbaInstance(context)._select(AdapterDba.DbOpenHelper.TRIP_TABLE, result_columns, whereClause, selectArgs, groupBy, having, orderBy);

        Trip trip = null;
        if (cursor.moveToNext()) {
            trip = new Trip();
            trip.setTripId(cursor.getInt(0));
            trip.setTripName(cursor.getString(1));
            trip.setTripStartPoint(cursor.getString(2));
            trip.setTripEndPoint(cursor.getString(3));
            trip.setTripDate(cursor.getString(4));
            trip.setTripTime(cursor.getString(5));
            trip.setTripStatus(cursor.getString(6));
            trip.setTripDirection(cursor.getString(7));
            trip.setTripDescription(cursor.getString(8));
            trip.setTripCategory(cursor.getString(10));
            trip.setUserId(cursor.getString(11));

        }
        if (trip != null) {
            ArrayList<Note> notes = new NoteTableOperations(context).selectNoteWithTripFk(trip.getTripId() + "");
            for (Note note : notes) {
                Log.i(TAG, note.getNoteBody());
                trip.getTripNotes().add(note);
            }
        }
            return trip;
        }

    public ArrayList<Trip> selectTripsUsingUserId(String id) {
        String[] result_columns = {AdapterDba.DbOpenHelper.TRIP_ID,
                AdapterDba.DbOpenHelper.TRIP_NAME,
                AdapterDba.DbOpenHelper.TRIP_START_POINT,
                AdapterDba.DbOpenHelper.TRIP_END_POINT,
                AdapterDba.DbOpenHelper.TRIP_DATE,
                AdapterDba.DbOpenHelper.TRIP_TIME,
                AdapterDba.DbOpenHelper.TRIP_STATUS,
                AdapterDba.DbOpenHelper.TRIP_DIRECTION,
                AdapterDba.DbOpenHelper.TRIP_DESCRIPTION,
                AdapterDba.DbOpenHelper.TRIP_REPITITION,
                AdapterDba.DbOpenHelper.TRIP_CATEGORY,
                AdapterDba.DbOpenHelper.USER_ID};
        String whereClause = AdapterDba.DbOpenHelper.USER_ID + "=?";
        String[] selectArgs = {id};
        String groupBy = null;
        String having = null;
        String orderBy = null;
        Cursor cursor = AdapterDba.getAdapterDbaInstance(context)._select(AdapterDba.DbOpenHelper.TRIP_TABLE, result_columns, whereClause, selectArgs, groupBy, having, orderBy);
        ArrayList<Trip> returnedData = new ArrayList<>();
        while (cursor.moveToNext()) {
            Trip trip = new Trip();
            trip.setTripId(cursor.getInt(0));
            trip.setTripName(cursor.getString(1));
            trip.setTripStartPoint(cursor.getString(2));
            trip.setTripEndPoint(cursor.getString(3));
            trip.setTripDate(cursor.getString(4));
            trip.setTripTime(cursor.getString(5));
            trip.setTripStatus(cursor.getString(6));
            trip.setTripDirection(cursor.getString(7));
            trip.setTripDescription(cursor.getString(8));
            trip.setTripRepetition(cursor.getString(9));
            trip.setTripCategory(cursor.getString(10));
            trip.setUserId(cursor.getString(11));

            ArrayList<Note> notes = new NoteTableOperations(context).selectNoteWithTripFk(trip.getTripId() + "");
            for (Note note : notes) {
                trip.getTripNotes().add(note);
            }
            returnedData.add(trip);
        }
        return returnedData;
    }

    public boolean insertTrip (Trip trip)
    {
        boolean flag = false;
        ContentValues newValues = new ContentValues();
        newValues.put(AdapterDba.DbOpenHelper.TRIP_NAME, trip.getTripName());
        newValues.put(AdapterDba.DbOpenHelper.TRIP_START_POINT, trip.getTripStartPoint());
        newValues.put(AdapterDba.DbOpenHelper.TRIP_END_POINT, trip.getTripEndPoint());
        newValues.put(AdapterDba.DbOpenHelper.TRIP_DATE, trip.getTripDate());
        newValues.put(AdapterDba.DbOpenHelper.TRIP_TIME, trip.getTripTime());
        newValues.put(AdapterDba.DbOpenHelper.TRIP_STATUS, trip.getTripStatus());
        newValues.put(AdapterDba.DbOpenHelper.TRIP_DIRECTION, trip.getTripDirection());
        newValues.put(AdapterDba.DbOpenHelper.TRIP_DESCRIPTION, trip.getTripDescription());
        newValues.put(AdapterDba.DbOpenHelper.TRIP_REPITITION, trip.getTripRepetition());
        newValues.put(AdapterDba.DbOpenHelper.TRIP_CATEGORY, trip.getTripCategory());
        newValues.put(AdapterDba.DbOpenHelper.USER_ID, trip.getUserId());
        AdapterDba.getAdapterDbaInstance(context)._insert(AdapterDba.DbOpenHelper.TRIP_TABLE , newValues);
        Trip  tripWithId=  this.selectAllTripsForGettingLastId();

        for (Note note : trip.getTripNotes())
        {
            note.setTripIdFk(tripWithId.getTripId());
            new NoteTableOperations(context).insertNote(note);

        }
        flag = true;
        return flag;
    }

    public boolean updateTrip(Trip trip)
    {
        boolean flag = false;
        String whereClause = AdapterDba.DbOpenHelper.TRIP_ID+"=?";
        String [] whereArgs = {trip.getTripId()+""};
        ContentValues newValues = new ContentValues();
        newValues.put(AdapterDba.DbOpenHelper.TRIP_NAME, trip.getTripName());
        newValues.put(AdapterDba.DbOpenHelper.TRIP_START_POINT, trip.getTripStartPoint());
        newValues.put(AdapterDba.DbOpenHelper.TRIP_END_POINT, trip.getTripEndPoint());
        newValues.put(AdapterDba.DbOpenHelper.TRIP_DATE, trip.getTripDate());
        newValues.put(AdapterDba.DbOpenHelper.TRIP_TIME, trip.getTripTime());
        newValues.put(AdapterDba.DbOpenHelper.TRIP_STATUS, trip.getTripStatus());
        newValues.put(AdapterDba.DbOpenHelper.TRIP_DIRECTION, trip.getTripDirection());
        newValues.put(AdapterDba.DbOpenHelper.TRIP_DESCRIPTION, trip.getTripDescription());
        newValues.put(AdapterDba.DbOpenHelper.TRIP_REPITITION, trip.getTripRepetition());
        newValues.put(AdapterDba.DbOpenHelper.TRIP_CATEGORY, trip.getTripCategory());
        newValues.put(AdapterDba.DbOpenHelper.USER_ID, trip.getUserId());
        AdapterDba.getAdapterDbaInstance(context)._update(AdapterDba.DbOpenHelper.TRIP_TABLE ,whereClause ,whereArgs , newValues);

        for (Note note : trip.getTripNotes())
        {
            new NoteTableOperations(context).updateNote(note);
            flag = true;
        }
        return flag;
    }

    public void deleteTrip (String id)
    {
        ArrayList<Note> notes = new NoteTableOperations(context).selectNoteWithTripFk(id + "");
        for (Note note : notes) {
            Log.i(TAG, note.getNoteBody());
            new NoteTableOperations(context).deleteNote(note.getNoteId() + "");
        }

        String whereClause = AdapterDba.DbOpenHelper.TRIP_ID+"=?";
        String [] whereArgs = {id};
        AdapterDba.getAdapterDbaInstance(context)._delete(AdapterDba.DbOpenHelper.TRIP_TABLE ,whereClause ,whereArgs);
    }
    public void deleteAllTrips ()
    {

            new NoteTableOperations(context).deleteAllNote();


        String whereClause = null;
        String [] whereArgs = null;
        AdapterDba.getAdapterDbaInstance(context)._delete(AdapterDba.DbOpenHelper.TRIP_TABLE ,whereClause ,whereArgs);
    }

    //Start Hanaa Section
    public void getTripFromFirebase(ArrayList<Trip> trips) {

       /* Trip localTrip = trips.get(trips.size()-1);
        Trip lastTrip = selectAllTripsForGettingLastId();
        if(localTrip!=null) {
            if (localTrip.getUserId() != lastTrip.getUserId()) {
                insertTrip(trips.get(trips.size() - 1));
            }
        }*/
        //deleteAllTrips();
        for (Trip trip : trips) {

            Date nowDate=null ;
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat TimeFormat = new SimpleDateFormat("HH:mm");
            Date userDate=null;
            Date myTime=null;
            Date CurrentTime=null;
            try {
                nowDate = sdf.parse(sdf.format(new Date()));
                CurrentTime = TimeFormat.parse(TimeFormat.format(new Date()));
            }catch (Exception e){
                e.printStackTrace();
            }
            Trip localTrip = selectSingleTrips(trip.getTripId() + "");

            if(localTrip==null) {
             //   Toast.makeText(context, "enter", Toast.LENGTH_SHORT).show();
                insertTrip(trip);
                byte[] last = ParcelableUtil.marshall(trip);

                try {
                    userDate = sdf.parse(trip.getTripDate());

                    myTime = TimeFormat.parse(trip.getTripTime());

                }
                catch (ParseException e) {
                    e.printStackTrace();
                }
                if ((userDate.compareTo(nowDate)==0 && myTime.compareTo(CurrentTime)>=0) ||userDate.compareTo(nowDate)>0) {
                    Intent intent = new Intent(context, AlarmScheduleService.class);
                    intent.putExtra("trip", last);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    context.startService(intent);
                }

            }

          /*  Trip localTrip = selectSingleTrips(trip.getTripId() + "");

               if(localTrip==null) {
                    Toast.makeText(context, "enter", Toast.LENGTH_SHORT).show();
                    insertTrip(trip);
                    Intent intent = new Intent(this.context, AlarmScheduleService.class);
                    intent.putExtra("trip_id", trip.getTripId());
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    this.context.startService(intent);

            }else{

                if(updateTrip(trip)) {
                    AlarmManager manager = (AlarmManager) this.context.getSystemService(Context.ALARM_SERVICE);
                    Intent alarmIntent = new Intent(this.context, MainActivity.class);
                     PendingIntent  pendingIntent = PendingIntent.getBroadcast(this.context,trip.getTripId() , alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);

                    manager.cancel(pendingIntent);//cancel the alarm manager of the pending intent

                    Intent intent=new Intent(this.context, AlarmScheduleService.class);
                    intent.putExtra("trip_id",trip.getTripId());
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    this.context.startService(intent);
                }
                else {
                    insertTrip(trip);
                    Intent intent=new Intent(this.context, AlarmScheduleService.class);
                    intent.putExtra("trip_id",trip.getTripId());
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    this.context.startService(intent);
                }
                Toast.makeText(context, "update", Toast.LENGTH_SHORT).show();



        }*/}
    }
    //end  Hanaa Section
}
