package com.qiscus.internship.sudutnegeri.ui.login;

import android.util.Log;

import com.google.gson.JsonObject;
import com.qiscus.internship.sudutnegeri.data.model.DataUser;
import com.qiscus.internship.sudutnegeri.data.model.ResultUser;
import com.qiscus.internship.sudutnegeri.data.network.RetrofitClient;
import com.qiscus.sdk.Qiscus;
import com.qiscus.sdk.data.model.QiscusAccount;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.HttpException;
import retrofit2.Response;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static org.greenrobot.eventbus.EventBus.TAG;

/**
 * Created by Vizyan on 1/10/2018.
 */

public class LoginPresenter {

    private LoginView loginView;

    public LoginPresenter(LoginView loginView){
        this.loginView = loginView;
    }

    public void loginUser(){
        String email = loginView.getEmail();
        String password = loginView.getPassword();

        RetrofitClient.getInstance()
                .getApi()
                .loginUser(email, password)
                .enqueue(new Callback<ResultUser>() {
                    @Override
                    public void onResponse(Call<ResultUser> call, Response<ResultUser> response) {
                        if (response.isSuccessful()){
                            ResultUser login = response.body();
                            String message = login.getMessage();

                            if (message.equals("success")) {
                                DataUser data = login.getData();
                                Log.e(null, "respon " + message);
                                String verify = data.getVerify();
                                if (verify.equals("yes")) {
                                    loginChat(data);
                                } else {
                                    loginView.notVerified();
                                }
                            } else {
                                loginView.failed();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ResultUser> call, Throwable t) {
                        Log.e("", "Gagal gan");
                        loginView.noConnection();
                    }
                });
    }

    public void loginAdmin(){
        String email = loginView.getEmail();
        String password = loginView.getPassword();

        RetrofitClient.getInstance()
                .getApi()
                .loginAdmin(email, password)
                .enqueue(new Callback<ResultUser>() {
                    @Override
                    public void onResponse(Call<ResultUser> call, Response<ResultUser> response) {
                        if (response.isSuccessful()){
                            ResultUser login = response.body();
                            String message = login.getMessage();

                            if (message.equals("success")) {
                                //DataUser data = login.getData();
                                loginView.successAdmin();
                            } else {
                                loginView.failed();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ResultUser> call, Throwable t) {

                    }
                });
    }

    public void loginChat(DataUser dataUser){
        Qiscus.setUser(dataUser.getEmail(), dataUser.getPasswd())
                .withUsername(dataUser.getName())
                .save()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(qiscusAccount -> {
                    loginView.successUser(dataUser);
                    Log.d(null, "ini respon sukses");
                }, throwable -> {
                    loginView.failedQiscuss(throwable);
                });

    }
}
