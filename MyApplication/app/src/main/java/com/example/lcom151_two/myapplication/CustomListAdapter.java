package com.example.lcom151_two.myapplication;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;

public class CustomListAdapter extends RecyclerView.Adapter<CustomListAdapter.MyViewHolder> {

    ArrayList<String> hotelName;
    ArrayList<String> hotelInfo;
    ArrayList<Integer> hotelImage;
    Context context;
    private final OnItemClickListener listener;

    public interface OnItemClickListener{
        void onItemClick(View view,int pos);
    }

    public CustomListAdapter(Context context, ArrayList<String> hotelName, ArrayList<String> hotelInfo, ArrayList<Integer> hotelImage,OnItemClickListener listener) {
        this.context=context;
        this.hotelName=hotelName;
        this.hotelInfo=hotelInfo;
        this.hotelImage=hotelImage;
        this.listener=listener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v=LayoutInflater.from(parent.getContext()).inflate(R.layout.rowlayout,parent,false);
        MyViewHolder vh=new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.name.setText(hotelName.get(position).toString());
        holder.info.setText(hotelInfo.get(position).toString());
        holder.image.setImageResource((Integer) hotelImage.get(position));
        holder.shadow.setImageResource(R.drawable.shadow);

    }

    @Override
    public int getItemCount() {
        return hotelName.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        // init the item view's
        TextView name,info;
        ImageView image,shadow;
        public MyViewHolder(final View itemView) {
            super(itemView);
            // get the reference of item view's
            name = (TextView) itemView.findViewById(R.id.textView4);
            info = (TextView) itemView.findViewById(R.id.textView5);
            image = (ImageView) itemView.findViewById(R.id.imageView6);
            shadow = (ImageView) itemView.findViewById(R.id.imageView8);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(view,getPosition());
                }
            });
        }
    }
}
