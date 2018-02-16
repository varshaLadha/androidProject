package com.example.lcom151_two.myapplication;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class design1 extends AppCompatActivity {

    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_design1);

        ActionBar ab=getSupportActionBar();
        ab.hide();

        imageView=(ImageView)findViewById(R.id.imageView5);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(design1.this,design2.class);
                startActivity(i);
            }
        });

    }
}
