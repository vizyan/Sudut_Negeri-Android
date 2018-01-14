package com.qiscus.internship.sudutnegeri.adapter.project;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qiscus.internship.sudutnegeri.R;
import com.qiscus.internship.sudutnegeri.data.model.DataProject;

import java.util.List;

/**
 * Created by Vizyan on 1/14/2018.
 */

public class ProjectAdapter extends RecyclerView.Adapter<ProjectViewHolder> {

    private List<DataProject> dataProjectList;
    private ProjectListener listener;

    public ProjectAdapter(List<DataProject> dataProjectList){
        this.dataProjectList = dataProjectList;
    }

    @Override
    public ProjectViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.item_project, parent, false);

        return new ProjectViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProjectViewHolder holder, int position) {
        DataProject dataProject = getItem(position);
        holder.bind(dataProject, listener);
    }

    private DataProject getItem(int position) {
        return dataProjectList.get(position);
    }

    @Override
    public int getItemCount() {
        if (dataProjectList == null) return 0;
        return dataProjectList.size();
    }

    public void setAdapterListener(ProjectListener listener) {
        this.listener = listener;
    }
}
