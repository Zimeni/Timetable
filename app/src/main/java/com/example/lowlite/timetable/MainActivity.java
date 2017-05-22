package com.example.lowlite.timetable;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import com.example.lowlite.timetable.data.ViewPagerAdapter;

import java.util.Calendar;


public class MainActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private TabLayout tabLayout;
    private ViewPagerAdapter viewPagerAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        viewPager = (ViewPager) findViewById(R.id.viewPager);

        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragments(new Monday(), "Mon");
        viewPagerAdapter.addFragments(new Tuesday(), "Tue");
        viewPagerAdapter.addFragments(new Wednesday(), "Wed");
        viewPagerAdapter.addFragments(new Thursday(), "Thu");
        viewPagerAdapter.addFragments(new Friday(), "Fri");
        viewPagerAdapter.addFragments(new Saturday(), "Sat");
        viewPagerAdapter.addFragments(new Sunday(), "Sun");

        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);


        int tabPosition;
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK);

        switch (day) {
            case Calendar.MONDAY: tabPosition = 0;
                break;

            case Calendar.TUESDAY: tabPosition = 1;
                break;

            case Calendar.WEDNESDAY: tabPosition = 2;
                break;

            case Calendar.THURSDAY: tabPosition = 3;
                break;

            case Calendar.FRIDAY: tabPosition = 4;
                break;

            case Calendar.SATURDAY: tabPosition = 5;
                break;

            case Calendar.SUNDAY: tabPosition = 6;
                break;
            default: tabPosition = 0;
        }
        viewPager.setCurrentItem(tabPosition);

    }


    protected void addEvent(View view){
        //call for a new activity with fields to fill and button to add data to database
        Intent intent = new Intent(this, AddEventActivity.class);
        startActivity(intent);
    }


}
