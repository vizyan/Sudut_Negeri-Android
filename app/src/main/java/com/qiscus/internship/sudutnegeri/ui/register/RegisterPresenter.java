package com.qiscus.internship.sudutnegeri.ui.register;

import android.util.Log;

import com.google.gson.JsonObject;
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
 * Created by Vizyan on 1/10/2018.
 */

public class RegisterPresenter {

    private RegisterView registerView;

    public RegisterPresenter(RegisterView registerView){
        this.registerView = registerView;
    }

    public void addUser(String path){
        String tag = "Register-addUser";
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
                .enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        if (response.isSuccessful()){
                            JsonObject body = response.body();
                            String message = body.get("message").getAsString();
                            if (message.equalsIgnoreCase("success")){
                                registerView.successAddUser();
                            } else {
                                String data = body.get("data").getAsString();
                                registerView.failed(data);
                            }
                            Log.d(tag, response.body().toString());
                        } else {
                            registerView.failed("Maaf terjadi kesalahan");
                            Log.d(tag, response.errorBody().toString());
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        registerView.failed("Tidak ada koneksi");
                        Log.d(tag, t.getMessage());
                    }
                });
    }

    public void uploadFile(File file, String random){
        String tag = "Register-uploadFile";
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
                            Log.d(tag, response.body().toString());
                        } else {
                            registerView.failed("Tidak dapat mengunggah gambar");
                            Log.d(tag, response.errorBody().toString());
                        }
                    }

                    @Override
                    public void onFailure(Call<UploadObject> call, Throwable t) {
                        registerView.failed("Tidak ada koneksi");
                        Log.d(tag, t.getMessage());
                    }
                });
    }
}
