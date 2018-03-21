package com.ititeam.tripplannermaster.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;

import com.ititeam.tripplannermaster.model.Note;

/**
 * Created by MARK on 3/18/2018.
 */

public class NoteTableOperations {

    Context context;

    public NoteTableOperations(Context context)
    {
        this.context = context;
    }

    public ArrayList<Note> selectAllNotes ()
    {
        String [] result_columns = {AdapterDba.DbOpenHelper.NOTE_ID,
                AdapterDba.DbOpenHelper.NOTE,
                AdapterDba.DbOpenHelper.TRIP_ID_FK};
        String whereClause = null;
        String [] selectArgs = null;
        String groupBy  = null;
        String having = null;
        String orderBy = null;
        Cursor cursor = AdapterDba.getAdapterDbaInstance(context)._select(AdapterDba.DbOpenHelper.NOTES_TABLE ,result_columns , whereClause, selectArgs, groupBy , having , orderBy);
        ArrayList<Note> returnedData = new ArrayList<Note>();
        while (cursor.moveToNext())
        {
            Note note = new Note();
            note.setNoteId(cursor.getString(0));
            note.setNoteBody(cursor.getString(1));
            note.setTripIdFk(cursor.getString(2));
            returnedData.add(note);
        }
        return returnedData;
    }
    public Note selectSingleNote (String id)
    {
        String [] result_columns = {AdapterDba.DbOpenHelper.NOTE_ID,
                AdapterDba.DbOpenHelper.NOTE,
                AdapterDba.DbOpenHelper.TRIP_ID_FK};
        String whereClause = AdapterDba.DbOpenHelper.NOTE_ID+"=?";
        String [] selectArgs = {id};
        String groupBy  = null;
        String having = null;
        String orderBy = null;
        Cursor cursor = AdapterDba.getAdapterDbaInstance(context)._select(AdapterDba.DbOpenHelper.NOTES_TABLE ,result_columns , whereClause, selectArgs, groupBy , having , orderBy);
        Note note = new Note();
        if (cursor.moveToNext())
        {
            note.setNoteId(cursor.getString(0));
            note.setNoteBody(cursor.getString(1));
            note.setTripIdFk(cursor.getString(2));
        }
        return note;
    }

    public void insertTrip (Note note)
    {
        ContentValues newValues = new ContentValues();
        newValues.put(AdapterDba.DbOpenHelper.NOTE_ID, note.getNoteId());
        newValues.put(AdapterDba.DbOpenHelper.NOTE, note.getNoteBody());
        newValues.put(AdapterDba.DbOpenHelper.TRIP_ID_FK, note.getTripIdFk());
        AdapterDba.getAdapterDbaInstance(context)._insert(AdapterDba.DbOpenHelper.NOTES_TABLE , newValues);
    }

    public void updateTrip (Note note)
    {
        String whereClause = AdapterDba.DbOpenHelper.NOTE_ID+"=?";
        String [] whereArgs = {note.getNoteId()};
        ContentValues newValues = new ContentValues();
        newValues.put(AdapterDba.DbOpenHelper.NOTE_ID, note.getNoteId());
        newValues.put(AdapterDba.DbOpenHelper.NOTE, note.getNoteBody());
        newValues.put(AdapterDba.DbOpenHelper.TRIP_ID_FK, note.getTripIdFk());
        AdapterDba.getAdapterDbaInstance(context)._update(AdapterDba.DbOpenHelper.NOTES_TABLE ,whereClause ,whereArgs , newValues);
    }

    public void deleteTrip (String id)
    {
        String whereClause = AdapterDba.DbOpenHelper.NOTE_ID+"=?";
        String [] whereArgs = {id};
        AdapterDba.getAdapterDbaInstance(context)._delete(AdapterDba.DbOpenHelper.NOTES_TABLE ,whereClause ,whereArgs);
    }
}
