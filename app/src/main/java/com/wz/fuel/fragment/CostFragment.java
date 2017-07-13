package com.wz.fuel.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wz.fuel.R;

/**
 * 花费统计Fragment
 */
public class CostFragment extends BaseFragment {
    @Override
    public View initView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cost, container, false);
        return view;
    }

    @Override
    public void findViewById(View view) {

    }

    @Override
    public void initData() {

    }

    @Override
    protected void refresh(Bundle data) {

    }
}
