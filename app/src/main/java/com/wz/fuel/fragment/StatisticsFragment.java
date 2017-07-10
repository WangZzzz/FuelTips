package com.wz.fuel.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wz.fuel.R;
import com.wz.fuel.adapter.ViewPagerFragmentAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 费用统计
 * 费用总计：元
 * 费用平均： ？元/年
 * 费用平均：？元/月
 * 费用平均：？元/天
 * 当月费用：？元/公里
 * 当月加油：？升
 */
public class StatisticsFragment extends BaseFragment {


    @BindView(R.id.tabLayout)
    TabLayout mTabLayout;
    @BindView(R.id.viewPager)
    ViewPager mViewPager;

    private List<BaseFragment> mFragments;
    private FuelConsumptionFragment mConsumptionFragment;
    private CostFragment mCostFragment;

    private ViewPagerFragmentAdapter mAdapter;
    private List<String> mTitles;

    @Override
    public View initView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_statistics, container, false);
        return view;
    }

    @Override
    public void initData() {
        mTitles = new ArrayList<String>();
        mFragments = new ArrayList<>();
        mConsumptionFragment = new FuelConsumptionFragment();
        mCostFragment = new CostFragment();
        mFragments.add(mConsumptionFragment);
        mFragments.add(mCostFragment);

        mTitles.add("油耗统计");
        mTitles.add("花费统计");

        mAdapter = new ViewPagerFragmentAdapter(getChildFragmentManager(), mFragments, mTitles);
        mViewPager.setAdapter(mAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    protected void refresh(Bundle data) {

    }
}
