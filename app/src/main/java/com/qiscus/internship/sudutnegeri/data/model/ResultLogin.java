package com.qiscus.internship.sudutnegeri.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResultLogin {

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("data")
    @Expose
    private DataUser data;

    public String getStatus() {
        return status;
    }

    public DataUser getData() {
        return data;
    }

}
