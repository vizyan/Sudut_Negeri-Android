package com.qiscus.internship.sudutnegeri.ui.Dashboard;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.qiscus.internship.sudutnegeri.R;

public class DashboardActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        initView();
        setupToolbar();
        initViewPager();
    }

    private void setupToolbar() {
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    private void initView() {
        toolbar = findViewById(R.id.toolbar);
        tabLayout = findViewById(R.id.tabs);
        viewPager = findViewById(R.id.viewPager);
        drawerLayout = findViewById(R.id.drawer);
        title = findViewById(R.id.title_bar);
    }

    private void initViewPager(){
        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager){
            @Override
            public void onTabSelected(TabLayout.Tab tab){
                super.onTabSelected(tab);
            }
        });
        setupTabIcons();
    }

    private void setupTabIcons(){
        tabLayout.getTabAt(0).setIcon(R.drawable.tab_home);
        tabLayout.getTabAt(1).setIcon(R.drawable.tab_jadwal);

        tabLayout.getTabAt(0).getIcon().setColorFilter(Color.parseColor("#FFFFFF"), PorterDuff.Mode.SRC_IN);
        tabLayout.getTabAt(1).getIcon().setColorFilter(Color.parseColor("#EE8871"), PorterDuff.Mode.SRC_IN);

        tabLayout.setOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager){

            @Override
            public void onTabSelected(TabLayout.Tab tab){
                super.onTabSelected(tab);
                switch (tab.getPosition()){
                    case 0:
                        title.setText("Dashboard Sudut");
                        break;
                    case 1:
                        title.setText("Dashboard Negeri");
                        break;
                }
                tab.getIcon().setColorFilter(Color.parseColor("#FFFFFF"), PorterDuff.Mode.SRC_IN);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab){
                super.onTabUnselected(tab);
                tab.getIcon().setColorFilter(Color.parseColor("#EE8871"), PorterDuff.Mode.SRC_IN);
            }

            @Override
            public void onTabReselected (TabLayout.Tab tab){
                tab.setText("");
            }
        });
    }

    private void setupViewPager(final ViewPager viewPager) {
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), this);
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.setOffscreenPageLimit(3);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setTabsFromPagerAdapter(viewPagerAdapter);
    }
}
