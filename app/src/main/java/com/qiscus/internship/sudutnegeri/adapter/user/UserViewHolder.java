package com.qiscus.internship.sudutnegeri.adapter.user;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qiscus.internship.sudutnegeri.R;
import com.qiscus.internship.sudutnegeri.data.model.DataProject;

/**
 * Created by Vizyan on 1/14/2018.
 */

class UserViewHolder extends RecyclerView.ViewHolder {
    private TextView tvProjectName;
    private ImageView imgProject;
    private LinearLayout linearLayout;

    public UserViewHolder(View view) {
        super(view);
        initView(itemView);
    }

    private void initView(View itemView) {
        tvProjectName = itemView.findViewById(R.id.textNameProject);
        imgProject = itemView.findViewById(R.id.img_project);
        linearLayout = itemView.findViewById(R.id.llItemProject);
    }

    public void bind(final DataProject dataProject, final UserListener userListener) {
        userListener.displayImg(imgProject, dataProject);
        tvProjectName.setText(dataProject.getNameProject());

        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userListener.onProjectClick(dataProject);
            }
        });
    }
}
