package com.wz.fuel.fragment;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.wz.fuel.AppConstants;
import com.wz.fuel.R;
import com.wz.fuel.activity.MainActivity;
import com.wz.fuel.mvp.bean.FuelPriceBean;
import com.wz.fuel.mvp.presenter.FuelPricePresenter;
import com.wz.fuel.mvp.view.IView;
import com.wz.util.ToastMsgUtil;
import com.wz.util.WLog;

import java.util.List;

import butterknife.BindView;


/**
 * A simple {@link Fragment} subclass.
 */
public class FuelPriceFragment extends BaseFragment implements IView<FuelPriceBean> {
    private static final String TAG = FuelPriceFragment.class.getSimpleName();
    @BindView(R.id.tv_price_gas_89)
    TextView mTvPriceGas89;
    @BindView(R.id.tv_price_gas_92)
    TextView mTvPriceGas92;
    @BindView(R.id.tv_price_gas_95)
    TextView mTvPriceGas95;
    @BindView(R.id.tv_price_diesel_0)
    TextView mTvPriceDiesel0;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    private static final int MSG_REFRESH_DATA = 1;
    private static final int REFRESH_DELAY = 2000;

    private FuelPricePresenter mPresenter;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_REFRESH_DATA:
                    mSwipeRefreshLayout.setRefreshing(false);
                    refresh();
                    break;
            }
        }
    };


    public FuelPriceFragment() {
        // Required empty public constructor
    }


    @Override
    public View initView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fuel_price, container, false);
        return view;
    }

    public void initData() {
        mSwipeRefreshLayout.setColorSchemeResources(R.color.lightblue, R.color.orange, R.color.greenyellow);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mHandler.sendEmptyMessageDelayed(MSG_REFRESH_DATA, REFRESH_DELAY);
            }
        });
        mPresenter = new FuelPricePresenter(this);
        if (!TextUtils.isEmpty(AppConstants.sProvince)) {
            mPresenter.queryPrice(true);
        }
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
            mPresenter.queryPrice(false);
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
    public void onSuccess(List<FuelPriceBean> fuelBeenList) {
        if (!TextUtils.isEmpty(AppConstants.sProvince) && fuelBeenList != null && fuelBeenList.size() > 0) {
            for (FuelPriceBean fuelBean : fuelBeenList) {
                if (AppConstants.sProvince.equals(fuelBean.province)) {
                    AppConstants.sFuelPriceBean = fuelBean;
                    setPrice(fuelBean);
                }
            }
        } else {
            ToastMsgUtil.info(getActivity(), "请先选择省份信息！", 1);
        }
    }

    private void setPrice(FuelPriceBean fuelBean) {
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        return rootView;
    }
}
