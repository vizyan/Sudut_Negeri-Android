package com.qiscus.internship.sudutnegeri.ui.landing;

import android.util.Log;

import com.qiscus.internship.sudutnegeri.data.model.DataProject;
import com.qiscus.internship.sudutnegeri.data.model.ResultListProject;
import com.qiscus.internship.sudutnegeri.data.network.RetrofitClient;

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

    public void showUser(){
        RetrofitClient.getInstance()
                .getApi()
                .getAllPorject()
                .enqueue(new Callback<ResultListProject>() {
                    @Override
                    public void onResponse(Call<ResultListProject> call, Response<ResultListProject> response) {
                        if(response.isSuccessful()){
                            ResultListProject project = response.body();
                            List<DataProject> data = project.getData();
                            landingView.showData(data);
                            Log.d(null, "Body" + response.body().getData());
                        }

                    }

                    @Override
                    public void onFailure(Call<ResultListProject> call, Throwable t) {
                        Log.e(null, "Gagal gan");
                    }
                });
    }
}
