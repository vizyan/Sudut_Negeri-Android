package com.qiscus.internship.sudutnegeri.ui.chat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.qiscus.internship.sudutnegeri.R;
import com.qiscus.sdk.ui.QiscusBaseChatActivity;
import com.qiscus.sdk.ui.fragment.QiscusBaseChatFragment;

import java.util.Date;

public class ChatActivity extends QiscusBaseChatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
    }

    @Override
    protected int getResourceLayout() {
        return 0;
    }

    @Override
    protected void onLoadView() {

    }

    @Override
    protected QiscusBaseChatFragment onCreateChatFragment() {
        return null;
    }

    @Override
    public void onUserStatusChanged(String user, boolean online, Date lastActive) {

    }

    @Override
    public void onUserTyping(String user, boolean typing) {

    }
}
