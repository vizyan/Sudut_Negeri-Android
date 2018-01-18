package com.qiscus.internship.sudutnegeri.data.network;

import com.google.gson.JsonObject;
import com.qiscus.internship.sudutnegeri.data.model.DataUser;
import com.qiscus.internship.sudutnegeri.data.model.ResultListProject;
import com.qiscus.internship.sudutnegeri.data.model.ResultProject;
import com.qiscus.internship.sudutnegeri.data.model.ResultUser;
import com.qiscus.internship.sudutnegeri.data.model.ResultListUser;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by vizyan on 02/01/18.
 */

public interface Api {

    @Headers("Content-Type: application/json")

    @GET("users")
    Call<ResultListUser> getAllUser();

    @GET("projects")
    Call<ResultListProject> getAllPorject();

    //Get unverified user
    @GET("users/verify/{verify}")
    Call<ResultListUser> getUnverifiedUser(
            @Path("verify") String verify
    );

    //Get unverified project
    @GET("projects/verify/{verify}")
    Call<ResultListProject> getUnverifiedProject(
            @Path("verify") String verify
    );

    @GET("users/{id}")
    Call<ResultUser> getUser(
            @Path("id") int id
    );

    @GET("projects/{id}")
    Call<ResultProject> getProject(
            @Path("id") int id
    );

    //Update user
    @FormUrlEncoded
    @PUT("users/{id}")
    Call<ResultUser> putUser(
            @Path("id") int id,
            @Field("name") String name,
            @Field("address") String address,
            @Field("phone") String phone,
            @Field("verify") String no
    );

    //Update project
    @FormUrlEncoded
    @PUT("projects/{id}")
    Call<ResultProject> putProject(
            @Path("id") int id,
            @Field("name_project") String name_project,
            @Field("verify") String verify,
            @Field("location") String location,
            @Field("target_at") String target,
            @Field("information") String information,
            @Field("photo") String urlPhoto,
            @Field("id_user") int id_user,
            @Field("funds") int funds
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
    @POST("projects")
    Call<ResultProject> postProject(

    );

    //generate token
    @FormUrlEncoded
    @POST("login/user")
    Call<ResultUser> loginUser(
            @Field("email") String email,
            @Field("password") String password
    );

    //not generate token
    @FormUrlEncoded
    @POST("login/user/cek")
    Call<ResultUser> cekLoginUser(
            @Field("email") String email,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("login/admin")
    Call<ResultUser> loginAdmin(
            @Field("email") String email,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("logout")
    Call<ResultUser> logout(
            @Field("email") String email,
            @Field("password") String password
    );

}
