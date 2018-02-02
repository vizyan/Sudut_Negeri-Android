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
import com.qiscus.sdk.Qiscus;
import com.qiscus.sdk.service.QiscusFirebaseService;

import org.json.JSONException;
import org.json.JSONObject;

import static android.content.ContentValues.TAG;

/**
 * Created by Vizyan on 1/20/2018.
 */

public class MyFirebaseMessagingService extends QiscusFirebaseService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        if (handleMessageReceived(remoteMessage)) { // For qiscus
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
        final int NOTIFICATION_ID = 1;

        NotificationManager mNotificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        String notificationChannelId = Qiscus.getApps().getPackageName() + ".qiscus.sdk.notification.channel";

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(notificationChannelId, "Chat", NotificationManager.IMPORTANCE_HIGH);
            NotificationManager notificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(notificationChannel);
            }
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, notificationChannelId)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(sender)
                .setChannelId(notificationChannelId)
                .setContentText(message);

        mNotificationManager.notify(NOTIFICATION_ID, builder.build());

    }
}
