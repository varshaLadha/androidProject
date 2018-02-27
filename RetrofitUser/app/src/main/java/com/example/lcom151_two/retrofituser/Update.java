package com.example.lcom151_two.retrofituser;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telecom.Call;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Callback;
import retrofit2.Response;

public class Update extends BaseClass {

    EditText fname,lname,password,mobile;
    Button update;
    TextView email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        fname=(EditText)findViewById(R.id.fname);
        lname=(EditText)findViewById(R.id.lname);
        mobile=(EditText)findViewById(R.id.mobile);
        email=(TextView)findViewById(R.id.email);
        Intent i=getIntent();
        String name=i.getStringExtra("name");

        Toast.makeText(this, name, Toast.LENGTH_SHORT).show();

        getData(name);
    }

    public void getData(String name){
        retrofit2.Call<GetUsersResponseModel> call=apiInterface.getUser(name);
        call.enqueue(new Callback<GetUsersResponseModel>() {
            @Override
            public void onResponse(retrofit2.Call<GetUsersResponseModel> call, Response<GetUsersResponseModel> response) {
                List<Row> getUserResponseModel=response.body().getRows();
                fname.setText(getUserResponseModel.get(0).getFirstname());
                lname.setText(getUserResponseModel.get(0).getLastname());
                email.setText(getUserResponseModel.get(0).getEmail());
                mobile.setText(getUserResponseModel.get(0).getMobile());
            }

            @Override
            public void onFailure(retrofit2.Call<GetUsersResponseModel> call, Throwable t) {
                Toast.makeText(Update.this, "error"+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void updateData(String fname,String lname,String mobile,String password,String name){

    }
}
