package com.qiscus.internship.sudutnegeri.ui.register;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.qiscus.internship.sudutnegeri.ui.landing.LandingActivity;
import com.qiscus.internship.sudutnegeri.ui.login.LoginActivity;

public class RegisterActivity extends AppCompatActivity implements RegisterView {

    private RegisterPresenter registerPresenter;
    AnimationDrawable animationDrawable;
    RelativeLayout rvReg;
    EditText etRegName,etRegEmail, etRegPasswd, etRegRetypePasswd, etRegIdNo, etRegAddress, etRegPhone ;
    Button btnRegReg, btnRegLog, btnPopupFRetry, btnPopupSNext;
    TextView tvPopupMsg, tvPopupType;
    String passwd, retypepasswd, email, name, idNo, address, phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_register);
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
        rvReg = findViewById(R.id.rvReg);
        etRegName = findViewById(R.id.etRegName);
        etRegEmail = findViewById(R.id.etRegEmail);
        etRegPasswd = findViewById(R.id.etRegPasswd);
        etRegRetypePasswd = findViewById(R.id.etRegRetypePasswd);
        etRegIdNo = findViewById(R.id.etRegIdNo);
        etRegAddress = findViewById(R.id.etRegAddress);
        etRegPhone = findViewById(R.id.etRegPhone);
        btnRegReg = findViewById(R.id.btnRegReg);
        btnRegLog = findViewById(R.id.btnRegLog);
    }

    private void initAnimation() {
        //animation duration
        animationDrawable = (AnimationDrawable) rvReg.getBackground();
        animationDrawable.setEnterFadeDuration(3000);
        animationDrawable.setExitFadeDuration(3000);
    }

    private void initPresenter() {
        //initial RegisterPresenter
        registerPresenter = new RegisterPresenter(this);
    }

    private void initVariable() {
        name = etRegName.getText().toString();
        email = etRegEmail.getText().toString();
        passwd = etRegPasswd.getText().toString();
        retypepasswd = etRegRetypePasswd.getText().toString();
        idNo = etRegIdNo.getText().toString();
        address = etRegAddress.getText().toString();
        phone = etRegPhone.getText().toString();
    }

    private void login() {
        btnRegLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent login = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(login);
                finish();
            }
        });
    }

    private void register() {
        btnRegReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validation()==true){
                    registerPresenter.addUser();
                }
            }
        });
    }

    private boolean validation(){
        initVariable();
        validName();
        validEmail();
        validPasswd();
        validRetypePasswd();
        validIdNo();
        validAddress();
        validPhone();

        if (validName().equals("false") || validEmail().equals("false") || validPasswd().equals("false") || validRetypePasswd().equals("false") || validIdNo().equals("false") || validAddress().equals("false") || validPhone().equals("false") ){
            return false;
        } else {
            return true;
        }
    }

    private void initPopupWindow(String messsage) {
        try {
            LayoutInflater inflater = (LayoutInflater) RegisterActivity.this
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            if (messsage.equals("success")){
                View layout = inflater.inflate(R.layout.layout_popup_success,
                        null);
                final PopupWindow pw = new PopupWindow(layout, RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT, false);
                pw.setOutsideTouchable(false);
                pw.showAtLocation(layout, Gravity.CENTER, 0, 0);
                btnPopupSNext = layout.findViewById(R.id.btnPopupSNext);

                btnPopupSNext.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        pw.dismiss();
                        Intent landing = new Intent(RegisterActivity.this, LandingActivity.class);
                        startActivity(landing);
                        finish();
                    }
                });

            } else {
                View layout = inflater.inflate(R.layout.layout_popup_failed,
                        null);
                final PopupWindow pw = new PopupWindow(layout, RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT, true);
                pw.setOutsideTouchable(false);
                pw.showAtLocation(layout, Gravity.CENTER, 0, 0);
                tvPopupMsg = layout.findViewById(R.id.tvPopupFMsg);
                tvPopupMsg = layout.findViewById(R.id.tvPopupSType);
                btnPopupFRetry = layout.findViewById(R.id.btnPopupFRetry);
                tvPopupMsg.setText(messsage);
                tvPopupType.setText("Gagal Bergabung");

                btnPopupFRetry.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        pw.dismiss();
                        registerPresenter.addUser();
                    }
                });
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @NonNull
    private String validEmail(){
        if(email.isEmpty()){
            etRegEmail.setBackgroundResource(R.drawable.bg_sounded_trans_red);
            etRegEmail.setHint("Isikan email");
            return "false";
        } else {
            if (Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                etRegEmail.setBackgroundResource(R.drawable.bg_rounded_trans_green);
                return "true";
            } else {
                etRegEmail.setBackgroundResource(R.drawable.bg_sounded_trans_red);
                etRegEmail.setText("");
                etRegEmail.setHint("Isikan email dengan benar");
                return "false";
            }
        }
    }

    @NonNull
    private String validName(){
        if(name.isEmpty()){
            etRegName.setBackgroundResource(R.drawable.bg_sounded_trans_red);
            etRegName.setHint("Isikan nama");
            return "false";
        } else {
            etRegName.setBackgroundResource(R.drawable.bg_rounded_trans_green);
            return "true";
        }
    }

    @NonNull
    private String validPasswd(){
        if(passwd.isEmpty()){
            etRegPasswd.setBackgroundResource(R.drawable.bg_sounded_trans_red);
            etRegPasswd.setHint("Isikan password");
            return "false";
        } else {
            if (passwd.length()<6){
                etRegPasswd.setBackgroundResource(R.drawable.bg_sounded_trans_red);
                etRegPasswd.setText("");
                Toast.makeText(this, "Password minimal 6 karakter", Toast.LENGTH_SHORT).show();
                return "false";
            } else {
                etRegPasswd.setBackgroundResource(R.drawable.bg_rounded_trans_green);
                return "true";
            }
        }
    }

    @NonNull
    private String validRetypePasswd(){
        if (retypepasswd.isEmpty()){
            etRegRetypePasswd.setBackgroundResource(R.drawable.bg_sounded_trans_red);
            etRegRetypePasswd.setHint("Isikan ulang password");
            return "false";
        } else {
            if(retypepasswd.matches(passwd)){
                etRegRetypePasswd.setBackgroundResource(R.drawable.bg_rounded_trans_green);
                return "true";
            } else {
                etRegRetypePasswd.setBackgroundResource(R.drawable.bg_sounded_trans_red);
                etRegRetypePasswd.setText("");
                etRegRetypePasswd.setHint("Masukkan ulang password");
                return "false";
            }
        }
    }

    @NonNull
    private String validIdNo(){
        if(idNo.isEmpty()){
            etRegIdNo.setHint("Isikan no KTP");
            etRegIdNo.setBackgroundResource(R.drawable.bg_sounded_trans_red);
            return "false";
        } else {
            etRegIdNo.setBackgroundResource(R.drawable.bg_rounded_trans_green);
            return "true";
        }
    }

    @NonNull
    private String validAddress(){
        if(address.isEmpty()){
            etRegAddress.setHint("Isikan Alamat");
            etRegAddress.setBackgroundResource(R.drawable.bg_sounded_trans_red);
            return "false";
        } else {
            etRegAddress.setBackgroundResource(R.drawable.bg_rounded_trans_green);
            return "true";
        }
    }

    @NonNull
    private String validPhone(){
        if(phone.isEmpty()){
            etRegPhone.setHint("Isikan no HP / no Telepon");
            etRegPhone.setBackgroundResource(R.drawable.bg_sounded_trans_red);
            return "false";
        } else {
            etRegPhone.setBackgroundResource(R.drawable.bg_rounded_trans_green);
            return "true";
        }
    }

    @Override
    public String getName() {
        return name;
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
    public String getNoIdentity() {
        return idNo;
    }

    @Override
    public String getAddress() {
        return address;
    }

    @Override
    public String getPhone() {
        return phone;
    }

    @Override
    public String getRetypePasswd() {
        return retypepasswd;
    }

    @Override
    public void success(String message) {
        initPopupWindow(message);
    }

    @Override
    public void noConnection() {

    }


}
