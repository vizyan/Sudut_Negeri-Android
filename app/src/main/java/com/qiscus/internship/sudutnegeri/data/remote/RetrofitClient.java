package com.qiscus.internship.sudutnegeri.data.remote;

import com.qiscus.internship.sudutnegeri.util.Constant;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by vizyan on 02/01/18.
 */

public class RetrofitClient {
    private static RetrofitClient retrofitClient;

    private RetrofitClient(){

    }

    public static RetrofitClient getInstance(){
        if(retrofitClient == null){
            retrofitClient = new RetrofitClient();
        }

        return retrofitClient;
    }

    public Api getApi(){
        return getRetrofit().create(Api.class);
    }

    public Retrofit getRetrofit(){
        return new Retrofit.Builder()
                .baseUrl(Constant.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}