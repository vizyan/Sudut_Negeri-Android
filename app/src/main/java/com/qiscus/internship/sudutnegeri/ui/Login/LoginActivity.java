package com.qiscus.internship.sudutnegeri.ui.Login;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.qiscus.internship.sudutnegeri.R;
import com.qiscus.internship.sudutnegeri.ui.AddUser.AddUserActivity;
import com.qiscus.internship.sudutnegeri.ui.Dashboard.DashboardActivity;

public class LoginActivity extends AppCompatActivity implements LoginView {

    RelativeLayout relativeLayout;
    AnimationDrawable animationDrawable;
    Button btnLogin, btnRegister;
    EditText etEmail, etPassword;
    private LoginPresenter loginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);
        initView();
        initPresenter();
        initAnimation();
        login();
        register();
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

    private void initView() {
        //initial variable
        relativeLayout = findViewById(R.id.relative);
        animationDrawable = (AnimationDrawable) relativeLayout.getBackground();
        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
    }

    private void initPresenter() {
        loginPresenter = new LoginPresenter(this);
    }

    private void initAnimation() {
        //animation duration
        animationDrawable.setEnterFadeDuration(3000);
        animationDrawable.setExitFadeDuration(3000);
    }

    private void login(){
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginPresenter.login();
            }
        });
    }

    private void register(){
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent register = new Intent(LoginActivity.this, AddUserActivity.class);
                startActivity(register);
                finish();
            }
        });
    }

    @Override
    public String getPassword() {
        return etPassword.getText().toString();
    }

    @Override
    public String getEmail() {
        return etEmail.getText().toString();
    }

    @Override
    public void success() {
        Intent login = new Intent(LoginActivity.this, DashboardActivity.class);
        startActivity(login);
        finish();
    }

    @Override
    public void failed() {
        Toast.makeText(this, "Maaf username atau password salah", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void noConnection() {
        Toast.makeText(this, "Maaf, terjadi kesalahan sambugan", Toast.LENGTH_SHORT).show();
    }
}
