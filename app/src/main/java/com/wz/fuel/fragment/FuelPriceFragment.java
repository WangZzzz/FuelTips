package com.wz.fuel.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wz.fragment.WBaseFragment;
import com.wz.fuel.AppConstants;
import com.wz.fuel.R;
import com.wz.fuel.mvp.bean.FuelBean;
import com.wz.fuel.mvp.presenter.FuelPricePresenter;
import com.wz.fuel.mvp.view.IView;
import com.wz.util.ToastMsgUtil;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class FuelPriceFragment extends WBaseFragment implements IView<FuelBean> {

    public FuelPriceFragment() {
        // Required empty public constructor
    }


    @Override
    public View initView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fuel_price, container, false);
        initData();
        return view;
    }

    public void refresh() {

    }

    private void initData() {
        FuelPricePresenter fuelPricePresenter = new FuelPricePresenter(this);
        fuelPricePresenter.queryPrice();
    }

    @Override
    public void onError(String errorMsg) {

    }

    @Override
    public void onSuccess(List<FuelBean> fuelBeenList) {
        if (fuelBeenList != null && fuelBeenList.size() > 0) {
            for (FuelBean fuelBean : fuelBeenList) {
                if (AppConstants.sProvince.equals(fuelBean.province)) {
                    ToastMsgUtil.info(getActivity(), fuelBean.toString(), 1);
                }
            }
        }
    }

    @Override
    public void showProgressDialog() {

    }

    @Override
    public void hideProgressDialog() {

    }
}
