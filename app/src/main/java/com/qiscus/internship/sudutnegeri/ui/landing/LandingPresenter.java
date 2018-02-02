package com.qiscus.internship.sudutnegeri.ui.landing;

import android.util.Log;

import com.google.gson.JsonObject;
import com.qiscus.internship.sudutnegeri.data.model.DataProject;
import com.qiscus.internship.sudutnegeri.data.model.ResultListProject;
import com.qiscus.internship.sudutnegeri.data.network.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by vizyan on 03/01/18.
 */

public class LandingPresenter {

    private LandingView landingView;

    public LandingPresenter(LandingView landingView){
        this.landingView = landingView;
    }

    public void getProjectByTime(){
        RetrofitClient.getInstance()
                .getApi()
                .getPorjectTime()
                .enqueue(new Callback<ResultListProject>() {
                    @Override
                    public void onResponse(Call<ResultListProject> call, Response<ResultListProject> response) {
                        if (response.isSuccessful()){
                            try{
                                ResultListProject resultListProject = response.body();
                                List<DataProject> dataProject = resultListProject.getData();
                                landingView.successShowProjectByTime(dataProject);
                            } catch (NullPointerException e){

                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ResultListProject> call, Throwable t) {

                    }
                });
    }

    public void getDonation(){
        RetrofitClient.getInstance()
                .getApi()
                .getDonation()
                .enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        if (response.isSuccessful()){
                            JsonObject jsonObject = response.body();
                            JsonObject data = jsonObject.get("data").getAsJsonObject();
                            String donation = data.get("total_donasi").toString();
                            landingView.successDonation(donation);

                        }
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {

                    }
                });
    }
}
