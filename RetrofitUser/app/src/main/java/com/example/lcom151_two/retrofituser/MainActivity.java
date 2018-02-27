package com.example.lcom151_two.retrofituser;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends BaseClass {

    Button submit,image,getAll;
    EditText fname,lname,email,password,mobile;
    String sampleFile="";
    ImageView iview;
    int PICK_IMAGE_REQUEST = 111;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        submit=(Button)findViewById(R.id.add);
        image=(Button)findViewById(R.id.image);
        getAll=(Button)findViewById(R.id.getUsers);
        fname=(EditText)findViewById(R.id.fname);
        lname=(EditText)findViewById(R.id.lname);
        email=(EditText)findViewById(R.id.email);
        password=(EditText)findViewById(R.id.password);
        mobile=(EditText)findViewById(R.id.mobile);
        iview=(ImageView)findViewById(R.id.imageView);
        bitmap= BitmapFactory.decodeResource(getResources(),R.drawable.nature);

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_PICK);
                startActivityForResult(Intent.createChooser(intent,"Select Image"),PICK_IMAGE_REQUEST);
            }
        });

        getAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MainActivity.this,display.class);
                startActivity(i);
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String first=fname.getText().toString();
                String last=lname.getText().toString();
                String mail=email.getText().toString();
                String pass=password.getText().toString();
                String mno=mobile.getText().toString();
                if(TextUtils.isEmpty(first) || TextUtils.isEmpty(last) || TextUtils.isEmpty(mail) || TextUtils.isEmpty(pass) || TextUtils.isEmpty(mno)){
                    Toast.makeText(MainActivity.this, "Please enter all the details", Toast.LENGTH_SHORT).show();
                }else {
                    insertData(first,last,mail,pass,mno);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data!=null && data.getData()!=null){
            Uri filePath=data.getData();
            try{
                bitmap= MediaStore.Images.Media.getBitmap(getContentResolver(),filePath);
                iview.setImageBitmap(bitmap);
            }catch (Exception e){
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void insertData(String fname,String lname,String email,String password,String mobile){
        ByteArrayOutputStream baos=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,baos);
        byte[] imageBytes=baos.toByteArray();
        sampleFile= Base64.encodeToString(imageBytes,Base64.DEFAULT);

        Log.i("Data","Fname "+fname+"\nLname : "+lname+"\nEmail : "+email+"\nPassword : "+password+"\n Mobile : "+mobile+"\nSample File : "+sampleFile );

        Call<InsertUserResponseModel> call=apiInterface.user(fname,lname,email,password,mobile,sampleFile);
        call.enqueue(new Callback<InsertUserResponseModel>() {
            @Override
            public void onResponse(Call<InsertUserResponseModel> call, Response<InsertUserResponseModel> response) {
                InsertUserResponseModel insertUserResponseModel=response.body();
                if(insertUserResponseModel.getStatus()==1){
                    Toast.makeText(MainActivity.this, insertUserResponseModel.getMsg(), Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(MainActivity.this, insertUserResponseModel.getMsg(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<InsertUserResponseModel> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Problem occured : "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
