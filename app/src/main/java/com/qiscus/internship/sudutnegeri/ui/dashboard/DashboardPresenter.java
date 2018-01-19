package com.qiscus.internship.sudutnegeri.ui.dashboard;

import android.provider.ContactsContract;
import android.util.Log;

import com.qiscus.internship.sudutnegeri.data.model.DataProject;
import com.qiscus.internship.sudutnegeri.data.model.ResultListProject;
import com.qiscus.internship.sudutnegeri.data.model.ResultUser;
import com.qiscus.internship.sudutnegeri.data.network.RetrofitClient;

import java.util.List;

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

    public void getProjectByVerify(){
        RetrofitClient.getInstance()
                .getApi()
                .getUnverifiedProject("yes")
                .enqueue(new Callback<ResultListProject>() {
                    @Override
                    public void onResponse(Call<ResultListProject> call, Response<ResultListProject> response) {
                        if(response.isSuccessful()){
                            ResultListProject resultListProject = response.body();
                            List<DataProject> dataProjects = resultListProject.getData();
                            dashboardView.successShowProjectVerify(dataProjects);
                        }
                    }

                    @Override
                    public void onFailure(Call<ResultListProject> call, Throwable t) {

                    }
                });
    }

    public void getProjectByUser(){

    }

    public void getProjectByTime(){
        RetrofitClient.getInstance()
                .getApi()
                .getPorjectTime()
                .enqueue(new Callback<ResultListProject>() {
                             @Override
                             public void onResponse(Call<ResultListProject> call, Response<ResultListProject> response) {
                                 ResultListProject resultListProject = response.body();
                                 List<DataProject> dataProject = resultListProject.getData();
                             }

                             @Override
                             public void onFailure(Call<ResultListProject> call, Throwable t) {

                             }
                         });
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
