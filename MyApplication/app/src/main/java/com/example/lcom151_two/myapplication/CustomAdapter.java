package com.example.lcom151_two.myapplication;

import android.content.Context;
import android.provider.ContactsContract;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by lcom151-two on 1/19/2018.
 */

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    ArrayList pname;
    ArrayList price;
    ArrayList img;
    Context context;

    public CustomAdapter(Context context,ArrayList pname,ArrayList price,ArrayList img){
        this.context=context;
        this.pname=pname;
        this.price=price;
        this.img=img;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.display,parent,false);
        MyViewHolder vh=new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.price.setText(price.get(position).toString());
        holder.name.setText(pname.get(position).toString());
        holder.img.setImageResource((Integer) img.get(position));
    }

    @Override
    public int getItemCount() {
        return pname.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name,price;
        ImageView img;
        public MyViewHolder(View itemView) {
            super(itemView);
            name=(TextView)itemView.findViewById(R.id.txtname);
            price=(TextView)itemView.findViewById(R.id.txtprice);
            img=(ImageView)itemView.findViewById(R.id.imageView10);
        }
    }
}
