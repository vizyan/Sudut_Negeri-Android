package com.qiscus.internship.sudutnegeri;

import android.app.Application;
import android.graphics.Color;
import android.os.Build;

import com.qiscus.internship.sudutnegeri.util.Constant;
import com.qiscus.sdk.Qiscus;
import com.qiscus.sdk.data.model.QiscusComment;
import com.qiscus.sdk.event.QiscusChatRoomEvent;
import com.qiscus.sdk.event.QiscusCommentReceivedEvent;
import com.qiscus.sdk.event.QiscusUserStatusEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

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
    }

    public void initQiscus() {
        Qiscus.init(this, Constant.QISCUS_APP_ID);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Qiscus.getChatConfig()
                    .setSwipeRefreshColorScheme(R.color.colorPrimary, R.color.colorAccent)
//                    .setLeftBubbleTextColor(R.color.qiscus_primary_text)
//                    .setLeftBubbleTimeColor(R.color.qiscus_secondary_text)
//                    .setLeftLinkTextColor(R.color.qiscus_primary_text)
                    .setSelectedBubbleBackgroundColor(R.color.colorPrimary)
                    .setMessageFieldHint("Ketikkan pesan...")
                    .setLeftLinkTextColor(R.color.colorAccent)
                    .setRightLinkTextColor(R.color.colorAccent)
                    .setReadIconColor(R.color.colorPrimary)
                    .setAppBarColor(R.color.colorPrimary)
                    .setStatusBarColor(R.color.colorPrimaryDark)
                    .setAccentColor(R.color.colorAccent)
                    .setAccountLinkingTextColor(R.color.colorPrimary)
                    .setButtonBubbleTextColor(R.color.colorPrimary)
                    .setReplyBarColor(R.color.colorPrimary)
                    .setReplySenderColor(R.color.colorPrimary)
                    .setSendButtonIcon(R.drawable.ic_qiscus_send_on)
                    .setShowAttachmentPanelIcon(R.drawable.ic_qiscus_send_off)
                    .setEnableAddFile(true)
                    .setEnableReplyNotification(true)
                    .setEnablePushNotification(false)
                    .setEnableFcmPushNotification(true);
        } else {
            Qiscus.getChatConfig()
//                    .setSwipeRefreshColorScheme(R.color.colorPrimary, R.color.colorAccent)
//                    .setLeftBubbleTextColor(R.color.qiscus_primary_text)
//                    .setLeftBubbleTimeColor(R.color.qiscus_secondary_text)
//                    .setLeftLinkTextColor(R.color.qiscus_primary_text)
                    .setSelectedBubbleBackgroundColor(R.color.colorPrimary)
                    .setReadIconColor(R.color.colorPrimary)
                    .setMessageFieldHint("Ketikkan pesan...")
                    .setLeftLinkTextColor(R.color.colorAccent)
                    .setRightLinkTextColor(R.color.colorAccent)
                    .setAppBarColor(R.color.colorPrimary)
                    .setStatusBarColor(R.color.colorPrimaryDark)
                    .setAccentColor(R.color.colorAccent)
                    .setAccountLinkingTextColor(R.color.colorPrimary)
                    .setButtonBubbleTextColor(R.color.colorPrimary)
                    .setReplyBarColor(R.color.colorPrimary)
                    .setReplySenderColor(R.color.colorPrimary)
                    .setSendButtonIcon(R.drawable.ic_qiscus_send_on)
                    .setShowAttachmentPanelIcon(R.drawable.ic_qiscus_send_off)
                    .setEnableAddFile(true)
                    .setEnablePushNotification(false)
                    .setEnableFcmPushNotification(true);
        }
    }
}
