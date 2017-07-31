package com.wz.fuel.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.wz.fuel.AppConstants;
import com.wz.fuel.R;
import com.wz.fuel.fragment.FuelPriceFragment;
import com.wz.fuel.fragment.FuelRecordFragment;
import com.wz.fuel.fragment.MineFragment;
import com.wz.fuel.fragment.StatisticsFragment;
import com.wz.fuel.message.MessageEvent;
import com.wz.util.ToastMsgUtil;
import com.wz.util.WLog;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;

import static com.wz.fuel.AppConstants.TAB_TITLES;


public class MainActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.tv_province)
    TextView mTvProvince;
    @BindView(android.R.id.tabhost)
    FragmentTabHost mTabHost;

    private FragmentManager mFragmentManager;

    private ProgressDialog mDialog;

    private static final int[] TAB_ICON_RES_IDS = {R.drawable.ic_fuel_price, R.drawable.ic_add_fuel, R.drawable.ic_statistics, R.drawable.ic_mine};
    private Class[] mFragmentClasses = {FuelPriceFragment.class, FuelRecordFragment.class, StatisticsFragment.class, MineFragment.class};

    private LocationClient mLocationClient;


    private String mCurrentTab = TAB_TITLES[0];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        mFragmentManager = getSupportFragmentManager();
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
        mTabHost.setup(this, mFragmentManager, R.id.fl_fragment);
        // 得到fragment的个数
        int count = mFragmentClasses.length;
        for (int i = 0; i < count; i++) {
            // 给每个Tab按钮设置图标、文字和内容
            TabHost.TabSpec tabSpec = mTabHost.newTabSpec(AppConstants.TAB_TITLES[i])
                    .setIndicator(getIndicatorView(i));
            // 将Tab按钮添加进Tab选项卡中
            mTabHost.addTab(tabSpec, mFragmentClasses[i], null);
            // 设置Tab按钮的背景
//            mTabHost.getTabWidget().getChildAt(i)
//                    .setBackgroundResource(R.drawable.selector_tab_background);
        }
        mTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                mCurrentTab = tabId;
                mToolbarTitle.setText(tabId);
            }
        });
    }

    private View getIndicatorView(int index) {
        View rootView = LayoutInflater.from(this).inflate(R.layout.tab_indicator_layout, null);
        TextView tvTitle = (TextView) rootView.findViewById(R.id.tv_tab_title);
        ImageView ivIcon = (ImageView) rootView.findViewById(R.id.iv_tab_icon);
        tvTitle.setText(AppConstants.TAB_TITLES[index]);
        ivIcon.setImageResource(TAB_ICON_RES_IDS[index]);
        return rootView;
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
                    refreshFuelPriceFragment();
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private BDLocationListener mLocationListener = new BDLocationListener() {
        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            if (bdLocation != null && bdLocation.getProvince() != null) {
                AppConstants.sProvince = processProvinceStr(bdLocation.getProvince());
                //重新获取当前价格
                refreshFuelPriceFragment();
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

    @Override
    public void onBackPressed() {
        if (!TAB_TITLES[0].equals(mCurrentTab)) {
            mTabHost.setCurrentTab(0);
        } else {
            super.onBackPressed();
        }
    }

    /**
     * 得到位置后刷新价格
     */
    private void refreshFuelPriceFragment() {
        MessageEvent messageEvent = new MessageEvent();
        messageEvent.messageType = MessageEvent.TYPE_REFRESH_FUEL_PRICE_FRAGMENT;
        EventBus.getDefault().post(messageEvent);
    }
}
