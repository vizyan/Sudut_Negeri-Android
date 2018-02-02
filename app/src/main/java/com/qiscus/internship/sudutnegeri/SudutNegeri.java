package com.qiscus.internship.sudutnegeri;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import com.qiscus.internship.sudutnegeri.ui.chat.ChatActivity;
import com.qiscus.internship.sudutnegeri.ui.chat.ChatFragment;
import com.qiscus.internship.sudutnegeri.util.Constant;
import com.qiscus.internship.sudutnegeri.util.RealTimeChatHandler;
import com.qiscus.sdk.Qiscus;
import com.qiscus.sdk.data.model.NotificationClickListener;
import com.qiscus.sdk.data.model.QiscusChatRoom;
import com.qiscus.sdk.data.model.QiscusComment;
import com.qiscus.sdk.data.remote.QiscusApi;
import com.qiscus.sdk.event.QiscusChatRoomEvent;
import com.qiscus.sdk.event.QiscusCommentReceivedEvent;
import com.qiscus.sdk.event.QiscusUserStatusEvent;
import com.qiscus.sdk.util.QiscusRxExecutor;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import static android.content.ContentValues.TAG;

/**
 * Created by Vizyan on 1/20/2018.
 */

public class SudutNegeri extends Application {
    public static SudutNegeri INSTANCE;
    private RealTimeChatHandler realTimeChatHandler;

    public static SudutNegeri getInstance() {
        return INSTANCE;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        INSTANCE = this;
        EventBus.getDefault().register(this);
        realTimeChatHandler = new RealTimeChatHandler();

        initQiscus();
    }

    public void initQiscus() {
        Qiscus.init(this, Constant.QISCUS_APP_ID);

        Qiscus.getChatConfig()
                .setStatusBarColor(R.color.gradient6)
                .setAccentColor(R.color.gradient5)
                .setSwipeRefreshColorScheme(R.color.gradient5, R.color.gradient6)
                .setSelectedBubbleBackgroundColor(R.color.gradient5)
                .setMessageFieldHint("Ketikkan pesan...")
                .setMessageFieldTextColor(R.color.black)
                .setRightBubbleColor(R.color.qiscus_white)
                .setRightBubbleTextColor(R.color.black)
                .setLeftBubbleColor(R.color.gradient5)
                .setLeftBubbleTextColor(R.color.qiscus_white)
                .setLeftLinkTextColor(R.color.colorAccent)
                .setRightLinkTextColor(R.color.colorAccent)
                .setReadIconColor(R.color.gradient5)
                .setLeftBubbleTimeColor(R.color.qiscus_dark_white)
                .setShowEmojiIcon(R.drawable.ic_add_emoticon)
                .setAppBarColor(R.color.gradient5)
                .setAccountLinkingTextColor(R.color.gradient5)
                .setButtonBubbleTextColor(R.color.gradient5)
                .setReplyBarColor(R.color.gradient5)
                .setReplySenderColor(R.color.gradient5)
                .setNotificationSmallIcon(R.drawable.ic_small_notification)
                .setNotificationBigIcon(R.drawable.ic_small_notification)
                .setEnableAvatarAsNotificationIcon(true)
                .setStopRecordIcon(R.drawable.btn_send_chat)
                .setSendButtonIcon(R.drawable.btn_send_chat)
                .setShowAttachmentPanelIcon(R.drawable.btn_add_atachment)
                .setEnableAddFile(true)
                .setNotificationClickListener(new NotificationClickListener() {
                    @Override
                    public void onClick(Context context, QiscusComment qiscusComment) {
                        QiscusRxExecutor.execute(QiscusApi.getInstance().getChatRoom(qiscusComment.getRoomId()),
                                new QiscusRxExecutor.Listener<QiscusChatRoom>() {
                                    @Override
                                    public void onSuccess(QiscusChatRoom qiscusChatRoom) {
                                        Intent intent = ChatActivity.generateIntent(getApplicationContext(), qiscusChatRoom);
                                        startActivity(intent);
                                    }

                                    @Override
                                    public void onError(Throwable throwable) {
                                        throwable.printStackTrace();
                                    }
                                });
                    }
                })
                .setEnablePushNotification(true)
                .setOnlyEnablePushNotificationOutsideChatRoom(false);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Qiscus.getChatConfig()
                    .setEnableReplyNotification(true);
        }
    }

    public RealTimeChatHandler getRealTimeChatHandler(){
        return realTimeChatHandler;
    }

    @Subscribe
    public void onReceivedComment(QiscusCommentReceivedEvent event) {
        Log.e(TAG, "Ini ada yang baru");
        realTimeChatHandler.updateChatrooms(event.getQiscusComment());
    }

    @Subscribe
    public void onUserChanged(QiscusChatRoomEvent qiscusChatRoomEvent){
        if(qiscusChatRoomEvent.isTyping()){
            ChatFragment chatFragment = new ChatFragment();
            chatFragment.onUserTyping(qiscusChatRoomEvent.getUser(), true);
        }else{
            ChatFragment chatFragment = new ChatFragment();
            chatFragment.onUserTyping(qiscusChatRoomEvent.getUser(), false);
        }
    }

    @Subscribe
    public  void onUserTyping(QiscusUserStatusEvent qiscusUserStatusEvent){
        if(qiscusUserStatusEvent.isOnline()){
            ChatFragment.onUserChanged(qiscusUserStatusEvent.getUser(), true, qiscusUserStatusEvent.getLastActive());
        }else {
            ChatFragment.onUserChanged(qiscusUserStatusEvent.getUser(), false, qiscusUserStatusEvent.getLastActive());
        }
    }

}
