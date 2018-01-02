package com.qiscus.internship.sudutnegeri.ui.Add;

import com.qiscus.internship.sudutnegeri.data.model.Car;

/**
 * Created by Vizyan on 1/3/2018.
 */

public interface AddView {
    String getYear();

    String getMake();

    String getModel();

    public void showSuccesSaveCar(Car carResponse);
}
