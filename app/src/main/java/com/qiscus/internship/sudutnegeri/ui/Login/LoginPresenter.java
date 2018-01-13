package com.qiscus.internship.sudutnegeri.ui.Login;

import android.util.Log;

import com.qiscus.internship.sudutnegeri.data.model.DataUser;
import com.qiscus.internship.sudutnegeri.data.model.ResultUser;
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

    public void login(){
        String email = loginVIew.getEmail();
        String password = loginVIew.getPassword();

        RetrofitClient.getInstance()
                .getApi()
                .getUser(email, password)
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
                                loginVIew.success();
                                Log.e("Respon ", "" + verify);
                            } else {
                                loginVIew.notVerified();
                            }
                        } else {
                            loginVIew.failed();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResultUser> call, Throwable t) {
                        Log.e("", "Gagal gan");
                        loginVIew.noConnection();
                    }
                });
    }
}
