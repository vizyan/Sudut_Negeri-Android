package com.qiscus.internship.sudutnegeri.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Vizyan on 1/13/2018.
 */

public class Project {

    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("name_project")
    @Expose
    private String nameProject;

    @SerializedName("create_at")
    @Expose
    private String createAt;

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

    public String getCreateAt() {
        return createAt;
    }

    public void setCreateAt(String createAt) {
        this.createAt = createAt;
    }
}
