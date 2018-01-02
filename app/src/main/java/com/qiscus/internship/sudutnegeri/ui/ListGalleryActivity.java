package com.qiscus.internship.sudutnegeri.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.qiscus.internship.sudutnegeri.R;
import com.qiscus.internship.sudutnegeri.data.model.Car;

import java.util.List;

public class ListGalleryActivity extends AppCompatActivity implements ListGalleryView {

    private RecyclerView recyclerView;
    private GalleryAdapter adapter;
    ListGalleryPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_gallery);

        recyclerView = findViewById(R.id.recycler);
        initView();
    }

    private void initView(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        adapter = new GalleryAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(linearLayoutManager);

        presenter = new ListGalleryPresenter(this);
        presenter.showListGallery();
    }

    @Override
    public void showData(List<Car> car) {
        adapter.setData(car);
    }

    @Override
    public void onFailure(String errorMessage) {
        Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show();
    }
}
