package com.qiscus.internship.sudutnegeri.ui.Landing;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.qiscus.internship.sudutnegeri.data.model.Car;
import com.qiscus.internship.sudutnegeri.data.model.Project;
import com.qiscus.internship.sudutnegeri.data.remote.RetrofitClient;

import java.lang.reflect.Type;
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

    public void showList(){
        RetrofitClient.getInstance()
                .getApi()
                .getAllPorject()
                .enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                        if(response.isSuccessful()){
                            JsonObject body = response.body();
                            JsonArray data = body.get("data").getAsJsonArray();
                            Type type = new TypeToken<List<Project>>(){}.getType();

                            List<Project> projectList = new Gson().fromJson(data, type);

                            landingView.showData(projectList);

                            Log.d("ListGallery", "onRespone" + body.toString());
                        }

                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        Log.e(null, "Gagal gan");
                    }
                });
    }
}
