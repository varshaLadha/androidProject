package com.example.lcom151_two.googlelogin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.Arrays;

public class Main2Activity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        GridView gridView;
        ArrayList arrayList;

        gridView=(GridView)findViewById(R.id.gridView);
        arrayList=new ArrayList(Arrays.asList(1,2,3,4,5,6,7,8,9,10));

        ArrayAdapter adapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1,arrayList);
        gridView.setAdapter(adapter);

    }
}
