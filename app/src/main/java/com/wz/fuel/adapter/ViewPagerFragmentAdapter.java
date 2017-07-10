package com.wz.fuel.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.wz.fuel.fragment.BaseFragment;

import java.util.List;

public class ViewPagerFragmentAdapter extends FragmentPagerAdapter {

    private List<BaseFragment> mFragments;
    private List<String> mTitles;

    public ViewPagerFragmentAdapter(FragmentManager fm, List<BaseFragment> fragments, List<String> titles) {
        super(fm);
        this.mFragments = fragments;
        mTitles = titles;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments == null ? null : mFragments.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
//        super.destroyItem(container, position, object);
    }

    @Override
    public int getCount() {
        return mFragments == null ? 0 : mFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles.get(position);
    }
}
