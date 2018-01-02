package com.qiscus.internship.sudutnegeri.ui.Add;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.qiscus.internship.sudutnegeri.data.model.Car;
import com.qiscus.internship.sudutnegeri.data.remote.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Vizyan on 1/3/2018.
 */

public class AddPresenter {

    private final AddView mView;

    public AddPresenter(AddView mView) {
        this.mView = mView;
    }

    public void saveCar(){
        String year = mView.getYear();
        String make = mView.getMake();
        String model = mView.getModel();

        RetrofitClient.getInstance()
                .getApi()
                .saveCar(year, make, model)
                .enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        if (response.isSuccessful()){
                            JsonObject body = response.body();
                            JsonObject data = body.get("data").getAsJsonObject();
                            Car carResponse = new Gson().fromJson(data, Car.class);

                            mView.showSuccesSaveCar(carResponse);
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {

                    }
                });
    }
}
