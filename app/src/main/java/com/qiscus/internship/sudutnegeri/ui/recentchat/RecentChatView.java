package com.qiscus.internship.sudutnegeri.ui.recentchat;

import com.qiscus.sdk.data.model.QiscusChatRoom;

import java.util.List;

/**
 * Created by Vizyan on 2/1/2018.
 */

interface RecentChatView {
    void successRoomList(List<QiscusChatRoom> qiscusChatRooms);

    void successChatAdmin(QiscusChatRoom qiscusChatRoom);

    void successChatUser(QiscusChatRoom qiscusChatRoom);

    void failedChatUser(Throwable throwable);
}
