package com.qiscus.internship.sudutnegeri.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResultUser {

    @SerializedName("data")
    @Expose
    private DataUser data;

    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("error")
    @Expose
    private String error;

    public String getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public DataUser getData() {
        return data;
    }

}
