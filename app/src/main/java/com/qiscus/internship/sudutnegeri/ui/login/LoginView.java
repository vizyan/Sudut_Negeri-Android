package com.qiscus.internship.sudutnegeri.ui.login;

/**
 * Created by Vizyan on 1/10/2018.
 */

interface LoginView {

    String getPassword();

    String getEmail();

    void success();

    void failed();

    void noConnection();

    void notVerified();
}
