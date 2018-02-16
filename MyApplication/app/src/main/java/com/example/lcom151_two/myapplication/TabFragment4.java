package com.example.lcom151_two.myapplication;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.Arrays;

public class TabFragment4 extends Fragment {

    ImageAdapter adapter;
    GridView gridView;
    ArrayList<String> pname=new ArrayList<>(Arrays.asList("Item name","Item name"));
    ArrayList<String> price=new ArrayList<>(Arrays.asList("Rs 22","Rs 25"));
    ArrayList<Integer> img=new ArrayList<>(Arrays.asList(R.drawable.img,R.drawable.img1));
    public TabFragment4() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.tab_fragment4,container,false);
        gridView=(GridView)view.findViewById(R.id.grid4);
        try{
            adapter=new ImageAdapter(getContext(),pname,price,img);
            if(!adapter.isEmpty()){
                gridView.setAdapter(adapter);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return view;
    }

}
