package com.qiscus.internship.sudutnegeri.ui.project;

import android.util.Log;

import com.qiscus.internship.sudutnegeri.data.model.DataProject;
import com.qiscus.internship.sudutnegeri.data.model.DataUser;
import com.qiscus.internship.sudutnegeri.data.model.ResultProject;
import com.qiscus.internship.sudutnegeri.data.model.ResultUser;
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
        RetrofitClient.getInstance()
                .getApi()
                .getProject(dataProject.getId())
                .enqueue(new Callback<ResultProject>() {
                    @Override
                    public void onResponse(Call<ResultProject> call, Response<ResultProject> response) {
                        if (response.isSuccessful()){
                            ResultProject resultProject = response.body();
                            DataProject dataProject = resultProject.getData();
                            projectView.successProjectById(dataProject);
                        }
                    }

                    @Override
                    public void onFailure(Call<ResultProject> call, Throwable t) {

                    }
                });
    }

    public void getUserById(DataProject dataProject){
        RetrofitClient.getInstance()
                .getApi()
                .getUser(dataProject.getIdUser())
                .enqueue(new Callback<ResultUser>() {
                    @Override
                    public void onResponse(Call<ResultUser> call, Response<ResultUser> response) {
                        if (response.isSuccessful()){
                            ResultUser resultUser = response.body();
                            DataUser dataUser = resultUser.getData();
                            projectView.successUserById(dataUser);
                        }
                    }

                    @Override
                    public void onFailure(Call<ResultUser> call, Throwable t) {

                    }
                });
    }

    public void putProject(DataProject dataProject){
        int funds = projectView.getFunds();
        RetrofitClient.getInstance()
                .getApi()
                .putProject(dataProject.getId(), dataProject.getNameProject(), "yes", dataProject.getLocation(), dataProject.getTargetAt(), dataProject.getInformation(), dataProject.getPhoto(), dataProject.getIdUser(), funds)
                .enqueue(new Callback<ResultProject>() {
                    @Override
                    public void onResponse(Call<ResultProject> call, Response<ResultProject> response) {
                        if (response.isSuccessful()){
                            projectView.successPutProject(dataProject);
                            Log.d(TAG, "onResponse: " +response.errorBody());
                        }
                    }

                    @Override
                    public void onFailure(Call<ResultProject> call, Throwable t) {
                        Log.e(null, t.getMessage());
                    }
                });
    }

    public void unverifyProject(int id){
        RetrofitClient.getInstance()
                .getApi()
                .deleteProject(id)
                .enqueue(new Callback<ResultProject>() {
                    @Override
                    public void onResponse(Call<ResultProject> call, Response<ResultProject> response) {
                        if (response.isSuccessful()){
                            projectView.successUnverify();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResultProject> call, Throwable t) {

                    }
                });
    }

    public void chatUser(String email){
        Qiscus.buildChatRoomWith(email)
                .build(new Qiscus.ChatBuilderListener() {
                    @Override
                    public void onSuccess(QiscusChatRoom qiscusChatRoom) {
                        projectView.successChatUser(qiscusChatRoom);
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        projectView.failedChatUser(throwable);
                    }
                });
    }
}
