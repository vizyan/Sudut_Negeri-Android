package com.qiscus.internship.sudutnegeri.data.remote;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by vizyan on 02/01/18.
 */

public interface Api {

    @GET("cars")
    Call<JsonObject> getProjekAll();

    @FormUrlEncoded
    @POST("cars")
    Call<JsonObject> saveCar(
            @Field("year") String year,
            @Field("make") String make,
            @Field("model") String model
    );

}
