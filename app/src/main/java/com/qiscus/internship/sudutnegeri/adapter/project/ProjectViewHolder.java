package com.qiscus.internship.sudutnegeri.adapter.project;

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

class ProjectViewHolder extends RecyclerView.ViewHolder {
    private TextView tvProjectName;
    private ImageView imgProject;
    private LinearLayout linearLayout;

    public ProjectViewHolder(View view) {
        super(view);
        initView(itemView);
    }

    private void initView(View itemView) {
        tvProjectName = itemView.findViewById(R.id.textNameProject);
        imgProject = itemView.findViewById(R.id.img_project);
        linearLayout = itemView.findViewById(R.id.llItemProject);
    }

    public void bind(final DataProject dataProject, final ProjectListener projectListener) {
        projectListener.displayImg(imgProject, dataProject);
        tvProjectName.setText(dataProject.getNameProject());

        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                projectListener.onProjectClick(dataProject);
            }
        });
    }
}
