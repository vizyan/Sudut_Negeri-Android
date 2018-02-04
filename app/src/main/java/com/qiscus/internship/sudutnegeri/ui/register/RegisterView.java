package com.qiscus.internship.sudutnegeri.ui.register;

/**
 * Created by Vizyan on 1/10/2018.
 */

interface RegisterView {

    String getName();

    String getEmail();

    String getPassword();

    String getNoIdentity();

    String getAddress();

    String getPhone();

    String getRetypePasswd();

    void failed(String s);

    void successAddUser();
}
