package com.example.lcom151_two.googlelogin;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

// image fetchig key = AIzaSyBBGWb127YoPrccqFOlpSglAlVTIBgOvHQ
//place info = https://maps.googleapis.com/maps/api/place/nearbysearch/json?location="+latitude+","+longitude+"&radius=5000&keyword=restaurants&key=AIzaSyBBGWb127YoPrccqFOlpSglAlVTIBgOvHQ
// image getting url = https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photoreference=CmRaAAAANxgfkOmJqvRIUfcnoQ7AZkVMWZbD73ityxFBqtpBoo-GNbjLjpvBZfaqDIGlvubbPaMeqBT6g_bOGCWbnyomEPNErX-txgvnOEM-AwIWfWROYLpSBSz4uY3dEX8OQupyEhDwG2eYzX1NndQbbbMwh4wtGhQvvbb32uFznlLzesEGqkQ8CG-FyQ&key=AIzaSyBBGWb127YoPrccqFOlpSglAlVTIBgOvHQ
// getting restaurant info - https://maps.googleapis.com/maps/api/place/details/json?placeid=ChIJqaQh949N4DsRhp0NHviKoV4&key=AIzaSyBBGWb127YoPrccqFOlpSglAlVTIBgOvHQ
// latitude = 21.14711  longitude = 72.760305

public class jsonData extends AppCompatActivity {

    //TextView txtData;
    ArrayList hotelName,hotelAddress,url,urlCall;
    GridView gs;
    String urlRef;
    double latitude,longitude;
    LocationManager locationManager;
    LocationListener locationListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_json_data);

        //txtData=(TextView)findViewById(R.id.textView2);

        locationManager=(LocationManager)getSystemService(Context.LOCATION_SERVICE);
        locationListener=new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                latitude=location.getLatitude();
                longitude=location.getLongitude();
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) { }

            @Override
            public void onProviderEnabled(String provider) { }

            @Override
            public void onProviderDisabled(String provider) { }
        };

        if(Build.VERSION.SDK_INT<23){
            if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED){
                locationManager=(LocationManager)getSystemService(Context.LOCATION_SERVICE);
            }
        }else{
            if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(this,new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},1);
            }else{
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,locationListener);

                Location location=locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

                if(location!=null){

                    latitude=location.getLatitude();
                    longitude=location.getLongitude();

                }
            }
        }

        hotelName=new ArrayList();
        hotelAddress=new ArrayList();
        url=new ArrayList();
        urlCall=new ArrayList();
        gs=(GridView) findViewById(R.id.gs);

        StringRequest sr=new StringRequest(Request.Method.GET, "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location="+latitude+","+longitude+"&radius=5000&keyword=restaurants&key=AIzaSyBBGWb127YoPrccqFOlpSglAlVTIBgOvHQ", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject jobj=new JSONObject(response);
                    JSONArray jaaray=jobj.getJSONArray("results");

                    for(int i=0;i<jaaray.length();i++){
                        JSONObject data=jaaray.getJSONObject(i);

                        try {
                            JSONArray dataArray=data.getJSONArray("photos");
                            JSONObject urlObj = dataArray.getJSONObject(0);
                            urlRef = urlObj.getString("photo_reference");
                            url.add("https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photoreference="+urlRef+"&key=AIzaSyBBGWb127YoPrccqFOlpSglAlVTIBgOvHQ");
                        }catch (Exception e){
                            url.add("https://i0.wp.com/robtims.com/wp-content/uploads/2016/01/empty.jpg?resize=500%2C333&ssl=1");
                        }

                        String name=data.getString("name");
                        hotelName.add(name);
                        String address=data.getString("vicinity");
                        hotelAddress.add(address);

                        urlCall.add("https://maps.googleapis.com/maps/api/place/details/json?placeid="+data.getString("place_id")+"&key=AIzaSyBBGWb127YoPrccqFOlpSglAlVTIBgOvHQ");

                    }

                    CustomPageAdapter adapter=new CustomPageAdapter(jsonData.this,hotelName,hotelAddress,url,urlCall);
                    gs.setAdapter(adapter);

                }catch (Exception e){
                    Toast.makeText(jsonData.this, "Error : "+e.getStackTrace(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(jsonData.this, "Volley Error "+error.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue rq= Volley.newRequestQueue(this);
        rq.add(sr);
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
            if(ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED){
                locationManager=(LocationManager)getSystemService(Context.LOCATION_SERVICE);
            }
        }
    }
}
