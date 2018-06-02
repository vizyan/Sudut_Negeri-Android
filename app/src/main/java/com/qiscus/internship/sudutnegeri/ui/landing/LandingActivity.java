package com.qiscus.internship.sudutnegeri.ui.landing;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
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
import com.qiscus.internship.sudutnegeri.util.CircleTransform;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class LandingActivity extends AppCompatActivity implements LandingView, ProjectListener {

    private List<DataProject> dataProjects;
    private LandingPresenter landingPresenter;
    private ProjectAdapter projectAdapter;
    private Toolbar toolbar;
    Button btnLandLog, btnLandReg;
    RecyclerView rvLand;
    TextView title, tvLandCount, tvLandingNoData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        initPresenter();
        initDataPresenter();
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_landing);
        setTitle("");
        initView();
        initAdapter();
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
        landingPresenter.getDonation();
    }

    private void initAdapter(){
        projectAdapter = new ProjectAdapter(dataProjects);
        projectAdapter.setAdapterListener(this);
        rvLand.setLayoutManager(new LinearLayoutManager(this));
        rvLand.setAdapter(projectAdapter);
    }
    private void rergister() {
        btnLandReg.setOnClickListener(v -> {
            Intent list = new Intent(LandingActivity.this, RegisterActivity.class);
            startActivity(list);
        });
    }

    private void login() {
        btnLandLog.setOnClickListener(v -> {
            Intent login = new Intent(LandingActivity.this, LoginActivity.class);
            startActivity(login);
        });
    }

    @Override
    public void successShowProjectByTime(List<DataProject> dataProject) {
        if (dataProject.size()>0){
            tvLandingNoData.setVisibility(View.GONE);
        }
        this.dataProjects = dataProject;
        initAdapter();
    }

    @Override
    public void successDonation(String donation) {
        tvLandCount.setText("Total donasi terkumpul Rp " + donation);
    }

    @Override
    public void failed(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onProjectClick(final DataProject dataProject) {
        Toast.makeText(LandingActivity.this, "Silahkan login untuk mengetahui detail projek", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void displayImgProject(ImageView imgProject, DataProject dataProject) {
        Picasso.with(this)
                .load(dataProject.getPhoto())
                .transform(new CircleTransform())
                .into(imgProject);
    }

}
