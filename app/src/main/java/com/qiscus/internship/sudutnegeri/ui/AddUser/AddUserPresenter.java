package com.qiscus.internship.sudutnegeri.ui.AddUser;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.qiscus.internship.sudutnegeri.data.model.Car;
import com.qiscus.internship.sudutnegeri.data.remote.RetrofitClient;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Vizyan on 1/10/2018.
 */

public class AddUserPresenter {

    private AddUserView addUserView;

    public AddUserPresenter(AddUserView addUserView){
        this.addUserView = addUserView;
    }

    public void saveCar(){
        String name = addUserView.getName();
        String email = addUserView.getEmail();
        String password = addUserView.getPassword();
        String retypepasswd = addUserView.getRetypePasswd();
        String noIdentity = addUserView.getNoIdentity();
        String address = addUserView.getAddress();
        String phone = addUserView.getPhone();


        RetrofitClient.getInstance()
                .getApi()
                .newUser(name, email, password, retypepasswd, noIdentity, address, phone, "no")
                .enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        if(response.isSuccessful()){
                            JsonObject object = response.body();
                            Log.e(null, "respon" +object);
                            addUserView.success();
                        } else {
                            addUserView.failedParse();
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        addUserView.cantConnect();
                    }
                });
    }
}
