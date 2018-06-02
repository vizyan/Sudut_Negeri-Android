package com.qiscus.internship.sudutnegeri.ui.about;

import android.util.Log;

import com.google.gson.JsonObject;
import com.qiscus.internship.sudutnegeri.data.network.RetrofitClient;
import com.qiscus.sdk.Qiscus;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

/**
 * Created by Vizyan on 1/16/2018.
 */

public class AboutPresenter {
    private AboutView aboutView;

    public AboutPresenter(AboutView aboutView){
        this.aboutView = aboutView;
    }

    public void logout(){
        String tag = "About-logout";
        String email = aboutView.getEmail();
        String passwd = aboutView.getPasswd();

        RetrofitClient.getInstance()
                .getApi()
                .logout(email, passwd)
                .enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        if(response.isSuccessful()){
                            Qiscus.clearUser();
                            aboutView.successLogout();
                            Log.d(tag, response.body().toString());
                        } else {
                            aboutView.failed("Maaf terjadi kesalahan");
                            Log.d(tag, response.errorBody().toString());
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        aboutView.failed("Tidak ada koneksi");
                        Log.d(tag, t.getMessage());
                    }
                });
    }
}
