package com.wz.fuel.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.wz.fuel.R;
import com.wz.view.PointIndicatorView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class FuelConsumptionFragment extends BaseFragment {


    @BindView(R.id.pointIndicator)
    PointIndicatorView pointIndicator;
    @BindView(R.id.iv_left)
    ImageView mIvLeft;
    @BindView(R.id.iv_right)
    ImageView mIvRight;

    public FuelConsumptionFragment() {
        // Required empty public constructor
    }


    @Override
    public View initView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fuel_consumption, container, false);
        return view;
    }

    @Override
    public void initData() {

    }

    @Override
    protected void refresh(Bundle data) {

    }

    @OnClick({R.id.iv_left, R.id.iv_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_left:
                pointIndicator.moveLeft();
                break;
            case R.id.iv_right:
                pointIndicator.moveRight();
                break;
        }
    }
}
