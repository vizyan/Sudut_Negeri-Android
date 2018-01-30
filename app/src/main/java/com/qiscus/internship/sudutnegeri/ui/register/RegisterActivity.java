package com.qiscus.internship.sudutnegeri.ui.register;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.qiscus.internship.sudutnegeri.R;
import com.qiscus.internship.sudutnegeri.ui.addproject.AddProjectActivity;
import com.qiscus.internship.sudutnegeri.ui.landing.LandingActivity;
import com.qiscus.internship.sudutnegeri.ui.login.LoginActivity;
import com.qiscus.internship.sudutnegeri.util.Constant;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.io.File;
import java.util.Random;

public class RegisterActivity extends AppCompatActivity implements RegisterView {

    private RegisterPresenter registerPresenter;
    private Uri uri;
    AnimationDrawable animationDrawable;
    Button btnRegReg, btnRegLog;
    EditText etRegName,etRegEmail, etRegPasswd, etRegRetypePasswd, etRegIdNo, etRegAddress, etRegPhone;
    File file;
    ImageView ivRegPhoto, ivRegAddPhoto;
    ProgressDialog progressDialog;
    RelativeLayout rvReg;
    String passwd, retypepasswd, email, name, idNo, address, phone, random;
    TextView tvPopupMsg, tvPopupType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        initPresenter();
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_register);
        initView();
        initAnimation();

        login();
        pickPhoto();
        register();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (animationDrawable != null && animationDrawable.isRunning()){
            animationDrawable.stop();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (animationDrawable != null && !animationDrawable.isRunning()){
            animationDrawable.start();
        }
    }

    private void initView() {
        //initial variable
        rvReg = findViewById(R.id.rvReg);
        etRegName = findViewById(R.id.etRegName);
        etRegEmail = findViewById(R.id.etRegEmail);
        etRegPasswd = findViewById(R.id.etRegPasswd);
        etRegRetypePasswd = findViewById(R.id.etRegRetypePasswd);
        etRegIdNo = findViewById(R.id.etRegIdNo);
        etRegAddress = findViewById(R.id.etRegAddress);
        etRegPhone = findViewById(R.id.etRegPhone);
        btnRegReg = findViewById(R.id.btnRegReg);
        btnRegLog = findViewById(R.id.btnRegLog);
        ivRegPhoto = findViewById(R.id.ivRegPhoto);
        ivRegAddPhoto = findViewById(R.id.ivRegAddPhoto);
    }

    private void initAnimation() {
        //animation duration
        animationDrawable = (AnimationDrawable) rvReg.getBackground();
        animationDrawable.setEnterFadeDuration(3000);
        animationDrawable.setExitFadeDuration(3000);
    }

    private void initPresenter() {
        //initial RegisterPresenter
        registerPresenter = new RegisterPresenter(this);
    }

    private void initVariable() {
        name = etRegName.getText().toString();
        email = etRegEmail.getText().toString();
        passwd = etRegPasswd.getText().toString();
        retypepasswd = etRegRetypePasswd.getText().toString();
        idNo = etRegIdNo.getText().toString();
        address = etRegAddress.getText().toString();
        phone = etRegPhone.getText().toString();
    }

    private void login() {
        btnRegLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent login = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(login);
                finish();
            }
        });
    }

    private void register() {
        btnRegReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validation()==true){
                    progressDialog = new ProgressDialog(RegisterActivity.this);
                    progressDialog.setTitle(null);
                    progressDialog.setMessage("Mendaftar, tunggu beberapa saat");
                    progressDialog.setIndeterminate(false);
                    progressDialog.setCancelable(false);
                    progressDialog.show();

                    random = random(32);
                    if (file==null){
                        registerPresenter.addUser(null);
                    } else {
                        registerPresenter.uploadFile(file, random);
                    }
                }
            }
        });
    }

    private void pickPhoto(){
        ivRegAddPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openGalleryIntent = new Intent(Intent.ACTION_PICK);
                openGalleryIntent.setType("image/*");
                startActivityForResult(openGalleryIntent, 200);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 200 && resultCode == Activity.RESULT_OK) {
            uri = data.getData();
            String filePath = getRealPathFromURIPath(uri, RegisterActivity.this);
            file = new File(filePath);
            Picasso.with(this)
                    .load(file)
                    .transform(new RegisterActivity.CircleTransform())
                    .into(ivRegPhoto);
        }
    }

    private String getRealPathFromURIPath(Uri uri, RegisterActivity registerActivity) {
        Cursor cursor = registerActivity.getContentResolver().query(uri, null, null, null, null);
        if (cursor == null) {
            return uri.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            return cursor.getString(idx);
        }
    }

    public class CircleTransform implements Transformation {
        @Override
        public Bitmap transform(Bitmap source) {
            int size = Math.min(source.getWidth(), source.getHeight());

            int x = (source.getWidth() - size) / 2;
            int y = (source.getHeight() - size) / 2;

            Bitmap squaredBitmap = Bitmap.createBitmap(source, x, y, size, size);
            if (squaredBitmap != source) {
                source.recycle();
            }

            Bitmap bitmap = Bitmap.createBitmap(size, size, source.getConfig());

            Canvas canvas = new Canvas(bitmap);
            Paint paint = new Paint();
            BitmapShader shader = new BitmapShader(squaredBitmap,
                    BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP);
            paint.setShader(shader);
            paint.setAntiAlias(true);

            float r = size / 2f;
            canvas.drawCircle(r, r, r, paint);

            squaredBitmap.recycle();
            return bitmap;
        }

        @Override
        public String key() {
            return "circle";
        }
    }

    private static String random(final int sizeOfRandomString)
    {
        final Random random=new Random();
        final StringBuilder sb=new StringBuilder(sizeOfRandomString);
        for(int i=0;i<sizeOfRandomString;++i)
            sb.append(Constant.ALLOWED_CHARACTERS.charAt(random.nextInt(Constant.ALLOWED_CHARACTERS.length())));
        return sb.toString();
    }

    private boolean validation(){
        initVariable();
        validName();
        validEmail();
        validPasswd();
        validRetypePasswd();
        validIdNo();
        validAddress();
        validPhone();
        validPhoto();

        if (validPhoto()==false){
            Toast.makeText(this, "Foto tidak lebih dari 1MB", Toast.LENGTH_LONG).show();
        }

        if (validName()==false || validEmail()==false || validPasswd()==false || validRetypePasswd()==false || validIdNo()==false || validAddress()==false || validPhone()==false || validPhoto()==false ){
            return false;
        } else {
            return true;
        }
    }

    private void initPopupWindow(String messsage, String error) {
        try {
            LayoutInflater inflater = (LayoutInflater) RegisterActivity.this
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            if (messsage.equals("success")){
                View layout = inflater.inflate(R.layout.layout_popup_success,
                        null);
                final PopupWindow pw = new PopupWindow(layout, RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT, false);
                pw.setOutsideTouchable(false);
                pw.showAtLocation(layout, Gravity.CENTER, 0, 0);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        Intent admin = new Intent(RegisterActivity.this, LandingActivity.class);
                        startActivity(admin);
                        finish();
                    }

                    private void finish() {
                        // TODO Auto-generated method stub

                    }
                }, 1000);

            } else {
                View layout = inflater.inflate(R.layout.layout_popup_failed, null);
                final PopupWindow pw = new PopupWindow(layout, RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT, true);
                pw.showAtLocation(layout, Gravity.CENTER, 0, 0);
                tvPopupMsg = layout.findViewById(R.id.tvPopupFMsg);
                tvPopupType = layout.findViewById(R.id.tvPopupFType);
                tvPopupMsg.setText(error);
                tvPopupType.setText("Gagal Bergabung");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @NonNull
    private boolean validEmail(){
        if(email.isEmpty()){
            etRegEmail.setBackgroundResource(R.drawable.bg_sounded_trans_red);
            etRegEmail.setHint("Isikan email");
            return false;
        } else {
            if (Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                etRegEmail.setBackgroundResource(R.drawable.bg_rounded_trans_green);
                return true;
            } else {
                etRegEmail.setBackgroundResource(R.drawable.bg_sounded_trans_red);
                etRegEmail.setText("");
                etRegEmail.setHint("Isikan email dengan benar");
                return false;
            }
        }
    }

    @NonNull
    private boolean validName(){
        if(name.isEmpty()){
            etRegName.setBackgroundResource(R.drawable.bg_sounded_trans_red);
            etRegName.setHint("Isikan nama");
            return false;
        } else {
            etRegName.setBackgroundResource(R.drawable.bg_rounded_trans_green);
            return true;
        }
    }

    @NonNull
    private boolean validPasswd(){
        if(passwd.isEmpty()){
            etRegPasswd.setBackgroundResource(R.drawable.bg_sounded_trans_red);
            etRegPasswd.setHint("Isikan password");
            return false;
        } else {
            if (passwd.length()<6){
                etRegPasswd.setBackgroundResource(R.drawable.bg_sounded_trans_red);
                etRegPasswd.setText("");
                Toast.makeText(this, "Password minimal 6 karakter", Toast.LENGTH_SHORT).show();
                return false;
            } else {
                etRegPasswd.setBackgroundResource(R.drawable.bg_rounded_trans_green);
                return true;
            }
        }
    }

    @NonNull
    private boolean validRetypePasswd(){
        if (validPasswd()==false){
            etRegRetypePasswd.setBackgroundResource(R.drawable.bg_sounded_trans_red);
            etRegRetypePasswd.setHint("Isikan ulang password");
            etRegRetypePasswd.setText("");
            return false;
        } else {
            if (retypepasswd.isEmpty()){
                etRegRetypePasswd.setBackgroundResource(R.drawable.bg_sounded_trans_red);
                etRegRetypePasswd.setHint("Isikan ulang password");
                return false;
            } else {
                if(retypepasswd.matches(passwd)){
                    etRegRetypePasswd.setBackgroundResource(R.drawable.bg_rounded_trans_green);
                    return true;
                } else {
                    etRegRetypePasswd.setBackgroundResource(R.drawable.bg_sounded_trans_red);
                    etRegRetypePasswd.setText("");
                    etRegRetypePasswd.setHint("Masukkan ulang password");
                    return false;
                }
            }
        }
    }

    @NonNull
    private boolean validIdNo(){
        if(idNo.isEmpty()){
            etRegIdNo.setHint("Isikan no KTP");
            etRegIdNo.setBackgroundResource(R.drawable.bg_sounded_trans_red);
            return false;
        } else if (!(idNo.length()==16)){
            etRegIdNo.setText("");
            etRegIdNo.setHint("Isikan no KTP dengan benar");
            etRegIdNo.setBackgroundResource(R.drawable.bg_sounded_trans_red);
            return false;
        } else {
            etRegIdNo.setBackgroundResource(R.drawable.bg_rounded_trans_green);
            return true;
        }
    }

    @NonNull
    private boolean validAddress(){
        if(address.isEmpty()){
            etRegAddress.setHint("Isikan Alamat");
            etRegAddress.setBackgroundResource(R.drawable.bg_sounded_trans_red);
            return false;
        } else {
            etRegAddress.setBackgroundResource(R.drawable.bg_rounded_trans_green);
            return true;
        }
    }

    @NonNull
    private boolean validPhone(){
        int a = phone.length();
        if(phone.isEmpty()){
            etRegPhone.setHint("Isikan nomer HP");
            etRegPhone.setBackgroundResource(R.drawable.bg_sounded_trans_red);
            return false;
        } else if (!(11 <= a && a <= 13)){
            etRegPhone.setText("");
            etRegPhone.setHint("Isikan nomer HP dengan benar");
            etRegPhone.setBackgroundResource(R.drawable.bg_sounded_trans_red);
            return false;
        } else {
            etRegPhone.setBackgroundResource(R.drawable.bg_rounded_trans_green);
            return true;
        }
    }

    private boolean validPhoto(){
        if (!(file==null)){
            long fileInKb, fileInMb;
            fileInKb = file.length() / 1024;
            fileInMb = fileInKb / 1024;

            if (fileInMb > 1 ){
                return false;
            } else {
                return true;
            }
        } return true;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public String getPassword() {
        return passwd;
    }

    @Override
    public String getNoIdentity() {
        return idNo;
    }

    @Override
    public String getAddress() {
        return address;
    }

    @Override
    public String getPhone() {
        return phone;
    }

    @Override
    public String getRetypePasswd() {
        return retypepasswd;
    }

    @Override
    public void success(String message) {
        progressDialog.dismiss();
        initPopupWindow(message, null);
    }

    @Override
    public void noConnection() {
        initPopupWindow("failed", "Tidak ada sambungan");
    }

    @Override
    public void failedRegister(String message, String error) {
        progressDialog.dismiss();
        initPopupWindow(message, error);
        if (error.equalsIgnoreCase("Email sudah digunakan")){
            etRegEmail.setText("");
            etRegEmail.setBackgroundResource(R.drawable.bg_rounded_trans_white);
        } else if (error.equalsIgnoreCase("No KTP sudah digunakan")){
            etRegIdNo.setText("");
            etRegIdNo.setBackgroundResource(R.drawable.bg_rounded_trans_white);
        }
    }

    @Override
    public void failedUploadFile() {
        Toast.makeText(this, "Hmm", Toast.LENGTH_LONG).show();
    }

}
