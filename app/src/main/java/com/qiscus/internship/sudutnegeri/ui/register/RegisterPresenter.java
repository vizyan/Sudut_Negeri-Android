package com.qiscus.internship.sudutnegeri.ui.register;

import android.util.Log;

import com.qiscus.internship.sudutnegeri.data.model.DataUser;
import com.qiscus.internship.sudutnegeri.data.model.ResultUser;
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
 * Created by Vizyan on 1/10/2018.
 */

public class RegisterPresenter {

    private RegisterView registerView;

    public RegisterPresenter(RegisterView registerView){
        this.registerView = registerView;
    }

    public void addUser(String path){
        String name = registerView.getName();
        String email = registerView.getEmail();
        String password = registerView.getPassword();
        final String retypepasswd = registerView.getRetypePasswd();
        String noIdentity = registerView.getNoIdentity();
        String address = registerView.getAddress();
        String phone = registerView.getPhone();
        String photo = "http://vizyan.xyz/images/" +path;

        RetrofitClient.getInstance()
                .getApi()
                .register(name, email, password, retypepasswd, noIdentity, address, phone, "no", photo)
                .enqueue(new Callback<ResultUser>() {
                    @Override
                    public void onResponse(Call<ResultUser> call, Response<ResultUser> response) {
                        if (response.isSuccessful()){
                            ResultUser resultUser = response.body();
                            String message = resultUser.getMessage();
                            String error = resultUser.getError();

                            if(message.equals("success")){
                                registerView.success(message);
                            } else {
                                registerView.failedRegister(message, error);
                                Log.d(null, message);
                            }
                        }

                    }

                    @Override
                    public void onFailure(Call<ResultUser> call, Throwable t) {
                        registerView.noConnection();
                    }
                });
    }

    public void uploadFile(File file, String random){
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
                            addUser(path);
                        }
                    }

                    @Override
                    public void onFailure(Call<UploadObject> call, Throwable t) {
                        registerView.failedUploadFile();
                    }
                });
    }
}
