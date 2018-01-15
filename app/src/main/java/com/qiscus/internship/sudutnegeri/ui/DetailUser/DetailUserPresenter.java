package com.qiscus.internship.sudutnegeri.ui.DetailUser;

import com.qiscus.internship.sudutnegeri.data.model.DataUser;
import com.qiscus.internship.sudutnegeri.data.model.ResultUser;
import com.qiscus.internship.sudutnegeri.data.network.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by aufalmarom23 on 14/01/18.
 */

public class DetailUserPresenter {
    private DetailUserView detailUserView;

    public DetailUserPresenter(DetailUserView detailUserView) {
        this.detailUserView = detailUserView;
    }

    public void getDetailUserById(DataUser dataUser) {
        RetrofitClient.getInstance()
                .getApi()
                .getUser(dataUser.getId())
                .enqueue(new Callback<ResultUser>() {
                    @Override
                    public void onResponse(Call<ResultUser> call, Response<ResultUser> response) {
                        if (response.isSuccessful()){

                            ResultUser resultUser = response.body();
                            DataUser dataUser = resultUser.getData();

                            detailUserView.showSuccessDetailUserById(dataUser);
                        }

                    }

                    @Override
                    public void onFailure(Call<ResultUser> call, Throwable t) {

                    }
                });
    }

}
