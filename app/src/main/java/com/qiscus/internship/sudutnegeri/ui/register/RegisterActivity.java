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

import okhttp3.Interceptor;

public class RegisterActivity extends AppCompatActivity implements RegisterView {

    RelativeLayout relativeLayout;
    AnimationDrawable animationDrawable;
    EditText etAddNama;
    EditText etAddEmail;
    EditText etAddPassword;
    EditText etAddRetypePassword;
    EditText etAddNoKTP;
    EditText etAddAlamat;
    EditText etAddNoTelp;
    Button btnRegister, btnLogin, btnRetry, btnNext;
    TextView tvMessage, tvType;
    RegisterPresenter registerPresenter;
    String passwd, retypepasswd, email, name, noIdentity, address, phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_register);
        initView();

        initPresenter();
        initAnimation();
        register();
        login();
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
        relativeLayout = findViewById(R.id.relativeRegister);
        etAddNama = findViewById(R.id.etAddNama);
        etAddEmail = findViewById(R.id.etAddEmail);
        etAddPassword = findViewById(R.id.etAddPassword);
        etAddRetypePassword = findViewById(R.id.etAddRetypePassword);
        etAddNoKTP = findViewById(R.id.etAddNoKTP);
        etAddAlamat = findViewById(R.id.etAddAlamat);
        etAddNoTelp = findViewById(R.id.etAddNoTelp);
        btnRegister = findViewById(R.id.btnRegister2);
        btnLogin = findViewById(R.id.btnLogin2);
        animationDrawable = (AnimationDrawable) relativeLayout.getBackground();
    }

    private void initAnimation() {
        //animation duration
        animationDrawable.setEnterFadeDuration(3000);
        animationDrawable.setExitFadeDuration(3000);
    }

    private void initPresenter() {
        //initial RegisterPresenter
        registerPresenter = new RegisterPresenter(this);
    }

    private void initVariable() {
        name = etAddNama.getText().toString();
        email = etAddEmail.getText().toString();
        passwd = etAddPassword.getText().toString();
        retypepasswd = etAddRetypePassword.getText().toString();
        noIdentity = etAddNoKTP.getText().toString();
        address = etAddAlamat.getText().toString();
        phone = etAddNoTelp.getText().toString();
    }

    private void login() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent login = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(login);
                finish();
            }
        });
    }

    private void register() {
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validation()==true){
                    registerPresenter.saveUser();
                }
            }
        });
    }

    private boolean validation(){
        initVariable();
        validNama();
        validEmail();
        validPassword();
        validRetypePassword();
        validNoKTP();
        validAlamat();
        validNoTelp();

        if (validNama() == "false" || validEmail() == "false" || validPassword() == "false" || validRetypePassword() == "false" || validNoKTP() == "false" || validAlamat() == "false" || validNoTelp() == "false" ){
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
                btnNext = layout.findViewById(R.id.btnNext);

                btnNext.setOnClickListener(new View.OnClickListener() {
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
                tvMessage = layout.findViewById(R.id.tvMessage);
                tvType = layout.findViewById(R.id.tvType);
                btnRetry = layout.findViewById(R.id.btnRetry);
                tvMessage.setText(messsage);
                tvType.setText("Gagal Bergabung");

                btnRetry.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        pw.dismiss();
                        registerPresenter.saveUser();
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
            etAddEmail.setBackgroundResource(R.drawable.bg_sounded_trans_red);
            etAddEmail.setHint("Isikan email");
            return "false";
        } else {
            if (Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                etAddEmail.setBackgroundResource(R.drawable.bg_rounded_trans_green);
                return "true";
            } else {
                etAddEmail.setBackgroundResource(R.drawable.bg_sounded_trans_red);
                etAddEmail.setText("");
                etAddEmail.setHint("Isikan email dengan benar");
                return "false";
            }
        }
    }

    @NonNull
    private String validNama(){
        if(name.isEmpty()){
            etAddNama.setBackgroundResource(R.drawable.bg_sounded_trans_red);
            etAddNama.setHint("Isikan nama");
            return "false";
        } else {
            etAddNama.setBackgroundResource(R.drawable.bg_rounded_trans_green);
            return "true";
        }
    }

    @NonNull
    private String validPassword(){
        if(passwd.isEmpty()){
            etAddPassword.setBackgroundResource(R.drawable.bg_sounded_trans_red);
            etAddPassword.setHint("Isikan password");
            return "false";
        } else {
            if (passwd.length()<6){
                etAddPassword.setBackgroundResource(R.drawable.bg_sounded_trans_red);
                etAddPassword.setText("");
                Toast.makeText(this, "Password minimal 6 karakter", Toast.LENGTH_SHORT).show();
                return "false";
            } else {
                etAddPassword.setBackgroundResource(R.drawable.bg_rounded_trans_green);
                return "true";
            }
        }
    }

    @NonNull
    private String validRetypePassword(){
        if (retypepasswd.isEmpty()){
            etAddRetypePassword.setBackgroundResource(R.drawable.bg_sounded_trans_red);
            etAddRetypePassword.setHint("Isikan ulang password");
            return "false";
        } else {
            if(retypepasswd.matches(passwd)){
                etAddRetypePassword.setBackgroundResource(R.drawable.bg_rounded_trans_green);
                return "true";
            } else {
                etAddRetypePassword.setBackgroundResource(R.drawable.bg_sounded_trans_red);
                etAddRetypePassword.setText("");
                etAddRetypePassword.setHint("Masukkan ulang password");
                return "false";
            }
        }
    }

    @NonNull
    private String validNoKTP(){
        if(noIdentity.isEmpty()){
            etAddNoKTP.setBackgroundResource(R.drawable.bg_sounded_trans_red);
            return "false";
        } else {
            etAddNoKTP.setBackgroundResource(R.drawable.bg_rounded_trans_green);
            return "true";
        }
    }

    @NonNull
    private String validAlamat(){
        if(address.isEmpty()){
            etAddAlamat.setBackgroundResource(R.drawable.bg_sounded_trans_red);
            return "false";
        } else {
            etAddAlamat.setBackgroundResource(R.drawable.bg_rounded_trans_green);
            return "true";
        }
    }

    @NonNull
    private String validNoTelp(){
        if(phone.isEmpty()){
            etAddNoTelp.setBackgroundResource(R.drawable.bg_sounded_trans_red);
            return "false";
        } else {
            etAddNoTelp.setBackgroundResource(R.drawable.bg_rounded_trans_green);
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
        return noIdentity;
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
    public void cantRegister() {
        initPopupWindow("");
    }

    @Override
    public void cantConnect() {

    }


}
