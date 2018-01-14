package com.qiscus.internship.sudutnegeri.ui.User;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.widget.EditText;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.qiscus.internship.sudutnegeri.R;
import com.qiscus.internship.sudutnegeri.data.model.DataUser;
import com.qiscus.internship.sudutnegeri.util.Constant;

public class UserActivity extends AppCompatActivity implements UserView {

    private UserPresenter presenter;
    private DataUser dataUser;
    private Toolbar toolbar;
    TextView title;
    EditText etUserName, etUserEmail, etUserIdNumber, etUserAddress, etUserPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_user);
        initView();
        setupToolbar();
        initIntent();
        initPresenter();
        setTitle("");
        title.setText("Profile");
    }

    private void initView() {
        etUserName = findViewById(R.id.etUserName);
        etUserEmail = findViewById(R.id.etuserEmail);
        etUserIdNumber = findViewById(R.id.etUserIdNumber);
        etUserAddress = findViewById(R.id.etUserAddress);
        etUserPhone = findViewById(R.id.etUserPhone);
        toolbar = findViewById(R.id.toolbar);
        title = findViewById(R.id.title_bar);
    }

    private void setupToolbar() {
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    private void initPresenter() {
        presenter = new UserPresenter(this);
        presenter.showUserById(dataUser);
    }

    private void initIntent() {
        dataUser = getIntent().getParcelableExtra(Constant.Extra.DATA);
        if (dataUser == null) finish();
    }

    @Override
    public void showSuccess(DataUser user) {
        etUserName.setText(user.getName());
        etUserEmail.setText(user.getEmail());
        etUserIdNumber.setText(user.getIdentityNumber());
        etUserAddress.setText(user.getAddress());
        etUserPhone.setText(user.getPhone());
    }
}
