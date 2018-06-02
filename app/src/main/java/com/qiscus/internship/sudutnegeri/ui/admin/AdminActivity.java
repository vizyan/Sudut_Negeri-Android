package com.qiscus.internship.sudutnegeri.ui.admin;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.qiscus.internship.sudutnegeri.R;

public class AdminActivity extends AppCompatActivity{

    private Toolbar toolbar;
    private TabLayout tlAdmin;
    private ViewPager vpAdmin;
    TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        setTitle("");
        initView();
        initViewPager();
        setupToolbar();
    }

    private void initView() {
        toolbar = findViewById(R.id.toolbar);
        tlAdmin = findViewById(R.id.tlAdmin);
        vpAdmin = findViewById(R.id.vpAdmin);
        title = findViewById(R.id.tvToolbarTitle);
    }

    private void setupToolbar() {
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    private void initViewPager() {
        setupViewPager(vpAdmin);
        tlAdmin.setupWithViewPager(vpAdmin);
        tlAdmin.setOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(vpAdmin){
            @Override
            public void onTabSelected(TabLayout.Tab tab){
                super.onTabSelected(tab);
            }
        });
        setupTab();
    }

    private void setupTab() {
        tlAdmin.getTabAt(0).setIcon(R.drawable.tab_home);
        tlAdmin.getTabAt(1).setIcon(R.drawable.tab_jadwal);

        tlAdmin.getTabAt(0).getIcon().setColorFilter(Color.parseColor("#F4F9FB"), PorterDuff.Mode.SRC_IN);
        tlAdmin.getTabAt(1).getIcon().setColorFilter(Color.parseColor("#66F4F9FB"), PorterDuff.Mode.SRC_IN);

        title.setText("Verifikasi Pengguna");

        tlAdmin.setOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(vpAdmin){
            @Override
            public void onTabSelected(TabLayout.Tab tab){
                super.onTabSelected(tab);
                switch (tab.getPosition()){
                    case 0:
                        title.setText("Verifikasi Pengguna");
                        break;
                    case 1:
                        title.setText("Verifikasi Projek");
                        break;
                }
                tab.getIcon().setColorFilter(Color.parseColor("#F4F9FB"), PorterDuff.Mode.SRC_IN);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab){
                super.onTabUnselected(tab);
                tab.getIcon().setColorFilter(Color.parseColor("#66F4F9FB"), PorterDuff.Mode.SRC_IN);
            }

            @Override
            public void onTabReselected (TabLayout.Tab tab){

            }
        });
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdmin viewPagerAdmin = new ViewPagerAdmin(getSupportFragmentManager(), this);
        viewPager.setAdapter(viewPagerAdmin);
        viewPager.setOffscreenPageLimit(2);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tlAdmin));
        tlAdmin.setTabsFromPagerAdapter(viewPagerAdmin);
    }
}
