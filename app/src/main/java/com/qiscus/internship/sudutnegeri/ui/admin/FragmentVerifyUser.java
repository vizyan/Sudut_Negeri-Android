package com.qiscus.internship.sudutnegeri.ui.admin;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.qiscus.internship.sudutnegeri.R;
import com.qiscus.internship.sudutnegeri.adapter.user.UserAdapter;
import com.qiscus.internship.sudutnegeri.adapter.user.UserListener;
import com.qiscus.internship.sudutnegeri.data.model.DataProject;
import com.qiscus.internship.sudutnegeri.data.model.DataUser;
import com.qiscus.internship.sudutnegeri.ui.user.UserActivity;
import com.qiscus.internship.sudutnegeri.util.Constant;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentVerifyUser extends Fragment implements UserListener, AdminView {

    private AdminPresenter adminPresenter;
    private UserAdapter userAdapter;
    RecyclerView rvUserVerif;
    SwipeRefreshLayout swipeRefreshLayout;
    TextView tvItemUName;

    public static FragmentVerifyUser newInstance() {
        return new FragmentVerifyUser();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_verify_user, container, false);
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
        adminPresenter.showUnverifiedUser();
    }

    private void initPresenter() {
        adminPresenter = new AdminPresenter(this);
    }

    private void initView() {
        rvUserVerif = getActivity().findViewById(R.id.rvUserVerif);
        tvItemUName = getActivity().findViewById(R.id.tvItemUName);
        swipeRefreshLayout = getActivity().findViewById(R.id.srlUser);
    }

    private void initDataPresenter() {
        adminPresenter.showUnverifiedUser();
    }

    private void refresh(){
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initDataPresenter();
            }
        });
    }

    @Override
    public void onUserClick(DataUser dataUser) {
        Intent intent = new Intent(getActivity(), UserActivity.class);
        intent.putExtra(Constant.Extra.DATA, dataUser);
        intent.putExtra(Constant.Extra.param, "admin");
        startActivity(intent);
    }

    @Override
    public void successShowUser(List<DataUser> dataUserList) {
        swipeRefreshLayout.setRefreshing(false);
        userAdapter = new UserAdapter(dataUserList);
        userAdapter.setAdapterListener(this);
        rvUserVerif.setLayoutManager(new LinearLayoutManager(getContext()));
        rvUserVerif.setAdapter(userAdapter);
    }

    @Override
    public void successShowProject(List<DataProject> projects) {

    }
}