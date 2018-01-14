package com.qiscus.internship.sudutnegeri.ui.landing;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.qiscus.internship.sudutnegeri.ui.register.RegisterActivity;
import com.qiscus.internship.sudutnegeri.ui.login.LoginActivity;

import java.util.List;

public class LandingActivity extends AppCompatActivity implements LandingView, ProjectListener {

    Button btnLogin, btnRegister;
    RecyclerView recyclerView;
    LandingPresenter landingPresenter;
    ProjectAdapter projectAdapter;
    TextView title;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        initPresenter();
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_landing);
        initView();
        setupToolbar();
        title.setText("Sudut Negeri");
        setSupportActionBar(toolbar);
        setTitle("");

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
                Intent list = new Intent(LandingActivity.this, RegisterActivity.class);
                startActivity(list);
            }
        });
    }

    private void initPresenter() {
        landingPresenter = new LandingPresenter(this);
        landingPresenter.showUser();
    }

    private void setupToolbar() {
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    private void initView(){
        toolbar = findViewById(R.id.toolbar);
        title = findViewById(R.id.title_bar);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);
        recyclerView = findViewById(R.id.recyclerLanding);
    }

    @Override
    public void showData(List<DataProject> dataProjectList) {
        projectAdapter = new ProjectAdapter(dataProjectList);
        projectAdapter.setAdapterListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(projectAdapter);
    }

    @Override
    public void onProjectClick(final DataProject dataProject) {
        //Intent intent = new Intent(this, DetailActivity.class);
        //intent.putExtra(Constant.Extra.DATA, (Parcelable) dataProject);
        //startActivity(intent);
        Toast.makeText(LandingActivity.this, "ini apa isinya " + dataProject.toString(), Toast.LENGTH_LONG);
        new AlertDialog.Builder(this)
                .setTitle("Hapus data ?")
                .setCancelable(false)
                .setMessage("Hapus data Car : " + dataProject.getNameProject())
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create()
                .show();
    }

    @Override
    public void displayImg(ImageView imgProject, DataProject dataProject) {

    }


}
