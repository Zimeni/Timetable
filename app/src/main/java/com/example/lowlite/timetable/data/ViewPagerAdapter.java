package com.example.lowlite.timetable.data;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.example.lowlite.timetable.SmartFragmentStatePagerAdapter;

import java.util.ArrayList;

/**
 * Created by Lowlite on 06/03/2017.
 */

public class ViewPagerAdapter extends SmartFragmentStatePagerAdapter {

    ArrayList<Fragment> fragments = new ArrayList<>();
    ArrayList<String> tabTitles = new ArrayList<>();

    public void addFragments(Fragment fragment, String tabTitle){
        this.fragments.add(fragment);
        this.tabTitles.add(tabTitle);
    }


    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles.get(position);
    }
}
