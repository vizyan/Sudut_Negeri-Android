package com.qiscus.internship.sudutnegeri.ui.splashscreen;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.qiscus.internship.sudutnegeri.R;
import com.qiscus.internship.sudutnegeri.data.model.DataUser;
import com.qiscus.internship.sudutnegeri.ui.addproject.AddProjectActivity;
import com.qiscus.internship.sudutnegeri.ui.dashboard.DashboardActivity;
import com.qiscus.internship.sudutnegeri.ui.landing.LandingActivity;
import com.qiscus.internship.sudutnegeri.util.Constant;

public class SplashscreenActivity extends AppCompatActivity implements SplashscreenView{

    private static int splashInterval = 2000;
    private SplashscreenPresenter splashscreenPresenter;
    ProgressDialog progressDialog;
    String email, passwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splashscreen);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                initPresenter();
                initPreference();
            }

            private void finish() {
                // TODO Auto-generated method stub

            }
        }, splashInterval);
    }

    private void initPreference(){
        SharedPreferences preferences = getSharedPreferences("LoginPreference", MODE_PRIVATE);
        email = preferences.getString("email", "");
        passwd = preferences.getString("password", "");
        if (email.isEmpty()){
            Intent landing = new Intent(SplashscreenActivity.this, LandingActivity.class);
            startActivity(landing);
            finish();
        } else {
            initDataPresenter();
        }
    }

    private void initPresenter(){
        splashscreenPresenter = new SplashscreenPresenter(this);
    }

    private void initDataPresenter(){
        splashscreenPresenter.loginUser();
    }

//    private void initProgressDialog(){
//        progressDialog = new ProgressDialog(SplashscreenActivity.this);
//        progressDialog.setTitle(null);
//        progressDialog.setMessage("Menyiapkan data");
//        progressDialog.setIndeterminate(false);
//        progressDialog.setCancelable(false);
//        progressDialog.show();
//    }

    @Override
    public String getPassword() {
        return passwd;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public void successUser(DataUser data) {
//        if (progressDialog.isShowing()) progressDialog.dismiss();
        Intent login = new Intent(SplashscreenActivity.this, DashboardActivity.class);
        login.putExtra(Constant.Extra.DATA, data);
        startActivity(login);
        finish();
    }

    @Override
    public void failed(String s) {
//        if (progressDialog.isShowing()) progressDialog.dismiss();
        Intent landing = new Intent(SplashscreenActivity.this, LandingActivity.class);
        startActivity(landing);
        finish();
    }
}
