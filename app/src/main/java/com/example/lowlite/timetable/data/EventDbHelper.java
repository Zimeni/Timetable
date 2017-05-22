package com.example.lowlite.timetable.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Lowlite on 24/02/2017.
 */

// helpes to create a db
public class EventDbHelper extends SQLiteOpenHelper {
    public static final String  DATABASE_NAME = "events.db";
    public static final int DATABASE_VERSION = 1;

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TimetableData.EventEntry.TABLE_NAME + " (" +
                    TimetableData.EventEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    TimetableData.EventEntry.COLUMN_EVENT_NAME + " TEXT," +
                    TimetableData.EventEntry.COLUMN_EVENT_START + " TEXT," +
                    TimetableData.EventEntry.COLUMN_EVENT_END + " TEXT," +
                    TimetableData.EventEntry.COLUMN_EVENT_PLACE + " TEXT," +
                    TimetableData.EventEntry.COLUMN_EVENT_DAY + " INTEGER)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + TimetableData.EventEntry.TABLE_NAME;


    public EventDbHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //create database
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
