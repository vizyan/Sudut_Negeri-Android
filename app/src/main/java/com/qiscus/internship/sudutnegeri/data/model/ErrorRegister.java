package com.qiscus.internship.sudutnegeri.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Vizyan on 1/13/2018.
 */

public class ErrorRegister {

    @SerializedName("email")
    @Expose
    private List<String> email = null;

    @SerializedName("identity_number")
    @Expose
    private List<String> identityNumber = null;

    public List<String> getEmail() {
        return email;
    }

    public void setEmail(List<String> email) {
        this.email = email;
    }

    public List<String> getIdentityNumber() {
        return identityNumber;
    }

    public void setIdentityNumber(List<String> identityNumber) {
        this.identityNumber = identityNumber;
    }
}
