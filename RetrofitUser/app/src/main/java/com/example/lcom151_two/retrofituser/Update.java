package com.example.lcom151_two.retrofituser;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telecom.Call;
import android.text.TextUtils;
import android.view.View;
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
        password=(EditText)findViewById(R.id.password);
        email=(TextView)findViewById(R.id.email);
        update=(Button)findViewById(R.id.update);
        Intent i=getIntent();
        final String name=i.getStringExtra("name");

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fn=fname.getText().toString();
                String ln=lname.getText().toString();
                String pass=password.getText().toString();
                String mno=mobile.getText().toString();
                if(TextUtils.isEmpty(fn) || TextUtils.isEmpty(ln) || TextUtils.isEmpty(pass) || TextUtils.isEmpty(mno)){
                    Toast.makeText(Update.this, "Please enter all the details", Toast.LENGTH_SHORT).show();
                }else {
                    updateData(fn,ln,mno,pass,name);
                }
            }
        });

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

    public void updateData(final String fname, String lname, String mobile, String password, String name){
        retrofit2.Call<DeleteUserResponseModel> call=apiInterface.updateUser(name,lname,mobile,fname,password);
        call.enqueue(new Callback<DeleteUserResponseModel>() {
            @Override
            public void onResponse(retrofit2.Call<DeleteUserResponseModel> call, Response<DeleteUserResponseModel> response) {
                DeleteUserResponseModel deleteUserResponseModel=response.body();
                if(deleteUserResponseModel.getSuccess()==1){
                    Intent i=new Intent(Update.this,display.class);
                    startActivity(i);
                    finish();
                    Toast.makeText(Update.this, deleteUserResponseModel.getMessage(), Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(Update.this, deleteUserResponseModel.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(retrofit2.Call<DeleteUserResponseModel> call, Throwable t) {
            }
        });
    }
}
