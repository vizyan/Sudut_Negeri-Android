package com.qiscus.internship.sudutnegeri.ui.admin;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Deadroit on 1/16/2017.
 */

public class ViewPagerAdmin extends FragmentPagerAdapter {
    final int PAGE_COUNT = 2;
    final Context context;

    public ViewPagerAdmin(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position){
            case 0:
                fragment = FragmentVerifyUser.newInstance();
                break;
            case 1:
                fragment = FragmentVerifyProject.newInstance();
                break;
            default:
                fragment = FragmentVerifyUser.newInstance();
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }
}