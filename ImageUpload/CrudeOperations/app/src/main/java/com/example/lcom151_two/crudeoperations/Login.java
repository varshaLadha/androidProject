package com.example.lcom151_two.crudeoperations;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {

    EditText email,password;
    Button login;
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email=(EditText)findViewById(R.id.email);
        password=(EditText)findViewById(R.id.password);

        sp=getSharedPreferences("login",MODE_PRIVATE);

        if(sp.contains("email") && sp.contains("password")){
            startActivity(new Intent(Login.this, MainActivity.class));
            finish();
        }

        login=(Button)findViewById(R.id.login);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringRequest sr=new StringRequest(Request.Method.POST, "http://10.0.2.2:2000/login", new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject=new JSONObject(response);
                            if(jsonObject.getInt("success")==1){
                                loginCheck();
                            }else {
                                Toast.makeText(Login.this, jsonObject.getString("msg"), Toast.LENGTH_SHORT).show();
                            }
                        }catch (Exception e){
                            Toast.makeText(Login.this, "Problem "+e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Login.this, "Error "+error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String,String> params=new HashMap<String, String>();
                        params.put("email",email.getText().toString());
                        params.put("password",password.getText().toString());
                        return params;
                    }
                };

                RequestQueue rq= Volley.newRequestQueue(Login.this);
                rq.add(sr);
            }
        });
    }

    public void loginCheck(){
        SharedPreferences.Editor editor=sp.edit();
        editor.putString("email",email.getText().toString());
        editor.putString("password",password.getText().toString());
        editor.commit();

        startActivity(new Intent(Login.this, MainActivity.class));
        finish();
    }
}
