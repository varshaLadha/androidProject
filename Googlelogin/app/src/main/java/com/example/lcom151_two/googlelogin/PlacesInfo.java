package com.example.lcom151_two.googlelogin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class PlacesInfo extends AppCompatActivity {

    Button restaurant,hotel,banquet,mall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_places_info);
    }

    public void searchPlace(View v){
        String place="";
        switch (v.getId()){
            case R.id.restaurants:
                place="restaurants";
                //Log.i("restaurant","restaurant clicked");
                break;
            case  R.id.hotels:
                place="hotel";
                //Log.i("hotels","hotels clicked");
                break;
            case  R.id.mall:
                place="mall";
                //Log.i("malls","malls clicked");
                break;
            case R.id.banquet:
                place="banquet";
                //Log.i("banquet","banquet clicked");
                break;
        }
        Intent i=new Intent(PlacesInfo.this,jsonData.class);
        i.putExtra("place",place);
        startActivity(i);
        //Log.i("msg","outside catch = "+place);
    }
}
