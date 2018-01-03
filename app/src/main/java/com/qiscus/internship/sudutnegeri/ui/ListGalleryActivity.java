package com.qiscus.internship.sudutnegeri.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.qiscus.internship.sudutnegeri.R;
import com.qiscus.internship.sudutnegeri.data.model.Car;
import com.qiscus.internship.sudutnegeri.ui.Add.AddActivity;

import java.util.List;

public class ListGalleryActivity extends AppCompatActivity implements ListGalleryView {

    private RecyclerView recyclerView;
    private GalleryAdapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    ListGalleryPresenter presenter;
    private FloatingActionButton btnAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_gallery);

        recyclerView = findViewById(R.id.recycler);
        swipeRefreshLayout = findViewById(R.id.swipe);
        initView();
        btnAdd = findViewById(R.id.btnAdd);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent add = new Intent(ListGalleryActivity.this, AddActivity.class);
                startActivity(add);
            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initView();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        initView();
    }

    private void initView(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        adapter = new GalleryAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(linearLayoutManager);

        presenter = new ListGalleryPresenter(this);
        presenter.showListGallery();
        swipeRefreshLayout.setRefreshing(false);
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
