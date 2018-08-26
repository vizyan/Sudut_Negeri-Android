package com.qiscus.internship.sudutnegeri.ui.user;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.qiscus.internship.sudutnegeri.R;
import com.qiscus.internship.sudutnegeri.data.model.DataUser;
import com.qiscus.internship.sudutnegeri.ui.about.AboutActivity;
import com.qiscus.internship.sudutnegeri.ui.admin.AdminActivity;
import com.qiscus.internship.sudutnegeri.ui.dashboard.DashboardActivity;
import com.qiscus.internship.sudutnegeri.ui.landing.LandingActivity;
import com.qiscus.internship.sudutnegeri.util.CircleTransform;
import com.qiscus.internship.sudutnegeri.util.Constant;
import com.qiscus.internship.sudutnegeri.util.Popup;
import com.squareup.picasso.Picasso;

public class UserActivity extends AppCompatActivity implements UserView, Popup.PopupListener {

    private UserPresenter userPresenter;
    private DataUser dataUser;
    Button btnUserSave, btnDrawerLogout, btnUserUnverify;
    DrawerLayout drawerLayout;
    EditText etUserName, etUserEmail, etUserIdNumber, etUserAddress, etUserPhone;
    ImageView ivDrawerPhoto, ivUserPhoto, ivBack;
    NavigationView navigationView;
    ProgressDialog progressDialog;
    String email, passwd, param, name, phone, address;
    TextView title, tvDrawerName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_user);
        setTitle("");
        initPresenter();
        initView();
        initNavigation();
        initDataIntent();
//        initDataPreference();
        initDataPresenter();
        initDataDrawer();
        initEditable();

        putUser();
        logout();
        unverifyUser();
        backPressed();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (param.equalsIgnoreCase("user")){
            Intent dashboard = new Intent(UserActivity.this, DashboardActivity.class);
            dashboard.putExtra(Constant.Extra.DATA, dataUser);
            startActivity(dashboard);
            finish();
        }
    }

    private void initPresenter() {
        userPresenter = new UserPresenter(this);
    }

    private void initDataPreference(){
        if (param.equalsIgnoreCase("admin")){

        } else {
            Gson gson = new Gson();
            SharedPreferences preferences = getSharedPreferences("LoginPreference", MODE_PRIVATE);
            email = preferences.getString("email", "");
            passwd = preferences.getString("password", "");
            String DataUser = preferences.getString("DataUser", "");
            dataUser = gson.fromJson(DataUser, DataUser.class);
            setData();
            Log.e("Ini preference", "Ini isinya "+dataUser);
        }
        Log.e("Admin", "Ini admin " + param);
    }

    private void savePreference(DataUser dataUser){
        Gson gson = new Gson();
        String DataUser = gson.toJson(dataUser);
        SharedPreferences preferences = getSharedPreferences("LoginPreference", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("DataUser", DataUser);
        editor.commit();
        editor.apply();
    }

    private void initView() {
        etUserName = findViewById(R.id.etUserName);
        etUserEmail = findViewById(R.id.etuserEmail);
        etUserIdNumber = findViewById(R.id.etUserIdNumber);
        etUserAddress = findViewById(R.id.etUserAddress);
        etUserPhone = findViewById(R.id.etUserPhone);
        btnUserSave = findViewById(R.id.btnUserSave);
        btnUserUnverify = findViewById(R.id.btnUserUnverify);
        title = findViewById(R.id.tvToolbarTitle);
        tvDrawerName = findViewById(R.id.tvDrawerName);
        ivDrawerPhoto = findViewById(R.id.ivDrawerUser);
        ivUserPhoto = findViewById(R.id.ivUserPhoto);
        ivBack = findViewById(R.id.ivBack);
        btnDrawerLogout = findViewById(R.id.btnDrawerLogout);
        drawerLayout = findViewById(R.id.dlUser);
        navigationView = findViewById(R.id.nvUser);
    }

    private void initNavigation() {
        navigationView.setNavigationItemSelectedListener(menuItem -> {
                if(menuItem.isChecked()) menuItem.setChecked(false);
                else menuItem.setChecked(true);

                drawerLayout.closeDrawers();
                switch (menuItem.getItemId()){
                    case R.id.navigation1:
                        Intent dashboard = new Intent(UserActivity.this, DashboardActivity.class);
                        dashboard.putExtra(Constant.Extra.DATA, dataUser);
                        startActivity(dashboard);
                        finish();
                        return false;
                    case R.id.navigation2:
                        Toast.makeText(getApplicationContext(),"Anda Berada di Profil",Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.navigation3:
                        Intent aboutUs = new Intent(UserActivity.this, AboutActivity.class);
                        aboutUs.putExtra(Constant.Extra.DATA, dataUser);
                        startActivity(aboutUs);
                        finish();
                        return false;
                    default:
                        Toast.makeText(getApplicationContext(),"Kesalahan Terjadi ",Toast.LENGTH_SHORT).show();
                        return true;
                }
        });
    }

    private void initDataIntent() {
        dataUser = getIntent().getParcelableExtra(Constant.Extra.DATA);
        param = getIntent().getStringExtra(Constant.Extra.param);
//        if (param.equalsIgnoreCase("admin")){
//        } else {
//            initDataPreference();
//        }
        if (param == null) finish();
        Log.d("Data Intent", ""+param+" sama "+dataUser);
    }

    private void initDataPresenter(){
        initProgressDialog();
        userPresenter.getUserById(dataUser);
    }

    private void initDataDrawer() {
        tvDrawerName.setText(dataUser.getName());

        Picasso.with(this)
                .load(dataUser.getPhoto())
                .transform(new CircleTransform())
                .into(ivDrawerPhoto);
    }

    private void initEditable() {
        if (param.equalsIgnoreCase("admin")){
            btnUserSave.setText("Verifikasi");
            etUserName.setEnabled(false);
            etUserEmail.setEnabled(false);
            etUserIdNumber.setEnabled(false);
            etUserAddress.setEnabled(false);
            etUserPhone.setEnabled(false);
            btnUserUnverify.setVisibility(View.VISIBLE);
            navigationView.setVisibility(View.GONE);
            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        } else {
            btnUserSave.setText("Simpan");
            etUserEmail.setEnabled(false);
            etUserIdNumber.setEnabled(false);
        }
    }

    private void initVariable() {
        name = etUserName.getText().toString();
        address = etUserAddress.getText().toString();
        phone = etUserPhone.getText().toString();
    }

    private void initProgressDialog(){
        progressDialog = new ProgressDialog(UserActivity.this);
        progressDialog.setTitle(null);
        progressDialog.setMessage("Tunggu beberapa saat");
        progressDialog.setIndeterminate(false);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    private void putUser() {
        btnUserSave.setOnClickListener(v ->  {
            initVariable();
            validatePhone();
            if (validatePhone()==true){
                initProgressDialog();
                if (param.equalsIgnoreCase("admin")){
                    userPresenter.putUser(dataUser.getId(),name, address, phone, "yes", "admin" );
                } else {
                    userPresenter.putUser(dataUser.getId(),name, address, phone, "yes", "user" );
                }
            }
        });
    }

    private void unverifyUser(){
        btnUserUnverify.setOnClickListener(v -> {
            initProgressDialog();
            userPresenter.unverifyUser(dataUser.getId());
        });
    }

    private void setData(){
        etUserName.setText(dataUser.getName());
        etUserEmail.setText(dataUser.getEmail());
        etUserIdNumber.setText(dataUser.getIdentityNumber());
        etUserAddress.setText(dataUser.getAddress());
        etUserPhone.setText(dataUser.getPhone());
        Picasso.with(this)
                .load(dataUser.getPhoto())
                .transform(new CircleTransform())
                .into(ivUserPhoto);
    }

    private void logout(){
        btnDrawerLogout.setOnClickListener(v ->  {
                AlertDialog.Builder builder = new AlertDialog.Builder(UserActivity.this);
                builder.setMessage("Apakah anda yakin ingin keluar ?");
                builder.setPositiveButton("Ya", new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int which){
                        userPresenter.logout();
                    }
                });
                builder.setNegativeButton("Tidak", null);
                builder.show();
        });
    }

    private void backPressed(){
        ivBack.setOnClickListener(v -> {
            if (param.equalsIgnoreCase("admin")){
                finish();
            } else {
                Intent dashboard = new Intent(UserActivity.this, DashboardActivity.class);
                dashboard.putExtra(Constant.Extra.DATA, dataUser);
                startActivity(dashboard);
                finish();
            }
        });

    }

    private boolean validatePhone(){
        phone = etUserPhone.getText().toString();
        int a = phone.length();
        if(phone.isEmpty()){
            etUserPhone.setHint("Isikan nomer HP");
            etUserPhone.setBackgroundResource(R.drawable.bg_rounded_trans_red);
            return false;
        } else if (!(11 <= a && a <= 13)){
            etUserPhone.setText("");
            etUserPhone.setHint("Isikan nomer HP dengan benar");
            etUserPhone.setBackgroundResource(R.drawable.bg_rounded_trans_red);
            return false;
        } else {
            etUserPhone.setBackgroundResource(R.drawable.bg_rounded_solid_purple);
            return true;
        }
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public String getPassword() {
        return email;
    }

    @Override
    public void successUnverify() {
        progressDialog.dismiss();
        Popup popup = new Popup(this);
        popup.PopupWindow(this, "success", "Akun ditolak");
    }

    @Override
    public void failed(String s) {
        progressDialog.dismiss();
    }

    @Override
    public void successUserbyId(DataUser dataUser) {
        progressDialog.dismiss();
        setData();
    }

    @Override
    public void successPutUser(DataUser dataUser) {
        progressDialog.dismiss();
        Popup popup = new Popup(this);
        if (param.equalsIgnoreCase("admin")){
            popup.PopupWindow(this, "success", "Verifikasi berhasil");
        } else {
            savePreference(dataUser);
            popup.PopupWindow(this, "success", "Akun berhasil diperbarui");
        }
        this.dataUser = dataUser;
        initDataDrawer();
    }

    @Override
    public void successLogout() {
        Intent logout = new Intent(UserActivity.this, LandingActivity.class);
        startActivity(logout);
        finish();
    }

    @Override
    public void PopupSuccess(String success) {
        if (param.equalsIgnoreCase("admin")){
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    // TODO Auto-generated method stub
                    Intent admin = new Intent(UserActivity.this, AdminActivity.class);
                    startActivity(admin);
                    UserActivity.this.finish();
                }

                private void finish() {
                    // TODO Auto-generated method stub

                }
            }, 1000);
        }
    }
}
