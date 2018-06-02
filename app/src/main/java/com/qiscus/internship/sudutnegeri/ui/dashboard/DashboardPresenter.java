package com.qiscus.internship.sudutnegeri.ui.dashboard;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.qiscus.internship.sudutnegeri.data.model.DataProject;
import com.qiscus.internship.sudutnegeri.data.model.DataUser;
import com.qiscus.internship.sudutnegeri.data.network.RetrofitClient;
import com.qiscus.sdk.Qiscus;

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
        String tag = "Dashboard-getPByVerify";
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
                            Log.d(tag, response.body().toString());
                        } else {
                            dashboardView.failed("Maaf terjadi kesalahan");
                            Log.d(tag, response.errorBody().toString());
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        dashboardView.failed("Tidak ada koneksi");
                        Log.d(tag, t.getMessage());
                    }
                });
    }

    public void getProjectByUser(DataUser dataUser){
        String tag = "Dashboard-getPByUser";
        RetrofitClient.getInstance()
                .getApi()
                .getProjectByUser(dataUser.getId())
                .enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        if (response.isSuccessful()){
                            JsonObject body = response.body();
                            JsonArray array = body.get("data").getAsJsonArray();
                            Type type = new TypeToken<List<DataProject>>(){}.getType();
                            List<DataProject> dataProjects = new Gson().fromJson(array, type);
                            dashboardView.successShowProjectByUser(dataProjects);
                            Log.d(tag, response.body().toString());
                        } else {
                            dashboardView.failed("Maaf terjadi kesalahan");
                            Log.d(tag, response.errorBody().toString());
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        dashboardView.failed("Maaf terjadi kesalahan");
                        Log.d(TAG, t.getMessage());
                    }
                });
    }

    public void logout(){
        String tag = "Dashboard-logout";
        String email = dashboardView.getEmail();
        String password = dashboardView.getPassword();

        RetrofitClient.getInstance()
                .getApi()
                .logout(email, password)
                .enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        if (response.isSuccessful()){
                            Qiscus.clearUser();
                            dashboardView.successLogout();
                            Log.d(tag, response.body().toString());
                        } else {
                            dashboardView.failed("Maaf terjadi kesalahan");
                            Log.d(tag, response.errorBody().toString());
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        dashboardView.failed("Tidak ada koneksi");
                        Log.d(tag, t.getMessage());
                    }
                });
    }


}
