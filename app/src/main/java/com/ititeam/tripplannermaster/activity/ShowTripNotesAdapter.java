package com.ititeam.tripplannermaster.activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ititeam.tripplannermaster.R;
import com.ititeam.tripplannermaster.model.Note;

import java.util.ArrayList;

/**
 * Created by Hanaa on 3/29/2018.
 */

public class ShowTripNotesAdapter extends ArrayAdapter {
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


    public ShowTripNotesAdapter(Context context, int resource, ArrayList<Note> Notes) {
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
            myView = inflater.inflate(R.layout.show_trip_notes_layout, parent, false);
            myholder = new ViewHolder(myView);
            myView.setTag(myholder);
        } else {
            myholder = (ViewHolder) myView.getTag();
        }
        TextView textView=myView.findViewById(R.id.noteTextView);
        ImageView imageView=myView.findViewById(R.id.show_trip_note_img);
          if(!myNotes.get(position).getStatus().equals(TripConstant.NoteDone)){
           imageView.setImageResource(R.drawable.x_icon);
        }
        else if(myNotes.get(position).getStatus().equals(TripConstant.NoteDone)) {
              imageView.setImageResource(R.mipmap.pink_check_mark);
          }
        textView.setText(myNotes.get(position).getNoteBody());
        //myholder.getheader().setText(myNotes.get(position).getNoteBody());
        return myView;
    }
}
