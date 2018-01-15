package com.qiscus.internship.sudutnegeri.ui.login;

import android.util.Log;

import com.google.gson.JsonObject;
import com.qiscus.internship.sudutnegeri.data.model.DataUser;
import com.qiscus.internship.sudutnegeri.data.model.ResultUser;
import com.qiscus.internship.sudutnegeri.data.network.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
                        ResultUser login = response.body();
                        String message = login.getMessage();

                        if (message.equals("success")) {
                            DataUser data = login.getData();
                            Log.e(null, "respon " + message);
                            String verify = data.getVerify();
                            if (verify.equals("yes")) {
                                loginView.successUser(data);
                                Log.e("Respon ", "" + verify);
                            } else {
                                loginView.notVerified();
                            }
                        } else {
                            loginView.failed();
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
                        ResultUser login = response.body();
                        String message = login.getMessage();

                        if (message.equals("success")) {
                            DataUser data = login.getData();
                            Log.e(null, "respon " + message);
                            loginView.successAdmin();
                        } else {
                            loginView.failed();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResultUser> call, Throwable t) {

                    }
                });
    }
}
