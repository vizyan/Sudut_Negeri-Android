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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.qiscus.internship.sudutnegeri.R;
import com.qiscus.internship.sudutnegeri.adapter.project.ProjectAdapter;
import com.qiscus.internship.sudutnegeri.adapter.project.ProjectListener;
import com.qiscus.internship.sudutnegeri.data.model.DataProject;
import com.qiscus.internship.sudutnegeri.data.model.DataUser;
import com.qiscus.internship.sudutnegeri.ui.AddProject.AddProjectActivity;
import com.qiscus.internship.sudutnegeri.ui.project.ProjectActivity;
import com.qiscus.internship.sudutnegeri.util.Constant;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentSudut extends Fragment implements ProjectListener, DashboardView {

    private DashboardPresenter dashboardPresenter;
    private ProjectAdapter projectAdapter;
    private List<DataProject> dataProjectList;
    private DataUser dataUser;
    Button btnSudutCreate;
    RecyclerView rvSudut;
    SearchView searchView;
    SwipeRefreshLayout swipeRefreshLayout;

    public static FragmentSudut newInstance() {
        // Required empty public constructor
        return new FragmentSudut();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sudut, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initPresenter();
        initView();
        initDataIntent();
        initDataPresenter();
        dashboardPresenter.getProjectByUser(dataUser);

        refresh();
        search();
        postProject();
    }

    private void initPresenter() {
        dashboardPresenter = new DashboardPresenter(this);
    }

    private void initAdapter() {
        projectAdapter.setAdapterListener(this);
        rvSudut.setLayoutManager(new LinearLayoutManager(getContext()));
        rvSudut.setAdapter(projectAdapter);
    }

    private void initView() {
        btnSudutCreate = getActivity().findViewById(R.id.btnSudutCreate);
        rvSudut = getActivity().findViewById(R.id.rvSudut);
        searchView = getActivity().findViewById(R.id.svDashboard);
        swipeRefreshLayout = getActivity().findViewById(R.id.srlSudut);
    }

    private void initDataPresenter() {
        dashboardPresenter.getProjectByUser(dataUser);
    }

    private void initDataIntent() {
        dataUser = getActivity().getIntent().getParcelableExtra(Constant.Extra.DATA);
    }

    private void refresh() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                dashboardPresenter.getProjectByUser(dataUser);
            }
        });
    }

    private void search() {
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        searchView = getActivity().findViewById(R.id.svDashboard);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);
        int id = searchView.getContext().getResources().getIdentifier("search", null, null);
        TextView textView = searchView.findViewById(id);
        //textView.setTextColor(Color.WHITE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
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

    private void postProject() {
        btnSudutCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddProjectActivity.class);
                intent.putExtra(Constant.Extra.User, dataUser);
                intent.putExtra(Constant.Extra.param, "negeri");
                startActivity(intent);
            }
        });
    }

    @Override
    public void onProjectClick(DataProject dataProject) {
        //Toast.makeText(getActivity(), "ini isinya " + dataProject.toString(), Toast.LENGTH_LONG).show();
        Intent intent = new Intent(getActivity(), ProjectActivity.class);
        intent.putExtra(Constant.Extra.Project, dataProject);
        intent.putExtra(Constant.Extra.param, "negeri");
        startActivity(intent);
    }

    @Override
    public void displayImg(ImageView imgProject, DataProject dataProject) {

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

    }

    @Override
    public void successShowProjectByUser(List<DataProject> dataProject) {
        projectAdapter = new ProjectAdapter(dataProjectList);
        swipeRefreshLayout.setRefreshing(false);
        dataProjectList = dataProject;
        initAdapter();
    }
}
