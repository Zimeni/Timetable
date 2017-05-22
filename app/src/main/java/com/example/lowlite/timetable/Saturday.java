package com.example.lowlite.timetable;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.lowlite.timetable.data.EventDbHelper;
import com.example.lowlite.timetable.data.TimetableData;

import java.util.ArrayList;

/**
 * Created by Lowlite on 06/03/2017.
 */

public class Saturday extends ListFragment {


    public Saturday(){

    }

    private ArrayList<Event> listOfEvents;
    EventAdapter eventAdapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        listOfEvents = new ArrayList<Event>();
        eventAdapter = new EventAdapter(getContext(), listOfEvents);
        setListAdapter(eventAdapter);


    }

    @Override
    public void onStart() {
        super.onStart();
        displayEvents();
    }


    public void displayEvents() {
        String name, start, end, place, sDay;
        int day; // as the data for days of the week is stored as integers in the database

        // steps for making db ready to read:
        // 1. create an instance of the helper - where the db is created
        EventDbHelper mDbHelper = new EventDbHelper(getContext());

        // 2. create a readable db
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        // 3. prepare a projection of all the columns in the db
        String[] projection = {
                TimetableData.EventEntry._ID,
                TimetableData.EventEntry.COLUMN_EVENT_NAME,
                TimetableData.EventEntry.COLUMN_EVENT_START,
                TimetableData.EventEntry.COLUMN_EVENT_END,
                TimetableData.EventEntry.COLUMN_EVENT_PLACE,
                TimetableData.EventEntry.COLUMN_EVENT_DAY,
        };

        // 4. get the poiner to the first raw of the table
        Cursor cursor = db.query(TimetableData.EventEntry.TABLE_NAME, projection, null, null, null, null, " CAST(" + TimetableData.EventEntry.COLUMN_EVENT_START + " AS INTEGER) ASC");

        listOfEvents.clear(); // to avoid duplicating the content of the database everytime the displayEvents() is called

        while (cursor.moveToNext()) {

            Event event = new Event();

            // display only Tuesday events
            day = cursor.getInt(cursor.getColumnIndexOrThrow(TimetableData.EventEntry.COLUMN_EVENT_DAY));
            if (day != 6)
                continue;

            listOfEvents.add(event); // placing this line here solves the problem of displaying only the last item of the database N times, where N is the number of rows in db

            // copy data from db to the event object
            event.id =  cursor.getString(cursor.getColumnIndexOrThrow(TimetableData.EventEntry._ID));
            event.name = cursor.getString(cursor.getColumnIndexOrThrow(TimetableData.EventEntry.COLUMN_EVENT_NAME));
            event.start = cursor.getString(cursor.getColumnIndexOrThrow(TimetableData.EventEntry.COLUMN_EVENT_START));
            event.end = cursor.getString(cursor.getColumnIndexOrThrow(TimetableData.EventEntry.COLUMN_EVENT_END));
            event.place = cursor.getString(cursor.getColumnIndexOrThrow(TimetableData.EventEntry.COLUMN_EVENT_PLACE));

            // selecting a proper word for a dayOfTheWeek as it is stored as a integer in the db, but we want it to be displayed as a word
            switch (day) {
                case 1:
                    sDay = "Monday";
                    break;
                case 2:
                    sDay = "Tuesday";
                    break;
                case 3:
                    sDay = "Wednesday";
                    break;
                case 4:
                    sDay = "Thursday";
                    break;
                case 5:
                    sDay = "Friday";
                    break;
                case 6:
                    sDay = "Saturday";
                    break;
                case 7:
                    sDay = "Sunday";
                    break;
                default:
                    sDay = "not sure";
            }
            event.dayOfWeek = sDay; // adding a proper word for dayOfTheWeek

            eventAdapter.notifyDataSetChanged();

        }

        cursor.close();
        db.close();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_saturday, container, false);
    }



    // Make edit and delete buttons visible and invisible:
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

                ImageView deleteBtn = (ImageView) arg1.findViewById(R.id.deleteEventBtn);
                ImageView editBtn = (ImageView) arg1.findViewById(R.id.editEvent);

                deleteBtn.setVisibility(View.INVISIBLE);
                editBtn.setVisibility(View.INVISIBLE);
            }
        });

        getListView().setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){
            @Override
            public boolean onItemLongClick(AdapterView<?> eventAdapter, View view, int position, long arg3){
                ImageView deleteBtn = (ImageView) view.findViewById(R.id.deleteEventBtn);
                ImageView editBtn = (ImageView) view.findViewById(R.id.editEvent);

                deleteBtn.setVisibility(View.VISIBLE);
                editBtn.setVisibility(View.VISIBLE);


                final AdapterView eventAd = eventAdapter; // to use it inside the deleteBtn click listener

                // once the btn is visible, we can click on it to DELETE an event
                deleteBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // get the position of the view(row) the delete btn belongs to
                        View parentRow = (View) v.getParent();
                        ListView listView = (ListView) parentRow.getParent();
                        final int position = listView.getPositionForView(parentRow);

                        // get the object of the listview row
                        Event eventObj = (Event) eventAd.getAdapter().getItem(position);

                        deleteEventFromDb(eventObj);
                    }
                });

                editBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // get the position of the view(row) the delete btn belongs to
                        View parentRow = (View) v.getParent();
                        ListView listView = (ListView) parentRow.getParent();
                        final int position = listView.getPositionForView(parentRow);

                        // get the object of the listview row
                        Event eventObj = (Event) eventAd.getAdapter().getItem(position);

                        editEvent(eventObj);
                    }
                });

                return true;
            }
        });




    }

    public void deleteEventFromDb(Event eventObj){

        EventDbHelper mDbHelper = new EventDbHelper(getContext());
        // Gets the data repository in write mode
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        // query for delete by id number of the item the button belongs to
        String table = TimetableData.EventEntry.TABLE_NAME;
        String whereClause = "_id=?";
        String[] whereArgs = new String[] { String.valueOf(eventObj.id) };

        db.delete(table, whereClause, whereArgs);

        db.close();

        // refresh the list after deleting item
        eventAdapter.notifyDataSetChanged();
        listOfEvents.clear();
        displayEvents();
    }

    public void editEvent(Event eventObj){

        Intent intent = new Intent(getContext(), EditEventActivity.class);
        intent.putExtra("eventObj", eventObj);
        startActivity(intent);

        eventAdapter.notifyDataSetChanged();
        listOfEvents.clear();
        displayEvents();

    }
}
