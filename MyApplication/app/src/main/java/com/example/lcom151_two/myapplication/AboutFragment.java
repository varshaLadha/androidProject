package com.example.lcom151_two.myapplication;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

//google map api key = AIzaSyANjqFoACIAlBODAqHvOsKUWduKBeJ-tqk

public class AboutFragment extends Fragment {

    MapView mapView;
    private GoogleMap googleMap;

    public AboutFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View view=inflater.inflate(R.layout.fragment_about,container,false);
        mapView=(MapView)view.findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);

        mapView.onResume();

        try{
            MapsInitializer.initialize(getActivity().getApplicationContext());
        }catch (Exception e){e.printStackTrace();}

        mapView.getMapAsync(new OnMapReadyCallback() {
            @SuppressLint("MissingPermission")
            @Override
            public void onMapReady(GoogleMap map) {
                googleMap=map;
                googleMap.setMyLocationEnabled(true);
                LatLng loc=new LatLng(-34,151);
                googleMap.addMarker(new MarkerOptions().position(loc).title("Hotel").snippet("Description"));

                CameraPosition cpos=new CameraPosition.Builder().target(loc).zoom(12).build();
                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cpos));
            }
        });


        return view;
    }

}
