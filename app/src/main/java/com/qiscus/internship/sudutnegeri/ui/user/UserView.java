package com.qiscus.internship.sudutnegeri.ui.user;

import com.qiscus.internship.sudutnegeri.data.model.DataUser;

/**
 * Created by Vizyan on 1/14/2018.
 */

public interface UserView {

    String getEmail();

    String getPassword();

    String getName();

    String getPhone();

    String getVerify();

    void successUserbyId(DataUser dataUser);

    void successPutUser(DataUser dataUser);

    void successLogout();

    String getAddress();
}
