package com.example.lcom151_two.crudeoperations;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    Button add,sort;
    GridView gs;
    ArrayList emailList;
    ArrayAdapter adapter;
    PopupWindow window;
    RelativeLayout relativeLayout;
    View customView;
    int i=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        relativeLayout=(RelativeLayout)findViewById(R.id.mainlayout);

        add=(Button)findViewById(R.id.addRecord);
        sort=(Button)findViewById(R.id.sort);
        gs=(GridView)findViewById(R.id.gridView);
        emailList=new ArrayList();

        display();

        sort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Collections.sort(emailList, new Comparator<String>() {
                    @Override
                    public int compare(String o1, String o2) {
                        if(i==0) {
                            return o1.compareToIgnoreCase(o2);
                        }
                        else {
                            return o2.compareToIgnoreCase(o1);
                        }
                    }
                });
                if(i==0){
                    i=1;
                    sort.setText("Sort Descending");
                }else {
                    i=0;
                    sort.setText("Sort Ascending");
                }
                adapter.notifyDataSetChanged();
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater=(LayoutInflater)MainActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                customView=inflater.inflate(R.layout.activity_add_data,null);
                window=new PopupWindow(customView, ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT,true);

                if(Build.VERSION.SDK_INT>=21){
                    window.setElevation(5.0f);
                }

                final Button addData=(Button)customView.findViewById(R.id.add);
                final ImageButton close=(ImageButton)customView.findViewById(R.id.close1);

                close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        window.dismiss();
                    }
                });

                addData.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AddData();
                    }
                });

                window.showAtLocation(relativeLayout, Gravity.CENTER,0,0);
            }
        });
    }

    public void display(){
        StringRequest sr=new StringRequest(Request.Method.GET, "http://10.0.2.2:2000/display", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    JSONArray array=jsonObject.getJSONArray("data");

                    for(int i=array.length()-1;i>-1;i--){
                        JSONObject jobj=array.getJSONObject(i);
                        emailList.add(jobj.getString("firstName"));
                    }

                    adapter=new ArrayAdapter(MainActivity.this,android.R.layout.simple_list_item_1,emailList);
                    gs.setAdapter(adapter);

                    gs.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                        @Override
                        public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                            PopupMenu menu=new PopupMenu(getApplicationContext(),view);
                            menu.getMenuInflater().inflate(R.menu.menu,menu.getMenu());

                            menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                                @Override
                                public boolean onMenuItemClick(MenuItem item) {
                                    switch (item.getItemId()){
                                        case R.id.view:
                                            LayoutInflater inflater=(LayoutInflater)MainActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                            customView=inflater.inflate(R.layout.display,null);
                                            window=new PopupWindow(customView, ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT,true);

                                            if(Build.VERSION.SDK_INT>=21){
                                                window.setElevation(5.0f);
                                            }

                                            window.showAtLocation(relativeLayout, Gravity.BOTTOM,0,0);
                                            dataManipulate(position,(String) emailList.get(position),"findOne");
                                            break;
                                        case R.id.update:
                                            LayoutInflater inflater1=(LayoutInflater)MainActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                            customView=inflater1.inflate(R.layout.activity_update,null);
                                            window=new PopupWindow(customView,ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT,true);

                                            if(Build.VERSION.SDK_INT>=21){
                                                window.setElevation(5.0f);
                                            }

                                            window.showAtLocation(relativeLayout, Gravity.CENTER,0,0);
                                            update(position,(String) emailList.get(position));
                                            break;
                                        case R.id.delete:
                                            dataManipulate(position,(String) emailList.get(position),"delete");
                                            break;
                                    }
                                    return false;
                                }
                            });
                            menu.show();
                            return false;
                        }
                    });

                }catch (Exception e){
                    Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Error from main: "+ error.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue rq= Volley.newRequestQueue(MainActivity.this);
        rq.add(sr);
    }

    public void dataManipulate(final int pos, final String name, final String url){

        StringRequest srDel=new StringRequest(Request.Method.POST, "http://10.0.2.2:2000/"+url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    if(url=="delete"){
                        JSONObject jobj=new JSONObject(response);
                        if(jobj.getInt("success")==1){
                            emailList.remove(pos);
                            adapter.notifyDataSetChanged();
                        }else {
                            Toast.makeText(MainActivity.this, "Couldnt delete record", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else if(url=="findOne"){
                        JSONObject jobj=new JSONObject(response);
                        if(jobj.getInt("success")==1){
                            TextView email,fname,lname,mobile;
                            ImageButton close=(ImageButton)customView.findViewById(R.id.close);
                            email=(TextView)customView.findViewById(R.id.email);
                            fname=(TextView)customView.findViewById(R.id.fname);
                            lname=(TextView)customView.findViewById(R.id.lname);
                            mobile=(TextView)customView.findViewById(R.id.mobile);
                            email.setText(jobj.getJSONObject("data").getString("email"));
                            fname.setText(jobj.getJSONObject("data").getString("firstName"));
                            lname.setText(jobj.getJSONObject("data").getString("lastName"));
                            mobile.setText(jobj.getJSONObject("data").getString("mobile"));
                            close.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    window.dismiss();
                                }
                            });
                        }
                        else {
                            Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
                        }
                    }
                }catch (Exception e){
                    Toast.makeText(MainActivity.this, "Problem "+e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Error from operation "+error.toString(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params=new HashMap<String, String>();
                params.put("firstName",name);
                return params;
            }
        };
        RequestQueue rqDel=Volley.newRequestQueue(MainActivity.this);
        rqDel.add(srDel);
    }

    public void AddData(){

        final EditText email,password,fname,lname,mobile;

        email=(EditText)customView.findViewById(R.id.email);
        password=(EditText)customView.findViewById(R.id.password);
        fname=(EditText)customView.findViewById(R.id.fname);
        lname=(EditText)customView.findViewById(R.id.lname);
        mobile=(EditText)customView.findViewById(R.id.mobile);

        StringRequest sr=new StringRequest(Request.Method.POST, "http://10.0.2.2:2000/register", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject jsonObject=new JSONObject(response);
                    if(jsonObject.getInt("success")==1){
                        emailList.add(0,fname.getText().toString());
                        adapter.notifyDataSetChanged();
                        window.dismiss();
                    }
                    else {
                        Toast.makeText(MainActivity.this, " "+jsonObject.getString("msg"), Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e){
                    Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Error : "+error.toString(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params=new HashMap<String, String>();
                params.put("email",email.getText().toString());
                params.put("password",password.getText().toString());
                params.put("firstName",fname.getText().toString());
                params.put("lastName",lname.getText().toString());
                params.put("mobile",mobile.getText().toString());
                return params;
            }
        };

        RequestQueue rq= Volley.newRequestQueue(MainActivity.this);
        rq.add(sr);
    }

    public void update(final int pos, final String name){
        final EditText email,password,fname,lname,mobile;
        Button update;
        ImageButton close=(ImageButton)customView.findViewById(R.id.close);

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                window.dismiss();
            }
        });

        email=(EditText)customView.findViewById(R.id.uemail);
        password=(EditText)customView.findViewById(R.id.upassword);
        fname=(EditText)customView.findViewById(R.id.ufname);
        lname=(EditText)customView.findViewById(R.id.ulname);
        mobile=(EditText)customView.findViewById(R.id.umobile);
        update=(Button)customView.findViewById(R.id.update);

        StringRequest sr=new StringRequest(Request.Method.POST, "http://10.0.2.2:2000/findOne", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject jsonObject=new JSONObject(response);
                    if(jsonObject.getInt("success")==1){
                        JSONObject jobj=jsonObject.getJSONObject("data");
                        email.setText(jobj.getString("email"));
                        fname.setText(jobj.getString("firstName"));
                        lname.setText(jobj.getString("lastName"));
                        mobile.setText(jobj.getString("mobile"));
                    }
                    else {
                        Toast.makeText(MainActivity.this, "Problem "+jsonObject.getString("msg"), Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e){
                    Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Error : "+error.toString(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params=new HashMap<String, String>();
                params.put("firstName",name);
                return params;
            }
        };

        RequestQueue rq= Volley.newRequestQueue(MainActivity.this);
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
                                emailList.set(pos,fname.getText().toString());
                                adapter.notifyDataSetChanged();
                                window.dismiss();
                            }
                            else {
                                Toast.makeText(MainActivity.this, "Problem "+jsonObject.getString("msg"), Toast.LENGTH_SHORT).show();
                            }
                        }catch (Exception e){
                            Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, "Error : "+error.toString(), Toast.LENGTH_SHORT).show();
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

                RequestQueue rq= Volley.newRequestQueue(MainActivity.this);
                rq.add(sr);
            }
        });
    }
}
