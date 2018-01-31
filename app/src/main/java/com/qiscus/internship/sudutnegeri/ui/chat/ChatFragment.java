package com.qiscus.internship.sudutnegeri.ui.chat;


import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.qiscus.internship.sudutnegeri.R;
import com.qiscus.internship.sudutnegeri.adapter.ChatAdapter;
import com.qiscus.sdk.data.model.QiscusChatRoom;
import com.qiscus.sdk.data.model.QiscusComment;
import com.qiscus.sdk.ui.fragment.QiscusBaseChatFragment;
import com.qiscus.sdk.ui.fragment.QiscusChatFragment;
import com.qiscus.sdk.ui.view.QiscusAudioRecorderView;
import com.qiscus.sdk.ui.view.QiscusEditText;
import com.qiscus.sdk.ui.view.QiscusMentionSuggestionView;
import com.qiscus.sdk.ui.view.QiscusRecyclerView;
import com.qiscus.sdk.ui.view.QiscusReplyPreviewView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChatFragment extends QiscusBaseChatFragment<ChatAdapter> {

    protected UserTypingListener userTypingListener;
    private QiscusEditText editText;

    public static QiscusBaseChatFragment newInstance(QiscusChatRoom qiscusChatRoom) {
        ChatFragment chatFragment = new ChatFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(CHAT_ROOM_DATA, qiscusChatRoom);
        chatFragment.setArguments(bundle);
        return chatFragment;
    }

    @Override
    protected int getResourceLayout() {
        return R.layout.fragment_chat;
    }

    @NonNull
    @Override
    protected ViewGroup getRootView(View view) {
        return (ViewGroup) view.findViewById(R.id.root_view);
    }

    @Nullable
    @Override
    protected ViewGroup getEmptyChatHolder(View view) {
        return (ViewGroup) view.findViewById(R.id.empty_chat);
    }

    @NonNull
    @Override
    protected SwipeRefreshLayout getSwipeRefreshLayout(View view) {
        return (SwipeRefreshLayout) view.findViewById(R.id.swipe_layout);
    }

    @NonNull
    @Override
    protected QiscusRecyclerView getMessageRecyclerView(View view) {
        return (QiscusRecyclerView) view.findViewById(R.id.list_message);
    }

    @Nullable
    @Override
    protected ViewGroup getMessageInputPanel(View view) {
        return (ViewGroup) view.findViewById(R.id.box);
    }

    @Nullable
    @Override
    protected ViewGroup getMessageEditTextContainer(View view) {
        return (ViewGroup) view.findViewById(R.id.field_message_container);
    }

    @NonNull
    @Override
    protected EditText getMessageEditText(View view) {
        editText = view.findViewById(R.id.field_message);
        editText.setHint("Ketikkan pesan");
        return editText;
    }

    @NonNull
    @Override
    protected ImageView getSendButton(View view) {
        return (ImageView) view.findViewById(R.id.button_send);
    }

    @NonNull
    @Override
    protected QiscusMentionSuggestionView getMentionSuggestionView(View view) {
        return (QiscusMentionSuggestionView) view.findViewById(R.id.mention_suggestion);
    }

    @Nullable
    @Override
    protected View getNewMessageButton(View view) {
        return view.findViewById(R.id.button_new_message);
    }

    @NonNull
    @Override
    protected View getLoadMoreProgressBar(View view) {
        return view.findViewById(R.id.progressBar);
    }

    @Nullable
    @Override
    protected ImageView getEmptyChatImageView(View view) {
        return (ImageView) view.findViewById(R.id.empty_chat_icon);
    }

    @Nullable
    @Override
    protected TextView getEmptyChatTitleView(View view) {
        return (TextView) view.findViewById(R.id.empty_chat_title);
    }

    @Nullable
    @Override
    protected TextView getEmptyChatDescView(View view) {
        return (TextView) view.findViewById(R.id.empty_chat_desc);
    }

    @Nullable
    @Override
    protected ViewGroup getAttachmentPanel(View view) {
        return (ViewGroup) view.findViewById(R.id.add_panel);
    }

    @Nullable
    @Override
    protected View getAddImageLayout(View view) {
        return view.findViewById(R.id.add_image);
    }

    @Nullable
    @Override
    protected ImageView getAddImageButton(View view) {
        return (ImageView) view.findViewById(R.id.button_add_image);
    }

    @Nullable
    @Override
    protected TextView getAddImageTextView(View view) {
        return (TextView) view.findViewById(R.id.button_add_image_text);
    }

    @Nullable
    @Override
    protected View getTakeImageLayout(View view) {
        return view.findViewById(R.id.pick_picture);
    }

    @Nullable
    @Override
    protected ImageView getTakeImageButton(View view) {
        return (ImageView) view.findViewById(R.id.button_pick_picture);
    }

    @Nullable
    @Override
    protected TextView getTakeImageTextView(View view) {
        return (TextView) view.findViewById(R.id.button_pick_picture_text);
    }

    @Nullable
    @Override
    protected View getAddFileLayout(View view) {
        return view.findViewById(R.id.add_file);
    }

    @Nullable
    @Override
    protected ImageView getAddFileButton(View view) {
        return (ImageView) view.findViewById(R.id.button_add_file);
    }

    @Nullable
    @Override
    protected TextView getAddFileTextView(View view) {
        return (TextView) view.findViewById(R.id.button_add_file_text);
    }

    @Nullable
    @Override
    protected View getRecordAudioLayout(View view) {
        return view.findViewById(R.id.add_audio);
    }

    @Nullable
    @Override
    protected ImageView getRecordAudioButton(View view) {
        return (ImageView) view.findViewById(R.id.button_add_audio);
    }

    @Nullable
    @Override
    protected TextView getRecordAudioTextView(View view) {
        return (TextView) view.findViewById(R.id.button_add_audio_text);
    }

    @Nullable
    @Override
    protected View getAddContactLayout(View view) {
        return view.findViewById(R.id.add_contact);
    }

    @Nullable
    @Override
    protected ImageView getAddContactButton(View view) {
        return (ImageView) view.findViewById(R.id.button_add_contact);
    }

    @Nullable
    @Override
    protected TextView getAddContactTextView(View view) {
        return (TextView) view.findViewById(R.id.button_add_contact_text);
    }

    @Nullable
    @Override
    protected View getAddLocationLayout(View view) {
        return view.findViewById(R.id.add_location);
    }

    @Nullable
    @Override
    protected ImageView getAddLocationButton(View view) {
        return (ImageView) view.findViewById(R.id.button_add_location);
    }

    @Nullable
    @Override
    protected TextView getAddLocationTextView(View view) {
        return (TextView) view.findViewById(R.id.button_add_location_text);
    }

    @Nullable
    @Override
    public ImageView getHideAttachmentButton(View view) {
        return (ImageView) view.findViewById(R.id.button_keyboard);
    }

    @Nullable
    @Override
    protected ImageView getToggleEmojiButton(View view) {
        return (ImageView) view.findViewById(R.id.button_add_emoticon);
    }

    @Nullable
    @Override
    protected QiscusAudioRecorderView getRecordAudioPanel(View view) {
        return (QiscusAudioRecorderView) view.findViewById(R.id.record_panel);
    }

    @Nullable
    @Override
    protected QiscusReplyPreviewView getReplyPreviewView(View view) {
        return (QiscusReplyPreviewView) view.findViewById(R.id.reply_preview);
    }

    @Nullable
    @Override
    protected View getGotoBottomButton(View view) {
        return view.findViewById(R.id.button_go_bottom);
    }

    @Override
    protected ChatAdapter onCreateChatAdapter() {
        return new ChatAdapter(getActivity(), qiscusChatRoom.isGroup());
    }

    @Override
    public void onUserTyping(String user, boolean typing) {
        if (userTypingListener != null) {
            userTypingListener.onUserTyping(user, typing);
        }
    }

    public interface UserTypingListener {
        void onUserTyping(String user, boolean typing);
    }
}
