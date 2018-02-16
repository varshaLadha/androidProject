package com.example.lcom151_two.myapplication;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.Arrays;

public class TabFragment3 extends Fragment {

    ImageAdapter adapter;
    GridView gridview;
    ArrayList<String> pname=new ArrayList<>(Arrays.asList("Item name","Item name"));
    ArrayList<String> price=new ArrayList<>(Arrays.asList("Rs 30","Rs25"));
    ArrayList<Integer> img=new ArrayList<>(Arrays.asList(R.drawable.img1,R.drawable.img1));

    public TabFragment3() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.tab_fragment3,container,false);
        gridview = (GridView) view.findViewById(R.id.grid3);
        try{
            adapter=new ImageAdapter(getContext(),pname,price,img);
            if (!adapter.isEmpty()) {
                gridview.setAdapter(adapter);
            }else{
                Log.e("TabFragment 1", "onCreateView: Something Wriong" );
            }
        }catch (Exception e){e.printStackTrace();}

        return view;
    }

}
