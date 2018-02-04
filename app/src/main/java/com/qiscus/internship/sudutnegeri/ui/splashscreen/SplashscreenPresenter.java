package com.qiscus.internship.sudutnegeri.ui.splashscreen;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.qiscus.internship.sudutnegeri.data.model.DataUser;
import com.qiscus.internship.sudutnegeri.data.network.RetrofitClient;
import com.qiscus.sdk.Qiscus;
import com.qiscus.sdk.data.model.QiscusAccount;

import java.lang.reflect.Type;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static android.content.ContentValues.TAG;

/**
 * Created by Vizyan on 1/10/2018.
 */

public class SplashscreenPresenter {

    private SplashscreenView splashscreenView;

    public SplashscreenPresenter(SplashscreenView splashscreenView){
        this.splashscreenView = splashscreenView;
    }

    public void loginUser(){
        String tag = "Splashscreen-loginUser";
        String email = splashscreenView.getEmail();
        String password = splashscreenView.getPassword();

        RetrofitClient.getInstance()
                .getApi()
                .cekLoginUser(email, password)
                .enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        if (response.isSuccessful()){
                            JsonObject body = response.body();
                            String message = body.get("message").getAsString();
                            if (message.equalsIgnoreCase("success")){
                                JsonObject data = body.get("data").getAsJsonObject();
                                DataUser dataUser = new Gson().fromJson(data, DataUser.class);
                                if (dataUser.getApiToken()!=null){
                                    loginChat(dataUser);
                                } else {
                                    splashscreenView.failed("Belum login");
                                }
                            } else {
                                splashscreenView.failed("Email atau kata sandi salah");
                            }
                            Log.d(tag, response.body().toString());
                        } else {
                            splashscreenView.failed("Maaf terjadi kesalahan");
                            Log.d(tag, response.errorBody().toString());
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        splashscreenView.failed("Tidak ada koneksi");
                        Log.d(tag, t.getMessage());
                    }
                });
    }

    public void loginChat(DataUser dataUser){
        String tag = "Splashscreen-loginChat";
        Qiscus.setUser(dataUser.getEmail(), dataUser.getEmail())
                .withUsername(dataUser.getName())
                .withAvatarUrl(dataUser.getPhoto())
                .save(new Qiscus.SetUserListener() {
                    @Override
                    public void onSuccess(QiscusAccount qiscusAccount) {
                        splashscreenView.successUser(dataUser);
                        Log.d(tag, qiscusAccount.toString());
                    }
                    @Override
                    public void onError(Throwable throwable) {
                        splashscreenView.failed("Tidak ada koneksi");
                        Log.d(tag, throwable.getMessage());
                    }
                });

    }

}
