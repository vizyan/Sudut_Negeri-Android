package com.qiscus.internship.sudutnegeri.adapter.recent;

import android.widget.ImageView;

import com.qiscus.sdk.data.model.QiscusChatRoom;

/**
 * Created by Vizyan on 2/1/2018.
 */

public interface RecentListener {

    void setImage(ImageView ivRoomPhoto, String avatarUrl);

    void onRecentClick(QiscusChatRoom qiscusChatRoom);
}
