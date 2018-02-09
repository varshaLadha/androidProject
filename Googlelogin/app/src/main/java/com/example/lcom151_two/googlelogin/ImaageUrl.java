package com.example.lcom151_two.googlelogin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class ImaageUrl extends AppCompatActivity {

    ImageView iview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imaage_url);

        iview=(ImageView)findViewById(R.id.iview1);

        Picasso.with(this)
                .load("https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photoreference=CmRaAAAANxgfkOmJqvRIUfcnoQ7AZkVMWZbD73ityxFBqtpBoo-GNbjLjpvBZfaqDIGlvubbPaMeqBT6g_bOGCWbnyomEPNErX-txgvnOEM-AwIWfWROYLpSBSz4uY3dEX8OQupyEhDwG2eYzX1NndQbbbMwh4wtGhQvvbb32uFznlLzesEGqkQ8CG-FyQ&key=AIzaSyBBGWb127YoPrccqFOlpSglAlVTIBgOvHQ")
                .into(iview);

    }
}
