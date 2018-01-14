package com.qiscus.internship.sudutnegeri.ui.dashboard;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qiscus.internship.sudutnegeri.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class NegeriFragment extends Fragment {


    public NegeriFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_negeri, container, false);
    }

    public static Fragment newInstance() {
        return new NegeriFragment();
    }
}
