package com.example.lowlite.timetable;

import android.view.View;
import android.widget.Toast;

import java.io.Serializable;

import static java.security.AccessController.getContext;

/**
 * Created by Lowlite on 23/02/2017.
 */

public class Event implements Serializable{
    public String id, name, start, end, place, dayOfWeek;


    public Event(){};

    public Event(String n, String s, String e, String p, String d){
        name = n;
        start = s;
        end = e;
        place = p;
        dayOfWeek = d;
    }




}
