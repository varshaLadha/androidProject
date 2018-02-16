package com.example.lcom151_two.myapplication;

import android.content.Context;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ImageAdapter extends BaseAdapter {
    private Context mContext;
    ArrayList<String> pname;
    ArrayList<String> price;
    ArrayList<Integer> img;

    public ImageAdapter(Context mContext, ArrayList<String> pname, ArrayList<String> price, ArrayList<Integer> img) {
        this.mContext = mContext;
        this.pname = pname;
        this.price = price;
        this.img = img;
    }

    public int getCount() {
        return pname.size();
    }

    public Object getItem(int position) {
        return 0;
    }

    public long getItemId(int position) {
        return 0;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView name, price1;
        ImageView img1;
        try{
            convertView=LayoutInflater.from(mContext).inflate(R.layout.display,null);
            name=convertView.findViewById(R.id.txtname);
            price1=convertView.findViewById(R.id.txtprice);
            img1=convertView.findViewById(R.id.imageView10);

            name.setText(pname.get(position));
            price1.setText(price.get(position));
            img1.setImageResource(img.get(position));
            return convertView;

        }catch (Exception e){e.printStackTrace();}

        return convertView;
    }
}