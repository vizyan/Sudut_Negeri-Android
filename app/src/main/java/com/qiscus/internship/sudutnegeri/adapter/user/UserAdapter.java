package com.qiscus.internship.sudutnegeri.adapter.user;

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

public class UserAdapter extends RecyclerView.Adapter<UserViewHolder> {

    private List<DataProject> dataProjectList;
    private UserListener listener;

    public UserAdapter(List<DataProject> dataProjectList){
        this.dataProjectList = dataProjectList;
    }

    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.item_project, parent, false);

        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(UserViewHolder holder, int position) {
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

    public void setAdapterListener(UserListener listener) {
        this.listener = listener;
    }
}
