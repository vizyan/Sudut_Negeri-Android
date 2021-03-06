package com.qiscus.internship.sudutnegeri.ui.dashboard;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.qiscus.internship.sudutnegeri.R;
import com.qiscus.internship.sudutnegeri.data.model.DataProject;
import com.qiscus.internship.sudutnegeri.data.model.DataUser;
import com.qiscus.internship.sudutnegeri.ui.about.AboutActivity;
import com.qiscus.internship.sudutnegeri.ui.landing.LandingActivity;
import com.qiscus.internship.sudutnegeri.ui.user.UserActivity;
import com.qiscus.internship.sudutnegeri.util.CircleTransform;
import com.qiscus.internship.sudutnegeri.util.Constant;
import com.squareup.picasso.Picasso;

import java.util.List;

public class DashboardActivity extends AppCompatActivity implements DashboardView {

    private DashboardPresenter dashboardPresenter;
    private DataUser dataUser;
    boolean doubleBackToExit = false;
    Button btnDrawerLogout;
    DrawerLayout drawerLayout;
    ImageView ivDrawerPhoto;
    NavigationView navigationView;
    SearchView svNegeri, svSudut;
    String email, passwd;
    SwipeRefreshLayout swipeRefreshLayoutNegeri, swipeRefreshLayoutSudut;
    TabLayout tabLayout;
    TextView tvToolbarTitle, tvDrawerName, tvDashboardSearch;
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

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
        } else if (!doubleBackToExit) {
            this.doubleBackToExit = true;
            Toast.makeText(this,"Tekan 2 kali untuk keluar", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(()->
                    doubleBackToExit = false, 2000);
        } else {
            super.onBackPressed();
            return;
        }
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
        svSudut = findViewById(R.id.svDashboardSudut);
        svNegeri = findViewById(R.id.svDashboardNegeri);
        swipeRefreshLayoutNegeri = findViewById(R.id.srlNegeri);
        swipeRefreshLayoutSudut = findViewById(R.id.srlSudut);
    }

    private void initNavigation() {

        navigationView.setNavigationItemSelectedListener(menuItem -> {
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
    }

    private void initDataDrawer() {
        tvDrawerName.setText(dataUser.getName());

        Picasso.with(this)
                .load(dataUser.getPhoto())
                .transform(new CircleTransform())
                .into(ivDrawerPhoto);
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
        tabLayout.getTabAt(0).setIcon(R.drawable.ic_sudut);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_negeri);

        tabLayout.getTabAt(1).getIcon().setColorFilter(Color.parseColor("#66F4F9FB"), PorterDuff.Mode.SRC_IN);

        tvToolbarTitle.setText("Dashboard Sudut");
        svNegeri.setVisibility(View.GONE);

        tabLayout.setOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager){
            @Override
            public void onTabSelected(TabLayout.Tab tab){
                super.onTabSelected(tab);
                switch (tab.getPosition()){
                    case 0:
                        tvToolbarTitle.setText("Dashboard Sudut");
                        svNegeri.setVisibility(View.GONE);
                        svSudut.setVisibility(View.VISIBLE);
                        break;
                    case 1:
                        tvToolbarTitle.setText("Dashboard Negeri");
                        svSudut.setVisibility(View.GONE);
                        svNegeri.setVisibility(View.VISIBLE);
                        break;
                }
                tab.getIcon().clearColorFilter();
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
        btnDrawerLogout.setOnClickListener(v ->  {
            AlertDialog.Builder builder = new AlertDialog.Builder(DashboardActivity.this);
            builder.setMessage("Apakah kalian yakin ingin keluar ?");
            builder.setPositiveButton("Ya", (dialog, which) -> dashboardPresenter.logout());
            builder.setNegativeButton("Tidak", null);
            builder.show();
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
    public void successShowProjectVerify(List<DataProject> dataProject) {}

    @Override
    public void successShowProjectByUser(List<DataProject> dataProjectList) {}

    @Override
    public void failed(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }

}
