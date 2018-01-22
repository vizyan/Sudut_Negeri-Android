package com.qiscus.internship.sudutnegeri.ui.splashscreen;

import com.qiscus.internship.sudutnegeri.data.model.DataUser;

/**
 * Created by Vizyan on 1/10/2018.
 */

interface SplashscreenView {

    String getPassword();

    String getEmail();

    void notLogin();

    void successUser(DataUser data);

    void failedQiscuss(Throwable throwable);
}
