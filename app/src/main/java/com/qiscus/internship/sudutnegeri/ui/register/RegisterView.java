package com.qiscus.internship.sudutnegeri.ui.register;

/**
 * Created by Vizyan on 1/10/2018.
 */

interface RegisterView {

    void success(String message);

    String getName();

    String getEmail();

    String getPassword();

    String getNoIdentity();

    String getAddress();

    String getPhone();

    String getRetypePasswd();

    void cantRegister();

    void cantConnect();
}
