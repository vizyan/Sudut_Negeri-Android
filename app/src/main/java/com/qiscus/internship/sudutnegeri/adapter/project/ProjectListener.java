package com.qiscus.internship.sudutnegeri.adapter.project;

import android.widget.ImageView;

import com.qiscus.internship.sudutnegeri.data.model.DataProject;

/**
 * Created by Vizyan on 1/14/2018.
 */

public interface ProjectListener {

    void onProjectClick(DataProject dataProject);

    void displayImgProject(ImageView imgProject, DataProject dataProject);
}
