package com.qiscus.internship.sudutnegeri.ui.AddUser;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.Toast;

import com.qiscus.internship.sudutnegeri.R;
import com.qiscus.internship.sudutnegeri.data.model.Car;
import com.qiscus.internship.sudutnegeri.util.Constant;

import java.util.regex.Pattern;

public class AddUserActivity extends AppCompatActivity implements AddUserView {

    RelativeLayout relativeLayout;
    AnimationDrawable animationDrawable;
    EditText etAddNama;
    EditText etAddEmail;
    EditText etAddPassword;
    EditText etAddRetypePassword;
    EditText etAddNoKTP;
    EditText etAddAlamat;
    EditText etAddNoTelp;
    Button btnRegister;
    AddUserPresenter addUserPresenter;
    String passwd, retypepasswd, email, nama, noktp, alamat, notelp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_add_user);
        initView();
        initPresenter();
        initAnimation();

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validation()==true){
                   addUserPresenter.saveCar();
                }
            }
        });
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
        animationDrawable = (AnimationDrawable) relativeLayout.getBackground();
    }

    private void initAnimation() {
        //animation duration
        animationDrawable.setEnterFadeDuration(3000);
        animationDrawable.setExitFadeDuration(3000);
    }

    private void initPresenter() {
        addUserPresenter = new AddUserPresenter(this);
    }

    private boolean validation(){
        nama = etAddNama.getText().toString();
        email = etAddEmail.getText().toString();
        passwd = etAddPassword.getText().toString();
        retypepasswd = etAddRetypePassword.getText().toString();
        noktp = etAddNoKTP.getText().toString();
        alamat = etAddAlamat.getText().toString();
        notelp = etAddNoTelp.getText().toString();

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

    @NonNull
    private String validEmail(){
        if(email.isEmpty()){
            etAddEmail.setBackgroundResource(R.drawable.edit_text_bg_red);
            etAddEmail.setHint("Isikan email");
            return "false";
        } else {
            if (Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                etAddEmail.setBackgroundResource(R.drawable.edit_text_bg_green);
                return "true";
            } else {
                etAddEmail.setBackgroundResource(R.drawable.edit_text_bg_red);
                etAddEmail.setText("");
                etAddEmail.setHint("Isikan email dengan benar");
                return "false";
            }
        }
    }

    @NonNull
    private String validNama(){
        if(nama.isEmpty()){
            etAddNama.setBackgroundResource(R.drawable.edit_text_bg_red);
            etAddNama.setHint("Isikan nama");
            return "false";
        } else {
            etAddNama.setBackgroundResource(R.drawable.edit_text_bg_green);
            return "true";
        }
    }

    @NonNull
    private String validPassword(){
        if(passwd.isEmpty()){
            etAddPassword.setBackgroundResource(R.drawable.edit_text_bg_red);
            etAddPassword.setHint("Isikan password");
            return "false";
        } else {
            if (passwd.length()<6){
                etAddPassword.setBackgroundResource(R.drawable.edit_text_bg_red);
                etAddPassword.setText("");
                Toast.makeText(this, "Password minimal 6 karakter", Toast.LENGTH_SHORT).show();
                return "false";
            } else {
                etAddPassword.setBackgroundResource(R.drawable.edit_text_bg_green);
                return "true";
            }
        }
    }

    @NonNull
    private String validRetypePassword(){
        if (retypepasswd.isEmpty()){
            etAddRetypePassword.setBackgroundResource(R.drawable.edit_text_bg_red);
            etAddRetypePassword.setHint("Isikan ulang password");
            return "false";
        } else {
            if(retypepasswd.matches(passwd)){
                etAddRetypePassword.setBackgroundResource(R.drawable.edit_text_bg_green);
                return "true";
            } else {
                etAddRetypePassword.setBackgroundResource(R.drawable.edit_text_bg_red);
                etAddRetypePassword.setText("");
                etAddRetypePassword.setHint("Masukkan ulang password");
                return "false";
            }
        }
    }

    @NonNull
    private String validNoKTP(){
        if(noktp.isEmpty()){
            etAddNoKTP.setBackgroundResource(R.drawable.edit_text_bg_red);
            return "false";
        } else {
            etAddNoKTP.setBackgroundResource(R.drawable.edit_text_bg_green);
            return "true";
        }
    }

    private String validAlamat(){
        if(alamat.isEmpty()){
            etAddAlamat.setBackgroundResource(R.drawable.edit_text_bg_red);
            return "false";
        } else {
            etAddAlamat.setBackgroundResource(R.drawable.edit_text_bg_green);
            return "true";
        }
    }

    private String validNoTelp(){
        if(notelp.isEmpty()){
            etAddNoTelp.setBackgroundResource(R.drawable.edit_text_bg_red);
            return "false";
        } else {
            etAddNoTelp.setBackgroundResource(R.drawable.edit_text_bg_green);
            return "true";
        }
    }

    //implement AddUserView
    @Override
    public String getMake() {
        return etAddNama.getText().toString();
    }

    @Override
    public String getModel() {
        return etAddEmail.getText().toString();
    }

    @Override
    public String getYear() {
        return etAddAlamat.getText().toString();
    }

    @Override
    public void showSaveCar(Car car) {
        Toast.makeText(this, "Selamat bergabung bersama para pengabdi negeri ini", Toast.LENGTH_LONG).show();
        Intent intent = new Intent();
        intent.putExtra(Constant.Extra.DATA, (Parcelable) car);
        setResult(RESULT_OK, intent);
        finish();
    }
}
