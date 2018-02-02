package com.qiscus.internship.sudutnegeri.adapter.user;

import android.widget.ImageView;

import com.qiscus.internship.sudutnegeri.data.model.DataUser;

/**
 * Created by Vizyan on 1/14/2018.
 */

public interface UserListener {

    void onUserClick(DataUser dataUser);

    void displayImageUser(ImageView listener, DataUser dataUser);
}
