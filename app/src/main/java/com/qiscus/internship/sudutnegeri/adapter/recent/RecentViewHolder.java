package com.qiscus.internship.sudutnegeri.adapter.recent;

import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qiscus.internship.sudutnegeri.R;
import com.qiscus.internship.sudutnegeri.data.model.DataUser;
import com.qiscus.sdk.data.model.QiscusAccount;
import com.qiscus.sdk.data.model.QiscusChatRoom;

import java.text.SimpleDateFormat;
import java.util.Date;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Vizyan on 2/1/2018.
 */

class RecentViewHolder extends RecyclerView.ViewHolder {
    private ImageView ivRoomPhoto, ivRoomUnread;
    private TextView tvRoomName, tvRoomLastChat, tvRoomDate, tvRoomUnread;
    private LinearLayout llRoom;
    private QiscusChatRoom qiscusRoom;

    public RecentViewHolder(View itemView) {
        super(itemView);
        initView(itemView);
    }

    private void initView(View view) {
        ivRoomPhoto = view.findViewById(R.id.ivRoomPhoto);
        ivRoomUnread = view.findViewById(R.id.ivRoomUnread);
        tvRoomName = view.findViewById(R.id.tvRoomName);
        tvRoomLastChat = view.findViewById(R.id.tvRoomLastChat);
        tvRoomDate = view.findViewById(R.id.tvRoomDate);
        tvRoomUnread = view.findViewById(R.id.tvRoomUnread);
        llRoom = view.findViewById(R.id.llRoom);
    }

    public void bind(QiscusChatRoom qiscusChatRoom, RecentListener recentListener, DataUser dataUser) {
        int count = qiscusChatRoom.getUnreadCount();
        String latestConversation = qiscusChatRoom.getLastComment().getMessage();
        String newlatestConversation = latestConversation;
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat dateFormatToday = new SimpleDateFormat("hh:mm a");
        Date messageDate = qiscusChatRoom.getLastComment().getTime();
        String finalDateFormat = "";
        if (DateUtils.isToday(messageDate.getTime())) {
            finalDateFormat = dateFormatToday.format(qiscusChatRoom.getLastComment().getTime());
        }
        else {
            finalDateFormat = dateFormat.format(qiscusChatRoom.getLastComment().getTime());
        }

        if (count == 0) {
            ivRoomUnread.setVisibility(View.INVISIBLE);
            tvRoomUnread.setVisibility(View.VISIBLE);
        }

        if (latestConversation.contains("[file]")) {
            newlatestConversation = qiscusChatRoom.getName() + " mengirim file";
        }

        if (latestConversation.length() > 25){
            StringBuilder builder = new StringBuilder(latestConversation);
            newlatestConversation = builder.substring(0, 24) + " ...";
        }

        if (qiscusChatRoom.getDistinctId().equals(dataUser.getEmail() + " " +dataUser.getEmail())){
            tvRoomName.setText("Diri sendiri");
        } else {
            tvRoomName.setText(qiscusChatRoom.getName());
        }
        Log.d("INI ID", qiscusChatRoom.getUniqueId());
        tvRoomLastChat.setText(newlatestConversation);
        tvRoomDate.setText(finalDateFormat);
        tvRoomUnread.setText(String.valueOf(count));
        recentListener.setImage(ivRoomPhoto, qiscusChatRoom.getAvatarUrl());

        llRoom.setOnClickListener(v -> recentListener.onRecentClick(qiscusChatRoom));
    }
}
