package com.example.lowlite.timetable.data;

import android.provider.BaseColumns;

/**
 * Created by Lowlite on 24/02/2017.
 */


// CONTRACT
public class TimetableData {
    private TimetableData(){}

    public final class EventEntry implements BaseColumns{
        public static final String TABLE_NAME = "events";
        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_EVENT_NAME = "name";
        public static final String COLUMN_EVENT_START = "start";
        public static final String COLUMN_EVENT_END = "end";
        public static final String COLUMN_EVENT_PLACE = "place";
        public static final String COLUMN_EVENT_DAY = "dayOfTheWeek";


        public static final int MONDAY = 1;
        public static final int TUESDAY = 2;
        public static final int WEDNESDAY = 3;
        public static final int THURSDAY = 4;
        public static final int FRIDAY = 5;
        public static final int SATURDAY = 6;
        public static final int SUNDAY = 7;

    }
}
