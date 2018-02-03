package com.qiscus.internship.sudutnegeri.ui.dashboard;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.qiscus.internship.sudutnegeri.data.model.DataProject;
import com.qiscus.internship.sudutnegeri.data.model.DataUser;
import com.qiscus.internship.sudutnegeri.data.model.ResultListProject;
import com.qiscus.internship.sudutnegeri.data.model.ResultUser;
import com.qiscus.internship.sudutnegeri.data.network.RetrofitClient;
import com.qiscus.sdk.data.model.QiscusChatRoom;
import com.qiscus.sdk.data.remote.QiscusApi;
import com.qiscus.sdk.util.QiscusRxExecutor;

import java.lang.reflect.Type;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

/**
 * Created by Vizyan on 1/15/2018.
 */

public class DashboardPresenter {

    private DashboardView dashboardView;

    public DashboardPresenter(DashboardView dashboardView){
        this.dashboardView = dashboardView;
    }

    public void getProjectByVerify(){
        RetrofitClient.getInstance()
                .getApi()
                .getProjectByVerify("yes")
                .enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        if(response.isSuccessful()){
                            JsonObject body = response.body();
                            JsonArray array = body.get("data").getAsJsonArray();
                            Type type = new TypeToken<List<DataProject>>(){}.getType();
                            List<DataProject> dataProjects = new Gson().fromJson(array, type);
                            dashboardView.successShowProjectVerify(dataProjects);
                            Log.d(TAG, response.body().toString());
                        } else {
                            dashboardView.failedShowProjectByVerify(response.errorBody().toString());
                            Log.d(TAG, response.errorBody().toString());
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        dashboardView.failedShowProjectByVerify("Tidak ada koneksi");
                        Log.d(TAG, t.getMessage());
                    }
                });
    }

    public void getProjectByUser(DataUser dataUser){
        RetrofitClient.getInstance()
                .getApi()
                .getProjectByUser(dataUser.getId())
                .enqueue(new Callback<ResultListProject>() {
                    @Override
                    public void onResponse(Call<ResultListProject> call, Response<ResultListProject> response) {
                        if (response.isSuccessful()){
                            ResultListProject resultListProject = response.body();
                            List<DataProject> dataProjectList = resultListProject.getData();
                            dashboardView.successShowProjectByUser(dataProjectList);
                        }
                    }

                    @Override
                    public void onFailure(Call<ResultListProject> call, Throwable t) {
                        dashboardView.failedShowProjectByUser();
                    }
                });
    }

    public void logout(){
        String email = dashboardView.getEmail();
        String password = dashboardView.getPassword();

        RetrofitClient.getInstance()
                .getApi()
                .logout(email, password)
                .enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        if (response.isSuccessful()){
                            dashboardView.successLogout();
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
    }


}
