package com.qiscus.internship.sudutnegeri.firebase;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.qiscus.sdk.Qiscus;

import static android.content.ContentValues.TAG;

/**
 * Created by Vizyan on 1/20/2018.
 */

public class MyFirebaseInstanceIdService extends FirebaseInstanceIdService {

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();

        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Qiscus.setFcmToken(refreshedToken);
        Log.e(TAG, "Ini token : " + refreshedToken);
    }
}
