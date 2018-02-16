package com.example.lcom151_two.myapplication;

import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class HotelApp extends AppCompatActivity {

    TextView name,address,distance;
    private TabLayout tabLayout;
    ArrayList<String> titles=new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel_app);

        name=(TextView)findViewById(R.id.name);
        address=(TextView)findViewById(R.id.address);
        distance=(TextView)findViewById(R.id.distance);
        Intent i=getIntent();
        String name1=i.getExtras().getString("name");
        String addr=i.getExtras().getString("addr");
        String dis=i.getExtras().getString("distance");

        name.setText(name1);
        address.setText(addr);
        distance.setText(dis);

        tabLayout = (TabLayout) findViewById(R.id.tabLayout1);

        ActionBar ab=getSupportActionBar();
        ab.hide();

        LinearLayout ll = (LinearLayout)findViewById(R.id.linearlayout);
        ll.setBackgroundColor(Color.WHITE);

        titles.add("Menu");
        titles.add("About");
        titles.add("Review");
        final ViewPager viewPager=(ViewPager)findViewById(R.id.pager1);
        final HotelAdapter pagerAdapter=new HotelAdapter(getSupportFragmentManager(),tabLayout.getTabCount(),titles);

        try{
            viewPager.setAdapter(pagerAdapter);
            tabLayout.setupWithViewPager(viewPager);
        }catch (Exception e){e.printStackTrace();}
    }
}
