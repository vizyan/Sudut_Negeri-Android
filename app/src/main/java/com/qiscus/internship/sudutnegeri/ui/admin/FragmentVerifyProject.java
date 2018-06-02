package com.qiscus.internship.sudutnegeri.ui.admin;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.qiscus.internship.sudutnegeri.R;
import com.qiscus.internship.sudutnegeri.adapter.project.ProjectAdapter;
import com.qiscus.internship.sudutnegeri.adapter.project.ProjectListener;
import com.qiscus.internship.sudutnegeri.data.model.DataProject;
import com.qiscus.internship.sudutnegeri.data.model.DataUser;
import com.qiscus.internship.sudutnegeri.ui.project.ProjectActivity;
import com.qiscus.internship.sudutnegeri.ui.user.UserActivity;
import com.qiscus.internship.sudutnegeri.util.CircleTransform;
import com.qiscus.internship.sudutnegeri.util.Constant;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentVerifyProject extends Fragment implements ProjectListener, AdminView {

    private AdminPresenter adminPresenter;
    private ProjectAdapter projectAdapter;
    RecyclerView rvProjectVerif;
    SwipeRefreshLayout swipeRefreshLayout;
    TextView tvItemPName, tvItemPAddress;

    public static FragmentVerifyProject newInstance() {
        return new FragmentVerifyProject();
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_verify_project, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initPresenter();
        initView();
        initDataPresenter();

        refresh();
    }

    @Override
    public void onResume() {
        super.onResume();
        initDataPresenter();
    }

    private void initPresenter() {
        adminPresenter = new AdminPresenter(this);
    }

    private void initView() {
        rvProjectVerif = getActivity().findViewById(R.id.rvProjectVerif);
        tvItemPName = getActivity().findViewById(R.id.tvItemPName);
        tvItemPAddress = getActivity().findViewById(R.id.tvItemPAddress);
        swipeRefreshLayout = getActivity().findViewById(R.id.srlProject);
    }

    private void initDataPresenter(){
        adminPresenter.getProjectByVerify();
    }

    private void refresh(){
        swipeRefreshLayout.setOnRefreshListener(() -> initDataPresenter());
    }

    @Override
    public void successShowUser(List<DataUser> dataUserList) {}

    @Override
    public void successShowProject(List<DataProject> projects) {
        swipeRefreshLayout.setRefreshing(false);
        projectAdapter = new ProjectAdapter(projects);
        projectAdapter.setAdapterListener(this);
        rvProjectVerif.setLayoutManager(new LinearLayoutManager(getContext()));
        rvProjectVerif.setAdapter(projectAdapter);
    }

    @Override
    public void failedShowUser(String s) {
        Toast.makeText(getActivity(), "Maaf, terjadi kesalahan", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void failedShowProject(String message) {
        Toast.makeText(getActivity(), "Maaf, terjadi kesalahan", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onProjectClick(DataProject dataProject) {
        Intent intent = new Intent(getActivity(), ProjectActivity.class);
        intent.putExtra(Constant.Extra.Project, dataProject);
        intent.putExtra(Constant.Extra.param, "admin");
        startActivity(intent);
    }

    @Override
    public void displayImgProject(ImageView imgProject, DataProject dataProject) {
        Picasso.with(getActivity())
                .load(dataProject.getPhoto())
                .transform(new CircleTransform())
                .into(imgProject);
    }
}
