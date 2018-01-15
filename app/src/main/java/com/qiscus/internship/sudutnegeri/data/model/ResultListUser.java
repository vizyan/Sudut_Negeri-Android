package com.qiscus.internship.sudutnegeri.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Vizyan on 1/14/2018.
 */

public class ResultListUser {
    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("data")
    @Expose
    private List<DataUser> data = null;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<DataUser> getData() {
        return data;
    }

    public void setData(List<DataUser> data) {
        this.data = data;
    }

}
