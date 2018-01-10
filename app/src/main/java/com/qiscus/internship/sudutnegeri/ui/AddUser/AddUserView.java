package com.qiscus.internship.sudutnegeri.ui.AddUser;

import com.qiscus.internship.sudutnegeri.data.model.Car;

/**
 * Created by Vizyan on 1/10/2018.
 */

interface AddUserView {
    String getMake();

    String getModel();

    String getYear();

    void showSaveCar(Car car);
}
