package com.qiscus.internship.sudutnegeri.data.remote;

import com.google.gson.JsonObject;
import com.qiscus.internship.sudutnegeri.data.model.DataUser;
import com.qiscus.internship.sudutnegeri.data.model.ResultUser;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by vizyan on 02/01/18.
 */

public interface Api {

    @Headers("Content-Type: application/json")

    @GET("projects")
    Call<JsonObject> getAllPorject();

    @FormUrlEncoded
    @POST("register")
    Call<ResultUser> newUser(
            @Field("name") String name,
            @Field("email") String email,
            @Field("password") String password,
            @Field("password_confirmation") String retypePassword,
            @Field("identity_number") String noIdentity,
            @Field("address") String address,
            @Field("phone") String phone,
            @Field("verify") String verify
    );

    @FormUrlEncoded
    @POST("login")
    Call<ResultUser> getUser(
            @Field("email") String email,
            @Field("password") String password
    );

}
