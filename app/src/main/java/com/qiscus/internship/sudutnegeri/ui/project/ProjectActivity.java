package com.qiscus.internship.sudutnegeri.ui.project;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.qiscus.internship.sudutnegeri.R;
import com.qiscus.internship.sudutnegeri.data.model.DataProject;
import com.qiscus.internship.sudutnegeri.data.model.DataUser;
import com.qiscus.internship.sudutnegeri.ui.admin.AdminActivity;
import com.qiscus.internship.sudutnegeri.ui.user.UserActivity;
import com.qiscus.internship.sudutnegeri.util.Constant;
import com.qiscus.sdk.Qiscus;
import com.qiscus.sdk.data.remote.QiscusPusherApi;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import me.biubiubiu.justifytext.library.JustifyTextView;
import retrofit2.HttpException;

public class ProjectActivity extends AppCompatActivity implements ProjectView {

    private ProjectPresenter projectPresenter;
    private DataProject dataProject;
    private DataUser dataUser;
    Button btnProjectDonate;
    Date date;
    FloatingActionButton fabProjectChat;
    ImageView ivProjectPhoto;
    int funds = 0;
    JustifyTextView jtProjectInformation;
    ProgressBar pbProjectProgress;
    String param, postDate;
    TextView tvProjectName, tvProjectLocation, tvProjectTarget, tvProjectCreator, tvPopupFMsg, tvPopupFType, tvPopupSMsg, tvPopupSType;

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
        initEditable();

        putProject();
        chat();
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

    private void initEditable() {
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
                } else if (param.equalsIgnoreCase("negeri")){
                    initDonate();
                }
            }
        });
    }

    private void chat() {
        fabProjectChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Qiscus.buildChatWith(dataUser.getEmail())
                        .build(ProjectActivity.this, new Qiscus.ChatActivityBuilderListener() {
                            @Override
                            public void onSuccess(Intent intent) {
                                startActivity(intent);
                                //QiscusPusherApi.getInstance().listenRoom();
                            }
                            @Override
                            public void onError(Throwable throwable) {
                                if (throwable instanceof HttpException) { //Error response from server
                                    HttpException e = (HttpException) throwable;
                                    try {
                                        String errorMessage = e.response().errorBody().string();
                                        JSONObject json = new JSONObject(errorMessage).getJSONObject("error");
                                        String finalError = json.getString("message");
                                        if (json.has("detailed_messages") ) {
                                            JSONArray detailedMessages = json.getJSONArray("detailed_messages");
                                            finalError = (String) detailedMessages.get(0);
                                        }
                                        Toast.makeText(ProjectActivity.this, finalError, Toast.LENGTH_LONG).show();
                                    } catch (IOException e1) {
                                        e1.printStackTrace();
                                    } catch (JSONException e1) {
                                        e1.printStackTrace();
                                    }
                                } else if (throwable instanceof IOException) { //Error from network
                                    Toast.makeText(ProjectActivity.this, "Tidak dapat terkoneksi dengan server", Toast.LENGTH_LONG).show();
                                } else { //Unknown error
                                    Toast.makeText(ProjectActivity.this, "Kesalahan", Toast.LENGTH_LONG).show();
                                }
                            }
                        });
            }
        });
    }

    private void popupWindow(String messsage, String param) {
        try {
            LayoutInflater inflater = (LayoutInflater) ProjectActivity.this
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            if (messsage.equals("success")){
                View layout = inflater.inflate(R.layout.layout_popup_success, null);
                final PopupWindow pw = new PopupWindow(layout, RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT, true);
                pw.setOutsideTouchable(false);
                pw.showAtLocation(layout, Gravity.CENTER, 0, 0);
                tvPopupSMsg = layout.findViewById(R.id.tvPopupSMsg);
                tvPopupSType = layout.findViewById(R.id.tvPopupSType);

                if(param.equalsIgnoreCase("admin")){

                    tvPopupSType.setText("Verifikasi berhasil");
                    tvPopupSMsg.setText("Project " + dataProject.getNameProject());

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            // TODO Auto-generated method stub
                            Intent admin = new Intent(ProjectActivity.this, AdminActivity.class);
                            startActivity(admin);
                            ProjectActivity.this.finish();
                        }

                        private void finish() {
                            // TODO Auto-generated method stub

                        }
                    }, 1000);
                } else {

                }
            } else {
                View layout = inflater.inflate(R.layout.layout_popup_failed, null);
                final PopupWindow pw = new PopupWindow(layout, RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT, true);
                pw.showAtLocation(layout, Gravity.CENTER, 0, 0);
                tvPopupFMsg = layout.findViewById(R.id.tvPopupFMsg);
                tvPopupFType = layout.findViewById(R.id.tvPopupFType);
                tvPopupFMsg.setText(messsage);
                tvPopupFType.setText("Gagal");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initDonate() {

    }

    private void setupDate(){
        SimpleDateFormat form = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMMM yyyy");
        date = new Date();
        try {
            date = form.parse(dataProject.getTargetAt());
            postDate = simpleDateFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void successProjectById(DataProject dataProject) {
        this.dataProject = dataProject;
        setupDate();
        tvProjectName.setText(dataProject.getNameProject());
        tvProjectLocation.setText(dataProject.getLocation());
        tvProjectTarget.setText("Hingga " +postDate);

        Picasso.with(ProjectActivity.this)
                .load(dataProject.getPhoto())
                .into(ivProjectPhoto);
    }

    @Override
    public void successUserById(DataUser dataUser) {
        this.dataUser = dataUser;
        tvProjectCreator.setText(dataUser.getName());
    }

    @Override
    public void successPutProject(DataProject dataProject) {
        if (param.equalsIgnoreCase("admin")){
            popupWindow("success", param);
        }
    }

    @Override
    public int getFunds() {
        return funds = funds + dataProject.getFunds();
    }
}
