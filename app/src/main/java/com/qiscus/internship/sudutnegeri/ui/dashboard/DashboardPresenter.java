package com.qiscus.internship.sudutnegeri.ui.dashboard;

import android.util.Log;

import com.qiscus.internship.sudutnegeri.data.model.ResultUser;
import com.qiscus.internship.sudutnegeri.data.network.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Vizyan on 1/15/2018.
 */

public class DashboardPresenter {

    private DashboardView dashboardView;

    public DashboardPresenter(DashboardView dashboardView){
        this.dashboardView = dashboardView;
    }

    public void getUserById(){

    }

    public void getAllProject(){

    }

    public void getProjectByUser(){

    }

    public void logout(){
        String email = dashboardView.getEmail();
        String password = dashboardView.getPassword();

        RetrofitClient.getInstance()
                .getApi()
                .logout(email, password)
                .enqueue(new Callback<ResultUser>() {
                    @Override
                    public void onResponse(Call<ResultUser> call, Response<ResultUser> response) {
                        if (response.isSuccessful()){
                            dashboardView.successLogout();
                        }
                        Log.e(null, "Respon" + password);
                    }

                    @Override
                    public void onFailure(Call<ResultUser> call, Throwable t) {
                        Log.e(null, "" +password);
                    }
                });
    }
}
