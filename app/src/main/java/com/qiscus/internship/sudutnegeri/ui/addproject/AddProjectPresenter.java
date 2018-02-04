package com.qiscus.internship.sudutnegeri.ui.addproject;

import android.app.ProgressDialog;
import android.provider.ContactsContract;
import android.util.Log;

import com.google.gson.JsonObject;
import com.qiscus.internship.sudutnegeri.data.model.DataUser;
import com.qiscus.internship.sudutnegeri.data.model.UploadObject;
import com.qiscus.internship.sudutnegeri.data.network.RetrofitClient;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

/**
 * Created by Vizyan on 1/22/2018.
 */

public class AddProjectPresenter {
    private AddProjectView addProjectView;

    public AddProjectPresenter(AddProjectView addProjectView){
        this.addProjectView = addProjectView;
    }

    public void postProject(DataUser dataUser, String path){
        String tag = "AddProject-postProject";
        String name = addProjectView.getProjectName();
        String location = addProjectView.getLocation();
        String target_at = addProjectView.getTarget();
        String information = addProjectView.getInformation();
        String photo = "http://vizyan.xyz/images/" +path;
        int target_funds = addProjectView.getTargetFunds();
        int id_user = dataUser.getId();
        int funds = 0;

        RetrofitClient.getInstance()
                .getApi()
                .postProject(name, "no", location, target_at, information, photo, id_user, funds, target_funds)
                .enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        if (response.isSuccessful()){
                            addProjectView.successPostProject();
                            Log.d(tag, response.body().toString());
                        } else {
                            addProjectView.failed("Maaf terjadi kesalahan");
                            Log.d(tag, response.errorBody().toString());
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        addProjectView.failed("Tidak ada koneksi");
                        Log.d(tag, t.getMessage());
                    }
                });
    }

    public void uploadFile(File file, DataUser dataUser, String random){
        String tag = "AddProject-uploadFile";
        RequestBody mFile = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("file", random + file.getName(), mFile);
        RequestBody filename = RequestBody.create(MediaType.parse("text/plain"), random + file.getName());
        RetrofitClient.getInstance()
                .getApi()
                .uploadFile(fileToUpload, filename)
                .enqueue(new Callback<UploadObject>() {
                    @Override
                    public void onResponse(Call<UploadObject> call, Response<UploadObject> response) {
                        if (response.isSuccessful()){
                            UploadObject uploadObject = response.body();
                            String path = uploadObject.getSuccess();
                            postProject(dataUser, path);
                            Log.d(tag, response.body().toString());
                        } else {
                            addProjectView.failed("Tidak dapat mengunggah gambar");
                            Log.d(tag, response.errorBody().toString());
                        }
                    }

                    @Override
                    public void onFailure(Call<UploadObject> call, Throwable t) {
                        addProjectView.failed("Tidak ada koneksi");
                        Log.d(tag, t.getMessage());
                    }
                });
    }
}
