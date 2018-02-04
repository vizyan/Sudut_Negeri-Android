package com.qiscus.internship.sudutnegeri.ui.project;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.animation.DecelerateInterpolator;
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
import com.qiscus.internship.sudutnegeri.ui.chat.ChatActivity;
import com.qiscus.internship.sudutnegeri.ui.splashscreen.SplashscreenActivity;
import com.qiscus.internship.sudutnegeri.util.Constant;
import com.qiscus.sdk.data.model.QiscusChatRoom;
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
    private Animator mCurrentAnimatorEffect;
    private ProgressBar progressBar;
    private ProgressDialog progressDialog;
    private int mShortAnimationDurationEffect;
    Button btnProjectDonate;
    ConstraintLayout clProject;
    Date date;
    FloatingActionButton fabProjectChat, fabProjectUnverify;
    ImageView ivProjectPhoto, ivBigPhoto, ivProjectVerify;
    int funds;
    JustifyTextView jtProjectInformation;
    ProgressBar pbProjectProgress;
    RelativeLayout rlBigPhoto;
    String param, postDate, verify;
    TextView tvProjectName, tvProjectLocation, tvProjectTarget, tvProjectCreator, tvPopupFMsg, tvPopupFType, tvPopupSMsg, tvPopupSType, tvProjectProgress;

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
        unverifyProject();
        chat();
        initZoomPhoto();
    }

    private void initPresenter() {
        projectPresenter = new ProjectPresenter(this);
    }

    private void initView() {
        btnProjectDonate = findViewById(R.id.btnProjectDonate);
        clProject = findViewById(R.id.clProject);
        fabProjectChat = findViewById(R.id.fabProjectChat);
        fabProjectUnverify = findViewById(R.id.fabProjectUnverify);
        ivProjectPhoto = findViewById(R.id.ivProjectPhoto);
        ivBigPhoto = findViewById(R.id.ivBigPhoto);
        ivProjectVerify = findViewById(R.id.ivProjectVerify);
        jtProjectInformation = findViewById(R.id.jtProjectInfo);
        pbProjectProgress = findViewById(R.id.pbProjectProgress);
        rlBigPhoto = findViewById(R.id.rlBigPhoto);
        tvProjectName = findViewById(R.id.tvProjectName);
        tvProjectLocation = findViewById(R.id.tvProjectLocation);
        tvProjectTarget = findViewById(R.id.tvProjectTarget);
        tvProjectCreator = findViewById(R.id.tvProjectCreator);
        progressBar = findViewById(R.id.pbProjectProgress);
        tvProjectProgress = findViewById(R.id.tvProjectProgress);

        mShortAnimationDurationEffect = getResources().getInteger(android.R.integer.config_shortAnimTime);
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
            fabProjectUnverify.setVisibility(View.VISIBLE);
        } else if (param.equalsIgnoreCase("sudut")){
            btnProjectDonate.setVisibility(View.INVISIBLE);
        }
    }

    private void initProgressBar(DataProject dataProject){
        progressBar.setMax(dataProject.getTargetFunds());
        progressBar.setProgress(dataProject.getFunds());
    }

    private void initProgressDialog(String message){
        progressDialog = new ProgressDialog(ProjectActivity.this);
        progressDialog.setTitle(null);
        progressDialog.setMessage(message);
        progressDialog.setIndeterminate(false);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    private void putProject() {
        btnProjectDonate.setOnClickListener(v -> {
            if (param.equalsIgnoreCase("admin")){
                initProgressDialog("Tunggu beberapa saat");
                projectPresenter.putProject(dataProject);
            } else if (param.equalsIgnoreCase("negeri")){
                initProgressDialog("Semoga rezekimu dilipatgandakan oleh-Nya");
                initDonate(dataProject);
            }
        });
    }

    private void unverifyProject(){
        fabProjectUnverify.setOnClickListener(v -> {
            initProgressDialog("Tunggu beberapa saat");
            projectPresenter.unverifyProject(dataProject.getId());
        });
    }

    private void chat() {
        fabProjectChat.setOnClickListener(v -> projectPresenter.chatUser(dataUser.getEmail()));
    }

    private void popupWindow(String messsage, String param, String verify) {
        try {
            LayoutInflater inflater = (LayoutInflater) ProjectActivity.this
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            if (messsage.equals("success")){
                View layout = inflater.inflate(R.layout.layout_popup_success, null);
                final PopupWindow pw = new PopupWindow(layout, RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT, true);
                pw.setOutsideTouchable(true);
                pw.showAtLocation(layout, Gravity.CENTER, 0, 0);
                tvPopupSMsg = layout.findViewById(R.id.tvPopupSMsg);
                tvPopupSType = layout.findViewById(R.id.tvPopupSType);

                if(param.equalsIgnoreCase("admin")){

                    tvPopupSType.setText(verify);
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

    private void initDonate(DataProject dataProject) {
        projectPresenter.putProject(dataProject);
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

    private void initZoomPhoto(){
        ivProjectPhoto.setOnClickListener(v -> setupZoom(ivProjectPhoto));
    }

    private void setupZoom(final View thumbView) {
        if (mCurrentAnimatorEffect != null) {
            mCurrentAnimatorEffect.cancel();
        }

        final Rect startBounds = new Rect();
        final Rect finalBounds = new Rect();
        final Point globalOffset = new Point();

        thumbView.getGlobalVisibleRect(startBounds);
        findViewById(R.id.clProject).getGlobalVisibleRect(finalBounds, globalOffset);
        startBounds.offset(-globalOffset.x, -globalOffset.y);
        finalBounds.offset(-globalOffset.x, -globalOffset.y);

        float startScale;
        if ((float) finalBounds.width() / finalBounds.height()
                > (float) startBounds.width() / startBounds.height()) {
            startScale = (float) startBounds.height() / finalBounds.height();
            float startWidth = startScale * finalBounds.width();
            float deltaWidth = (startWidth - startBounds.width()) / 2;
            startBounds.left -= deltaWidth;
            startBounds.right += deltaWidth;
        } else {
            startScale = (float) startBounds.width() / finalBounds.width();
            float startHeight = startScale * finalBounds.height();
            float deltaHeight = (startHeight - startBounds.height()) / 2;
            startBounds.top -= deltaHeight;
            startBounds.bottom += deltaHeight;
        }

        thumbView.setAlpha(0f);
        rlBigPhoto.setVisibility(View.VISIBLE);
        ivBigPhoto.setVisibility(View.VISIBLE);
        btnProjectDonate.setVisibility(View.INVISIBLE);
        fabProjectChat.setVisibility(View.INVISIBLE);
        fabProjectUnverify.setVisibility(View.INVISIBLE);

        rlBigPhoto.setPivotX(0f);
        rlBigPhoto.setPivotY(0f);

        AnimatorSet set = new AnimatorSet();
        set.play(ObjectAnimator.ofFloat(rlBigPhoto, View.X, startBounds.left, finalBounds.left))
                .with(ObjectAnimator.ofFloat(rlBigPhoto, View.Y, startBounds.top, finalBounds.top))
                .with(ObjectAnimator.ofFloat(rlBigPhoto, View.SCALE_X, startScale, 1f)).with(ObjectAnimator.ofFloat(rlBigPhoto, View.SCALE_Y, startScale, 1f));
        set.setDuration(mShortAnimationDurationEffect);
        set.setInterpolator(new DecelerateInterpolator());
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mCurrentAnimatorEffect = null;
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                mCurrentAnimatorEffect = null;
            }
        });
        set.start();
        mCurrentAnimatorEffect = set;

        final float startScaleFinal = startScale;
        rlBigPhoto.setOnClickListener( v -> {
                if (mCurrentAnimatorEffect != null) {
                    mCurrentAnimatorEffect.cancel();
                }

                AnimatorSet set1 = new AnimatorSet();
                set1.play(ObjectAnimator
                        .ofFloat(rlBigPhoto, View.X, startBounds.left))
                        .with(ObjectAnimator.ofFloat(rlBigPhoto, View.Y,startBounds.top))
                        .with(ObjectAnimator.ofFloat(rlBigPhoto, View.SCALE_X, startScaleFinal))
                        .with(ObjectAnimator.ofFloat(rlBigPhoto, View.SCALE_Y, startScaleFinal));
                set1.setDuration(mShortAnimationDurationEffect);
                set1.setInterpolator(new DecelerateInterpolator());
                set1.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        thumbView.setAlpha(1f);
                        rlBigPhoto.setVisibility(View.GONE);
                        ivBigPhoto.setVisibility(View.GONE);
                        fabProjectChat.setVisibility(View.VISIBLE);
                        btnProjectDonate.setVisibility(View.VISIBLE);
                        if (param.equalsIgnoreCase("admin")){
                            fabProjectUnverify.setVisibility(View.VISIBLE);
                        }
                        mCurrentAnimatorEffect = null;
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {
                        thumbView.setAlpha(1f);
                        rlBigPhoto.setVisibility(View.GONE);
                        ivBigPhoto.setVisibility(View.GONE);
                        fabProjectChat.setVisibility(View.VISIBLE);
                        mCurrentAnimatorEffect = null;
                    }
                });
                set1.start();
                mCurrentAnimatorEffect = set1;
        });
    }

    private void setupTooltip(String message){
        ivProjectVerify.setOnClickListener(v ->  {
            if (message.equalsIgnoreCase("yes")){
                Snackbar snackbar = Snackbar.make(clProject, "Sudah diverifikasi", Snackbar.LENGTH_SHORT );
                snackbar.show();
            } else {
                Snackbar snackbar = Snackbar.make(clProject, "Belum diverifikasi", Snackbar.LENGTH_SHORT );
                snackbar.show();
            }
        });
    }

    @Override
    public void successProjectById(DataProject dataProject) {
        this.dataProject = dataProject;
        setupDate();
        tvProjectName.setText(dataProject.getNameProject());
        tvProjectLocation.setText(dataProject.getLocation());
        tvProjectTarget.setText("Hingga : " +postDate);
        jtProjectInformation.setText(dataProject.getInformation() + "\n");
        verify = dataProject.getVerify().toString();
        tvProjectProgress.setText("Target : Rp "+ dataProject.getFunds() +" / Rp "+ dataProject.getTargetFunds());

        if (verify.equalsIgnoreCase("yes")){
            ivProjectVerify.setBackgroundResource(R.drawable.ic_success);
            setupTooltip("yes");
        } else {
            ivProjectVerify.setBackgroundResource(R.drawable.ic_failed);
            setupTooltip("no");
        }

        Picasso.with(ProjectActivity.this)
                .load(dataProject.getPhoto())
                .into(ivProjectPhoto);

        Picasso.with(ProjectActivity.this)
                .load(dataProject.getPhoto())
                .into(ivBigPhoto);

        initProgressBar(dataProject);
    }

    @Override
    public void successUserById(DataUser dataUser) {
        this.dataUser = dataUser;
        tvProjectCreator.setText(dataUser.getName());
    }

    @Override
    public void successPutProject(DataProject dataProject) {
        progressDialog.dismiss();
        if (param.equalsIgnoreCase("admin")){
            popupWindow("success", param, "Verifikasi berhasil");
        } else {
            Toast.makeText(this, "Donasi Rp 500 berhasil", Toast.LENGTH_LONG).show();
            initDataPresenter();
        }
    }

    @Override
    public int getFunds() {
        return funds = 500 + dataProject.getFunds();
    }

    @Override
    public void successUnverify() {
        progressDialog.dismiss();
        if (param.equalsIgnoreCase("admin")){
            popupWindow("success", param, "Projek telah ditolak");
        }
    }

    @Override
    public void successChatUser(QiscusChatRoom qiscusChatRoom) {
        Intent intent = ChatActivity.generateIntent(ProjectActivity.this, qiscusChatRoom);
        startActivity(intent);
    }

    @Override
    public void failed(String s) {
        if (progressDialog.isShowing()){
            progressDialog.dismiss();
        }
    }
}
