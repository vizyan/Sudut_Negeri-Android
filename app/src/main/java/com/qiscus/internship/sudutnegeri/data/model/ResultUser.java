package com.qiscus.internship.sudutnegeri.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResultUser {

    @SerializedName("data")
    @Expose
    private DataUser data;

    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("errors")
    @Expose
    private DataUser error;

    public DataUser getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public DataUser getData() {
        return data;
    }

}
