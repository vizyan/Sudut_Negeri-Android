package com.qiscus.internship.sudutnegeri.ui.DetailUser;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import com.qiscus.internship.sudutnegeri.R;
import com.qiscus.internship.sudutnegeri.data.model.DataUser;
import com.qiscus.internship.sudutnegeri.util.Constant;

public class DetailUserActivity extends AppCompatActivity implements DetailUserView {

    private DataUser dataUser;
    private DetailUserPresenter detailUserPresenter;
    TextView tvId, tvName, tvEmail,  tvCreatedAt, tvUpdateAt, tvIdentityNumber, tvAddress, tvPhone;
    Button btnVerify;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_user);
        initView();
        initIntentData();
        initPresenter();
    }


    private void initPresenter() {
        detailUserPresenter = new DetailUserPresenter(this);
        detailUserPresenter.getDetailUserById(dataUser);
    }

    private void initIntentData() {
        dataUser = getIntent().getParcelableExtra(Constant.Extra.DATA);
        if (dataUser == null) finish();
    }

    private void initView() {
        tvId = findViewById(R.id.tvId);
        tvName = findViewById(R.id.tvName);
        tvEmail = findViewById(R.id.tvEmail);
        tvCreatedAt = findViewById(R.id.tvCreatedAt);
        tvUpdateAt = findViewById(R.id.tvUpdateAt);
        tvIdentityNumber = findViewById(R.id.tvIdentityNumber);
        tvAddress = findViewById(R.id.tvAddress);
        tvPhone = findViewById(R.id.tvPhone);
        btnVerify = findViewById(R.id.btnVerify);

    }


    @Override
    public void showSuccessDetailUserById(DataUser dataUser) {
        // TODO: 11/18/17 what you want
        tvId.setText(String.valueOf(dataUser.getId()));
        tvName.setText(dataUser.getName());
        tvEmail.setText(dataUser.getEmail());
        tvCreatedAt.setText(dataUser.getCreatedAt());
        tvUpdateAt.setText(dataUser.getUpdatedAt());
        tvIdentityNumber.setText(dataUser.getIdentityNumber());
        tvAddress.setText(dataUser.getAddress());
        tvPhone.setText(dataUser.getPhone());

    }
}
