package com.wz.fuel.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.wz.fuel.R;
import com.wz.fuel.db.GreenDaoManager;
import com.wz.fuel.mvp.bean.FuelRecordBean;
import com.wz.fuel.mvp.bean.FuelRecordBeanDao;
import com.wz.util.WLog;
import com.wz.view.PointIndicatorView;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * A simple {@link Fragment} subclass.
 */
public class FuelConsumptionFragment extends BaseFragment implements View.OnClickListener {

    private static final String TAG = FuelConsumptionFragment.class.getSimpleName();

    //最近三个月数据
    private static final int TYPE_LAST_THREE_MONTH = 1;
    //最近半年数据
    private static final int TYPE_LAST_HALF_YEAR = 2;
    //最近一年数据
    private static final int TYPE_LAST_YEAR = 3;
    //所有数据
    private static final int TYPE_ALL = 4;

    private String[] mTitles;

    private int mCurrentIndex = 0;


    private List<FuelRecordBean> mRecordList;

    private ImageView mIvLeft;
    private PointIndicatorView mPointIndicator;
    private ImageView mIvRight;
    private TextView mTvTitle;
    private LineChart mLineChart;
    private TextView mTvAverageConsumption;
    private TextView mTvMileage;
    private TextView mTvMaximumConsumption;
    private TextView mTvTotalLiters;
    private TextView mTvMinimumConsumption;
    private TextView mTvAverageMileage;
    private TextView mTvRecentConsumption;

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
        mTvTitle = (TextView) view.findViewById(R.id.tv_title);
        mLineChart = (LineChart) view.findViewById(R.id.lineChart);
        mTvAverageConsumption = (TextView) view.findViewById(R.id.tv_average_consumption);
        mTvMileage = (TextView) view.findViewById(R.id.tv_mileage);
        mTvMaximumConsumption = (TextView) view.findViewById(R.id.tv_maximum_consumption);
        mTvTotalLiters = (TextView) view.findViewById(R.id.tv_total_liters);
        mTvMinimumConsumption = (TextView) view.findViewById(R.id.tv_minimum_consumption);
        mTvAverageMileage = (TextView) view.findViewById(R.id.tv_average_mileage);
        mTvRecentConsumption = (TextView) view.findViewById(R.id.tv_recent_consumption);

        mIvRight.setOnClickListener(this);
        mIvLeft.setOnClickListener(this);
    }

    @Override
    public void initData() {
        mRecordList = new ArrayList<>();
        mTitles = new String[4];
        mTitles[0] = getString(R.string.title_fuel_consumption_last_three_month);
        mTitles[1] = getString(R.string.title_fuel_consumption_last_half_year);
        mTitles[2] = getString(R.string.title_fuel_consumption_last_year);
        mTitles[3] = getString(R.string.title_fuel_consumption);
        mTvTitle.setText(mTitles[0]);
        initChart();
        queryData();
    }

    private void queryData() {
        Observable.create(new ObservableOnSubscribe<List<FuelRecordBean>>() {
            @Override
            public void subscribe(ObservableEmitter<List<FuelRecordBean>> e) throws Exception {
                FuelRecordBeanDao recordDao = GreenDaoManager.getInstance().getDaoSession().getFuelRecordBeanDao();
                if (recordDao != null) {
                    List<FuelRecordBean> recordBeenList = recordDao.queryBuilder().orderAsc(FuelRecordBeanDao.Properties.FuelDate).list();
                    e.onNext(recordBeenList);
                } else {
                    e.onError(new Exception("can not get FuelRecordBeanDao!"));
                }
            }
        })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<List<FuelRecordBean>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<FuelRecordBean> fuelRecordBeanList) {
                        if (fuelRecordBeanList != null && fuelRecordBeanList.size() > 0) {
                            mRecordList.clear();
                            mRecordList.addAll(fuelRecordBeanList);
                            if (mRecordList.size() > 1) {
                                setData(TYPE_LAST_THREE_MONTH);
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        WLog.e(TAG, e.getMessage(), e);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    /**
     * 初始化设置图表
     */
    private void initChart() {
        mLineChart.setNoDataText(getString(R.string.tips_no_data));
        mLineChart.setNoDataTextColor(getResources().getColor(R.color.orangered));
    }

    /**
     * 根据不同的图表类型初始化数据
     *
     * @param type
     */
    private void setData(int type) {
        switch (type) {
            case TYPE_LAST_THREE_MONTH:
                break;
            case TYPE_LAST_HALF_YEAR:
                break;
            case TYPE_LAST_YEAR:
                break;
            case TYPE_ALL:
                break;
        }
    }

    @Override
    protected void refresh(Bundle data) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_left:
                mCurrentIndex = mPointIndicator.moveLeft();
                changeTitle();
                break;
            case R.id.iv_right:
                mCurrentIndex = mPointIndicator.moveRight();
                changeTitle();
                break;
        }
    }

    private void changeTitle() {
        mTvTitle.setText(mTitles[mCurrentIndex]);
    }
}
