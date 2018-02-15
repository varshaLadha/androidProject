package com.example.lcom151_two.crudeoperations;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Update extends AppCompatActivity {

    EditText email,password,fname,lname,mobile;
    Button update;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        Intent i=getIntent();
        final String name=i.getStringExtra("name");


        email=(EditText)findViewById(R.id.uemail);
        password=(EditText)findViewById(R.id.upassword);
        fname=(EditText)findViewById(R.id.ufname);
        lname=(EditText)findViewById(R.id.ulname);
        mobile=(EditText)findViewById(R.id.umobile);
        update=(Button)findViewById(R.id.update);

        StringRequest sr=new StringRequest(Request.Method.POST, "http://10.0.2.2:2000/findOne", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject jsonObject=new JSONObject(response);
                    if(jsonObject.getInt("success")==1){
                        JSONObject jobj=jsonObject.getJSONObject("data");
                        email.setText(jobj.getString("email"));
                        //password.setText(jobj.getString("email"));
                        fname.setText(jobj.getString("firstName"));
                        lname.setText(jobj.getString("lastName"));
                        mobile.setText(jobj.getString("mobile"));
                    }
                    else {
                        Toast.makeText(Update.this, "Problem "+jsonObject.getString("msg"), Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e){
                    Toast.makeText(Update.this, e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Update.this, "Error : "+error.toString(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params=new HashMap<String, String>();
                params.put("firstName",name);
                return params;
            }
        };

        RequestQueue rq= Volley.newRequestQueue(Update.this);
        rq.add(sr);

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringRequest sr=new StringRequest(Request.Method.POST, "http://10.0.2.2:2000/update", new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject=new JSONObject(response);
                            if(jsonObject.getInt("success")==1){
                                Intent i=new Intent(Update.this,MainActivity.class);
                                startActivity(i);
                                finish();
                            }
                            else {
                                Toast.makeText(Update.this, "Problem "+jsonObject.getString("msg"), Toast.LENGTH_SHORT).show();
                            }
                        }catch (Exception e){
                            Toast.makeText(Update.this, e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Update.this, "Error : "+error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String,String> params=new HashMap<String, String>();

                        params.put("fname",name);
                        params.put("email",email.getText().toString());
                        params.put("password",password.getText().toString());
                        params.put("firstName",fname.getText().toString());
                        params.put("lastName",lname.getText().toString());
                        params.put("mobile",mobile.getText().toString());
                        return params;
                    }
                };

                RequestQueue rq= Volley.newRequestQueue(Update.this);
                rq.add(sr);
            }
        });
    }
}
