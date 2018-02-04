package com.qiscus.internship.sudutnegeri.ui.recentchat;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import com.qiscus.internship.sudutnegeri.R;
import com.qiscus.internship.sudutnegeri.SudutNegeri;
import com.qiscus.internship.sudutnegeri.adapter.recent.RecentAdapter;
import com.qiscus.internship.sudutnegeri.adapter.recent.RecentListener;
import com.qiscus.internship.sudutnegeri.data.model.DataUser;
import com.qiscus.internship.sudutnegeri.ui.chat.ChatActivity;
import com.qiscus.internship.sudutnegeri.util.CircleTransform;
import com.qiscus.internship.sudutnegeri.util.Constant;
import com.qiscus.internship.sudutnegeri.util.RealTimeChatHandler;
import com.qiscus.sdk.data.model.QiscusChatRoom;
import com.qiscus.sdk.data.model.QiscusComment;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RecentChatActivity extends AppCompatActivity implements RecentChatView, RecentListener, RealTimeChatHandler.Listener{

    private RecentChatPresenter recentChatPresenter;
    private RecentAdapter recentAdapter;
    private DataUser dataUser;
    Button btnRecentAdmin;
    List<QiscusChatRoom> qiscusChatRooms;
    ImageView ivRecentNoData, ivRecentUPhoto;
    RecyclerView rvRecentChat;
    SearchView svRecentSearch;
    SwipeRefreshLayout srlRecentChat;
    TextView tvRecentNoData, tvRecentUName, tvRecentUEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        initPresenter();
        initPresenter();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recent_chat);
        initView();
        initDataIntent();
        initDataPresenter();

        refresh();
        search();
        chatAdmin();
    }

    @Override
    protected void onStart() {
        super.onStart();
        SudutNegeri.getInstance().getRealTimeChatHandler().setListener(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        SudutNegeri.getInstance().getRealTimeChatHandler().removeListener();
    }

    @Override
    protected void onResume() {
        super.onResume();
        recentChatPresenter.getRoomList();
    }

    private void initPresenter() {
        recentChatPresenter = new RecentChatPresenter(this);
    }

    private void initDataPresenter(){
        recentChatPresenter.getRoomList();
    }

    private void initView() {
        btnRecentAdmin = findViewById(R.id.btnRecentAdmin);
        ivRecentNoData = findViewById(R.id.ivRecentNoData);
        ivRecentUPhoto = findViewById(R.id.ivRecentUPhoto);
        rvRecentChat = findViewById(R.id.rvRecentChat);
        srlRecentChat = findViewById(R.id.srlRecentChat);
        svRecentSearch = findViewById(R.id.svRecentSearch);
        tvRecentNoData = findViewById(R.id.tvRecentNoData);
        tvRecentUName = findViewById(R.id.tvRecentUName);
        tvRecentUEmail = findViewById(R.id.tvRecentUEmail);
    }

    private void initDataIntent(){
        dataUser = getIntent().getParcelableExtra(Constant.Extra.DATA);
        if (dataUser==null) finish();

        String photo = dataUser.getPhoto();

        if (photo==null){
            photo = "null";
        }

        Picasso.with(this)
                .load(photo)
                .transform(new CircleTransform())
                .into(ivRecentUPhoto);

        tvRecentUName.setText(dataUser.getName());
        tvRecentUEmail.setText(dataUser.getEmail());
    }

    private void initAdapter() {
        recentAdapter.setAdapterListener(this);
        rvRecentChat.setLayoutManager(new LinearLayoutManager(this));
        rvRecentChat.setAdapter(recentAdapter);
    }

    private void refresh(){
        srlRecentChat.setOnRefreshListener(() -> recentChatPresenter.getRoomList());
    }

    private void search(){
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        svRecentSearch.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        svRecentSearch.setMaxWidth(Integer.MAX_VALUE);
        int id = svRecentSearch.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
        TextView textView = svRecentSearch.findViewById(id);
        textView.setTextColor(Color.WHITE);

        svRecentSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                recentAdapter.getFilter().filter(query);
                initAdapter();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                recentAdapter.getFilter().filter(newText);
                initAdapter();
                return false;
            }
        });
    }

    private void chatAdmin(){
        btnRecentAdmin.setOnClickListener(v -> recentChatPresenter.chatAdmin("snqiscus@gmail.com"));
    }

    @Override
    public void successRoomList(List<QiscusChatRoom> qiscusChatRooms) {
        recentAdapter = new RecentAdapter(qiscusChatRooms);
        srlRecentChat.setRefreshing(false);
        this.qiscusChatRooms = qiscusChatRooms;
        if (qiscusChatRooms.size()>0){
            ivRecentNoData.setVisibility(View.GONE);
            tvRecentNoData.setVisibility(View.GONE);
        }
        initAdapter();
    }

    @Override
    public void successChatAdmin(QiscusChatRoom qiscusChatRoom) {
        Intent intent = ChatActivity.generateIntent(RecentChatActivity.this, qiscusChatRoom);
        startActivity(intent);
    }

    @Override
    public void successChatUser(QiscusChatRoom qiscusChatRoom) {
        Intent intent = ChatActivity.generateIntent(RecentChatActivity.this, qiscusChatRoom);
        startActivity(intent);
    }

    @Override
    public void failed(String s) {

    }

    @Override
    public void setImage(ImageView ivRoomPhoto, String avatarUrl) {
        Picasso.with(this)
                .load(avatarUrl)
                .transform(new CircleTransform())
                .into(ivRoomPhoto);
    }

    @Override
    public void onProjectClick(QiscusChatRoom qiscusChatRoom) {
        recentChatPresenter.chatUser(qiscusChatRoom);
    }

    @Override
    public void onReceiveComment(QiscusComment comment) {
        recentChatPresenter.getRoomList();
    }
}
