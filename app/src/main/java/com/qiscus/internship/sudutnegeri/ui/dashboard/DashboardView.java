package com.qiscus.internship.sudutnegeri.ui.dashboard;

import com.qiscus.internship.sudutnegeri.data.model.DataProject;

import java.util.List;

/**
 * Created by Vizyan on 1/15/2018.
 */

interface DashboardView {
    String getEmail();

    String getPassword();

    void successLogout();

    void successShowProjectVerify(List<DataProject> dataProject);
}
