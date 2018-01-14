package com.qiscus.internship.sudutnegeri.ui.admin;


import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.qiscus.internship.sudutnegeri.R;
import com.qiscus.internship.sudutnegeri.adapter.user.UserAdapter;
import com.qiscus.internship.sudutnegeri.adapter.user.UserListener;
import com.qiscus.internship.sudutnegeri.data.model.DataUser;
import com.qiscus.internship.sudutnegeri.ui.User.UserActivity;
import com.qiscus.internship.sudutnegeri.util.Constant;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentVerifyProject extends Fragment implements UserListener, AdminView {

    private AdminPresenter presenter;
    private UserAdapter adapter;
    private RecyclerView recyclerView;
    private TextView tvName;

    public static FragmentVerifyProject newInstance() {
        return new FragmentVerifyProject();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fragment_verify_project, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initPresenter();
        initView();
    }

    private void initView() {
        recyclerView = getActivity().findViewById(R.id.rvUserList);
        tvName = getActivity().findViewById(R.id.tvNameUser);
    }

    private void initPresenter() {
        presenter = new AdminPresenter(this);
        presenter.showUnverifiedUser();
    }

    @Override
    public void onUserClick(DataUser dataUser) {
        Intent intent = new Intent(getActivity(), UserActivity.class);
        intent.putExtra(Constant.Extra.DATA, (Parcelable) dataUser);
        startActivity(intent);
    }

    @Override
    public void showUser(List<DataUser> user) {
        adapter = new UserAdapter(user);
        adapter.setAdapterListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
    }
}
