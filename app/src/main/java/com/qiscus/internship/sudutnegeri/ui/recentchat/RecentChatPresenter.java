package com.qiscus.internship.sudutnegeri.ui.recentchat;

import com.qiscus.sdk.data.model.QiscusChatRoom;
import com.qiscus.sdk.data.remote.QiscusApi;
import com.qiscus.sdk.util.QiscusRxExecutor;

import java.util.List;

/**
 * Created by Vizyan on 2/1/2018.
 */

public class RecentChatPresenter {

    private RecentChatView recentChatView;

    public RecentChatPresenter(RecentChatView recentChatView){
        this.recentChatView = recentChatView;
    }

    public void getRoomList(){
        QiscusRxExecutor.execute(QiscusApi.getInstance().getChatRooms(1, 20, false), new QiscusRxExecutor.Listener<List<QiscusChatRoom>>() {
            @Override
            public void onSuccess(List<QiscusChatRoom> qiscusChatRooms) {
                //Success getting the rooms
                recentChatView.successRoomList(qiscusChatRooms);
            }

            @Override
            public void onError(Throwable throwable) {
                //Something went wrong
            }
        });
    }

}
