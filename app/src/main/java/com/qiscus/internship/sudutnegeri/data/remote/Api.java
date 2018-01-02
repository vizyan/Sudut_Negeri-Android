package com.qiscus.internship.sudutnegeri.data.remote;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by vizyan on 02/01/18.
 */

public interface Api {

    @GET("cars")
    Call<JsonObject> getCarAll();

}
