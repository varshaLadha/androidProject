package com.example.lcom151_two.myapplication;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

public class HotelAdapter extends FragmentStatePagerAdapter {

    ArrayList<String> titles;
    int noOftabs;

    public HotelAdapter(FragmentManager fm,int noOftabs,ArrayList titles) {
        super(fm);
        this.noOftabs=noOftabs;
        this.titles=titles;
    }

    public CharSequence getPageTitle(int position){
        return titles.get(position);
    }

    @Override
    public Fragment getItem(int position) {
        try{
            Fragment fragment;
            switch (position){
                case 0:
                    fragment= new MenuFragment();
                    break;
                case 1:
                    fragment= new AboutFragment();
                    break;
                case 2:
                    fragment= new ReviewFragment();
                    break;
                default:
                    fragment= new MenuFragment();
            }
            return fragment;
        }catch (Exception e){e.printStackTrace();}
        return null;
    }

    @Override
    public int getCount() {
        return titles.size();
    }
}
