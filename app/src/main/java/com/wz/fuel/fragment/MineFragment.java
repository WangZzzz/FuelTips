package com.wz.fuel.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.wz.fuel.R;

import butterknife.BindView;

public class MineFragment extends BaseFragment {
    @BindView(R.id.lv_settings)
    ListView mLvSettings;

    private static final String[] TITLES = {"设置"};

    @Override
    public View initView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mine, container, false);
        return view;
    }

    @Override
    public void initData() {
    }
}
