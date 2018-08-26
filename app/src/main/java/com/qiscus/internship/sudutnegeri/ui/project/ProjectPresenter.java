package com.qiscus.internship.sudutnegeri.ui.project;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.qiscus.internship.sudutnegeri.data.model.DataProject;
import com.qiscus.internship.sudutnegeri.data.model.DataUser;
import com.qiscus.internship.sudutnegeri.data.network.RetrofitClient;
import com.qiscus.sdk.Qiscus;
import com.qiscus.sdk.data.model.QiscusChatRoom;
import com.qiscus.sdk.data.remote.QiscusApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

/**
 * Created by aufalmarom23 on 15/01/18.
 */

public class ProjectPresenter {

    private ProjectView projectView;

    public ProjectPresenter(ProjectView projectView){
        this.projectView = projectView;
    }

    public void getPorjectById(DataProject dataProject){
        String tag = "Project-getPById";
        RetrofitClient.getInstance()
                .getApi()
                .getProject(dataProject.getId())
                .enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        if (response.isSuccessful()){
                            JsonObject body = response.body();
                            JsonObject data = body.get("data").getAsJsonObject();
                            DataProject dataProject = new Gson().fromJson(data, DataProject.class);
                            projectView.successProjectById(dataProject);
                            Log.d(tag, response.body().toString());
                        } else {
                            projectView.failed("Maaf terjadi kesalahan");
                            Log.d(tag, response.errorBody().toString());
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        projectView.failed("Tidak ada koneksi");
                        Log.d(tag, t.getMessage());
                    }
                });
    }

    public void getUserById(DataProject dataProject){
        String tag = "Project-getUById";
        RetrofitClient.getInstance()
                .getApi()
                .getUser(dataProject.getIdUser())
                .enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        if (response.isSuccessful()) {
                            JsonObject body = response.body();
                            JsonObject data = body.get("data").getAsJsonObject();
                            DataUser dataUser = new Gson().fromJson(data, DataUser.class);
                            projectView.successUserById(dataUser);
                            Log.d(tag, response.body().toString());
                        } else {
                            projectView.failed("Maaf terjadi kesalahan");
                            Log.d(tag, response.errorBody().toString());
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        projectView.failed("Tidak ada koneksi");
                        Log.d(tag, t.getMessage());
                    }
                });
    }

    public void putProject(DataProject dataProject){
        String tag = "Project-putProject";
        int funds = projectView.getFunds();
        RetrofitClient.getInstance()
                .getApi()
                .putProject(dataProject.getId(), dataProject.getNameProject(), "yes", dataProject.getLocation(), dataProject.getTargetAt(), dataProject.getInformation(), dataProject.getPhoto(), dataProject.getIdUser(), funds)
                .enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        if (response.isSuccessful()){
                            projectView.successPutProject(dataProject);
                            Log.d(tag, response.body().toString());
                        } else {
                            projectView.failed("Maaf terjadi kesalahan");
                            Log.d(tag, response.errorBody().toString());
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        projectView.failed("Tidak ada koneksi");
                        Log.d(tag, t.getMessage());
                    }
                });
    }

    public void unverifyProject(int id){
        String tag = "Project-unverifyProject";
        RetrofitClient.getInstance()
                .getApi()
                .deleteProject(id)
                .enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        if (response.isSuccessful()){
                            projectView.successUnverify();
                            Log.d(tag, response.body().toString());
                        } else {
                            projectView.failed("Maaf terjadi kesalahan");
                            Log.d(tag, response.errorBody().toString());
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        projectView.failed("Tidak ada koneksi");
                        Log.d(tag, t.getMessage());
                    }
                });
    }

    public void chatUser(String email){
        String tag = "Project-chatUser";
        Qiscus.buildChatRoomWith(email)
                .build(new Qiscus.ChatBuilderListener() {
                    @Override
                    public void onSuccess(QiscusChatRoom qiscusChatRoom) {
                        projectView.successChatUser(qiscusChatRoom);
                        Log.d(tag, qiscusChatRoom.toString());
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        projectView.failedChat(throwable.getMessage().toString());
                        Log.d(tag, throwable.getMessage());
                    }
                });
    }
}
