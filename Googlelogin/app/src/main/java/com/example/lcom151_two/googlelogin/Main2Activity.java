package com.example.lcom151_two.googlelogin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class Main2Activity extends AppCompatActivity {

    Button prev,next;
    public int currentPage=0;
    public int displayPP=5;
    ArrayList arrayList,tempList;
    GridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        prev=(Button)findViewById(R.id.previous);
        next=(Button)findViewById(R.id.next);

        tempList=new ArrayList();

        gridView=(GridView)findViewById(R.id.gridView);
        arrayList=new ArrayList(Arrays.asList(1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25));

        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentPage=currentPage-1;
                addItem(currentPage);
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentPage=currentPage+1;
                addItem(currentPage);
            }
        });

        int rem=arrayList.size()%displayPP;
        if(rem>0){
            for(int i=0;i<displayPP-rem;i++){
                arrayList.add("");
            }
        }

        addItem(0);

    }

    public void addItem(int count){

        tempList.clear();
        count=count*displayPP;

        for(int j=0;j<displayPP;j++){
            tempList.add(j,arrayList.get(count));
            count++;
        }

        if(count>=arrayList.size()){
            next.setEnabled(false);
        }else {
            next.setEnabled(true);
        }

        if(currentPage<=0){
            prev.setEnabled(false);
        }
        else {
            prev.setEnabled(true);
        }

        setView();
    }

    public void setView(){
        ArrayAdapter adapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1,tempList);
        gridView.setAdapter(adapter);
    }
}
