package com.wz.fuel.activity;

import android.app.ProgressDialog;
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
import com.baidu.location.LocationClientOption;
import com.viewpagerindicator.TabPageIndicator;
import com.wz.activity.WBaseActivity;
import com.wz.fuel.AppConstants;
import com.wz.fuel.R;
import com.wz.fuel.adapter.MainFragmentAdapter;
import com.wz.fuel.fragment.FuelPriceFragment;
import com.wz.fuel.fragment.FuelRecordFragment;
import com.wz.util.ToastMsgUtil;
import com.wz.util.WLog;

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

    private ProgressDialog mDialog;

    private static final String[] TAB_TITLES = {"今日油价", "加油统计"};
    private static final int[] TAB_ICON_RES_IDS = {R.drawable.ic_fuel_price, R.drawable.ic_add_fuel};

    private MainFragmentAdapter mAdapter;
    private List<Fragment> mFragments;

    private FuelRecordFragment mFuelRecordFragment;
    private FuelPriceFragment mFuelPriceFragment;
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
        LocationClientOption option = new LocationClientOption();
        //需要设置此项，否则无法获取具体的地址
        option.setIsNeedAddress(true);
        //可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        option.setIgnoreKillProcess(false);
        mLocationClient.setLocOption(option);
        initToolbar();
        initTabHost();
    }

    private void initToolbar() {
        mToolbarTitle.setText("今日油价");
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
        mFuelPriceFragment = new FuelPriceFragment();
        mFuelRecordFragment = new FuelRecordFragment();
        mFragments.add(mFuelPriceFragment);
        mFragments.add(mFuelRecordFragment);
        mAdapter = new MainFragmentAdapter(getSupportFragmentManager(), mFragments, TAB_TITLES, TAB_ICON_RES_IDS);
        mViewPager.setAdapter(mAdapter);
        mTabIndicator.setViewPager(mViewPager);
        mTabIndicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mToolbarTitle.setText(TAB_TITLES[position]);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
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
                    if (mLocationClient != null) {
                        mLocationClient.unRegisterLocationListener(mLocationListener);
                        mLocationClient.stop();
                    }
                    mTvProvince.setText(AppConstants.sProvince);
                    mFuelPriceFragment.refresh();
                }
                break;
        }
    }

    private BDLocationListener mLocationListener = new BDLocationListener() {
        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            if (bdLocation != null && bdLocation.getProvince() != null) {
                AppConstants.sProvince = processProvinceStr(bdLocation.getProvince());
                //重新获取当前价格
                mFuelPriceFragment.refresh();
                WLog.d(TAG, "addr : " + bdLocation.getAddrStr());
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
            mLocationClient.stop();
        }

        @Override
        public void onConnectHotSpotMessage(String s, int i) {

        }
    };

    private String processProvinceStr(String provnice) {
        if (!TextUtils.isEmpty(provnice)) {
            if (provnice.contains("黑龙江") || provnice.contains("内蒙古")) {
                return provnice.substring(0, 3);
            } else {
                return provnice.substring(0, 2);
            }
        }
        return provnice;
    }

    public void showProgressDialog(String msg) {
        if (mDialog == null) {
            mDialog = new ProgressDialog(this);
            mDialog.setTitle("提示");
            mDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            mDialog.setCancelable(false);
            mDialog.setCanceledOnTouchOutside(false);
        }
        if (!TextUtils.isEmpty(msg)) {
            mDialog.setMessage(msg);
        } else {
            mDialog.setMessage("加载中，请稍候...");
        }
        if (!mDialog.isShowing()) {
            mDialog.show();
        }
    }

    public void hideProgressDialog() {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
        }
    }

    @Override
    protected void onDestroy() {
        if (mLocationClient != null) {
            mLocationClient.unRegisterLocationListener(mLocationListener);
            mLocationClient.stop();
        }
        if (mDialog != null) {
            mDialog.dismiss();
            mDialog = null;
        }
        super.onDestroy();
    }
}
