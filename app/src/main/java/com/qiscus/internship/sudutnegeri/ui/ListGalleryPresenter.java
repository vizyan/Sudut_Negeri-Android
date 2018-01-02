package com.qiscus.internship.sudutnegeri.ui;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.qiscus.internship.sudutnegeri.data.model.Car;
import com.qiscus.internship.sudutnegeri.data.remote.RetrofitClient;

import java.lang.reflect.Type;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by vizyan on 02/01/18.
 */

public class ListGalleryPresenter {
    private ListGalleryView listGalleryView;

    public ListGalleryPresenter(ListGalleryView listGalleryView){
        this.listGalleryView = listGalleryView;
    }

    public void showListGallery(){
        RetrofitClient.getInstance()
                .getApi()
                .getCarAll()
                .enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        if(response.isSuccessful()){
                            JsonObject body = response.body();
                            JsonArray data = body.get("data").getAsJsonArray();
                            Type type = new TypeToken<List<Car>>(){}.getType();

                            List<Car> carList = new Gson().fromJson(data, type);

                            listGalleryView.showData(carList);

                            Log.d("ListGallery", "onRespone" + carList.toString());
                        } else {
                            listGalleryView.onFailure("NO DATA");
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        t.printStackTrace();
                        listGalleryView.onFailure(t.getMessage());
                    }
                });
    }
}
