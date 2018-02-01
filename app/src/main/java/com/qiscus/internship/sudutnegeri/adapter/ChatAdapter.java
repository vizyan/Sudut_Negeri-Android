package com.qiscus.internship.sudutnegeri.adapter;

import android.content.Context;
import android.view.ViewGroup;

import com.qiscus.internship.sudutnegeri.R;
import com.qiscus.sdk.data.model.QiscusComment;
import com.qiscus.sdk.ui.adapter.QiscusChatAdapter;
import com.qiscus.sdk.ui.adapter.viewholder.QiscusBaseMessageViewHolder;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Vizyan on 1/31/2018.
 */

public class ChatAdapter extends QiscusChatAdapter {

    public ChatAdapter(Context context, boolean groupChat) {
        super(context, groupChat);
    }

}
