package com.qiscus.internship.sudutnegeri.ui.user;

import com.qiscus.internship.sudutnegeri.data.model.DataUser;
import com.qiscus.internship.sudutnegeri.data.model.ResultUser;

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

    void successUnverify(ResultUser resultUser);
}
