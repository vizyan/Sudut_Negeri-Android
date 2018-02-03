package com.qiscus.internship.sudutnegeri.ui.login;

import android.util.Log;

import com.qiscus.internship.sudutnegeri.data.model.DataUser;
import com.qiscus.internship.sudutnegeri.data.model.ResultUser;
import com.qiscus.internship.sudutnegeri.data.network.RetrofitClient;
import com.qiscus.sdk.Qiscus;
import com.qiscus.sdk.data.model.QiscusAccount;

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
                        if (response.isSuccessful()){
                            ResultUser login = response.body();
                            String message = login.getMessage();

                            if (message.equals("success")) {
                                DataUser data = login.getData();
                                Log.e(null, "respon " + message);
                                String verify = data.getVerify();
                                if (verify.equals("yes")) {
                                    loginChat(data, "user");
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
                                DataUser dataUser = login.getData();
                                loginChat(dataUser, "admin");
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

    public void loginChat(DataUser dataUser, String user){

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
                    }
                    @Override
                    public void onError(Throwable throwable) {
                        //do anything if error occurs
                        loginView.failedQiscuss(throwable);
                    }
                });
    }
}
