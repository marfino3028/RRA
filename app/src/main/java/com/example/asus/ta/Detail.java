package com.example.asus.ta;

import android.content.Intent;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class Detail extends AppCompatActivity {



    private TabLayout tabLayout;
    private AppBarLayout appBarLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        tabLayout = findViewById(R.id.tab_id);
        appBarLayout = findViewById(R.id.appbarid);
        viewPager = findViewById(R.id.viewpager);
        FragmentAdapter adapter = new FragmentAdapter(getSupportFragmentManager());

        adapter.AddFragment(new GambarFragment(),"Gambar");
        adapter.AddFragment(new BahanFragment(),"Bahan");
        adapter.AddFragment(new ProsesFragment(),"Proses");

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);


    }
}
