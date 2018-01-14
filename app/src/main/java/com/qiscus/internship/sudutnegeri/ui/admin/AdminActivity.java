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
import com.qiscus.internship.sudutnegeri.ui.dashboard.ViewPagerAdapter;

public class AdminActivity extends AppCompatActivity implements AdminView {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private AdminPresenter presenter;
    TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        setTitle("");
        initView();
        setupToolbar();
        initViewPager();
        initPresenter();
    }

    private void initPresenter() {
        presenter = new AdminPresenter(this);
        presenter.showUnverifiedUser();
    }

    private void initView() {
        toolbar = findViewById(R.id.toolbar);
        tabLayout = findViewById(R.id.tabs);
        viewPager = findViewById(R.id.viewPager);
        title = findViewById(R.id.title_bar);
    }

    private void setupToolbar() {
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    private void initViewPager() {
        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager){
            @Override
            public void onTabSelected(TabLayout.Tab tab){
                super.onTabSelected(tab);
            }
        });
        setupTab();
    }

    private void setupTab() {
        tabLayout.getTabAt(0).setIcon(R.drawable.tab_home);
        tabLayout.getTabAt(1).setIcon(R.drawable.tab_jadwal);

        tabLayout.getTabAt(0).getIcon().setColorFilter(Color.parseColor("#66F4F9FB"), PorterDuff.Mode.SRC_IN);
        tabLayout.getTabAt(1).getIcon().setColorFilter(Color.parseColor("#F4F9FB"), PorterDuff.Mode.SRC_IN);

        title.setText("Verifikasi Pengguna");

        tabLayout.setOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager){
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
                tab.setText("");
            }
        });
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), this);
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.setOffscreenPageLimit(2);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setTabsFromPagerAdapter(viewPagerAdapter);
    }
}
