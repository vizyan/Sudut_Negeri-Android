package com.qiscus.internship.sudutnegeri.ui.dashboard;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.qiscus.internship.sudutnegeri.R;
import com.qiscus.internship.sudutnegeri.data.model.DataProject;
import com.qiscus.internship.sudutnegeri.data.model.DataUser;
import com.qiscus.internship.sudutnegeri.ui.about.AboutActivity;
import com.qiscus.internship.sudutnegeri.ui.landing.LandingActivity;
import com.qiscus.internship.sudutnegeri.ui.user.UserActivity;
import com.qiscus.internship.sudutnegeri.util.Constant;

import java.util.List;

public class DashboardActivity extends AppCompatActivity implements DashboardView {

    private DashboardPresenter dashboardPresenter;
    private DataUser dataUser;
    Button btnDrawerLogout;
    DrawerLayout drawerLayout;
    ImageView ivDrawerPhoto;
    NavigationView navigationView;
    String email, passwd;
    TabLayout tabLayout;
    TextView tvToolbarTitle, tvDrawerName;
    Toolbar toolbar;
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        setTitle("");
        initPresenter();
        initPreference();
        initView();
        initNavigation();
        initViewPager();
        initDataIntent();
        initDataDrawer();
        setupToolbar();

        logout();
    }

    private void initPresenter() {
        dashboardPresenter = new DashboardPresenter(this);
    }

    private void initPreference(){
        SharedPreferences preferences = getSharedPreferences("LoginPreference", MODE_PRIVATE);
        email = preferences.getString("email", "");
        passwd = preferences.getString("password", "");
    }

    private void initView() {
        navigationView = findViewById(R.id.nvDashboard);
        toolbar = findViewById(R.id.tbDashboard);
        tabLayout = findViewById(R.id.tlDashboard);
        viewPager = findViewById(R.id.vpDashboard);
        drawerLayout = findViewById(R.id.dlDashboard);
        tvToolbarTitle = findViewById(R.id.tvToolbarTitle);
        tvDrawerName = findViewById(R.id.tvDrawerName);
        ivDrawerPhoto = findViewById(R.id.ivDrawerUser);
        btnDrawerLogout = findViewById(R.id.btnDrawerLogout);
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
                        Intent userDetail = new Intent(DashboardActivity.this, UserActivity.class);
                        userDetail.putExtra(Constant.Extra.DATA, dataUser);
                        userDetail.putExtra(Constant.Extra.param, "user");
                        startActivity(userDetail);
                        finish();
                        return false;
                    case R.id.navigation3:
                        Intent aboutUs = new Intent(DashboardActivity.this, AboutActivity.class);
                        aboutUs.putExtra(Constant.Extra.DATA, dataUser);
                        startActivity(aboutUs);
                        finish();
                        return true;
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

    private void initDataIntent() {
        dataUser = getIntent().getParcelableExtra(Constant.Extra.DATA);
        if (dataUser == null) finish();
        //tvDrawerName.setText(dataUser.getName());
    }

    private void initDataDrawer() {
        tvDrawerName.setText(dataUser.getName());

        //Picasso.with(this)
        //        .load(dataUser.getAddress())
        //        .into(ivDrawerUser);
    }

    private void setupToolbar() {
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    private void setupViewPager(final ViewPager viewPager) {
        ViewPagerDashboard viewPagerDashboard = new ViewPagerDashboard(getSupportFragmentManager(), this);
        viewPager.setAdapter(viewPagerDashboard);
        viewPager.setOffscreenPageLimit(2);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setTabsFromPagerAdapter(viewPagerDashboard);
    }

    private void setupTab(){
        tabLayout.getTabAt(0).setIcon(R.drawable.tab_home);
        tabLayout.getTabAt(1).setIcon(R.drawable.tab_jadwal);

        tabLayout.getTabAt(0).getIcon().setColorFilter(Color.parseColor("#66F4F9FB"), PorterDuff.Mode.SRC_IN);
        tabLayout.getTabAt(1).getIcon().setColorFilter(Color.parseColor("#F4F9FB"), PorterDuff.Mode.SRC_IN);

        tvToolbarTitle.setText("Dashboard Sudut");

        tabLayout.setOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager){
            @Override
            public void onTabSelected(TabLayout.Tab tab){
                super.onTabSelected(tab);
                switch (tab.getPosition()){
                    case 0:
                        tvToolbarTitle.setText("Dashboard Sudut");
                        break;
                    case 1:
                        tvToolbarTitle.setText("Dashboard Negeri");
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

    private void logout(){
        btnDrawerLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(DashboardActivity.this);
                builder.setMessage("Apakah kalian yakin ingin keluar ?");
                builder.setPositiveButton("Ya", new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int which){
                        dashboardPresenter.logout();
                    }
                });
                builder.setNegativeButton("Tidak", null);
                builder.show();
            }
        });
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public String getPassword() {
        return passwd;
    }

    @Override
    public void successLogout() {
        Intent logout = new Intent(DashboardActivity.this, LandingActivity.class);
        startActivity(logout);
        finish();
    }

    @Override
    public void successShowProjectVerify(List<DataProject> dataProject) {

    }
}
