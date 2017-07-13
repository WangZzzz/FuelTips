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

/**
 * A simple {@link Fragment} subclass.
 */
public class FuelConsumptionFragment extends BaseFragment {


    private ImageView mIvLeft;
    private PointIndicatorView mPointIndicator;
    private ImageView mIvRight;

    public FuelConsumptionFragment() {
        // Required empty public constructor
    }


    @Override
    public View initView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fuel_consumption, container, false);
        return view;
    }

    @Override
    public void findViewById(View view) {
        mIvLeft = (ImageView) view.findViewById(R.id.iv_left);
        mIvRight = (ImageView) view.findViewById(R.id.iv_right);
        mPointIndicator = (PointIndicatorView) view.findViewById(R.id.pointIndicator);
    }

    @Override
    public void initData() {

    }

    @Override
    protected void refresh(Bundle data) {

    }

}
