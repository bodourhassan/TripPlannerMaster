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
                AdapterDba.DbOpenHelper.STATUS,
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
            note.setNoteId(cursor.getInt(0));
            note.setNoteBody(cursor.getString(1));
            note.setStatus(cursor.getString(2));
            note.setTripIdFk(cursor.getInt(3));
            returnedData.add(note);
        }
        return returnedData;
    }
    public Note selectSingleNote (String id)
    {
        String [] result_columns = {AdapterDba.DbOpenHelper.NOTE_ID,
                AdapterDba.DbOpenHelper.NOTE,
                AdapterDba.DbOpenHelper.STATUS,
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
            note.setNoteId(cursor.getInt(0));
            note.setNoteBody(cursor.getString(1));
            note.setStatus(cursor.getString(2));
            note.setTripIdFk(cursor.getInt(3));
        }
        return note;
    }

    public ArrayList<Note> selectNoteWithTripFk (String id)
    {
        String [] result_columns = {AdapterDba.DbOpenHelper.NOTE_ID,
                AdapterDba.DbOpenHelper.NOTE,
                AdapterDba.DbOpenHelper.STATUS,
                AdapterDba.DbOpenHelper.TRIP_ID_FK};
        String whereClause = AdapterDba.DbOpenHelper.TRIP_ID_FK+"=?";
        String [] selectArgs = {id};
        String groupBy  = null;
        String having = null;
        String orderBy = null;
        Cursor cursor = AdapterDba.getAdapterDbaInstance(context)._select(AdapterDba.DbOpenHelper.NOTES_TABLE ,result_columns , whereClause, selectArgs, groupBy , having , orderBy);
        Note note = new Note();
        ArrayList<Note> tripNotes = new ArrayList<>();
        while (cursor.moveToNext())
        {
            note.setNoteId(cursor.getInt(0));
            note.setNoteBody(cursor.getString(1));
            note.setStatus(cursor.getString(2));
            note.setTripIdFk(cursor.getInt(3));
            tripNotes.add(note);
        }
        return tripNotes;
    }

    public void insertNote (Note note)
    {
        ContentValues newValues = new ContentValues();
        newValues.put(AdapterDba.DbOpenHelper.NOTE, note.getNoteBody());
        newValues.put(AdapterDba.DbOpenHelper.STATUS, note.getStatus());
        newValues.put(AdapterDba.DbOpenHelper.TRIP_ID_FK, note.getTripIdFk());
        AdapterDba.getAdapterDbaInstance(context)._insert(AdapterDba.DbOpenHelper.NOTES_TABLE , newValues);
    }

    public void updateNote (Note note)
    {
        String whereClause = AdapterDba.DbOpenHelper.NOTE_ID+"=?";
        String [] whereArgs = {note.getNoteId()+""};
        ContentValues newValues = new ContentValues();
        newValues.put(AdapterDba.DbOpenHelper.NOTE, note.getNoteBody());
        newValues.put(AdapterDba.DbOpenHelper.STATUS, note.getStatus());
        newValues.put(AdapterDba.DbOpenHelper.TRIP_ID_FK, note.getTripIdFk());
        AdapterDba.getAdapterDbaInstance(context)._update(AdapterDba.DbOpenHelper.NOTES_TABLE ,whereClause ,whereArgs , newValues);
    }

    public void deleteNote (String id)
    {
        String whereClause = AdapterDba.DbOpenHelper.NOTE_ID+"=?";
        String [] whereArgs = {id};
        AdapterDba.getAdapterDbaInstance(context)._delete(AdapterDba.DbOpenHelper.NOTES_TABLE ,whereClause ,whereArgs);
    }
}
