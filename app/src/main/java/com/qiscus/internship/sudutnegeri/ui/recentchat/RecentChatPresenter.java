package com.qiscus.internship.sudutnegeri.ui.recentchat;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.qiscus.internship.sudutnegeri.ui.chat.ChatActivity;
import com.qiscus.internship.sudutnegeri.ui.project.ProjectActivity;
import com.qiscus.sdk.Qiscus;
import com.qiscus.sdk.data.model.QiscusChatRoom;
import com.qiscus.sdk.data.remote.QiscusApi;
import com.qiscus.sdk.util.QiscusRxExecutor;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import retrofit2.HttpException;

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

    public void chatUser(QiscusChatRoom qiscusChatRoom){
        QiscusRxExecutor.execute(QiscusApi
                        .getInstance().getChatRoom(qiscusChatRoom.getId()),
                new QiscusRxExecutor.Listener<QiscusChatRoom>() {
                    @Override
                    public void onSuccess(QiscusChatRoom qiscusChatRoom) {
                        recentChatView.successChatUser(qiscusChatRoom);
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        recentChatView.failedChatUser(throwable);
                    }
                });
    }

    public void chatAdmin(String email){
        Qiscus.buildChatRoomWith(email)
                .build(new Qiscus.ChatBuilderListener() {
                    @Override
                    public void onSuccess(QiscusChatRoom qiscusChatRoom) {
                        recentChatView.successChatAdmin(qiscusChatRoom);
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        recentChatView.failedChatUser(throwable);
                    }
                });
    }

}
