package com.qiscus.internship.sudutnegeri.ui.about;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.AnimationDrawable;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.qiscus.internship.sudutnegeri.R;
import com.qiscus.internship.sudutnegeri.data.model.DataUser;
import com.qiscus.internship.sudutnegeri.ui.dashboard.DashboardActivity;
import com.qiscus.internship.sudutnegeri.ui.landing.LandingActivity;
import com.qiscus.internship.sudutnegeri.ui.user.UserActivity;
import com.qiscus.internship.sudutnegeri.util.CircleTransform;
import com.qiscus.internship.sudutnegeri.util.Constant;
import com.squareup.picasso.Picasso;

public class AboutActivity extends AppCompatActivity implements AboutView{

    private AboutPresenter aboutPresenter;
    private DataUser dataUser;
    AnimationDrawable animationDrawable;
    Button btnDrawerLogout;
    DrawerLayout drawerLayout;
    ImageView ivDrawerPhoto;
    NavigationView navigationView;
    RelativeLayout relativeLayout;
    String email, passwd;
    TextView tvDrawerName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        initPresenter();
        initPreference();
        initView();
        initAnimation();
        initNavigation();
        initDataIntent();
        initDataDrawer();

        logout();
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (animationDrawable != null && animationDrawable.isRunning()){
            animationDrawable.stop();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (animationDrawable != null && !animationDrawable.isRunning()){
            animationDrawable.start();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent dashboard = new Intent(AboutActivity.this, DashboardActivity.class);
        dashboard.putExtra(Constant.Extra.DATA, dataUser);
        dashboard.putExtra(Constant.Extra.param, "user");
        startActivity(dashboard);
        finish();
    }

    private void initPresenter() {
        aboutPresenter = new AboutPresenter(this);
    }

    private void initPreference() {
        SharedPreferences preferences = getSharedPreferences("LoginPreference", MODE_PRIVATE);
        email = preferences.getString("email", "");
        passwd = preferences.getString("password", "");
    }

    private void initView() {
        relativeLayout = findViewById(R.id.rlAbout);
        navigationView = findViewById(R.id.nvAbout);
        drawerLayout = findViewById(R.id.dlAbout);
        tvDrawerName = findViewById(R.id.tvDrawerName);
        ivDrawerPhoto = findViewById(R.id.ivDrawerUser);
        btnDrawerLogout = findViewById(R.id.btnDrawerLogout);
    }

    private void initAnimation() {
        animationDrawable = (AnimationDrawable) relativeLayout.getBackground();
        animationDrawable.setEnterFadeDuration(3000);
        animationDrawable.setExitFadeDuration(3000);
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
                        Intent dashboard = new Intent(AboutActivity.this, DashboardActivity.class);
                        dashboard.putExtra(Constant.Extra.DATA, dataUser);
                        startActivity(dashboard);
                        finish();
                        return false;
                    case R.id.navigation2:
                        Intent userDetail = new Intent(AboutActivity.this, UserActivity.class);
                        userDetail.putExtra(Constant.Extra.DATA, dataUser);
                        userDetail.putExtra(Constant.Extra.param, "user");
                        startActivity(userDetail);
                        finish();
                        return false;
                    case R.id.navigation3:
                        Toast.makeText(getApplicationContext(),"Anda Berada di Tentang Kami",Toast.LENGTH_SHORT).show();
                        return true;
                    default:
                        return false;
                }
            }
        });
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

    private void logout() {
        btnDrawerLogout.setOnClickListener(v ->  {
            AlertDialog.Builder builder = new AlertDialog.Builder(AboutActivity.this);
            builder.setMessage("Apakah kalian yakin ingin keluar ?");
            builder.setPositiveButton("Ya", new DialogInterface.OnClickListener(){
                public void onClick(DialogInterface dialog, int which){
                    aboutPresenter.logout();
                }
            });
            builder.setNegativeButton("Tidak", null);
            builder.show();
        });
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public String getPasswd() {
        return passwd;
    }

    @Override
    public void successLogout() {
        Intent logout = new Intent(AboutActivity.this, LandingActivity.class);
        startActivity(logout);
        finish();
    }

    @Override
    public void failed(String s) {
        Toast.makeText(this, "Kesalahan sambungan", Toast.LENGTH_SHORT).show();
    }
}
