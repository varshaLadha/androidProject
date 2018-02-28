package com.example.lcom151_two.googlelogin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.SignInButton;

public class passportGoogle extends AppCompatActivity {

    private SignInButton googleSignin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passport_google);

        googleSignin=(SignInButton)findViewById(R.id.googleSignin);

        googleSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringRequest sr=new StringRequest(Request.Method.GET, "http://10.0.2.2:2000/auth/google", new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("Error from volley",error.toString());
                        Toast.makeText(passportGoogle.this, error.getMessage()+" "+error.toString(), Toast.LENGTH_SHORT).show();
                    }
                });

                RequestQueue rq= Volley.newRequestQueue(passportGoogle.this);
                rq.add(sr);
            }
        });

    }
}
