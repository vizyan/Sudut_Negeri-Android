package com.qiscus.internship.sudutnegeri.ui.addproject;

import android.util.Log;

import com.qiscus.internship.sudutnegeri.data.model.DataUser;
import com.qiscus.internship.sudutnegeri.data.model.ResultProject;
import com.qiscus.internship.sudutnegeri.data.network.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Vizyan on 1/22/2018.
 */

public class AddProjectPresenter {
    private AddProjectView addProjectView;

    public AddProjectPresenter(AddProjectView addProjectView){
        this.addProjectView = addProjectView;
    }

    public void postProject(DataUser dataUser){
        String name = addProjectView.getProjectName();
        String location = addProjectView.getLocation();
        String target_at = addProjectView.getTarget();
        String information = addProjectView.getInformation();
        String photo = addProjectView.getPhoto();
        int id_user = dataUser.getId();
        int funds = 0;

        RetrofitClient.getInstance()
                .getApi()
                .postProject(name, "no", location, target_at, information, photo, id_user, funds)
                .enqueue(new Callback<ResultProject>() {
                    @Override
                    public void onResponse(Call<ResultProject> call, Response<ResultProject> response) {
                        if (response.isSuccessful()){
                            Log.e(null, response.body().toString());
                            addProjectView.successPostProject();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResultProject> call, Throwable t) {
                        addProjectView.failedPostProject(t.getMessage());
                        Log.e(null, t.getMessage());
                    }
                });
    }
}
