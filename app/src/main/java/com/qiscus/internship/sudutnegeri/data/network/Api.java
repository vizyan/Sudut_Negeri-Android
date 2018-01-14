package com.qiscus.internship.sudutnegeri.data.network;

import com.google.gson.JsonObject;
import com.qiscus.internship.sudutnegeri.data.model.DataUser;
import com.qiscus.internship.sudutnegeri.data.model.ResultListProject;
import com.qiscus.internship.sudutnegeri.data.model.ResultUser;
import com.qiscus.internship.sudutnegeri.data.model.ResultListUser;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by vizyan on 02/01/18.
 */

public interface Api {

    @Headers("Content-Type: application/json")

    @GET("projects")
    Call<ResultListProject> getAllPorject();

    @GET("users")
    Call<ResultListUser> getAllUser();

    @GET("users/{id}")
    Call<ResultUser> getUser(
            @Path("id") int id
    );

    @FormUrlEncoded
    @POST("register")
    Call<ResultUser> register(
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
    @POST("login/user")
    Call<ResultUser> loginUser(
            @Field("email") String email,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("login/admin")
    Call<JsonObject> loginAdmin(
            @Field("email") String email,
            @Field("password") String password
    );

}
