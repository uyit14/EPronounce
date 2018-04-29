package com.example.uytai.epronounce;

import android.database.Cursor;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ResourceActivity extends AppCompatActivity {

    @BindView(R.id.main_tabs)
    TabLayout main_tabs;
    @BindView(R.id.main_tabPager)
    ViewPager main_tabPager;
    private SectionsPagerAdapter mSectionsPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resource);
        ButterKnife.bind(this);
        init();
    }


    private void init() {
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        main_tabPager.setAdapter(mSectionsPagerAdapter);
        main_tabs.setupWithViewPager(main_tabPager);
    }
}
