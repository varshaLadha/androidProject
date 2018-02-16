package com.example.lcom151_two.myapplication;

import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;

import java.util.ArrayList;

public class design4 extends AppCompatActivity {
    private TabLayout tabLayout;
    ArrayList<String> titles=new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_design4);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);

        ActionBar ab=getSupportActionBar();
        ab.hide();

        LinearLayout ll = (LinearLayout)findViewById(R.id.linearlayout);
        ll.setBackgroundColor(Color.WHITE);

        LinearLayout ll1 = (LinearLayout)findViewById(R.id.ll1);
        ll1.setBackgroundColor(Color.WHITE);

        titles.add("Main Course");
        titles.add("Appitizeer");
        titles.add("Desserts");
        titles.add("Soup");
        final ViewPager viewPager=(ViewPager)findViewById(R.id.pager);
        final PagerAdapter pagerAdapter=new PagerAdapter(getSupportFragmentManager(),tabLayout.getTabCount(),titles);

        try{
            viewPager.setAdapter(pagerAdapter);
            tabLayout.setupWithViewPager(viewPager);
        }catch (Exception e){e.printStackTrace();}

    }
}
