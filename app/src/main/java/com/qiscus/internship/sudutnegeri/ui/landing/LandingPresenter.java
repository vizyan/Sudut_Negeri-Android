package com.qiscus.internship.sudutnegeri.ui.landing;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.qiscus.internship.sudutnegeri.data.model.DataProject;
import com.qiscus.internship.sudutnegeri.data.network.RetrofitClient;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

/**
 * Created by vizyan on 03/01/18.
 */

public class LandingPresenter {

    private LandingView landingView;

    public LandingPresenter(LandingView landingView){
        this.landingView = landingView;
    }

    public void getProjectByTime(){
        String tag = "Landing-getProject";
        RetrofitClient.getInstance()
                .getApi()
                .getPorjectTime()
                .enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        if (response.isSuccessful()){
                            JsonObject body = response.body();
                            JsonArray array = body.get("data").getAsJsonArray();
                            Type type = new TypeToken<List<DataProject>>(){}.getType();

                            List<DataProject> dataProjects = new Gson().fromJson(array, type);
                            landingView.successShowProjectByTime(dataProjects);
                            Log.d(tag, response.body().toString());
                        } else {
                            landingView.failed("Maaf terjadi kesalahan");
                            Log.d(tag, response.errorBody().toString());
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        landingView.failed("Tidak ada koneksi");
                        Log.d(tag, t.getMessage());
                    }
                });
    }

    public void getDonation(){
        String tag = "Landing-getDonation";
        RetrofitClient.getInstance()
                .getApi()
                .getDonation()
                .enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        if (response.isSuccessful()){
                            JsonObject jsonObject = response.body();
                            JsonObject data = jsonObject.get("data").getAsJsonObject();
                            String donation = data.get("all_donation").getAsString();
                            landingView.successDonation(donation);
                            Log.d(tag, response.body().toString());
                        } else {
                            landingView.failed("Maaf terjadi kesalahan");
                            Log.d(tag, response.errorBody().toString());
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        landingView.failed("Tidak ada koneksi");
                        Log.d(tag, t.getMessage());
                    }
                });
    }
}
