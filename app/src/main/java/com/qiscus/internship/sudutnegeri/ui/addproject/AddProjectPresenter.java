package com.qiscus.internship.sudutnegeri.ui.addproject;

import android.provider.ContactsContract;
import android.util.Log;

import com.qiscus.internship.sudutnegeri.data.model.DataUser;
import com.qiscus.internship.sudutnegeri.data.model.ResultProject;
import com.qiscus.internship.sudutnegeri.data.model.UploadObject;
import com.qiscus.internship.sudutnegeri.data.network.RetrofitClient;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
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

    public void postProject(DataUser dataUser, String path){
        String name = addProjectView.getProjectName();
        String location = addProjectView.getLocation();
        String target_at = addProjectView.getTarget();
        String information = addProjectView.getInformation();
        String photo = "http://vizyan.xyz/images/" +path;
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

    public void uploadFile(File file, DataUser dataUser){
        RequestBody mFile = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("file", file.getName(), mFile);
        RequestBody filename = RequestBody.create(MediaType.parse("text/plain"), file.getName());
        RetrofitClient.getInstance()
                .getApi()
                .uploadFile(fileToUpload, filename)
                .enqueue(new Callback<UploadObject>() {
                    @Override
                    public void onResponse(Call<UploadObject> call, Response<UploadObject> response) {
                        UploadObject uploadObject = response.body();
                        String path = uploadObject.getSuccess();
                        postProject(dataUser, path);
                        addProjectView.successUploadFile(path);
                    }

                    @Override
                    public void onFailure(Call<UploadObject> call, Throwable t) {
                        addProjectView.failedUploadFile();
                    }
                });
    }
}
