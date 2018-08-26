package com.qiscus.internship.sudutnegeri.ui.user;

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

import static android.content.ContentValues.TAG;

/**
 * Created by Vizyan on 1/14/2018.
 */

public class UserPresenter {

    private UserView userView;

    public UserPresenter(UserView userView){
        this.userView = userView;
    }

    public void getUserById(DataUser dataUser){
        String tag = "User-getUserById";
        RetrofitClient.getInstance()
                .getApi()
                .getUser(dataUser.getId())
                .enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        if (response.isSuccessful()){
                            JsonObject body = response.body();
                            JsonObject data = body.get("data").getAsJsonObject();
                            DataUser dataUser = new Gson().fromJson(data, DataUser.class);
                            userView.successUserbyId(dataUser);
                            Log.d(tag, response.body().toString());
                        } else {
                            userView.failed("Maaf terjadi kesalahan");
                            Log.d(tag, response.errorBody().toString());
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        userView.failed("Tidak ada koneksi");
                        Log.e(tag, t.getMessage());
                    }
                });
    }

    public void putUser(int id, String name, String address, String phone, String verify, String param){
        String tag = "User-putUser";

        RetrofitClient.getInstance()
                .getApi()
                .putUser(id, name, address, phone, verify)
                .enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        if (response.isSuccessful()){
                            JsonObject body = response.body();
                            JsonObject data = body.get("data").getAsJsonObject();
                            DataUser dataUser = new Gson().fromJson(data, DataUser.class);
                            String user = "user";
                            if (param.equalsIgnoreCase(user)){
                                putUserQiscus(dataUser);
                            } else {
                                userView.successPutUser(dataUser);
                            }
                            Log.d(tag, response.body().toString());
                        } else {
                            userView.failed("Maaf terjadi kesalahan");
                            Log.d(tag, response.errorBody().toString());
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        userView.failed("Tidak ada koneksi");
                        Log.d(tag, t.getMessage());
                    }
                });
    }

    public void putUserQiscus(DataUser dataUser){
        String tag = "User-putUserQiscus";

        Qiscus.updateUser(dataUser.getName(), dataUser.getPhoto(), new Qiscus.SetUserListener() {
            @Override
            public void onSuccess(QiscusAccount qiscusAccount) {
                //do anything after it successfully updated
                userView.successPutUser(dataUser);
                Log.d(tag, qiscusAccount.toString());
            }

            @Override
            public void onError(Throwable throwable) {
                //do anything if error occurs
                userView.failed("Tidak ada koneksi");
                Log.d(tag, throwable.getMessage());
            }
        });
    }

    public void logout() {
        String tag = "User-logout";
        String email = userView.getEmail();
        String password = userView.getPassword();

        RetrofitClient.getInstance()
                .getApi()
                .logout(email, password)
                .enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        if (response.isSuccessful()){
                            Qiscus.clearUser();
                            userView.successLogout();
                            Log.d(tag, response.body().toString());
                        } else {
                            userView.failed("Maaf terjadi kesalahan");
                            Log.d(tag, response.errorBody().toString());
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        userView.failed("Tidak ada koneksi");
                        Log.d(tag, t.getMessage());
                    }
                });
    }

    public void unverifyUser(int id){
        String tag = "User-unverifyUser";
        RetrofitClient.getInstance()
                .getApi()
                .deleteUser(id)
                .enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        if (response.isSuccessful()){
                            userView.successUnverify();
                            Log.d(tag, response.body().toString());
                        } else {
                            userView.failed("Maaf terjadi kesalahan");
                            Log.d(tag, response.errorBody().toString());
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        userView.failed("Tidak ada koneksi");
                        Log.d(tag, t.getMessage());
                    }
                });
    }
}
