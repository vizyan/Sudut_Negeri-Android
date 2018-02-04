package com.qiscus.internship.sudutnegeri.ui.login;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.qiscus.internship.sudutnegeri.data.model.DataUser;
import com.qiscus.internship.sudutnegeri.data.network.RetrofitClient;
import com.qiscus.sdk.Qiscus;
import com.qiscus.sdk.data.model.QiscusAccount;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

/**
 * Created by Vizyan on 1/10/2018.
 */

public class LoginPresenter {

    private LoginView loginView;

    public LoginPresenter(LoginView loginView){
        this.loginView = loginView;
    }

    public void loginUser(){
        String tag = "Login-loginUser";
        String email = loginView.getEmail();
        String password = loginView.getPassword();

        RetrofitClient.getInstance()
                .getApi()
                .loginUser(email, password)
                .enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        if (response.isSuccessful()){
                            JsonObject body = response.body();
                            String message = body.get("message").getAsString();
                            if (message.equalsIgnoreCase("success")){
                                JsonObject data = body.get("data").getAsJsonObject();
                                DataUser dataUser = new Gson().fromJson(data, DataUser.class);
                                String verify = dataUser.getVerify();
                                if (verify.equalsIgnoreCase("yes")){
                                    loginChat(dataUser, "user");
                                } else {
                                    loginView.failed("Akun belum diverifikasi");
                                }
                            } else {
                                loginView.failed("Email atau kata sandi salah");
                            }
                            Log.d(tag, response.body().toString());
                        } else {
                            loginView.failed("Maaf terjadi kesalahan");
                            Log.d(tag, response.errorBody().toString());
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        loginView.failed("Tidak ada koneksi");
                        Log.d(tag, t.getMessage());
                    }
                });
    }

    public void loginAdmin(){
        String tag = "Login-loginAdmin";
        String email = loginView.getEmail();
        String password = loginView.getPassword();

        RetrofitClient.getInstance()
                .getApi()
                .loginAdmin(email, password)
                .enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        if (response.isSuccessful()){
                            JsonObject body = response.body();
                            String message = body.get("message").getAsString();
                            if (message.equalsIgnoreCase("success")){
                                JsonObject data = body.get("data").getAsJsonObject();
                                DataUser dataUser = new Gson().fromJson(data, DataUser.class);
                                loginChat(dataUser, "admin");
                            } else {
                                loginView.failed("Email atau kata sandi salah");
                            }
                            Log.d(tag, response.body().toString());
                        } else {
                            loginView.failed("Maaf terjadi kesalahan");
                            Log.d(tag, response.errorBody().toString());
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        loginView.failed("Tidak ada koneksi");
                        Log.d(tag, t.getMessage());
                    }
                });
    }

    public void loginChat(DataUser dataUser, String user){
        String tag = "Login-loginChat";

        Qiscus.setUser(dataUser.getEmail(), dataUser.getEmail())
                .withUsername(dataUser.getName())
                .withAvatarUrl(dataUser.getPhoto())
                .save(new Qiscus.SetUserListener() {
                    @Override
                    public void onSuccess(QiscusAccount qiscusAccount) {
                        //on success followup
                        if (user.equalsIgnoreCase("user")){
                            loginView.successUser(dataUser);
                        } else {
                            loginView.successAdmin(dataUser);
                        }
                        Log.d(tag, qiscusAccount.toString());
                    }
                    @Override
                    public void onError(Throwable t) {
                        //do anything if error occurs
                        loginView.failed("Tidak ada koneksi");
                        Log.d(tag, t.getMessage());
                    }
                });
    }
}
