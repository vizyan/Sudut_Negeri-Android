package com.qiscus.internship.sudutnegeri.ui.addproject;

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

    void failedUploadFile();

    int getTargetFunds();
}
