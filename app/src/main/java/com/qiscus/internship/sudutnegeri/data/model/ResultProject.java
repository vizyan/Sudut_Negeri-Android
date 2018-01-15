package com.qiscus.internship.sudutnegeri.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Vizyan on 1/15/2018.
 */

public class ResultProject {

    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("data")
    @Expose
    private DataProject dataProject;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DataProject getData() {
        return dataProject;
    }

    public void setData(DataProject data) {
        this.dataProject = data;
    }

}
