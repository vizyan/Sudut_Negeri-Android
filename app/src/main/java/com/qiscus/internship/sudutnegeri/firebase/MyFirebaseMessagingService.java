package com.qiscus.internship.sudutnegeri.firebase;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.qiscus.internship.sudutnegeri.R;
import com.qiscus.sdk.service.QiscusFirebaseService;

import org.json.JSONException;
import org.json.JSONObject;

import static android.content.ContentValues.TAG;

/**
 * Created by Vizyan on 1/20/2018.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        if (QiscusFirebaseService.handleMessageReceived(remoteMessage)) { // For qiscus
            try {
                JSONObject jsonObject = new JSONObject(remoteMessage.getData().get("payload"));
                String sender = jsonObject.getString("username").toLowerCase().toString();
                String message = jsonObject.getString("message").toString();
                sendPushNotification(sender, message);
                Log.e(TAG, message);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return;
        }

    }

    private void sendPushNotification(String sender, String message) {
        final int NOTIFICATION_ID = 1002;
        String NOTIFICATION_CHANNEL_ID = "sudut_negeri";

        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        String id = "my_channel_01";
        CharSequence name = "sudut negeri";
        String description = "sudut_negeri";
        int importance = NotificationManager.IMPORTANCE_HIGH;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel mChannel = new NotificationChannel(id, name, importance);
            mChannel.setDescription(description);
            mChannel.enableLights(true);
            mChannel.setLightColor(Color.RED);
            mChannel.enableVibration(true);
            mNotificationManager.createNotificationChannel(mChannel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(sender)
                .setChannelId(NOTIFICATION_CHANNEL_ID)
                .setContentText(message);

        mNotificationManager.notify(NOTIFICATION_ID, builder.build());

    }
}
