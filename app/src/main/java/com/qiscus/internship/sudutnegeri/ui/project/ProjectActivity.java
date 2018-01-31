package com.qiscus.internship.sudutnegeri.ui.project;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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
import com.qiscus.internship.sudutnegeri.ui.user.UserActivity;
import com.qiscus.internship.sudutnegeri.util.Constant;
import com.qiscus.sdk.Qiscus;
import com.qiscus.sdk.data.model.QiscusChatRoom;
import com.qiscus.sdk.data.remote.QiscusApi;
import com.qiscus.sdk.data.remote.QiscusPusherApi;
import com.qiscus.sdk.ui.view.QiscusChatScrollListener;
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
    private int mShortAnimationDurationEffect;
    Button btnProjectDonate, btnProjectUnverify;
    ConstraintLayout clProject;
    Date date;
    FloatingActionButton fabProjectChat;
    ImageView ivProjectPhoto, ivBigPhoto, ivProjectVerify;
    int funds = 0;
    JustifyTextView jtProjectInformation;
    ProgressBar pbProjectProgress;
    RelativeLayout rlBigPhoto;
    String param, postDate, verify;
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
        unverifyProject();
        chat();
        initZoomPhoto();
    }

    private void initPresenter() {
        projectPresenter = new ProjectPresenter(this);
    }

    private void initView() {
        btnProjectDonate = findViewById(R.id.btnProjectDonate);
        btnProjectUnverify = findViewById(R.id.btnProjectUnverify);
        clProject = findViewById(R.id.clProject);
        fabProjectChat = findViewById(R.id.fabProjectChat);
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
            btnProjectUnverify.setVisibility(View.VISIBLE);
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

    private void unverifyProject(){
        btnProjectUnverify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                projectPresenter.unverifyProject(dataProject.getId());
            }
        });
    }

    private void chat() {
        fabProjectChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Qiscus.buildChatRoomWith(dataUser.getEmail())
                        .build(new Qiscus.ChatBuilderListener() {
                            @Override
                            public void onSuccess(QiscusChatRoom qiscusChatRoom) {
                                Intent intent = ChatActivity.generateIntent(ProjectActivity.this, qiscusChatRoom);
                                startActivity(intent);
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

//                Qiscus.buildChatWith(dataUser.getEmail())
//                        .build(ProjectActivity.this, new Qiscus.ChatActivityBuilderListener() {
//                            @Override
//                            public void onSuccess(Intent intent) {
//                                startActivity(intent);
//                                //QiscusPusherApi.getInstance().listenRoom();
//                            }
//                            @Override
//                            public void onError(Throwable throwable) {
//                                if (throwable instanceof HttpException) { //Error response from server
//                                    HttpException e = (HttpException) throwable;
//                                    try {
//                                        String errorMessage = e.response().errorBody().string();
//                                        JSONObject json = new JSONObject(errorMessage).getJSONObject("error");
//                                        String finalError = json.getString("message");
//                                        if (json.has("detailed_messages") ) {
//                                            JSONArray detailedMessages = json.getJSONArray("detailed_messages");
//                                            finalError = (String) detailedMessages.get(0);
//                                        }
//                                        Toast.makeText(ProjectActivity.this, finalError, Toast.LENGTH_LONG).show();
//                                    } catch (IOException e1) {
//                                        e1.printStackTrace();
//                                    } catch (JSONException e1) {
//                                        e1.printStackTrace();
//                                    }
//                                } else if (throwable instanceof IOException) { //Error from network
//                                    Toast.makeText(ProjectActivity.this, "Tidak dapat terkoneksi dengan server", Toast.LENGTH_LONG).show();
//                                } else { //Unknown error
//                                    Toast.makeText(ProjectActivity.this, "Kesalahan", Toast.LENGTH_LONG).show();
//                                }
//                            }
//                        });
            }
        });
    }

    private void popupWindow(String messsage, String param, String verify) {
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

    private void initZoomPhoto(){
        ivProjectPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setupZoom(ivProjectPhoto);
            }
        });
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
        fabProjectChat.setVisibility(View.INVISIBLE);

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
        rlBigPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mCurrentAnimatorEffect != null) {
                    mCurrentAnimatorEffect.cancel();
                }

                AnimatorSet set = new AnimatorSet();
                set.play(ObjectAnimator
                        .ofFloat(rlBigPhoto, View.X, startBounds.left))
                        .with(ObjectAnimator.ofFloat(rlBigPhoto, View.Y,startBounds.top))
                        .with(ObjectAnimator.ofFloat(rlBigPhoto, View.SCALE_X, startScaleFinal))
                        .with(ObjectAnimator.ofFloat(rlBigPhoto, View.SCALE_Y, startScaleFinal));
                set.setDuration(mShortAnimationDurationEffect);
                set.setInterpolator(new DecelerateInterpolator());
                set.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        thumbView.setAlpha(1f);
                        rlBigPhoto.setVisibility(View.GONE);
                        ivBigPhoto.setVisibility(View.GONE);
                        fabProjectChat.setVisibility(View.VISIBLE);
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
                set.start();
                mCurrentAnimatorEffect = set;
            }
        });
    }

    private void setupTooltip(String message){
        ivProjectVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (message.equalsIgnoreCase("yes")){
                    Snackbar snackbar = Snackbar.make(clProject, "Sudah diverifikasi", Snackbar.LENGTH_SHORT );
                    snackbar.show();
                } else {
                    Snackbar snackbar = Snackbar.make(clProject, "Belum diverifikasi", Snackbar.LENGTH_SHORT );
                    snackbar.show();
                }
            }
        });
    }

    @Override
    public void successProjectById(DataProject dataProject) {
        this.dataProject = dataProject;
        setupDate();
        tvProjectName.setText(dataProject.getNameProject());
        tvProjectLocation.setText(dataProject.getLocation());
        tvProjectTarget.setText("Projek berlangsung hingga : " +postDate);
        jtProjectInformation.setText(dataProject.getInformation() + "\n");
        verify = dataProject.getVerify().toString();

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
    }

    @Override
    public void successUserById(DataUser dataUser) {
        this.dataUser = dataUser;
        tvProjectCreator.setText(dataUser.getName());
    }

    @Override
    public void successPutProject(DataProject dataProject) {
        if (param.equalsIgnoreCase("admin")){
            popupWindow("success", param, "Verifikasi berhasil");
        }
    }

    @Override
    public int getFunds() {
        return funds = funds + dataProject.getFunds();
    }

    @Override
    public void successUnverify() {
        if (param.equalsIgnoreCase("admin")){
            popupWindow("success", param, "Projek telah ditolak");
        }
    }
}
