package com.example.lcom151_two.myapplication;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import static butterknife.ButterKnife.findById;
import butterknife.ButterKnife;

public class MenuFragment extends Fragment {

    private TabLayout tabLayout;
    View view;
    PageAdapter1 adapter;


    public MenuFragment() {
        // Required empty public constructor
    }

    private void setupViewPager(ViewPager viewPager){
        adapter=new PageAdapter1(getChildFragmentManager());
        adapter.addFragment(new TabFragement1(),"Main Course");
        adapter.addFragment(new TabFragment2(),"Appitezers");
        adapter.addFragment(new TabFragment3(),"Desserts");
        adapter.addFragment(new TabFragment4(),"Soup");
        viewPager.setAdapter(adapter);
    }

    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_menu,null);
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this,view);
        final ViewPager viewPager = ButterKnife.findById(view,R.id.pager);
        setupViewPager(viewPager);
        tabLayout = ButterKnife.findById(view,R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);
        return view;

    }

}
