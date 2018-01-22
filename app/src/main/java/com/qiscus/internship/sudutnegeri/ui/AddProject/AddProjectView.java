package com.qiscus.internship.sudutnegeri.ui.AddProject;

/**
 * Created by Vizyan on 1/22/2018.
 */

interface AddProjectView {
    String getProjectName();

    String getLocation();

    String getTarget();

    String getInformation();

    String getPhoto();

    void successPostProject();

    void failedPostProject(String message);
}
