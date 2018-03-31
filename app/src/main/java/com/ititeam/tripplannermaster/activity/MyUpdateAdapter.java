package com.ititeam.tripplannermaster.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;

import com.ititeam.tripplannermaster.DB.NoteTableOperations;
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

    public MyUpdateAdapter(@NonNull Context context, int resource, @NonNull ArrayList<String> myNote) {
        super(context, resource, myNote);
        mycontext=context;
        notes=myNote;
        noteTableOperations=new NoteTableOperations(mycontext);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater= (LayoutInflater)  mycontext.getSystemService(mycontext.LAYOUT_INFLATER_SERVICE);
        View myView = inflater.inflate(R.layout.my_update_item,parent,false);
       EditText myNote=myView.findViewById(R.id.MyNoteU);
        FloatingActionButton myDelete = myView.findViewById(R.id.MyDelet);
        myDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notes.remove(position);
                notifyDataSetChanged();
                notifyDataSetInvalidated();
            }
        });
       myNote.setOnFocusChangeListener(new View.OnFocusChangeListener() {
           /**
            * Called when the focus state of a view has changed.
            *
            * @param v        The view whose state has changed.
            * @param hasFocus The new focus state of v.
            */
           @Override
           public void onFocusChange(View v, boolean hasFocus) {
               notes.set(position,myNote.getText().toString());
               notifyDataSetChanged();
               notifyDataSetInvalidated();
           }
       });
        myNote.setText(notes.get(position).toString());

        return myView;
    }

}
