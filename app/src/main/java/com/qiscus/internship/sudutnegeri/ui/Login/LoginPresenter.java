package com.qiscus.internship.sudutnegeri.ui.Login;

import android.util.Log;
import android.widget.Toast;

import com.qiscus.internship.sudutnegeri.data.model.DataUser;
import com.qiscus.internship.sudutnegeri.data.model.ResultLogin;
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
        String header = "application/json";

        RetrofitClient.getInstance()
                .getApi()
                .getUser(email, password)
                .enqueue(new Callback<ResultLogin>() {
                    @Override
                    public void onResponse(Call<ResultLogin> call, Response<ResultLogin> response) {
                        ResultLogin user = response.body();
                        String status = user.getStatus();

                        if (status.equals("success")) {
                            DataUser data = user.getData();
                            Log.e(null, "respon " + status);
                            String verify = data.getVerify();

                            if (verify.equals("yes")) {
                                loginVIew.success();
                                Log.e("Respon ", "" + verify);
                            }
                        } else {
                            loginVIew.failed();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResultLogin> call, Throwable t) {
                        Log.e("", "Gagal gan");
                        loginVIew.noConnection();
                    }
                });
    }
}
