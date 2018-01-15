package com.qiscus.internship.sudutnegeri.ui.admin;

import com.qiscus.internship.sudutnegeri.data.model.DataProject;
import com.qiscus.internship.sudutnegeri.data.model.DataUser;

import java.util.List;

/**
 * Created by Vizyan on 1/14/2018.
 */

public interface AdminView {

    void successShowUser(List<DataUser> dataUserList);

    void successShowProject(List<DataProject> projects);
}
