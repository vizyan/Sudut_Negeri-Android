package com.qiscus.internship.sudutnegeri.ui.login;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.qiscus.internship.sudutnegeri.R;
import com.qiscus.internship.sudutnegeri.data.model.DataUser;
import com.qiscus.internship.sudutnegeri.ui.admin.AdminActivity;
import com.qiscus.internship.sudutnegeri.ui.register.RegisterActivity;
import com.qiscus.internship.sudutnegeri.ui.dashboard.DashboardActivity;
import com.qiscus.internship.sudutnegeri.util.Constant;
import com.qiscus.internship.sudutnegeri.util.Popup;

public class LoginActivity extends AppCompatActivity implements LoginView, Popup.PopupListener {

    private LoginPresenter loginPresenter;
    private Popup popup;
    private ProgressDialog progressDialog;
    RelativeLayout rvLog;
    AnimationDrawable animationDrawable;
    Button btnLogLog, btnLogReg;
    EditText etLogEmail, etLogPasswd;
    String email, passwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        initPresenter();
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);
        initView();
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
        rvLog = findViewById(R.id.rvLog);
        btnLogLog = findViewById(R.id.btnLogLog);
        btnLogReg = findViewById(R.id.btnLogReg);
        etLogEmail = findViewById(R.id.etLogEmail);
        etLogPasswd = findViewById(R.id.etLogPasswd);
        popup = new Popup(this);
    }

    private void initPresenter() {
        loginPresenter = new LoginPresenter(this);
    }

    private void initAnimation() {
        //animation duration
        animationDrawable = (AnimationDrawable) rvLog.getBackground();
        animationDrawable.setEnterFadeDuration(3000);
        animationDrawable.setExitFadeDuration(3000);
    }

    private void login(){
        btnLogLog.setOnClickListener(v ->  {
            if(validation()==true){
                if(email.equals("snqiscus@gmail.com")){
                    initProgressDialog();
                    loginPresenter.loginAdmin();
                } else {
                    initProgressDialog();
                    loginPresenter.loginUser();
                }
            }
        });
    }

    private void register(){
        btnLogReg.setOnClickListener(v -> {
            Intent register = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(register);
            finish();
        });
    }

    private void initVariable(){
        email = etLogEmail.getText().toString();
        passwd = etLogPasswd.getText().toString();
    }

    private void initProgressDialog(){
        progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setTitle(null);
        progressDialog.setMessage("Tunggu beberapa saat");
        progressDialog.setIndeterminate(false);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    private boolean validation(){
        initVariable();
        validEmail();
        validPasswd();

        if(validEmail().equals("false") || validPasswd().equals("false")){
            return false;
        } else {
            return true;
        }
    }

    @NonNull
    private String validEmail(){
        if(email.isEmpty()){
            etLogEmail.setBackgroundResource(R.drawable.bg_rounded_trans_red);
            etLogEmail.setHint("Isikan email");
            return "false";
        } else {
            if (Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                etLogEmail.setBackgroundResource(R.drawable.bg_rounded_trans_green);
                return "true";
            } else {
                etLogEmail.setBackgroundResource(R.drawable.bg_rounded_trans_red);
                etLogEmail.setText("");
                etLogEmail.setHint("Isikan email dengan benar");
                return "false";
            }
        }
    }

    @NonNull
    private String validPasswd(){
        if(passwd.isEmpty()){
            etLogPasswd.setBackgroundResource(R.drawable.bg_rounded_trans_red);
            etLogPasswd.setHint("Isikan password");
            return "false";
        } else {
            if (passwd.length()<6){
                etLogPasswd.setBackgroundResource(R.drawable.bg_rounded_trans_red);
                etLogPasswd.setText("");
                Toast.makeText(this, "Password minimal 6 karakter", Toast.LENGTH_SHORT).show();
                return "false";
            } else {
                etLogPasswd.setBackgroundResource(R.drawable.bg_rounded_trans_green);
                return "true";
            }
        }
    }

    private void savePreference(DataUser dataUser){
        Gson gson = new Gson();
        String DataUser = gson.toJson(dataUser);
        SharedPreferences preferences = getSharedPreferences("LoginPreference", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("email", email);
        editor.putString("password", passwd);
        editor.putString("DataUser", DataUser);
        editor.commit();
        editor.apply();
    }

    @Override
    public String getPassword() {
        return passwd;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public void failed(String message) {
        progressDialog.dismiss();
        popup.PopupWindow(this, "failed", message);
    }

    @Override
    public void successUser(DataUser dataUser) {
        progressDialog.dismiss();
        savePreference(dataUser);

        Intent login = new Intent(LoginActivity.this, DashboardActivity.class);
        login.putExtra(Constant.Extra.DATA, dataUser);
        startActivity(login);
        finish();
    }

    @Override
    public void successAdmin(DataUser dataUser) {
        progressDialog.dismiss();
        Intent login = new Intent(LoginActivity.this, AdminActivity.class);
        login.putExtra(Constant.Extra.DATA, dataUser);
        startActivity(login);
        finish();
    }

    @Override
    public void PopupSuccess(String success) {

    }
}
