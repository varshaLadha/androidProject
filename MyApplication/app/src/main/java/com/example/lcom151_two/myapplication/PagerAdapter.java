package com.example.lcom151_two.myapplication;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class PagerAdapter extends FragmentStatePagerAdapter {

    ArrayList<String> titles;
    int noOftabs;

    public PagerAdapter(FragmentManager fm,int noOftabs,ArrayList titles) {
        super(fm);
        this.noOftabs=noOftabs;
        this.titles=titles;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        //Log.d("Page Title", "Title: "+titles.get(position));
        return titles.get(position);
    }

    @Override
    public Fragment getItem(int position) {
        try{
            Fragment fragment;
            switch (position){
                case 0:
                    fragment= new TabFragement1();
                    break;
                case 1:
                    fragment= new TabFragment2();
                    break;
                case 2:
                    fragment= new TabFragment3();
                    break;
                case 3:
                    fragment=  new TabFragment4();
                    break;
                default:
                    fragment= new TabFragement1();
            }
            return fragment;
        }catch (Exception e){e.printStackTrace();}
        return null;
    }

    @Override
    public int getCount() {
        return  titles.size();
    }
}
