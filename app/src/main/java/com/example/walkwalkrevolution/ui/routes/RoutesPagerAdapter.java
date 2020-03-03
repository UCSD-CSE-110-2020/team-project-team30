package com.example.walkwalkrevolution.ui.routes;

import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

//Example built on: https://www.simplifiedcoding.net/android-tablayout-example-using-viewpager-fragments/
public class RoutesPagerAdapter extends FragmentStatePagerAdapter {
    //integer to count number of tabs
    int tabCount;

    //Constructor to the class
    public RoutesPagerAdapter(FragmentManager fm, int tabCount) {
        super(fm);
        //Initializing tab count
        this.tabCount= tabCount;
    }

    //Overriding method getItem
    @Override
    public Fragment getItem(int position) {
        //Returning the current tabs
        switch (position) {
            case 0:
                MyRoutesFragment tab1 = new MyRoutesFragment();
                return tab1;
            case 1:
                TeamRoutesFragment tab2 = new TeamRoutesFragment();
                Log.d("Pressing tab2", "I PRESSED tab 2");
                return tab2;
            case 2:
                TeamRoutesFragment tab3 = new TeamRoutesFragment();
                return tab3;
            default:
                return null;
        }
    }

    //Overriden method getCount to get the number of tabs
    @Override
    public int getCount() {
        return tabCount;
    }
}
