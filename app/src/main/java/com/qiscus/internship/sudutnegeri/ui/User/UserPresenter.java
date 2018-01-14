package com.qiscus.internship.sudutnegeri.ui.User;

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

    private UserView userVIew;

    public UserPresenter(UserView userView){
        this.userVIew = userView;
    }

    public void showUserById(DataUser dataUser){
        RetrofitClient.getInstance()
                .getApi()
                .getUser(dataUser.getId())
                .enqueue(new Callback<ResultUser>() {
                    @Override
                    public void onResponse(Call<ResultUser> call, Response<ResultUser> response) {
                        if (response.isSuccessful()){
                            ResultUser resultUser = response.body();
                            DataUser dataUser = resultUser.getData();
                            userVIew.showSuccess(dataUser);
                        }
                    }

                    @Override
                    public void onFailure(Call<ResultUser> call, Throwable t) {

                    }
                });
    }
}
