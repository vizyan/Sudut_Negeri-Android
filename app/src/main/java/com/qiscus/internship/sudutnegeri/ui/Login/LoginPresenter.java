package com.qiscus.internship.sudutnegeri.ui.Login;

import com.google.gson.JsonObject;
import com.qiscus.internship.sudutnegeri.data.remote.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Vizyan on 1/10/2018.
 */

public class LoginPresenter {

    private LoginView loginVIew;

    public LoginPresenter(LoginView loginView){
        this.loginVIew = loginView;
    }

    public void validLogin(){
        RetrofitClient.getInstance()
                .getApi()
                .getProjekAll()
                .enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {

                    }
                });
    }
}
