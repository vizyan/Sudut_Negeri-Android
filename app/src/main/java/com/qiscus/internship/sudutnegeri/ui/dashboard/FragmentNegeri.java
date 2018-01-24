package com.qiscus.internship.sudutnegeri.ui.dashboard;


import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.qiscus.internship.sudutnegeri.R;
import com.qiscus.internship.sudutnegeri.adapter.project.ProjectAdapter;
import com.qiscus.internship.sudutnegeri.adapter.project.ProjectListener;
import com.qiscus.internship.sudutnegeri.data.model.DataProject;
import com.qiscus.internship.sudutnegeri.ui.project.ProjectActivity;
import com.qiscus.internship.sudutnegeri.util.Constant;

import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentNegeri extends Fragment implements DashboardView, ProjectListener {

    private DashboardPresenter dashboardPresenter;
    private ProjectAdapter projectAdapter;
    List<DataProject> dataProjectList;
    RecyclerView rvNegeri;
    SearchView searchViewNegeri, searchViewSudut;
    SwipeRefreshLayout swipeRefreshLayout;

    public static FragmentNegeri newInstance() {
        // Required empty public constructor
        return new FragmentNegeri();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_negeri, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        iniPresenter();
        initView();
        initDataPresenter();
        dashboardPresenter.getProjectByVerify();

        refresh();
        search();
    }

    private void iniPresenter() {
        dashboardPresenter = new DashboardPresenter(this);
    }

    private void initView() {
        rvNegeri = getActivity().findViewById(R.id.rvNegeri);
        swipeRefreshLayout = getActivity().findViewById(R.id.srlNegeri);
        searchViewNegeri = getActivity().findViewById(R.id.svDashboardNegeri);
    }

    private void initAdapter() {
        projectAdapter.setAdapterListener(this);
        rvNegeri.setLayoutManager(new LinearLayoutManager(getContext()));
        rvNegeri.setAdapter(projectAdapter);
    }

    private void initDataPresenter() {
        dashboardPresenter.getProjectByVerify();
    }

    private void refresh(){
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                dashboardPresenter.getProjectByVerify();
            }
        });
    }

    private void search(){
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        searchViewNegeri.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
        searchViewNegeri.setMaxWidth(Integer.MAX_VALUE);
        int id = searchViewNegeri.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
        TextView textView = searchViewNegeri.findViewById(id);
        textView.setTextColor(Color.WHITE);

        searchViewNegeri.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                projectAdapter.getFilter().filter(query);
                initAdapter();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                projectAdapter.getFilter().filter(newText);
                initAdapter();
                return false;
            }
        });
    }

    @Override
    public String getEmail() {
        return null;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public void successLogout() {

    }

    @Override
    public void successShowProjectVerify(List<DataProject> dataProject) {
        projectAdapter = new ProjectAdapter(dataProjectList);
        swipeRefreshLayout.setRefreshing(false);
        dataProjectList = dataProject;
        initAdapter();
    }

    @Override
    public void successShowProjectByUser(List<DataProject> dataProjectList) {

    }

    @Override
    public void onProjectClick(DataProject dataProject) {
        Intent intent = new Intent(getActivity(), ProjectActivity.class);
        intent.putExtra(Constant.Extra.Project, dataProject);
        intent.putExtra(Constant.Extra.param, "negeri");
        startActivity(intent);
    }

    @Override
    public void displayImg(ImageView imgProject, DataProject dataProject) {

    }
}
