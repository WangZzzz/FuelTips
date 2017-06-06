package com.wz.fuel.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.wz.fragment.WBaseFragment;
import com.wz.fuel.AppConstants;
import com.wz.fuel.R;
import com.wz.fuel.activity.MainActivity;
import com.wz.fuel.mvp.bean.FuelBean;
import com.wz.fuel.mvp.presenter.FuelPricePresenter;
import com.wz.fuel.mvp.view.IView;
import com.wz.util.ToastMsgUtil;
import com.wz.util.WLog;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * A simple {@link Fragment} subclass.
 */
public class FuelPriceFragment extends WBaseFragment implements IView<FuelBean> {
    private static final String TAG = FuelPriceFragment.class.getSimpleName();
    @BindView(R.id.tv_price_gas_89)
    TextView mTvPriceGas89;
    @BindView(R.id.tv_price_gas_92)
    TextView mTvPriceGas92;
    @BindView(R.id.tv_price_gas_95)
    TextView mTvPriceGas95;
    @BindView(R.id.tv_price_diesel_0)
    TextView mTvPriceDiesel0;
    Unbinder unbinder;

    private FuelPricePresenter mPresenter;

    public FuelPriceFragment() {
        // Required empty public constructor
    }


    @Override
    public View initView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fuel_price, container, false);
        unbinder = ButterKnife.bind(this, view);
        initData();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        WLog.d(TAG, "onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        WLog.d(TAG, "onResume");
    }

    public void refresh() {
        if (!TextUtils.isEmpty(AppConstants.sProvince)) {
            mPresenter.queryPrice();
        }
    }

    private void initData() {
        mPresenter = new FuelPricePresenter(this);
        if (!TextUtils.isEmpty(AppConstants.sProvince)) {
            mPresenter.queryPrice();
        }
    }

    @Override
    public void onError(String errorMsg) {
        ToastMsgUtil.error(getActivity(), errorMsg, 1);
        ListView listView = new ListView(getContext());
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                return false;
            }
        });
    }

    @Override
    public void onSuccess(List<FuelBean> fuelBeenList) {
        if (!TextUtils.isEmpty(AppConstants.sProvince) && fuelBeenList != null && fuelBeenList.size() > 0) {
            for (FuelBean fuelBean : fuelBeenList) {
                if (AppConstants.sProvince.equals(fuelBean.province)) {
                    setPrice(fuelBean);
                }
            }
        } else {
            ToastMsgUtil.info(getActivity(), "请先选择省份信息！", 1);
        }
    }

    private void setPrice(FuelBean fuelBean) {
        if (fuelBean != null) {
            mTvPriceDiesel0.setText(fuelBean.price_diesel_0 + "");
            mTvPriceGas89.setText(fuelBean.price_gas_89 + "");
            mTvPriceGas92.setText(fuelBean.price_gas_92 + "");
            mTvPriceGas95.setText(fuelBean.price_gas_95 + "");
        }
    }

    @Override
    public void showProgressDialog() {
        ((MainActivity) getActivity()).showProgressDialog(null);
    }

    @Override
    public void hideProgressDialog() {
        ((MainActivity) getActivity()).hideProgressDialog();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
