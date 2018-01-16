package com.qiscus.internship.sudutnegeri.ui.about;

import com.qiscus.internship.sudutnegeri.data.model.ResultUser;
import com.qiscus.internship.sudutnegeri.data.network.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Vizyan on 1/16/2018.
 */

public class AboutPresenter {
    private AboutView aboutView;

    public AboutPresenter(AboutView aboutView){
        this.aboutView = aboutView;
    }

    public void logout(){
        String email = aboutView.getEmail();
        String passwd = aboutView.getPasswd();

        RetrofitClient.getInstance()
                .getApi()
                .logout(email, passwd)
                .enqueue(new Callback<ResultUser>() {
                    @Override
                    public void onResponse(Call<ResultUser> call, Response<ResultUser> response) {
                        if(response.isSuccessful()){
                            aboutView.successLogout();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResultUser> call, Throwable t) {
                        aboutView.failedLogout();
                    }
                });
    }
}
