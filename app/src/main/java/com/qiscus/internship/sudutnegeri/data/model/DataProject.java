package com.qiscus.internship.sudutnegeri.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Vizyan on 1/13/2018.
 */

public class DataProject implements Parcelable {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name_project")
    @Expose
    private String nameProject;
    @SerializedName("verify")
    @Expose
    private String verify;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("location")
    @Expose
    private String location;
    @SerializedName("target_at")
    @Expose
    private String targetAt;
    @SerializedName("information")
    @Expose
    private String information;
    @SerializedName("photo")
    @Expose
    private String photo;
    @SerializedName("id_user")
    @Expose
    private Integer idUser;
    @SerializedName("funds")
    @Expose
    private Integer funds;
    @SerializedName("difftime")
    @Expose
    private Integer difftime;

    protected DataProject(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readInt();
        }
        nameProject = in.readString();
        verify = in.readString();
        createdAt = in.readString();
        updatedAt = in.readString();
        location = in.readString();
        targetAt = in.readString();
        information = in.readString();
        photo = in.readString();
        idUser = in.readInt();
        funds = in.readInt();
    }

    public static final Creator<DataProject> CREATOR = new Creator<DataProject>() {
        @Override
        public DataProject createFromParcel(Parcel in) {
            return new DataProject(in);
        }

        @Override
        public DataProject[] newArray(int size) {
            return new DataProject[size];
        }
    };

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNameProject() {
        return nameProject;
    }

    public void setNameProject(String nameProject) {
        this.nameProject = nameProject;
    }

    public String getVerify() {
        return verify;
    }

    public void setVerify(String verify) {
        this.verify = verify;
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTargetAt() {
        return targetAt;
    }

    public void setTargetAt(String targetAt) {
        this.targetAt = targetAt;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public Integer getFunds() {
        return funds;
    }

    public void setFunds(Integer funds) {
        this.funds = funds;
    }

    public Integer getDifftime() {
        return difftime;
    }

    public void setDifftime(Integer difftime) {
        this.difftime = difftime;
    }

    @Override
    public String toString() {
        return "DataProject{" +
                "id=" + id +
                ", name_project='" + nameProject + '\'' +
                ", created_at='" + createdAt + '\'' +
                ", updated_at='" + updatedAt + '\'' +
                ", location='" + location + '\'' +
                ", information='" + information + '\'' +
                ", photo='" + photo + '\'' +
                ", id_user='" + idUser + '\'' +
                ", funds='" + funds + '\'' +
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
        dest.writeString(nameProject);
        dest.writeString(verify);
        dest.writeString(createdAt);
        dest.writeString(updatedAt);
        dest.writeString(location);
        dest.writeString(targetAt);
        dest.writeString(information);
        dest.writeString(photo);
        dest.writeInt(idUser);
        dest.writeInt(funds);
    }
}
