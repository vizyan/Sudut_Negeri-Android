package com.qiscus.internship.sudutnegeri.ui.LandingPage;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.qiscus.internship.sudutnegeri.R;
import com.qiscus.internship.sudutnegeri.data.model.Car;
import com.qiscus.internship.sudutnegeri.ui.AddUser.AddUserActivity;
import com.qiscus.internship.sudutnegeri.ui.LoginActivity;

import java.util.List;

public class LandingActivity extends AppCompatActivity implements LandingView {

    Button btnLogin, btnRegister;
    RecyclerView recyclerView;
    LandingPresenter landingPresenter;
    LandingAdapter landingAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);

        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);
        recyclerView = findViewById(R.id.recyclerLanding);
        initView();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent login = new Intent(LandingActivity.this, LoginActivity.class);
                startActivity(login);
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent list = new Intent(LandingActivity.this, AddUserActivity.class);
                startActivity(list);
            }
        });
    }

    private void initView(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        landingAdapter = new LandingAdapter(this);
        recyclerView.setAdapter(landingAdapter);
        recyclerView.setLayoutManager(linearLayoutManager);

        landingPresenter = new LandingPresenter(this);
        landingPresenter.showList();
    }

    @Override
    public void showData(List<Car> projectList) {
        landingAdapter.setData(projectList);
    }
}
