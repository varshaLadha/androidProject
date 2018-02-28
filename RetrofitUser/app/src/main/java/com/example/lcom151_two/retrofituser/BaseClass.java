package com.example.lcom151_two.retrofituser;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

public class BaseClass extends AppCompatActivity {
    ApiInterface apiInterface;
    Bitmap bitmap;
    ArrayList names;
    ArrayAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        apiInterface=ApiClient.getClient().create(ApiInterface.class);
        names=new ArrayList();
    }
}
