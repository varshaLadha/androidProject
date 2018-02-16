package com.example.lcom151_two.myapplication;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.Arrays;

public class TabFragment2 extends Fragment {

    GridView gridView;
    ArrayList<String> pname=new ArrayList<>(Arrays.asList("Item Name","Item Name"));
    ArrayList<String> price=new ArrayList<>(Arrays.asList("Rs 12","Rs 18"));
    ArrayList<Integer> img=new ArrayList<>(Arrays.asList(R.drawable.img,R.drawable.img));
    ImageAdapter adapter;

    public TabFragment2() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.tab_fragment2,container,false);
        gridView = (GridView) view.findViewById(R.id.grid2);
        try{
            adapter=new ImageAdapter(getContext(),pname,price,img);
            if (!adapter.isEmpty()) {
                gridView.setAdapter(adapter);
            }else{
                Log.e("TabFragment 1", "onCreateView: Something Wriong" );
            }
        }catch (Exception e){e.printStackTrace();}

        return view;
    }

}
