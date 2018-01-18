package com.qiscus.internship.sudutnegeri.ui.project;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.qiscus.internship.sudutnegeri.R;
import com.qiscus.internship.sudutnegeri.data.model.DataProject;
import com.qiscus.internship.sudutnegeri.data.model.DataUser;
import com.qiscus.internship.sudutnegeri.util.Constant;
import com.squareup.picasso.Picasso;

import me.biubiubiu.justifytext.library.JustifyTextView;

public class ProjectActivity extends AppCompatActivity implements ProjectView {

    private ProjectPresenter projectPresenter;
    private DataProject dataProject;
    private DataUser dataUser;
    Button btnProjectDonate;
    FloatingActionButton fabProjectChat;
    ImageView ivProjectPhoto;
    int funds = 0;
    JustifyTextView jtProjectInformation;
    ProgressBar pbProjectProgress;
    String param;
    TextView tvProjectName, tvProjectLocation, tvProjectTarget, tvProjectCreator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_project);
        setTitle("");
        initPresenter();
        initView();
        initDataIntent();
        initDataPresenter();
        initEditabel();

        putProject();
    }



    private void initPresenter() {
        projectPresenter = new ProjectPresenter(this);
    }

    private void initView() {
        btnProjectDonate = findViewById(R.id.btnProjectDonate);
        fabProjectChat = findViewById(R.id.fabProjectChat);
        ivProjectPhoto = findViewById(R.id.ivProjectPhoto);
        jtProjectInformation = findViewById(R.id.jtProjectInfo);
        pbProjectProgress = findViewById(R.id.pbProjectProgress);
        tvProjectName = findViewById(R.id.tvProjectName);
        tvProjectLocation = findViewById(R.id.tvProjectLocation);
        tvProjectTarget = findViewById(R.id.tvProjectTarget);
        tvProjectCreator = findViewById(R.id.tvProjectCreator);
    }

    private void initDataIntent() {
        dataProject = getIntent().getParcelableExtra(Constant.Extra.Project);
        param = getIntent().getStringExtra(Constant.Extra.param);
        if (dataProject == null) finish();
    }

    private void initDataPresenter() {
        projectPresenter.getPorjectById(dataProject);
        projectPresenter.getUserById(dataProject);
    }

    private void initEditabel() {
        if (param.equalsIgnoreCase("admin")){
            btnProjectDonate.setText("Verifikasi");
        }
    }

    private void putProject() {
        btnProjectDonate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (param.equalsIgnoreCase("admin")){
                    projectPresenter.putProject(dataProject);
                } else if (param.equalsIgnoreCase("creator")){

                }
            }
        });
    }

    @Override
    public void successProjectById(DataProject dataProject) {
        tvProjectName.setText(dataProject.getNameProject());
        tvProjectLocation.setText(dataProject.getLocation());
        tvProjectTarget.setText(dataProject.getTargetAt());

        Picasso.with(ProjectActivity.this)
                .load(dataProject.getPhoto())
                .into(ivProjectPhoto);
    }

    @Override
    public void successUserById(DataUser dataUser) {
        tvProjectCreator.setText(dataUser.getName());
    }

    @Override
    public void successPutProject(DataProject dataProject) {
        btnProjectDonate.setVisibility(View.INVISIBLE);
    }

    @Override
    public int getFunds() {
        return funds;
    }
}
