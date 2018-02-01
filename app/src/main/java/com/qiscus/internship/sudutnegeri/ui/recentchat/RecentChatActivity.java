package com.qiscus.internship.sudutnegeri.ui.recentchat;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.qiscus.internship.sudutnegeri.R;
import com.qiscus.internship.sudutnegeri.adapter.project.ProjectAdapter;
import com.qiscus.internship.sudutnegeri.adapter.recent.RecentAdapter;
import com.qiscus.internship.sudutnegeri.adapter.recent.RecentListener;
import com.qiscus.internship.sudutnegeri.ui.chat.ChatActivity;
import com.qiscus.internship.sudutnegeri.ui.project.ProjectActivity;
import com.qiscus.sdk.Qiscus;
import com.qiscus.sdk.data.model.QiscusChatRoom;
import com.qiscus.sdk.data.remote.QiscusApi;
import com.qiscus.sdk.util.QiscusRxExecutor;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import retrofit2.HttpException;

public class RecentChatActivity extends AppCompatActivity implements RecentChatView, RecentListener{

    private RecentChatPresenter recentChatPresenter;
    private RecentAdapter recentAdapter;
    List<QiscusChatRoom> qiscusChatRooms;
    ImageView ivRecentNoData;
    RecyclerView rvRecentChat;
    SwipeRefreshLayout srlRecentChat;
    TextView tvRecentNoData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        initPresenter();
        initPresenter();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recent_chat);
        initView();
        initDataPresenter();

        refresh();
    }

    private void initPresenter() {
        recentChatPresenter = new RecentChatPresenter(this);
    }

    private void initDataPresenter(){
        recentChatPresenter.getRoomList();
    }

    private void initView() {
        ivRecentNoData = findViewById(R.id.ivRecentNoData);
        rvRecentChat = findViewById(R.id.rvRecentChat);
        srlRecentChat = findViewById(R.id.srlRecentChat);
        tvRecentNoData = findViewById(R.id.tvRecentNoData);
    }

    private void initAdapter() {
        recentAdapter.setAdapterListener(this);
        rvRecentChat.setLayoutManager(new LinearLayoutManager(this));
        rvRecentChat.setAdapter(recentAdapter);
    }

    private void refresh(){
        srlRecentChat.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                recentChatPresenter.getRoomList();
            }
        });
    }

    public class CircleTransform implements Transformation {
        @Override
        public Bitmap transform(Bitmap source) {
            int size = Math.min(source.getWidth(), source.getHeight());

            int x = (source.getWidth() - size) / 2;
            int y = (source.getHeight() - size) / 2;

            Bitmap squaredBitmap = Bitmap.createBitmap(source, x, y, size, size);
            if (squaredBitmap != source) {
                source.recycle();
            }

            Bitmap bitmap = Bitmap.createBitmap(size, size, source.getConfig());

            Canvas canvas = new Canvas(bitmap);
            Paint paint = new Paint();
            BitmapShader shader = new BitmapShader(squaredBitmap,
                    BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP);
            paint.setShader(shader);
            paint.setAntiAlias(true);

            float r = size / 2f;
            canvas.drawCircle(r, r, r, paint);

            squaredBitmap.recycle();
            return bitmap;
        }

        @Override
        public String key() {
            return "circle";
        }
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
    public void setImage(ImageView ivRoomPhoto, String avatarUrl) {
        Picasso.with(this)
                .load(avatarUrl)
                .transform(new CircleTransform())
                .into(ivRoomPhoto);
    }

    @Override
    public void onProjectClick(QiscusChatRoom qiscusChatRoom) {
        QiscusRxExecutor.execute(QiscusApi
                        .getInstance().getChatRoom(qiscusChatRoom.getId()),
                new QiscusRxExecutor.Listener<QiscusChatRoom>() {
                    @Override
                    public void onSuccess(QiscusChatRoom qiscusChatRoom) {
                        Intent intent = ChatActivity.generateIntent(RecentChatActivity.this, qiscusChatRoom);
                        startActivity(intent);
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        if (throwable instanceof HttpException) { //Error response from server
                            HttpException e = (HttpException) throwable;
                            try {
                                String errorMessage = e.response().errorBody().string();
                                JSONObject json = new JSONObject(errorMessage).getJSONObject("error");
                                String finalError = json.getString("message");
                                if (json.has("detailed_messages") ) {
                                    JSONArray detailedMessages = json.getJSONArray("detailed_messages");
                                    finalError = (String) detailedMessages.get(0);
                                }
                                Toast.makeText(RecentChatActivity.this, finalError, Toast.LENGTH_LONG).show();
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            } catch (JSONException e1) {
                                e1.printStackTrace();
                            }
                        } else if (throwable instanceof IOException) { //Error from network
                            Toast.makeText(RecentChatActivity.this, "Tidak dapat terkoneksi dengan server", Toast.LENGTH_LONG).show();
                        } else { //Unknown error
                            Toast.makeText(RecentChatActivity.this, "Kesalahan", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}
