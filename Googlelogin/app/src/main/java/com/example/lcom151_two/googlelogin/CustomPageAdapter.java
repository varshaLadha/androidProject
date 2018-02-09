package com.example.lcom151_two.googlelogin;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class CustomPageAdapter extends BaseAdapter {

    private Context context;
    ArrayList<String> name;
    ArrayList<String> address;
    ArrayList<String> url;
    ArrayList<String> website;

    public CustomPageAdapter(Context context,ArrayList<String> name,ArrayList<String> address,ArrayList<String> url,ArrayList<String> website){
        this.context=context;
        this.name=name;
        this.address=address;
        this.url=url;
        this.website=website;
    }
    @Override
    public int getCount() {
        return name.size();
    }

    @Override
    public Object getItem(int position) {
        return 0;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ImageView imageView;
        TextView hname,haddress;

        try{
            convertView= LayoutInflater.from(context).inflate(R.layout.design,null);
            imageView=convertView.findViewById(R.id.imageView);
            hname=convertView.findViewById(R.id.textView4);
            haddress=convertView.findViewById(R.id.textView5);

            hname.setText(name.get(position));
            haddress.setText(address.get(position));
            Picasso.with(context)
                    .load(url.get(position))
                    .into(imageView);

            StringRequest sr1=new StringRequest(Request.Method.GET, website.get(position), new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        final JSONObject jobj1=new JSONObject(response);
                        if(jobj1.getJSONObject("result").has("website")){
                            imageView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent();
                                    intent.setAction(Intent.ACTION_VIEW);
                                    intent.addCategory(Intent.CATEGORY_BROWSABLE);
                                    try {
                                        intent.setData(Uri.parse(jobj1.getJSONObject("result").getString("website")));
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    context.startActivity(intent);
                                }
                            });
                        }
                        else{
                            imageView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent();
                                    intent.setAction(Intent.ACTION_VIEW);
                                    intent.addCategory(Intent.CATEGORY_BROWSABLE);
                                    try {
                                        intent.setData(Uri.parse(jobj1.getJSONObject("result").getString("url")));
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    context.startActivity(intent);
                                }
                            });
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.i("Error from fetch url",error.toString());
                }
            });

            RequestQueue rq1= Volley.newRequestQueue(context);
            rq1.add(sr1);

            return convertView;
        }catch (Exception e){
            Log.i("Error in Adapter: ",e.getStackTrace().toString());
        }
        return convertView;
    }
}
