package com.qiscus.internship.sudutnegeri.ui.login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.qiscus.internship.sudutnegeri.R;
import com.qiscus.internship.sudutnegeri.data.model.DataUser;
import com.qiscus.internship.sudutnegeri.ui.admin.AdminActivity;
import com.qiscus.internship.sudutnegeri.ui.register.RegisterActivity;
import com.qiscus.internship.sudutnegeri.ui.dashboard.DashboardActivity;
import com.qiscus.internship.sudutnegeri.util.Constant;

public class LoginActivity extends AppCompatActivity implements LoginView {

    private LoginPresenter loginPresenter;
    RelativeLayout rvLog;
    AnimationDrawable animationDrawable;
    Button btnLogLog, btnLogReg, btnPopupFRetry;
    EditText etLogEmail, etLogPasswd;
    TextView tvPopupMsg, tvPopupType;
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
        btnPopupFRetry = findViewById(R.id.btnPopupFRetry);
        etLogEmail = findViewById(R.id.etLogEmail);
        etLogPasswd = findViewById(R.id.etLogPasswd);
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
        btnLogLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validation()==true){
                    if(email.equals("admin@admin.com")){
                        loginPresenter.loginAdmin();
                    } else {
                        loginPresenter.loginUser();
                    }
                }
            }
        });
    }

    private void register(){
        btnLogReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent register = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(register);
                finish();
            }
        });
    }

    private void initVariable(){
        email = etLogEmail.getText().toString();
        passwd = etLogPasswd.getText().toString();
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
            etLogEmail.setBackgroundResource(R.drawable.bg_sounded_trans_red);
            etLogEmail.setHint("Isikan email");
            return "false";
        } else {
            if (Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                etLogEmail.setBackgroundResource(R.drawable.bg_rounded_trans_green);
                return "true";
            } else {
                etLogEmail.setBackgroundResource(R.drawable.bg_sounded_trans_red);
                etLogEmail.setText("");
                etLogEmail.setHint("Isikan email dengan benar");
                return "false";
            }
        }
    }

    @NonNull
    private String validPasswd(){
        if(passwd.isEmpty()){
            etLogPasswd.setBackgroundResource(R.drawable.bg_sounded_trans_red);
            etLogPasswd.setHint("Isikan password");
            return "false";
        } else {
            if (passwd.length()<6){
                etLogPasswd.setBackgroundResource(R.drawable.bg_sounded_trans_red);
                etLogPasswd.setText("");
                Toast.makeText(this, "Password minimal 6 karakter", Toast.LENGTH_SHORT).show();
                return "false";
            } else {
                etLogPasswd.setBackgroundResource(R.drawable.bg_rounded_trans_green);
                return "true";
            }
        }
    }

    private void initPopupWindow(String messsage) {
        try {
            LayoutInflater inflater = (LayoutInflater) LoginActivity.this
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View layout = inflater.inflate(R.layout.layout_popup_failed,
                    null);
            final PopupWindow pw = new PopupWindow(layout, RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT, true);
            pw.setOutsideTouchable(false);
            pw.showAtLocation(layout, Gravity.CENTER, 0, 0);
            tvPopupMsg = layout.findViewById(R.id.tvPopupFMsg);
            tvPopupType = layout.findViewById(R.id.tvPopupFType);
            btnPopupFRetry = layout.findViewById(R.id.btnPopupFRetry);
            tvPopupMsg.setText(messsage);
            tvPopupType.setText("Gagal Masuk");

            btnPopupFRetry.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        pw.dismiss();
                        loginPresenter.loginUser();
                    }
                });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initPreference(){
        SharedPreferences preferences = getSharedPreferences("LoginPreference", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("email", email);
        editor.putString("password", passwd);
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
    public void failed() {
        initPopupWindow("Email atau kata sandi salah");
    }

    @Override
    public void noConnection() {
        initPopupWindow("Kesalahan sambungan");
    }

    @Override
    public void notVerified() {
        initPopupWindow("Akun belum diverifikasi");
    }

    @Override
    public void successUser(DataUser data) {
        initPreference();

        Intent login = new Intent(LoginActivity.this, DashboardActivity.class);
        login.putExtra(Constant.Extra.User, data);
        startActivity(login);
        finish();
    }

    @Override
    public void successAdmin() {
        Intent login = new Intent(LoginActivity.this, AdminActivity.class);
        startActivity(login);
        finish();
    }
}
