package com.qiscus.internship.sudutnegeri.ui.chat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.qiscus.internship.sudutnegeri.R;
import com.qiscus.sdk.data.model.QiscusChatRoom;
import com.qiscus.sdk.ui.QiscusBaseChatActivity;
import com.qiscus.sdk.ui.fragment.QiscusBaseChatFragment;
import com.qiscus.sdk.ui.view.QiscusCircularImageView;
import com.qiscus.sdk.util.QiscusDateUtil;
import com.squareup.picasso.Picasso;

import java.util.Date;

public class ChatActivity extends QiscusBaseChatActivity {

    public ImageView back;
    public static TextView tvChatTitle, tvChatSubtitle;
    private QiscusCircularImageView qiscusCircularImageView;
    private String email;

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
        back = findViewById(R.id.back);
        tvChatTitle = findViewById(R.id.tv_title);
        tvChatSubtitle = findViewById(R.id.tv_subtitle);
        qiscusCircularImageView = findViewById(R.id.profile_picture);
    }

    @Override
    protected void onViewReady(Bundle savedInstanceState) {
        super.onViewReady(savedInstanceState);
        SharedPreferences preferences = getSharedPreferences("LoginPreference", MODE_PRIVATE);
        email = preferences.getString("email", "");
        if (qiscusChatRoom.getDistinctId().equals(email +" "+email)){
            tvChatTitle.setText("Diri sendiri");
        } else {
            tvChatTitle.setText(qiscusChatRoom.getName());
        }

        Picasso.with(this)
                .load(qiscusChatRoom.getAvatarUrl())
                .into(qiscusCircularImageView);

        back();
    }

    private void back() {
        back.setOnClickListener((v) -> finish());
    }

    @Override
    protected QiscusBaseChatFragment onCreateChatFragment() {
        return ChatFragment.newInstance(qiscusChatRoom);
    }

    @Override
    public void onUserTyping(String user, boolean typing) {}

    @Override
    public void onUserStatusChanged(String user, boolean online, Date lastActive) {}

}
