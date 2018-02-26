package com.example.lcom151_two.retrofit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RetrofitInsert extends AppCompatActivity {

    Button submit;
    EditText username,password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofit_insert);

        submit=(Button)findViewById(R.id.Submit);
        username=(EditText)findViewById(R.id.username);
        password=(EditText)findViewById(R.id.password);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user=username.getText().toString();
                String pass=password.getText().toString();

                if(TextUtils.isEmpty(user)){
                    Toast.makeText(RetrofitInsert.this, "Please enter username", Toast.LENGTH_SHORT).show();
                }else if (TextUtils.isEmpty(pass)){
                    Toast.makeText(RetrofitInsert.this, "Please enter password", Toast.LENGTH_SHORT).show();
                }else {
                    insertData(user,pass);
                }
            }
        });
    }

    private void insertData(String username,String password){
        ApiService apiService=ApiClient.getClient().create(ApiService.class);
        Call<InsertUserResponseModel> call=apiService.insertUser(username,password);
        call.enqueue(new Callback<InsertUserResponseModel>() {
            @Override
            public void onResponse(Call<InsertUserResponseModel> call, Response<InsertUserResponseModel> response) {
                InsertUserResponseModel insertUserResponseModel=response.body();
                if(insertUserResponseModel.getStatus()==1){
                    Toast.makeText(RetrofitInsert.this, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(RetrofitInsert.this, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<InsertUserResponseModel> call, Throwable t) {
                Toast.makeText(RetrofitInsert.this, "Error "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
