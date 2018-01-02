package com.qiscus.internship.sudutnegeri.ui.Add;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;

import com.qiscus.internship.sudutnegeri.R;
import com.qiscus.internship.sudutnegeri.data.model.Car;
import com.qiscus.internship.sudutnegeri.util.Constant;

public class AddActivity extends AppCompatActivity implements AddView{

    private EditText etYear;
    private EditText etMake;
    private EditText etModel;
    private AddPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        initView();
        initPresenter();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_add_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.menuSave:
                mPresenter.saveCar();
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    private void initPresenter() {
        mPresenter = new AddPresenter(this);
    }

    private void initView() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        etYear = findViewById(R.id.etYear);
        etMake = findViewById(R.id.etMake);
        etModel = findViewById(R.id.etModel);
    }

    @Override
    public String getYear() {
        return etYear.getText().toString();
    }

    @Override
    public String getMake() {
        return etMake.getText().toString();
    }

    @Override
    public String getModel() {
        return etModel.getText().toString();
    }

    @Override
    public void showSuccesSaveCar(Car carResponse) {
        Intent intent = new Intent();
        intent.putExtra(Constant.Extra.DATA, (Parcelable) carResponse);
        setResult(RESULT_OK, intent);
        finish();
    }
}
