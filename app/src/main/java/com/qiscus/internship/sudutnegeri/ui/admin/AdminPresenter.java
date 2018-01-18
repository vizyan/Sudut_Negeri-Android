package com.qiscus.internship.sudutnegeri.ui.admin;

import android.util.Log;

import com.qiscus.internship.sudutnegeri.data.model.DataProject;
import com.qiscus.internship.sudutnegeri.data.model.DataUser;
import com.qiscus.internship.sudutnegeri.data.model.ResultListProject;
import com.qiscus.internship.sudutnegeri.data.model.ResultListUser;
import com.qiscus.internship.sudutnegeri.data.network.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Vizyan on 1/14/2018.
 */

public class AdminPresenter {

    private AdminView adminView;

    public AdminPresenter(AdminView adminView){
        this.adminView = adminView;
    }

    public void showUnverifiedUser(){
        RetrofitClient.getInstance()
                .getApi()
                .getUnverifiedUser("no")
                .enqueue(new Callback<ResultListUser>() {
                    @Override
                    public void onResponse(Call<ResultListUser> call, Response<ResultListUser> response) {
                        if (response.isSuccessful()){
                            ResultListUser resultListUser = response.body();
                            List<DataUser> user = resultListUser.getData();
                            adminView.successShowUser(user);
                            Log.e(null, "Body" + response.body().getData());
                        }
                    }

                    @Override
                    public void onFailure(Call<ResultListUser> call, Throwable t) {

                    }
                });
    }

    public void showUnverifiedProject(){
        RetrofitClient.getInstance()
                .getApi()
                .getUnverifiedProject("no")
                .enqueue(new Callback<ResultListProject>() {
                    @Override
                    public void onResponse(Call<ResultListProject> call, Response<ResultListProject> response) {
                        if (response.isSuccessful()){
                            ResultListProject resultListProject = response.body();
                            List<DataProject> projects = resultListProject.getData();
                            adminView.successShowProject(projects);
                        }
                    }

                    @Override
                    public void onFailure(Call<ResultListProject> call, Throwable t) {

                    }
                });
    }
}
