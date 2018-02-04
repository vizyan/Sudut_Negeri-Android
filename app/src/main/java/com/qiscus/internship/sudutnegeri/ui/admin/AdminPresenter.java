package com.qiscus.internship.sudutnegeri.ui.admin;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.qiscus.internship.sudutnegeri.data.model.DataProject;
import com.qiscus.internship.sudutnegeri.data.model.DataUser;
import com.qiscus.internship.sudutnegeri.data.network.RetrofitClient;

import java.lang.reflect.Type;
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

    public void getUserByVerify(){
        String tag = "Admin-getUser";
        RetrofitClient.getInstance()
                .getApi()
                .getUserByVerify("no")
                .enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        if (response.isSuccessful()){
                            JsonObject body = response.body();
                            JsonArray array = body.get("data").getAsJsonArray();
                            Type type = new TypeToken<List<DataUser>>(){}.getType();
                            List<DataUser> dataUsers = new Gson().fromJson(array, type);
                            adminView.successShowUser(dataUsers);
                            Log.d(tag, response.body().toString());
                        } else {
                            adminView.failedShowUser("Maaf terjadi kesalahan");
                            Log.d(tag, response.errorBody().toString());
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        adminView.failedShowUser("Tidak ada koneksi");
                        Log.d(tag, t.getMessage());
                    }
                });
    }

    public void getProjectByVerify(){
        String tag = "Admin-getProject";
        RetrofitClient.getInstance()
                .getApi()
                .getProjectByVerify("no")
                .enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        if (response.isSuccessful()){
                            JsonObject body = response.body();
                            JsonArray array = body.get("data").getAsJsonArray();
                            Type type = new TypeToken<List<DataProject>>(){}.getType();
                            List<DataProject> dataProjects = new Gson().fromJson(array, type);
                            adminView.successShowProject(dataProjects);
                            Log.d(tag, response.body().toString());
                        } else {
                            adminView.failedShowProject("Maaf terjadi kesalahan");
                            Log.d(tag, response.errorBody().toString());
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        adminView.failedShowProject("Tidak ada koneksi");
                        Log.d(tag, t.getMessage());
                    }
                });
    }
}
