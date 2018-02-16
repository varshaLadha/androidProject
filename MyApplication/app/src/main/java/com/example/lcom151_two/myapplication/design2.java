package com.example.lcom151_two.myapplication;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicReferenceArray;

public class design2 extends AppCompatActivity {

    ArrayList<String> hotelAddress=new ArrayList<String>(Arrays.asList("Adajan,Surat,Gujarat","Vesu,Surat,Gujarat","Citylight,Surat,Gujarat"));
    ArrayList<String> hotelDistannce=new ArrayList<String>(Arrays.asList("2km away","5km away","3km away"));
    ArrayList<String> hotelName =new ArrayList<String>(Arrays.asList("Fancy Pie Pizza","Night Reastaurant","Pie Pizza"));
    ArrayList<String> hotelInfo =new ArrayList<String>(Arrays.asList("Pizza | Italian Food 118 Plccadilly","Drinks | Italian Food 118 Plccadilly","Pizza | Italian Food 118 Plccadilly"));
    ArrayList<Integer> hotelImages =new ArrayList<Integer>(Arrays.asList(R.drawable.banner1,R.drawable.banner2,R.drawable.banner3));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_design2);

        try{
            ActionBar ab=getSupportActionBar();
            ab.hide();

            RecyclerView recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
            LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getApplicationContext());
            recyclerView.setLayoutManager(linearLayoutManager);

            CustomListAdapter cld=new CustomListAdapter(design2.this, hotelName, hotelInfo, hotelImages, new CustomListAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view,int pos) {
                    //Toast.makeText(design2.this, pos+"", Toast.LENGTH_SHORT).show();
                    String name= hotelName.get(pos);
                    String addr=hotelAddress.get(pos);
                    String dis=hotelDistannce.get(pos);
                    //Toast.makeText(design2.this, name+" "+addr+" "+dis, Toast.LENGTH_SHORT).show();
                    Intent i=new Intent(design2.this,HotelApp.class);
                    i.putExtra("name",name);
                    i.putExtra("addr",addr);
                    i.putExtra("distance",dis);
                    startActivity(i);
                }
            });
            recyclerView.setAdapter(cld);
            cld.notifyDataSetChanged();
        }catch (Exception e){e.printStackTrace();}

    }
}
