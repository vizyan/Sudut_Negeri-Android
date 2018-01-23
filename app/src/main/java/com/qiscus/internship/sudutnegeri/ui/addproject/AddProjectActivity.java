package com.qiscus.internship.sudutnegeri.ui.addproject;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.qiscus.internship.sudutnegeri.R;
import com.qiscus.internship.sudutnegeri.data.model.DataProject;
import com.qiscus.internship.sudutnegeri.data.model.DataUser;
import com.qiscus.internship.sudutnegeri.util.Constant;

import java.io.File;
import java.util.Calendar;

public class AddProjectActivity extends AppCompatActivity implements AddProjectView {

    private AddProjectPresenter addProjectPresenter;
    private DataProject dataProject;
    private DataUser dataUser;
    private Uri uri;
    Button btnAddPCreate, btnAddPTaget, btnAddPPhoto;
    ConstraintLayout constraintLayout;
    DatePickerDialog datePickerDialog;
    File file;
    EditText etAddPName, etAddPLocation, etAddPInformation;
    String project_name, location, target_at, information, photo;
    TextView tvAddPTarger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_project);
        initPresenter();
        initView();
        initAnimation();
        initDataIntent();

        postProject();
        setupDate();
        pickImage();
    }

    private void pickImage() {
        btnAddPPhoto.setOnClickListener(new View.OnClickListener() {
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
            String filePath = getRealPathFromURIPath(uri, AddProjectActivity.this);
            file = new File(filePath);
            btnAddPPhoto.setText(file.getName());
        }
    }

    private String getRealPathFromURIPath(Uri uri, AddProjectActivity addProjectActivity) {
        Cursor cursor = addProjectActivity.getContentResolver().query(uri, null, null, null, null);
        if (cursor == null) {
            return uri.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            return cursor.getString(idx);
        }
    }

    private void initPresenter() {
        addProjectPresenter = new AddProjectPresenter(this);
    }

    private void initView() {
        btnAddPCreate = findViewById(R.id.btnAddPCreate);
        btnAddPTaget = findViewById(R.id.btnAddPTarget);
        btnAddPPhoto = findViewById(R.id.btnAddPPhoto);
        constraintLayout = findViewById(R.id.rlAddP);
        etAddPName = findViewById(R.id.etAddPName);
        etAddPLocation = findViewById(R.id.etAddPLocation);
        etAddPInformation = findViewById(R.id.etAddPInformation);
    }

    private void initAnimation() {

    }

    private void initDataIntent() {
        dataUser = getIntent().getParcelableExtra(Constant.Extra.User);
    }

    private void setupDate(){
        btnAddPTaget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                // date picker dialog
                datePickerDialog = new DatePickerDialog(AddProjectActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // set day of month , month and year value in the edit text
                                btnAddPTaget.setText(year + "-"
                                        + (monthOfYear + 1) + "-" + dayOfMonth);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });
    }

    private void setupVariable(){
        project_name = etAddPName.getText().toString();
        location = etAddPLocation.getText().toString();
        information = etAddPInformation.getText().toString();
        target_at = btnAddPTaget.getText().toString();
        photo = btnAddPPhoto.getText().toString();
    }
    private boolean validate(){
        validName();
        validLocation();
        validTarget();
        validPhoto();
        validInformation();

        if (validName() || validLocation() || validTarget() || validPhoto() || validInformation()){
            Toast.makeText(this, "" +validLocation(), Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    private boolean validName(){
        if (project_name.isEmpty()){
            etAddPName.setBackgroundResource(R.drawable.bg_sounded_trans_red);
            etAddPName.setHint("Isikan nama projek");
            return false;
        }
        etAddPName.setBackgroundResource(R.drawable.bg_rounded_trans_green);
        return true;
    }

    private boolean validLocation(){
        if(location.isEmpty()) {
            etAddPLocation.setBackgroundResource(R.drawable.bg_sounded_trans_red);
            etAddPLocation.setHint("Isikan lokasi");
            return false;
        }
        etAddPLocation.setBackgroundResource(R.drawable.bg_rounded_trans_green);
        return true;
    }

    private boolean validTarget(){
        if (target_at.isEmpty()) {
            btnAddPTaget.setBackgroundResource(R.drawable.bg_sounded_trans_red);
            btnAddPTaget.setHint("Pilih tanggal target");
            return false;
        }
        btnAddPTaget.setBackgroundResource(R.drawable.bg_rounded_trans_green);
        return true;
    }

    private boolean validPhoto(){
        if (photo.isEmpty()){
            btnAddPPhoto.setBackgroundResource(R.drawable.bg_sounded_trans_red);
            btnAddPPhoto.setHint("Pilih foto dari gallery");
            return false;
        }
        btnAddPPhoto.setBackgroundResource(R.drawable.bg_rounded_trans_green);
        return true;
    }

    private boolean validInformation(){
        if (information.isEmpty()) {
            etAddPInformation.setBackgroundResource(R.drawable.bg_sounded_trans_red);
            etAddPInformation.setHint("Isikan informasi");
            return false;
        }
        etAddPInformation.setBackgroundResource(R.drawable.bg_rounded_trans_green);
        return true;
    }

    private void postProject() {
        btnAddPCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setupVariable();
                addProjectPresenter.uploadFile(file, dataUser);
                if (validate()) {
                    Toast.makeText(AddProjectActivity.this, " " +validate(), Toast.LENGTH_LONG).show();
                    //addProjectPresenter.postProject(dataUser);
                }
            }
        });
    }

    @Override
    public String getProjectName() {
        return project_name;
    }

    @Override
    public String getLocation() {
        return location;
    }

    @Override
    public String getTarget() {
        return target_at;
    }

    @Override
    public String getInformation() {
        return information;
    }

    @Override
    public String getPhoto() {
        return photo;
    }

    @Override
    public void successPostProject() {
        Toast.makeText(AddProjectActivity.this, "Berhasil", Toast.LENGTH_LONG).show();
    }

    @Override
    public void failedPostProject(String message) {
        Toast.makeText(AddProjectActivity.this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void successUploadFile(String response) {
        photo = "http://vizyan.xyz/images/" + response;
        Toast.makeText(AddProjectActivity.this, "Berhasil " + response, Toast.LENGTH_LONG).show();
    }

    @Override
    public void failedUploadFile() {
        Toast.makeText(AddProjectActivity.this, "Gagal", Toast.LENGTH_LONG).show();
    }
}
