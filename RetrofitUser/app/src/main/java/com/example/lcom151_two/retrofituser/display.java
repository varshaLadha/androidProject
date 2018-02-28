package com.example.lcom151_two.retrofituser;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialog;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.squareup.picasso.Picasso;

import io.fabric.sdk.android.Fabric;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class display extends BaseClass {

    GridView gridView;
    TextView name,email,mobile;
    ImageView imageView;
    ImageButton close;
    Button insert;
    LinearLayout displayLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_display);

        gridView=(GridView)findViewById(R.id.gridview);
        insert=(Button)findViewById(R.id.insert);
        displayLayout=(LinearLayout)findViewById(R.id.displayLayout);

        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(display.this,MainActivity.class);
                startActivity(i);
                finish();
            }
        });

        display();
    }

    public void display(){
        Call<GetUsersResponseModel> call=apiInterface.getUsers();
        call.enqueue(new Callback<GetUsersResponseModel>() {
            @Override
            public void onResponse(Call<GetUsersResponseModel> call, Response<GetUsersResponseModel> response) {
                if (response.body().getSuccess() == 1) {
                    final List<Row> getUserResponseModel = response.body().getRows();
                    for (int i = 0; i < getUserResponseModel.size(); i++) {
                        names.add(getUserResponseModel.get(i).getFirstname());
                    }

                    adapter=new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, names);
                    gridView.setAdapter(adapter);

                    gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, final View view, final int position, long id) {
                            PopupMenu menu=new PopupMenu(getApplicationContext(),view);
                            menu.getMenuInflater().inflate(R.menu.menu,menu.getMenu());

                            menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                                @Override
                                public boolean onMenuItemClick(MenuItem item) {
                                    switch (item.getItemId()){
                                        case R.id.view:
                                            View v=getLayoutInflater().inflate(R.layout.fragment_bottom_sheet_dialog,null);
                                            name=(TextView)v.findViewById(R.id.name);
                                            email=(TextView)v.findViewById(R.id.email);
                                            mobile=(TextView)v.findViewById(R.id.mobile);
                                            imageView=(ImageView)v.findViewById(R.id.imageView);
                                            close=(ImageButton)v.findViewById(R.id.close);
                                            final BottomSheetDialog bottomSheetDialog=new BottomSheetDialog(display.this);
                                            bottomSheetDialog.setContentView(v);
                                            bottomSheetDialog.show();

                                            close.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    bottomSheetDialog.dismiss();
                                                }
                                            });
                                            getInfo(names.get(position).toString());
                                            break;
                                        case R.id.delete:
                                            delete(position,names.get(position).toString());
                                            break;
                                        case R.id.update:
                                            Intent i=new Intent(display.this,Update.class);
                                            i.putExtra("name", (String) names.get(position));
                                            i.putExtra("pos",position);
                                            startActivity(i);
                                            break;
                                    }
                                    return false;
                                }
                            });
                            menu.show();
                        }
                    });
                }else {
                    Toast.makeText(display.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GetUsersResponseModel> call, Throwable t) {
                Toast.makeText(display.this, "error"+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getInfo( String username){
        Call<GetUsersResponseModel> call=apiInterface.getUser(username);
        call.enqueue(new Callback<GetUsersResponseModel>() {
            @Override
            public void onResponse(Call<GetUsersResponseModel> call, Response<GetUsersResponseModel> response) {
                List<Row> getUserResponseModel=response.body().getRows();
                String url="http://10.0.2.2:2000/"+getUserResponseModel.get(0).getImage();
                Picasso.with(getApplicationContext())
                        .load(url)
                        .into(imageView);
                name.setText(getUserResponseModel.get(0).getFirstname()+ " "+getUserResponseModel.get(0).getLastname());
                email.setText(getUserResponseModel.get(0).getEmail());
                mobile.setText(getUserResponseModel.get(0).getMobile());
            }

            @Override
            public void onFailure(Call<GetUsersResponseModel> call, Throwable t) {
                Toast.makeText(display.this, "error"+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void delete(final int pos, String name){
        Call<DeleteUserResponseModel> call=apiInterface.delUSer(name);
        call.enqueue(new Callback<DeleteUserResponseModel>() {
            @Override
            public void onResponse(Call<DeleteUserResponseModel> call, Response<DeleteUserResponseModel> response) {
                DeleteUserResponseModel deleteUserResponseModel=response.body();
                if(deleteUserResponseModel.getSuccess()==1){
                    Toast.makeText(display.this, deleteUserResponseModel.getMessage(), Toast.LENGTH_SHORT).show();
                    names.remove(pos);
                    adapter.notifyDataSetChanged();
                }else {
                    Toast.makeText(display.this, deleteUserResponseModel.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DeleteUserResponseModel> call, Throwable t) {
                Toast.makeText(display.this, "Problem occurred "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
