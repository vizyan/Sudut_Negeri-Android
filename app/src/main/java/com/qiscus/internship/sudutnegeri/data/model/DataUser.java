package com.qiscus.internship.sudutnegeri.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Vizyan on 1/12/2018.
 */

public class DataUser implements Parcelable {

    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("password")
    @Expose
    String passwd;

    @SerializedName("password_confirmation")
    @Expose
    String passwd_confirmation;

    @SerializedName("created_at")
    @Expose
    private String createdAt;

    @SerializedName("updated_at")
    @Expose
    private String updatedAt;

    @SerializedName("api_token")
    @Expose
    private String apiToken;

    @SerializedName("identity_number")
    @Expose
    private String identityNumber;

    @SerializedName("address")
    @Expose
    private String address;

    @SerializedName("phone")
    @Expose
    private String phone;

    @SerializedName("verify")
    @Expose
    private String verify;

    @SerializedName("photo")
    @Expose
    private String photo;

    public DataUser(){

    }

    protected DataUser(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readInt();
        }
        name = in.readString();
        email = in.readString();
        passwd = in.readString();
        passwd_confirmation = in.readString();
        createdAt = in.readString();
        updatedAt = in.readString();
        apiToken = in.readString();
        identityNumber = in.readString();
        address = in.readString();
        phone = in.readString();
        verify = in.readString();
        photo = in.readString();
    }

    public static final Creator<DataUser> CREATOR = new Creator<DataUser>() {
        @Override
        public DataUser createFromParcel(Parcel in) {
            return new DataUser(in);
        }

        @Override
        public DataUser[] newArray(int size) {
            return new DataUser[size];
        }
    };

    public String getVerify() {
        return verify;
    }

    public void setVerify(String verify) {
        this.verify = verify;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswd_confirmation() {
        return passwd_confirmation;
    }

    public void setPasswd_confirmation(String passwd_confirmation) {
        this.passwd_confirmation = passwd_confirmation;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getApiToken() {
        return apiToken;
    }

    public void setApiToken(String apiToken) {
        this.apiToken = apiToken;
    }

    public String getIdentityNumber() {
        return identityNumber;
    }

    public void setIdentityNumber(String identityNumber) {
        this.identityNumber = identityNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    @Override
    public String toString() {
        return "DataUser{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", password='" + passwd + '\'' +
                ", identity_number='" + identityNumber + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                ", verify='" + verify + '\'' +
                ", photo='" + photo + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(id);
        }
        dest.writeString(name);
        dest.writeString(email);
        dest.writeString(passwd);
        dest.writeString(passwd_confirmation);
        dest.writeString(createdAt);
        dest.writeString(updatedAt);
        dest.writeString(apiToken);
        dest.writeString(identityNumber);
        dest.writeString(address);
        dest.writeString(phone);
        dest.writeString(verify);
        dest.writeString(photo);
    }
}
