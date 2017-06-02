package com.wz.fuel.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.viewpagerindicator.IconPagerAdapter;

import java.util.List;

public class MainFragmentAdapter extends FragmentPagerAdapter implements IconPagerAdapter {

    private List<Fragment> mFragments;
    private String[] mTitles;
    private int[] mResIds;

    public MainFragmentAdapter(FragmentManager fm, List<Fragment> fragments, String[] titles, int[] resIds) {
        super(fm);
        this.mFragments = fragments;
        mTitles = titles;
        mResIds = resIds;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments == null ? null : mFragments.get(position);
    }

    @Override
    public int getIconResId(int index) {
        return mResIds[index];
    }

    @Override
    public int getCount() {
        return mFragments == null ? 0 : mFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }
}
