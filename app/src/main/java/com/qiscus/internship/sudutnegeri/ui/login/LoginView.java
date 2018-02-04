package com.qiscus.internship.sudutnegeri.ui.login;

import com.qiscus.internship.sudutnegeri.data.model.DataUser;

/**
 * Created by Vizyan on 1/10/2018.
 */

interface LoginView {

    String getPassword();

    String getEmail();

    void successUser(DataUser data);

    void successAdmin(DataUser dataUser);

    void failed(String message);

}
