package com.qiscus.internship.sudutnegeri.ui.dashboard;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Deadroit on 1/16/2017.
 */

public class ViewPagerDashboard extends FragmentPagerAdapter {
    final int PAGE_COUNT = 2;
    final Context context;

    public ViewPagerDashboard(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position){
            case 0:
                fragment = FragmentSudut.newInstance();
                break;
            case 1:
                fragment = FragmentNegeri.newInstance();
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }
}