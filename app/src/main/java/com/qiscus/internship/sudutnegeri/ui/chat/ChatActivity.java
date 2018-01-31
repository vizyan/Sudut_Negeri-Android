package com.qiscus.internship.sudutnegeri.ui.chat;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;
import android.widget.Toast;

import com.qiscus.internship.sudutnegeri.R;
import com.qiscus.sdk.data.model.QiscusChatRoom;
import com.qiscus.sdk.ui.QiscusBaseChatActivity;
import com.qiscus.sdk.ui.fragment.QiscusBaseChatFragment;
import com.qiscus.sdk.ui.view.QiscusCircularImageView;
import com.squareup.picasso.Picasso;

import java.util.Date;

public class ChatActivity extends QiscusBaseChatActivity {

    private TextView tvChatTitle, tvChatSubtitle;
    private QiscusCircularImageView qiscusCircularImageView;

    public static Intent generateIntent(Context context, QiscusChatRoom qiscusChatRoom){
        Intent intent = new Intent(context, ChatActivity.class);
        intent.putExtra(CHAT_ROOM_DATA, qiscusChatRoom);
        return intent;
    }

    @Override
    protected int getResourceLayout() {
        return R.layout.activity_chat;
    }

    @Override
    protected void onLoadView() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        tvChatTitle = findViewById(R.id.tv_title);
        tvChatSubtitle = findViewById(R.id.tv_subtitle);
        qiscusCircularImageView = findViewById(R.id.profile_picture);
    }

    @Override
    protected void onViewReady(Bundle savedInstanceState) {
        super.onViewReady(savedInstanceState);
        tvChatTitle.setText(qiscusChatRoom.getName());
        Picasso.with(this)
                .load(qiscusChatRoom.getAvatarUrl())
                .into(qiscusCircularImageView);
    }

    @Override
    protected QiscusBaseChatFragment onCreateChatFragment() {
        return ChatFragment.newInstance(qiscusChatRoom);
    }

    @Override
    public void onUserStatusChanged(String user, boolean online, Date lastActive) {
        if (online){
            tvChatSubtitle.setText("Online");
        } else {
            tvChatSubtitle.setText("");
        }
    }

    @Override
    public void onUserTyping(String user, boolean typing) {
        if (typing){
            tvChatSubtitle.setText("Sedang mengetik...");
        } else {

        }
    }
}
