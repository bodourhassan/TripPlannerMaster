package com.ititeam.tripplannermaster.activity;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.ititeam.tripplannermaster.R;
import com.ititeam.tripplannermaster.model.Note;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by body on 24/03/2018.
 */

public class MyNoteAdapter extends ArrayAdapter {
    Context myContext;
    ArrayList<Note> myNotes;

    /**
     * Constructor
     *
     * @param context  The current context.
     * @param resource The resource ID for a layout file containing a TextView to use when
     *                 instantiating views.
     * @param objects  The objects to represent in the ListView.
     */


    public MyNoteAdapter(Context context, int resource, ArrayList<Note> Notes) {
        super(context, resource, Notes);
        myContext = context;
        myNotes = Notes;

    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View myView = convertView;
        ViewHolder myholder;
        if (myView == null) {

            LayoutInflater inflater = (LayoutInflater) myContext.getSystemService(myContext.LAYOUT_INFLATER_SERVICE);
            myView = inflater.inflate(R.layout.ech_item_note, parent, false);
            myholder = new ViewHolder(myView);
            myView.setTag(myholder);
        } else {
            myholder = (ViewHolder) myView.getTag();
        }

        myholder.getheader().setText(myNotes.get(position).getNoteBody());
        return myView;
    }
}
