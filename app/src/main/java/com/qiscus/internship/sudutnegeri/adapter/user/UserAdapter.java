package com.qiscus.internship.sudutnegeri.adapter.user;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qiscus.internship.sudutnegeri.R;
import com.qiscus.internship.sudutnegeri.adapter.project.ProjectListener;
import com.qiscus.internship.sudutnegeri.data.model.DataUser;

import java.util.List;

/**
 * Created by Vizyan on 1/14/2018.
 */

public class UserAdapter extends RecyclerView.Adapter<UserViewHolder> {

    private List<DataUser> dataUsersList;
    private UserListener listener;

    public UserAdapter(List<DataUser> dataUsersList){
        this.dataUsersList = dataUsersList;
    }

    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.item_user, parent, false);

        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(UserViewHolder holder, int position) {
        DataUser dataUser = get(position);
        holder.bind(dataUser, listener);
    }

    private DataUser get(int position) {
        return dataUsersList.get(position);
    }

    @Override
    public int getItemCount() {
        if (dataUsersList == null) return 0;
        return dataUsersList.size();
    }

    public void setAdapterListener(UserListener listener) {
        this.listener = listener;
    }
}
