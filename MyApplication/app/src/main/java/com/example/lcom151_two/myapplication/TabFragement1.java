package com.example.lcom151_two.myapplication;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.Arrays;

public class TabFragement1 extends Fragment {
    GridView gridview;
    ArrayList<String> pname=new ArrayList<>(Arrays.asList("Item Name","Item Name","Item Name","Item Name","Item Name","Item Name"));
    ArrayList<String> price=new ArrayList<>(Arrays.asList("Rs 12","Rs 18","Rs 22","Rs 28","Rs 15","Rs 23"));
    ArrayList<Integer> img=new ArrayList<>(Arrays.asList(R.drawable.img1,R.drawable.img,R.drawable.img,R.drawable.img1,R.drawable.img1,R.drawable.img));
    ImageAdapter adapter;
    public TabFragement1() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.tab_fragement1,container,false);
        gridview = (GridView) view.findViewById(R.id.gs);
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
