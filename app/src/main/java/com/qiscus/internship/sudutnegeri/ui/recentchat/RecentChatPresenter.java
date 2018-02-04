package com.qiscus.internship.sudutnegeri.ui.recentchat;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
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

import static android.content.ContentValues.TAG;

/**
 * Created by Vizyan on 2/1/2018.
 */

public class RecentChatPresenter {

    private RecentChatView recentChatView;

    public RecentChatPresenter(RecentChatView recentChatView){
        this.recentChatView = recentChatView;
    }

    public void getRoomList(){
        String tag = "RecentChat-getRoom";
        QiscusRxExecutor.execute(QiscusApi.getInstance().getChatRooms(1, 20, false), new QiscusRxExecutor.Listener<List<QiscusChatRoom>>() {
            @Override
            public void onSuccess(List<QiscusChatRoom> qiscusChatRooms) {
                recentChatView.successRoomList(qiscusChatRooms);
                Log.d(tag, qiscusChatRooms.toString());
            }

            @Override
            public void onError(Throwable throwable) {
                recentChatView.failed("Tidak ada koneksi");
                Log.d(tag, throwable.getMessage());
            }
        });
    }

    public void chatUser(QiscusChatRoom qiscusChatRoom){
        String tag = "RecentChat-chatUser";
        QiscusRxExecutor.execute(QiscusApi
                        .getInstance().getChatRoom(qiscusChatRoom.getId()),
                new QiscusRxExecutor.Listener<QiscusChatRoom>() {
                    @Override
                    public void onSuccess(QiscusChatRoom qiscusChatRoom) {
                        recentChatView.successChatUser(qiscusChatRoom);
                        Log.d(tag, qiscusChatRoom.toString());
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        recentChatView.failed("Tidak ada koneksi");
                        Log.d(tag, throwable.getMessage());
                    }
                });
    }

    public void chatAdmin(String email){
        String tag = "RecentChat-chatAdmin";
        Qiscus.buildChatRoomWith(email)
                .build(new Qiscus.ChatBuilderListener() {
                    @Override
                    public void onSuccess(QiscusChatRoom qiscusChatRoom) {
                        recentChatView.successChatAdmin(qiscusChatRoom);
                        Log.d(tag, qiscusChatRoom.toString());
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        recentChatView.failed("Tidak ada koneksi");
                        Log.d(tag, throwable.getMessage());
                    }
                });
    }

}
