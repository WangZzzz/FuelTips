package com.wz.fuel.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wz.fuel.R;
import com.wz.fuel.db.GreenDaoManager;
import com.wz.fuel.mvp.bean.FuelRecordBean;
import com.wz.fuel.mvp.bean.FuelRecordBeanDao;

import java.util.List;

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

    private List<FuelRecordBean> mRecords;

    public StatisticsFragment() {
        // Required empty public constructor
    }


    @Override
    public View initView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_statistics, container, false);
        return view;
    }

    @Override
    public void initData() {

    }

    private void queryDb() {
        FuelRecordBeanDao recordDao = GreenDaoManager.getInstance().getDaoSession().getFuelRecordBeanDao();
    }


}
