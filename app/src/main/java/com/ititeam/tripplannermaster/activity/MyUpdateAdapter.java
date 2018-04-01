package com.ititeam.tripplannermaster.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;

import com.ititeam.tripplannermaster.DB.NoteTableOperations;
import com.ititeam.tripplannermaster.DB.TripTableOperations;
import com.ititeam.tripplannermaster.R;
import com.ititeam.tripplannermaster.model.Note;

import java.util.ArrayList;

/**
 * Created by body on 30/03/2018.
 */

public class MyUpdateAdapter extends ArrayAdapter {
     Context mycontext;
     ArrayList<String> notes;
     NoteTableOperations noteTableOperations;
     TripTableOperations tripTableOperations;
       String myTripId;
    public MyUpdateAdapter(@NonNull Context context, int resource, @NonNull ArrayList<String> myNote,String TripId) {
        super(context, resource, myNote);
        mycontext=context;
        notes=myNote;
        noteTableOperations=new NoteTableOperations(mycontext);
        tripTableOperations=new TripTableOperations(mycontext);
         myTripId=TripId;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater= (LayoutInflater)  mycontext.getSystemService(mycontext.LAYOUT_INFLATER_SERVICE);
        View myView = inflater.inflate(R.layout.my_update_item,parent,false);
       TextView myNote=myView.findViewById(R.id.MyNoteU);
        FloatingActionButton myDelete = myView.findViewById(R.id.MyDelet);
        myDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notes.remove(position);
                notifyDataSetChanged();
                notifyDataSetInvalidated();

            int noteid=tripTableOperations.selectSingleTrips(myTripId).getTripNotes().get(position).getNoteId();
                noteTableOperations.deleteNote(noteid+"");

            }
        });
        myNote.setText(notes.get(position).toString());

        return myView;
    }

}
