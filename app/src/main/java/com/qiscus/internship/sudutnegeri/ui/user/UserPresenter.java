package com.qiscus.internship.sudutnegeri.ui.user;

import android.util.Log;

import com.qiscus.internship.sudutnegeri.data.model.DataUser;
import com.qiscus.internship.sudutnegeri.data.model.ResultUser;
import com.qiscus.internship.sudutnegeri.data.network.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Vizyan on 1/14/2018.
 */

public class UserPresenter {

    private UserView userView;

    public UserPresenter(UserView userView){
        this.userView = userView;
    }

    public void getUserById(DataUser dataUser){
        RetrofitClient.getInstance()
                .getApi()
                .getUser(dataUser.getId())
                .enqueue(new Callback<ResultUser>() {
                    @Override
                    public void onResponse(Call<ResultUser> call, Response<ResultUser> response) {
                        if (response.isSuccessful()){
                            ResultUser resultUser = response.body();
                            DataUser dataUser = resultUser.getData();
                            userView.successUserbyId(dataUser);
                        }
                    }

                    @Override
                    public void onFailure(Call<ResultUser> call, Throwable t) {

                    }
                });
    }

    public void putUser(DataUser dataUser){

        String name = userView.getName();
        String address = userView.getAddress();
        String phone = userView.getPhone();
        String verify = userView.getVerify();

        RetrofitClient.getInstance()
                .getApi()
                .putUser(dataUser.getId(), name, address, phone, verify)
                .enqueue(new Callback<ResultUser>() {
                    @Override
                    public void onResponse(Call<ResultUser> call, Response<ResultUser> response) {
                        if (response.isSuccessful()){
                            ResultUser resultUser = response.body();
                            DataUser dataUser = resultUser.getData();
                            userView.successPut(dataUser);
                        }
                    }

                    @Override
                    public void onFailure(Call<ResultUser> call, Throwable t) {

                    }
                });
    }

    public void logout() {
        String email = userView.getEmail();
        String password = userView.getPassword();

        RetrofitClient.getInstance()
                .getApi()
                .logout(email, password)
                .enqueue(new Callback<ResultUser>() {
                    @Override
                    public void onResponse(Call<ResultUser> call, Response<ResultUser> response) {
                        if (response.isSuccessful()){
                            userView.successLogout();
                        }
                        Log.e(null, "Respon" + password);
                    }

                    @Override
                    public void onFailure(Call<ResultUser> call, Throwable t) {

                    }
                });
    }
}
