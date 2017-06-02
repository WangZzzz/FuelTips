package com.wz.fuel.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.viewpagerindicator.TabPageIndicator;
import com.wz.activity.WBaseActivity;
import com.wz.fuel.AppConstants;
import com.wz.fuel.R;
import com.wz.fuel.adapter.MainFragmentAdapter;
import com.wz.fuel.fragment.FuelPriceFragment;
import com.wz.fuel.fragment.FuelRecordFragment;
import com.wz.util.ToastMsgUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;

public class MainActivity extends WBaseActivity implements View.OnClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.viewPager)
    ViewPager mViewPager;
    @BindView(R.id.tv_province)
    TextView mTvProvince;
    @BindView(R.id.tabIndicator)
    TabPageIndicator mTabIndicator;

    private static final String[] TAB_TITLES = {"今日油价", "加油统计"};
    private static final int[] TAB_ICON_RES_IDS = {R.drawable.ic_fuel_price, R.drawable.ic_add_fuel};

    private MainFragmentAdapter mAdapter;
    private List<Fragment> mFragments;

    private LocationClient mLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        mLocationClient = new LocationClient(this);
        mLocationClient.registerLocationListener(mLocationListener);
        initToolbar();
        initTabHost();
    }

    private void initToolbar() {
        mToolbarTitle.setText("加油助手");
        if (TextUtils.isEmpty(AppConstants.sProvince)) {
            mTvProvince.setText("定位中");
            mLocationClient.start();
        } else {
            mTvProvince.setText(AppConstants.sProvince);
        }
        mTvProvince.setOnClickListener(this);
    }

    private void initTabHost() {
        mFragments = new ArrayList<>();
        mFragments.add(new FuelPriceFragment());
        mFragments.add(new FuelRecordFragment());
        mAdapter = new MainFragmentAdapter(getSupportFragmentManager(), mFragments, TAB_TITLES, TAB_ICON_RES_IDS);
        mViewPager.setAdapter(mAdapter);
        mTabIndicator.setViewPager(mViewPager);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_province:
                chooseProvince();
                break;
        }
    }

    private void chooseProvince() {
        Intent intent = new Intent(MainActivity.this, ChooseProvinceActivity.class);
        startActivityForResult(intent, AppConstants.REQUEST_CHOOSE_PROVINCE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case AppConstants.REQUEST_CHOOSE_PROVINCE:
                if (resultCode == RESULT_CANCELED) {
                    ToastMsgUtil.info(MainActivity.this, "取消选择", 0);
                    if (TextUtils.isEmpty(AppConstants.sProvince)) {
                        mTvProvince.setText("未知省份");
                    }
                } else if (resultCode == RESULT_OK) {
                    mTvProvince.setText(AppConstants.sProvince);
                }
                break;
        }
    }

    private BDLocationListener mLocationListener = new BDLocationListener() {
        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            if (bdLocation != null && bdLocation.getProvince() != null) {
                AppConstants.sProvince = bdLocation.getProvince();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mTvProvince.setText(AppConstants.sProvince);
                        mLocationClient.stop();
                    }
                });
            } else {
                Toasty.info(MainActivity.this, "定位失败，请手动选择省份！", 1).show();
                chooseProvince();
            }
        }

        @Override
        public void onConnectHotSpotMessage(String s, int i) {

        }
    };
}
