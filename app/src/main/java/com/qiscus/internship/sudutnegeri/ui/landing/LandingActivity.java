package com.qiscus.internship.sudutnegeri.ui.landing;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.qiscus.internship.sudutnegeri.R;
import com.qiscus.internship.sudutnegeri.adapter.project.ProjectAdapter;
import com.qiscus.internship.sudutnegeri.adapter.project.ProjectListener;
import com.qiscus.internship.sudutnegeri.data.model.DataProject;
import com.qiscus.internship.sudutnegeri.ui.admin.AdminActivity;
import com.qiscus.internship.sudutnegeri.ui.project.ProjectActivity;
import com.qiscus.internship.sudutnegeri.ui.register.RegisterActivity;
import com.qiscus.internship.sudutnegeri.ui.login.LoginActivity;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class LandingActivity extends AppCompatActivity implements LandingView, ProjectListener {

    private LandingPresenter landingPresenter;
    private ProjectAdapter projectAdapter;
    private Toolbar toolbar;
    Button btnLandLog, btnLandReg;
    RecyclerView rvLand;
    TextView title, tvLandCount, tvLandingNoData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_landing);
        setTitle("");
        initPresenter();
        initView();
        initDataPresenter();
        setupToolbar();
        setSupportActionBar(toolbar);

        login();
        rergister();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initPresenter();
    }

    private void initPresenter() {
        landingPresenter = new LandingPresenter(this);
        landingPresenter.showProject();
    }

    private void setupToolbar() {
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    private void initView(){
        toolbar = findViewById(R.id.toolbar);
        title = findViewById(R.id.tvToolbarTitle);
        btnLandLog = findViewById(R.id.btnLandLog);
        btnLandReg = findViewById(R.id.btnLandReg);
        tvLandCount = findViewById(R.id.tvLandCount);
        tvLandingNoData = findViewById(R.id.tvLandingNoData);
        rvLand = findViewById(R.id.rvLand);
        title.setText("Sudut Negeri");
    }

    private void initDataPresenter() {
        landingPresenter.getProjectByTime();
    }

    private void rergister() {
        btnLandReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent list = new Intent(LandingActivity.this, RegisterActivity.class);
                startActivity(list);
            }
        });
    }

    private void login() {
        btnLandLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent login = new Intent(LandingActivity.this, LoginActivity.class);
                startActivity(login);
            }
        });
    }

    @Override
    public void success(List<DataProject> dataProjectList) {

    }

    @Override
    public void successShowProjectByTime(List<DataProject> dataProject) {
        if (dataProject.size()>0){
            tvLandingNoData.setVisibility(View.GONE);
        }
        projectAdapter = new ProjectAdapter(dataProject);
        projectAdapter.setAdapterListener(this);
        rvLand.setLayoutManager(new LinearLayoutManager(this));
        rvLand.setAdapter(projectAdapter);
    }

    @Override
    public void onProjectClick(final DataProject dataProject) {

    }

    @Override
    public void displayImg(ImageView imgProject, DataProject dataProject) {
        //Picasso.with(this).load(dataProject.getPhoto()).into(imgProject);
    }

}
