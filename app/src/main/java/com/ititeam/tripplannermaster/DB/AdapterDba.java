package com.ititeam.tripplannermaster.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Mark on 02/03/18.
 */

public class AdapterDba {

    Context context;
    private static AdapterDba adapterDba;

    private AdapterDba(Context context) {

        this.context = context;
    }

    public static AdapterDba getAdapterDbaInstance (Context context)
    {
        if(adapterDba == null)
        {
            adapterDba = new AdapterDba(context);
        }
        return adapterDba;
    }

    public Cursor _select (String tableName , String [] result_columns , String whereClause , String [] whereArgs , String groupBy , String having , String orderBy)
    {
        SQLiteDatabase db = DbOpenHelper.getDbopenHelperInstance(context).getReadableDatabase();
        Cursor cursor =  db.query(tableName , result_columns , whereClause , whereArgs  , groupBy , having , orderBy);
        return cursor;
    }

    public void _insert (String tableName , ContentValues newValues)
    {
        SQLiteDatabase db = DbOpenHelper.getDbopenHelperInstance(context).getWritableDatabase();
        db.insert(tableName , null , newValues);
    }

    public void _delete (String tableName , String whereClause , String [] whereArgs)
    {
        SQLiteDatabase db = DbOpenHelper.getDbopenHelperInstance(context).getWritableDatabase();
        db.delete(tableName,whereClause, whereArgs);
    }

    public void _update (String tableName , String whereClause , String [] whereArgs ,ContentValues newValues)
    {
        SQLiteDatabase db = DbOpenHelper.getDbopenHelperInstance(context).getWritableDatabase();
        db.update(tableName,newValues ,whereClause ,whereArgs);
    }

    public static class DbOpenHelper extends SQLiteOpenHelper
    {

        private static DbOpenHelper dbOpenHelper;

        private static final String DATABASE_NAME = "contacts.db";
        private static final int DATABSE_VERSION  = 1;

        // creating and droping table Trip
        public static final String TRIP_TABLE = "TRIP";

        public static final String TRIP_ID = "TRIP_ID";
        public static final String TRIP_NAME = "TRIP_NAME";
        public static final String TRIP_START_POINT = "TRIP_START_POINT";
        public static final String TRIP_END_POINT = "TRIP_END_POINT";
        public static final String TRIP_DATE = "TRIP_DATE";
        public static final String TRIP_TIME = "TRIP_TIME";
        public static final String TRIP_STATUS = "TRIP_STATUS";
        public static final String TRIP_DIRECTION = "TRIP_DIRECTION";
        public static final String TRIP_DESCRIPTION = "TRIP_DESCRIPTION";
        public static final String TRIP_REPITITION = "TRIP_REPITITION";
        public static final String USER_ID = "USER_ID";

        private static final String TRIP_CREATE_STATMENT = "CREATE TABLE "+
                TRIP_TABLE +" (" + TRIP_ID +" integer primary key autoincrement , "
                                     + TRIP_NAME + " text , "
                                     + TRIP_START_POINT + " text , "
                                     + TRIP_END_POINT + " text , "
                                     + TRIP_DATE + " text , "
                                     + TRIP_TIME + " text , "
                                     + TRIP_STATUS + " text , "
                                     + TRIP_DIRECTION + " text , "
                                     + TRIP_DESCRIPTION + " text , "
                                     + TRIP_REPITITION + " text , "
                                     + USER_ID + " text )";
        private static final String TRIP_DROP_STATEMENT = "DROP TABLE IF IT EXISTS "+
                TRIP_TABLE;

        //creating and droping table NOTE
        public static final String NOTES_TABLE = "NOTES";

        public static final String NOTE_ID = "NOTE_ID";
        public static final String NOTE = "NOTE";
        public static final String TRIP_ID_FK = "TRIP_ID_FK";

        private static final String NOTES_CREATE_STATMENT = "CREATE TABLE "+
                NOTES_TABLE +" (" + NOTE_ID +" integer primary key autoincrement , "
                + NOTE + " text , "
                + TRIP_ID_FK + " integer REFERENCES "+TRIP_TABLE+");";

        private static final String NOTES_DROP_STATEMENT = "DROP TABLE IF IT EXISTS "+
                NOTES_TABLE;

        //creating table MONTH_REPITITION
        public static final String MONTH_REPITITION_TABLE = "MONTH_REPITITION";

        public static final String REPITITION_ID = "REPITITION_ID";
        public static final String REPITITION_TRIP_ID_FK = "REPITITION_TRIP_ID";
        public static final String REPITITION_DAY = "REPITITION_DAY";

        private static final String MONTH_REPITITION_CREATE_STATMENT = "CREATE TABLE "+
                MONTH_REPITITION_TABLE +" (" + REPITITION_ID +" integer primary key autoincrement , "
                + REPITITION_TRIP_ID_FK + " integer REFERENCES "+TRIP_TABLE+" , "
                + REPITITION_DAY + " text );";

        private static final String MONTH_REPITITION_DROP_STATEMENT = "DROP TABLE IF IT EXISTS "+
                MONTH_REPITITION_TABLE;

        private DbOpenHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABSE_VERSION);
        }


        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            sqLiteDatabase.execSQL(TRIP_CREATE_STATMENT);
            sqLiteDatabase.execSQL(NOTES_CREATE_STATMENT);
            sqLiteDatabase.execSQL(MONTH_REPITITION_CREATE_STATMENT);
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

            //mechanism for upgrading the database
            sqLiteDatabase.execSQL(TRIP_DROP_STATEMENT);
            sqLiteDatabase.execSQL(NOTES_DROP_STATEMENT);
            sqLiteDatabase.execSQL(MONTH_REPITITION_DROP_STATEMENT);
            onCreate(sqLiteDatabase);
        }

        public static DbOpenHelper getDbopenHelperInstance (Context context)
        {
            if (dbOpenHelper == null)
            {
                dbOpenHelper = new DbOpenHelper(context);
            }
            return dbOpenHelper;
        }
    }
}
