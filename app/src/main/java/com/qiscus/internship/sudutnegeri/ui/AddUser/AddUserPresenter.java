package com.qiscus.internship.sudutnegeri.ui.AddUser;

import android.app.ProgressDialog;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.qiscus.internship.sudutnegeri.data.model.Car;
import com.qiscus.internship.sudutnegeri.data.remote.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Vizyan on 1/10/2018.
 */

public class AddUserPresenter {

    private AddUserView addUserView;

    public AddUserPresenter(AddUserView addUserView){
        this.addUserView = addUserView;
    }

    public void saveCar(){
        String make = addUserView.getMake();
        String model = addUserView.getModel();
        String year = addUserView.getYear();

        RetrofitClient.getInstance()
                .getApi()
                .saveCar(year, make, model)
                .enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        if(response.isSuccessful()){
                            JsonObject object = response.body();
                            JsonObject data = object.get("data").getAsJsonObject();
                            Car car = new Gson().fromJson(data, Car.class);

                            addUserView.showSaveCar(car);
                        } else {
                            addUserView.failedParse();
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        addUserView.cantConnect();
                    }
                });
    }
}
