package com.example.lcom151_two.googlelogin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class NodeApi extends AppCompatActivity {

    Button submit,register;
    EditText email,password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_node_api);

        submit=(Button)findViewById(R.id.button2);
        register=(Button)findViewById(R.id.register);
        email=(EditText)findViewById(R.id.email);
        password=(EditText)findViewById(R.id.password);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequsetCall("/login");
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequsetCall("/register");
            }
        });
    }

    public void RequsetCall(final String url){
        StringRequest sr=new StringRequest(Request.Method.POST, "http://10.0.2.2:2000"+url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    if (url == "/login") {
                        JSONObject jobj = new JSONObject(response);
                        if (jobj.getInt("success") == 1) {
                            Intent i = new Intent(NodeApi.this, jsonData.class);
                            startActivity(i);
                        } else {
                            Toast.makeText(NodeApi.this, jobj.getString("msg"), Toast.LENGTH_SHORT).show();
                        }
                        Log.i("Json object", jobj.toString());
                    }else if(url =="/register"){
                        JSONObject jobj = new JSONObject(response);
                        if (jobj.getInt("success") == 1) {
                            Toast.makeText(NodeApi.this, jobj.getString("msg"), Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(NodeApi.this, jobj.getString("msg"), Toast.LENGTH_SHORT).show();
                        }
                        Log.i("Json object", jobj.toString());
                    }
                }catch (Exception e){
                    Log.i("error",e.getMessage());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(NodeApi.this, "Error from volley"+error.toString(), Toast.LENGTH_SHORT).show();
            }
        })
        {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> param= new HashMap<String, String>();
                param.put("email",email.getText().toString());
                param.put("password",password.getText().toString());
                return param;
            }
        };

        RequestQueue rq= Volley.newRequestQueue(NodeApi.this);
        rq.add(sr);
    }
}
