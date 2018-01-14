package com.qiscus.internship.sudutnegeri.ui.admin;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qiscus.internship.sudutnegeri.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentVerifyUser extends Fragment {

    private AdminPresenter presenter;

    public static FragmentVerifyUser newInstance() {
        return new FragmentVerifyUser();
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fragment_verify_user, container, false);
    }

    private void initPresenter() {

    }
}
