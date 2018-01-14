package com.qiscus.internship.sudutnegeri.adapter.user;

import android.widget.ImageView;

import com.qiscus.internship.sudutnegeri.data.model.DataProject;

/**
 * Created by Vizyan on 1/14/2018.
 */

public interface UserListener {

    void onProjectClick(DataProject dataProject);

    void displayImg(ImageView imgProject, DataProject dataProject);
}
