package com.example.lowlite.timetable;

import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;

import com.example.lowlite.timetable.data.EventDbHelper;
import com.example.lowlite.timetable.data.TimetableData;

import java.util.Calendar;

public class AddEventActivity extends AppCompatActivity {

    EditText eventName, startTime, endTime, eventPlace;
    Spinner editDay;
    String daySelected;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);

        eventName = (EditText) findViewById(R.id.editEventName);
        startTime = (EditText) findViewById(R.id.editStartTime);
        endTime = (EditText) findViewById(R.id.editEndTime);
        eventPlace = (EditText) findViewById(R.id.editPlace);

        // Spinner for days of the week:
        editDay = (Spinner) findViewById(R.id.editDay);
        String [] days = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item, days);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        editDay.setAdapter(arrayAdapter);


        startTime.setOnClickListener(new View.OnClickListener() {

             @Override
             public void onClick(View v) {
                 // TODO Auto-generated method stub
                 Calendar mcurrentTime = Calendar.getInstance();
                 int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                 int minute = mcurrentTime.get(Calendar.MINUTE);
                 TimePickerDialog mTimePicker;
                 mTimePicker = new TimePickerDialog(AddEventActivity.this, new TimePickerDialog.OnTimeSetListener() {
                     @Override
                     public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                         startTime.setText(selectedHour + ":" + selectedMinute);
                     }
                 }, hour, minute, true);//Yes 24 hour time
                 mTimePicker.show();
             }
        });

        endTime.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(AddEventActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        endTime.setText(selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.show();
            }
        });

    }

    // adding data do db
    protected void SubmitEvent(View view){
        // get values from the edit fields
        String sEventName = eventName.getText().toString();
        String sEventStartTime = startTime.getText().toString();
        String sEventEndTime = endTime.getText().toString();
        String sEventPlace = eventPlace.getText().toString();


        EventDbHelper mDbHelper = new EventDbHelper(this);
        // Gets the data repository in write mode
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(TimetableData.EventEntry.COLUMN_EVENT_NAME, sEventName);
        values.put(TimetableData.EventEntry.COLUMN_EVENT_START, sEventStartTime);
        values.put(TimetableData.EventEntry.COLUMN_EVENT_END, sEventEndTime);
        values.put(TimetableData.EventEntry.COLUMN_EVENT_PLACE, sEventPlace);


        // getting an integer depending on the dayOfTheWeek
        int day;
        daySelected = editDay.getSelectedItem().toString(); // get the string of the day from the spinner
        switch(daySelected){
            case "Monday": day = 1;
                break;
            case "Tuesday": day = 2;
                break;
            case "Wednesday": day = 3;
                break;
            case "Thursday": day = 4;
                break;
            case "Friday": day = 5;
                break;
            case "Saturday": day = 6;
                break;
            case "Sunday": day = 7;
                break;
            default: day = 0;
        }

        values.put(TimetableData.EventEntry.COLUMN_EVENT_DAY, day);

        // insert data to db
        db.insert(TimetableData.EventEntry.TABLE_NAME, null, values);

        db.close();
        finish();
    }
}
