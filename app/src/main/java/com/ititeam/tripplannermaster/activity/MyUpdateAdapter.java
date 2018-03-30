package com.ititeam.tripplannermaster.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;

import com.ititeam.tripplannermaster.R;
import com.ititeam.tripplannermaster.model.Note;

import java.util.ArrayList;

/**
 * Created by body on 30/03/2018.
 */

public class MyUpdateAdapter extends ArrayAdapter {
     Context mycontext;
     ArrayList<Note> notes;
    public MyUpdateAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Note> myNote) {
        super(context, resource, myNote);
        mycontext=context;
        notes=myNote;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater= (LayoutInflater)  mycontext.getSystemService(mycontext.LAYOUT_INFLATER_SERVICE);
        View myView = inflater.inflate(R.layout.my_update_item,parent,false);
       EditText myNote=myView.findViewById(R.id.MyNoteU);
        myNote.setText(notes.get(position).getNoteBody().toString());
        return myView;
    }

}
