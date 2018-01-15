package com.qiscus.internship.sudutnegeri.ui.dashboard;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.qiscus.internship.sudutnegeri.R;
import com.qiscus.internship.sudutnegeri.data.model.DataUser;
import com.qiscus.internship.sudutnegeri.ui.AboutActivity;
import com.qiscus.internship.sudutnegeri.ui.landing.LandingActivity;
import com.qiscus.internship.sudutnegeri.ui.user.UserActivity;
import com.qiscus.internship.sudutnegeri.util.Constant;

public class DashboardActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private DataUser dataUser;
    TextView title, tvDrawerName;
    Button btnDrawerLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        setupToolbar();
        initView();
        initNavigation();
        initViewPager();
        initDataIntent();
        logout();
    }

    private void initView() {
        navigationView = findViewById(R.id.navigation_view);
        toolbar = findViewById(R.id.toolbar);
        tabLayout = findViewById(R.id.tabs);
        viewPager = findViewById(R.id.viewPager);
        drawerLayout = findViewById(R.id.drawer);
        title = findViewById(R.id.title_bar);
        tvDrawerName = findViewById(R.id.tvDrawerName);
        btnDrawerLogout = findViewById(R.id.btnDrawerLogout);
    }

    private void initDataIntent() {
        dataUser = getIntent().getParcelableExtra(Constant.Extra.User);
        if (dataUser == null) finish();
        //tvDrawerName.setText(dataUser.getName());
    }

    private void initNavigation() {

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                if(menuItem.isChecked()) menuItem.setChecked(false);
                else menuItem.setChecked(true);
                drawerLayout.closeDrawers();
                switch (menuItem.getItemId()){
                    case R.id.navigation1:
                        Toast.makeText(getApplicationContext(),"Anda Berada di Dashboard",Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.navigation2:
                        Intent Profil = new Intent(DashboardActivity.this, UserActivity.class);
                        startActivity(Profil);
                        return false;
                    case R.id.navigation3:
                        Intent AboutUs = new Intent(DashboardActivity.this, AboutActivity.class);
                        startActivity(AboutUs);
                        return false;
                    default:
                        Toast.makeText(getApplicationContext(),"Kesalahan Terjadi ",Toast.LENGTH_SHORT).show();
                        return true;
                }
            }
        });
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
        setupTab();
    }

    private void setupToolbar() {
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    private void setupTab(){
        tabLayout.getTabAt(0).setIcon(R.drawable.tab_home);
        tabLayout.getTabAt(1).setIcon(R.drawable.tab_jadwal);

        tabLayout.getTabAt(0).getIcon().setColorFilter(Color.parseColor("#66F4F9FB"), PorterDuff.Mode.SRC_IN);
        tabLayout.getTabAt(1).getIcon().setColorFilter(Color.parseColor("#F4F9FB"), PorterDuff.Mode.SRC_IN);

        title.setText("Dashboard Sudut");

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

    private void setupViewPager(final ViewPager viewPager) {
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), this);
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.setOffscreenPageLimit(2);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setTabsFromPagerAdapter(viewPagerAdapter);
    }

    private void logout(){
        btnDrawerLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(DashboardActivity.this);
                builder.setMessage("Apakah kalian yakin ingin keluar ?");
                builder.setPositiveButton("Ya", new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int which){
                        Intent logout = new Intent(DashboardActivity.this, LandingActivity.class);
                        startActivity(logout);
                        finish();
                    }
                });
                builder.setNegativeButton("Tidak", null);
                builder.show();
            }
        });
    }
}
