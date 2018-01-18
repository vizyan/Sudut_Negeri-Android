package com.qiscus.internship.sudutnegeri.ui.project;

import com.qiscus.internship.sudutnegeri.data.model.DataProject;
import com.qiscus.internship.sudutnegeri.data.model.DataUser;

/**
 * Created by aufalmarom23 on 15/01/18.
 */

public interface ProjectView {
    
    void successProjectById(DataProject dataProject);

    void successUserById(DataUser dataUser);

    void successPutProject(DataProject dataProject);

    int getFunds();
}
