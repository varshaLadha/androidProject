package com.example.lcom151_two.myapplication;

import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.Arrays;

public class design3 extends AppCompatActivity {

    ArrayList pname=new ArrayList<>(Arrays.asList("Item Name","Item Name","Item Name","Item Name","Item Name","Item Name"));
    ArrayList price=new ArrayList<>(Arrays.asList("Rs 12","Rs 18","Rs 22","Rs 28","Rs 15","Rs 23"));
    ArrayList img=new ArrayList<>(Arrays.asList(R.drawable.img1,R.drawable.img,R.drawable.img,R.drawable.img1,R.drawable.img1,R.drawable.img));


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_design3);

        ActionBar ab=getSupportActionBar();
        ab.hide();

        LinearLayout ll = (LinearLayout)findViewById(R.id.linearlayout);
        ll.setBackgroundColor(Color.WHITE);

        LinearLayout ll1 = (LinearLayout)findViewById(R.id.ll1);
        ll1.setBackgroundColor(Color.WHITE);

        RelativeLayout rl=(RelativeLayout)findViewById(R.id.relativelayout);
        rl.setBackgroundColor(Color.rgb(220,220,220));

        GridView gridview = (GridView) findViewById(R.id.gs);
        ImageAdapter adapter=new ImageAdapter(design3.this,pname,price,img);
        gridview.setAdapter(adapter);

    }
}
