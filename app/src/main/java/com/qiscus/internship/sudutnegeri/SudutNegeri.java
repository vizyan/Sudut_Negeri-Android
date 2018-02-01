package com.qiscus.internship.sudutnegeri;

import android.app.Application;
import android.graphics.Color;
import android.os.Build;

import com.qiscus.internship.sudutnegeri.ui.chat.ChatActivity;
import com.qiscus.internship.sudutnegeri.ui.chat.ChatFragment;
import com.qiscus.internship.sudutnegeri.util.Constant;
import com.qiscus.sdk.Qiscus;
import com.qiscus.sdk.data.model.QiscusComment;
import com.qiscus.sdk.event.QiscusChatRoomEvent;
import com.qiscus.sdk.event.QiscusCommentReceivedEvent;
import com.qiscus.sdk.event.QiscusUserStatusEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Vizyan on 1/20/2018.
 */

public class SudutNegeri extends Application {
    public static SudutNegeri INSTANCE;

    public static SudutNegeri getInstance() {
        return INSTANCE;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        INSTANCE = this;

        initQiscus();
        EventBus.getDefault().register(this);
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
                .setNotificationSmallIcon(R.drawable.logo_blue)
                .setNotificationBigIcon(R.drawable.logo_blue)
                .setStopRecordIcon(R.drawable.btn_send_chat)
                .setSendButtonIcon(R.drawable.btn_send_chat)
                .setShowAttachmentPanelIcon(R.drawable.btn_add_atachment)
                .setEnableAddFile(true)
                .setEnablePushNotification(true)
                .setEnableFcmPushNotification(false);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            Qiscus.getChatConfig()
                    .setEnableReplyNotification(true);
        }

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            Qiscus.getChatConfig()
//                    .setStatusBarColor(R.color.gradient6)
//                    .setAccentColor(R.color.gradient5)
//                    .setSwipeRefreshColorScheme(R.color.gradient5, R.color.gradient6)
//                    .setSelectedBubbleBackgroundColor(R.color.gradient5)
//                    .setMessageFieldHint("Ketikkan pesan...")
//                    .setMessageFieldTextColor(R.color.black)
//                    .setRightBubbleColor(R.color.qiscus_white)
//                    .setRightBubbleTextColor(R.color.black)
//                    .setLeftBubbleColor(R.color.gradient5)
//                    .setLeftBubbleTextColor(R.color.qiscus_white)
//                    .setLeftLinkTextColor(R.color.colorAccent)
//                    .setRightLinkTextColor(R.color.colorAccent)
//                    .setReadIconColor(R.color.gradient5)
//                    .setLeftBubbleTimeColor(R.color.qiscus_dark_white)
//                    .setShowEmojiIcon(R.drawable.ic_add_emoticon)
//                    .setAppBarColor(R.color.gradient5)
//                    .setAccountLinkingTextColor(R.color.gradient5)
//                    .setButtonBubbleTextColor(R.color.gradient5)
//                    .setReplyBarColor(R.color.gradient5)
//                    .setReplySenderColor(R.color.gradient5)
//                    .setNotificationSmallIcon(R.drawable.logo_blue)
//                    .setNotificationBigIcon(R.drawable.logo_blue)
//                    .setStopRecordIcon(R.drawable.btn_send_chat)
//                    .setSendButtonIcon(R.drawable.btn_send_chat)
//                    .setShowAttachmentPanelIcon(R.drawable.btn_add_atachment)
//                    .setEnableAddFile(true)
//                    .setEnableReplyNotification(true)
//                    .setEnablePushNotification(true)
//                    .setEnableFcmPushNotification(false);
//        } else {
//            Qiscus.getChatConfig()
//                    .setStatusBarColor(R.color.gradient6)
//                    .setAccentColor(R.color.gradient5)
//                    .setSwipeRefreshColorScheme(R.color.colorPrimary, R.color.colorAccent)
//                    .setSelectedBubbleBackgroundColor(R.color.gradient5)
//                    .setReadIconColor(R.color.gradient5)
//                    .setMessageFieldHint("Ketikkan pesan...")
//                    .setMessageFieldTextColor(R.color.black)
//                    .setRightBubbleColor(R.color.qiscus_white)
//                    .setRightBubbleTextColor(R.color.black)
//                    .setLeftBubbleColor(R.color.gradient5)
//                    .setLeftBubbleTextColor(R.color.qiscus_white)
//                    .setShowEmojiIcon(R.drawable.ic_add_emoticon)
//                    .setLeftLinkTextColor(R.color.colorAccent)
//                    .setRightLinkTextColor(R.color.colorAccent)
//                    .setAppBarColor(R.color.gradient5)
//                    .setAccountLinkingTextColor(R.color.gradient5)
//                    .setButtonBubbleTextColor(R.color.gradient5)
//                    .setReplyBarColor(R.color.gradient5)
//                    .setReplySenderColor(R.color.gradient5)
//                    .setStopRecordIcon(R.drawable.btn_send_chat)
//                    .setSendButtonIcon(R.drawable.btn_send_chat)
//                    .setShowAttachmentPanelIcon(R.drawable.btn_add_atachment)
//                    .setEnableAddFile(true)
//                    .setEnablePushNotification(false)
//                    .setEnableFcmPushNotification(true);
//        }
    }

    @Subscribe
    public void onReceivedComment(QiscusCommentReceivedEvent event) {

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
