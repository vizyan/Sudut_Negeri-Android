package com.qiscus.internship.sudutnegeri.ui.addproject;

import android.app.DatePickerDialog;
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

import java.util.Calendar;

public class AddProjectActivity extends AppCompatActivity implements AddProjectView {

    private AddProjectPresenter addProjectPresenter;
    private DataProject dataProject;
    private DataUser dataUser;
    Button btnAddPCreate, btnAddPTaget, btnAddPPhoto;
    ConstraintLayout constraintLayout;
    DatePickerDialog datePickerDialog;
    EditText etAddPName, etAddPLocation, etAddPInformation;
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
        etAddPLocation = findViewById(R.id.etAddPInformation);
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

    private void postProject() {
        btnAddPCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AddProjectActivity.this, dataUser.toString(), Toast.LENGTH_LONG).show();
                addProjectPresenter.postProject(dataUser);
            }
        });
    }

    @Override
    public String getProjectName() {
        return etAddPName.getText().toString();
    }

    @Override
    public String getLocation() {
        return etAddPLocation.getText().toString();
    }

    @Override
    public String getTarget() {
        return tvAddPTarger.getText().toString();
    }

    @Override
    public String getInformation() {
        return etAddPInformation.getText().toString();
    }

    @Override
    public String getPhoto() {
        return "nanti sek";
    }

    @Override
    public void successPostProject() {
        Toast.makeText(AddProjectActivity.this, "Berhasil", Toast.LENGTH_LONG).show();
    }

    @Override
    public void failedPostProject(String message) {
        Toast.makeText(AddProjectActivity.this, message, Toast.LENGTH_LONG).show();
    }
}
