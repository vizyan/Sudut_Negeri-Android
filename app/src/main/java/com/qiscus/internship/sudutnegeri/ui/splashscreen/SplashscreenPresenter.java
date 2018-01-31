package com.qiscus.internship.sudutnegeri.ui.splashscreen;

import android.util.Log;

import com.qiscus.internship.sudutnegeri.data.model.DataUser;
import com.qiscus.internship.sudutnegeri.data.model.ResultUser;
import com.qiscus.internship.sudutnegeri.data.network.RetrofitClient;
import com.qiscus.sdk.Qiscus;
import com.qiscus.sdk.data.model.QiscusAccount;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Vizyan on 1/10/2018.
 */

public class SplashscreenPresenter {

    private SplashscreenView splashscreenView;

    public SplashscreenPresenter(SplashscreenView splashscreenView){
        this.splashscreenView = splashscreenView;
    }

    public void loginUser(){
        String email = splashscreenView.getEmail();
        String password = splashscreenView.getPassword();

        RetrofitClient.getInstance()
                .getApi()
                .cekLoginUser(email, password)
                .enqueue(new Callback<ResultUser>() {
                    @Override
                    public void onResponse(Call<ResultUser> call, Response<ResultUser> response) {
                        ResultUser login = response.body();
                        String message = login.getMessage();

                        if (message.equals("success")) {
                            DataUser data = login.getData();
                            String token = data.getApiToken();
                            Log.e(null, "respon " + token);
                            if (token==null) {
                                splashscreenView.notLogin();
                            } else {
                                loginChat(data);
                            }
                        } else {
                            splashscreenView.notLogin();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResultUser> call, Throwable t) {
                        Log.e("", "Gagal gan");
                        splashscreenView.notLogin();
                    }
                });
    }

    public void loginChat(DataUser dataUser){
        Qiscus.setUser(dataUser.getEmail(), dataUser.getPasswd())
                .withUsername(dataUser.getName())
                .withAvatarUrl(dataUser.getPhoto())
                .save(new Qiscus.SetUserListener() {
                    @Override
                    public void onSuccess(QiscusAccount qiscusAccount) {
                        //on success followup
                        splashscreenView.successUser(dataUser);
                    }
                    @Override
                    public void onError(Throwable throwable) {
                        //do anything if error occurs
                        splashscreenView.failedQiscus(throwable);
                    }
                });

    }

}
