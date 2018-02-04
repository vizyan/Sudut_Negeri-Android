package com.qiscus.internship.sudutnegeri.ui.landing;

import com.qiscus.internship.sudutnegeri.data.model.DataProject;

import java.util.List;

/**
 * Created by vizyan on 03/01/18.
 */

public interface LandingView {
    void successShowProjectByTime(List<DataProject> dataProject);

    void successDonation(String donation);

    void failed(String s);
}
