package com.example.lowlite.timetable;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
/**
 * Created by Lowlite on 23/02/2017.
 */

public class EventAdapter extends BaseAdapter {
    Context cnx;
    LayoutInflater linflater;
    ArrayList<Event> listOfEvents;

    EventAdapter(Context context, ArrayList<Event> events){
        cnx = context;
        listOfEvents = events;
        linflater = (LayoutInflater)cnx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public Object getItem(int position) {
        return listOfEvents.get(position);
    }

    @Override
    public int getCount() {
        return listOfEvents.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        View view = convertView;
        if(view == null){
            view = linflater.inflate(R.layout.event, parent, false);
        }

        // get the object from the arraylist
        Event ev = (Event)getItem(position);

        // set fields to data from the object
        ((TextView)view.findViewById(R.id.lEventName)).setText(ev.name);
        ((TextView)view.findViewById(R.id.lEventStart)).setText(ev.start);
        ((TextView)view.findViewById(R.id.lEventEnd)).setText(ev.end);
        ((TextView)view.findViewById(R.id.lEventPlace)).setText(ev.place);

        return view;
    }


    @Override
    public long getItemId(int position) {
        return position;
    }


}
