package com.qiscus.internship.sudutnegeri.ui.admin;

import android.util.Log;

import com.google.gson.JsonObject;
import com.qiscus.internship.sudutnegeri.data.model.DataUser;
import com.qiscus.internship.sudutnegeri.data.network.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Vizyan on 1/14/2018.
 */

public class AdminPresenter {

    private AdminView adminView;

    public AdminPresenter(AdminView adminView){
        this.adminView = adminView;
    }

    public void showUnverifiedUser(){
        RetrofitClient.getInstance()
                .getApi()
                .getAllUser()
                .enqueue(new Callback<DataUser>() {
                    @Override
                    public void onResponse(Call<DataUser> call, Response<DataUser> response) {
                        Log.i(null, response.body().toString());
                    }

                    @Override
                    public void onFailure(Call<DataUser> call, Throwable t) {

                    }
                });
    }
}
