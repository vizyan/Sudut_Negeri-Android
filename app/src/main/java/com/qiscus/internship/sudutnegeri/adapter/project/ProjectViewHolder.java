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
    private TextView tvItemPName, tvItemPAddress;
    private ImageView ivItemP;
    private LinearLayout llItemP;

    public ProjectViewHolder(View view) {
        super(view);
        initView(itemView);
    }

    private void initView(View itemView) {
        tvItemPName = itemView.findViewById(R.id.tvItemPName);
        tvItemPAddress = itemView.findViewById(R.id.tvItemPAddress);
        ivItemP = itemView.findViewById(R.id.ivItemP);
        llItemP = itemView.findViewById(R.id.llItemP);
    }

    public void bind(final DataProject dataProject, final ProjectListener projectListener) {
        projectListener.displayImgProject(ivItemP, dataProject);
        tvItemPName.setText(dataProject.getNameProject());
        tvItemPAddress.setText(dataProject.getLocation());

        llItemP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                projectListener.onProjectClick(dataProject);
            }
        });
    }
}
