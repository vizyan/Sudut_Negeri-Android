package com.qiscus.internship.sudutnegeri.util;

import android.util.Log;

import com.qiscus.sdk.data.model.QiscusComment;

/**
 * Created by Vizyan on 2/2/2018.
 */

public class RealTimeChatHandler {

    private Listener listener;

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    public void removeListener() {
        listener = null;
    }

    public void updateChatrooms(QiscusComment qiscusComment) {
        triggerListener(qiscusComment);
    }

    private void triggerListener(QiscusComment comment) {
        if (listener != null) {
            listener.onReceiveComment(comment);
        }

    }

    public interface Listener {
        void onReceiveComment(QiscusComment comment);

    }
}
