package com.qiscus.internship.sudutnegeri.ui.admin;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
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
import com.qiscus.internship.sudutnegeri.adapter.user.UserAdapter;
import com.qiscus.internship.sudutnegeri.adapter.user.UserListener;
import com.qiscus.internship.sudutnegeri.data.model.DataProject;
import com.qiscus.internship.sudutnegeri.data.model.DataUser;
import com.qiscus.internship.sudutnegeri.ui.recentchat.RecentChatActivity;
import com.qiscus.internship.sudutnegeri.ui.user.UserActivity;
import com.qiscus.internship.sudutnegeri.util.CircleTransform;
import com.qiscus.internship.sudutnegeri.util.Constant;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentVerifyUser extends Fragment implements UserListener, AdminView {

    private AdminPresenter adminPresenter;
    private UserAdapter userAdapter;
    private DataUser dataUser;
    FloatingActionButton fabUserVerifChat;
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
        initDataIntent();

        refresh();
        chat();
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
        fabUserVerifChat = getActivity().findViewById(R.id.fabUserVerifChat);
        rvUserVerif = getActivity().findViewById(R.id.rvUserVerif);
        tvItemUName = getActivity().findViewById(R.id.tvItemUName);
        swipeRefreshLayout = getActivity().findViewById(R.id.srlUser);
    }

    private void initDataPresenter() {
        adminPresenter.getUserByVerify();
    }

    private void refresh(){
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initDataPresenter();
            }
        });
    }

    private void initDataIntent() {
        dataUser = getActivity().getIntent().getParcelableExtra(Constant.Extra.DATA);
        if (dataUser == null) getActivity().finish();
    }

    private void chat(){
        fabUserVerifChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent recentChat = new Intent(getActivity(), RecentChatActivity.class);
                recentChat.putExtra(Constant.Extra.DATA, dataUser);
                startActivity(recentChat);
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
    public void displayImageUser(ImageView imageView, DataUser dataUser) {
        Picasso.with(getActivity())
                .load(dataUser.getPhoto())
                .transform(new CircleTransform())
                .into(imageView);
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
    public void successShowProject(List<DataProject> projects) {}
}
