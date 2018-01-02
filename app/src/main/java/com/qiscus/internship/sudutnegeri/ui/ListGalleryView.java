package com.qiscus.internship.sudutnegeri.ui;

import com.qiscus.internship.sudutnegeri.data.model.Car;

import java.util.List;

/**
 * Created by vizyan on 02/01/18.
 */

public interface ListGalleryView {

    void showData(List<Car> car);

    void onFailure(String errorMessage);
}
