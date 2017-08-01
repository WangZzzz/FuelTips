package com.wz.fuel.fragment;


import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.wz.fuel.R;
import com.wz.fuel.db.GreenDaoManager;
import com.wz.fuel.mvp.bean.FuelRecordBean;
import com.wz.fuel.mvp.bean.FuelRecordBeanDao;
import com.wz.util.NumberUtil;
import com.wz.util.TimeUtil;
import com.wz.util.WLog;
import com.wz.view.PointIndicatorView;

import java.util.ArrayList;
import java.util.Calendar;
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
    //近3个月统计曲线
    private LineChart mLineChart1;
    //近半年
    private LineChart mLineChart2;
    //近一年
    private LineChart mLineChart3;
    //全部数据
    private LineChart mLineChart4;
    private TextView mTvAverageConsumption;
    private TextView mTvMileage;
    private TextView mTvMaximumConsumption;
    private TextView mTvTotalLiters;
    private TextView mTvMinimumConsumption;
    private TextView mTvAverageMileage;
    private TextView mTvRecentConsumption;

    private int mCurrentMonth;

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
        mLineChart1 = (LineChart) view.findViewById(R.id.lineChart1);
        mLineChart2 = (LineChart) view.findViewById(R.id.lineChart2);
        mLineChart3 = (LineChart) view.findViewById(R.id.lineChart3);
        mLineChart4 = (LineChart) view.findViewById(R.id.lineChart4);
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
        mCurrentMonth = Calendar.getInstance().get(Calendar.MONTH) + 1;
        WLog.d(TAG, "当前月份是：" + mCurrentMonth);
        mTitles = new String[4];
        mTitles[0] = getString(R.string.title_fuel_consumption_last_three_month);
        mTitles[1] = getString(R.string.title_fuel_consumption_last_half_year);
        mTitles[2] = getString(R.string.title_fuel_consumption_last_year);
        mTitles[3] = getString(R.string.title_fuel_consumption);
        mTvTitle.setText(mTitles[0]);
        queryData();
        initChart(mLineChart1);
    }

    private void queryData() {
        Observable.create(new ObservableOnSubscribe<List<FuelRecordBean>>() {
            @Override
            public void subscribe(ObservableEmitter<List<FuelRecordBean>> e) throws Exception {
                FuelRecordBeanDao recordDao = GreenDaoManager.getInstance().getDaoSession().getFuelRecordBeanDao();
                if (recordDao != null) {
                    List<FuelRecordBean> recordBeenList = recordDao.queryBuilder().orderDesc(FuelRecordBeanDao.Properties.FuelDate).list();
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
    private void initChart(LineChart lineChart) {
        lineChart.setNoDataText(getString(R.string.tips_no_data));
        lineChart.setNoDataTextColor(getResources().getColor(R.color.orangered));
        lineChart.setDrawGridBackground(false);
        // no description text
        lineChart.getDescription().setEnabled(false);
        // disable touch gestures
        lineChart.setTouchEnabled(false);
        // disable scaling and dragging
        lineChart.setDragEnabled(false);
        lineChart.setScaleEnabled(false);
        // mChart.setScaleXEnabled(true);
        // mChart.setScaleYEnabled(true);
        // if disabled, scaling can be done on x- and y-axis separately
        lineChart.setPinchZoom(false);

        XAxis xAxis = lineChart.getXAxis();
        xAxis.enableGridDashedLine(10f, 10f, 0f);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        YAxis yAxis = lineChart.getAxisLeft();
        yAxis.removeAllLimitLines(); // reset all limit lines to avoid overlapping lines
        //设置y轴最大值
        yAxis.setAxisMaximum(120f);
        //设置y轴最小值
        yAxis.setAxisMinimum(-50f);
        //leftAxis.setYOffset(20f);
        yAxis.enableGridDashedLine(10f, 10f, 0f);
        yAxis.setDrawZeroLine(false);

        // limit lines are drawn behind data (and not on top)
        yAxis.setDrawLimitLinesBehindData(true);

        lineChart.getAxisRight().setEnabled(false);
    }

    /**
     * 根据不同的图表类型初始化数据
     *
     * @param type
     */
    private void setData(int type) {
        switch (type) {
            case TYPE_LAST_THREE_MONTH:
                List<FuelRecordBean> recordList1 = processData(3);
                mLineChart1.setData(getLineDate(recordList1));
                mLineChart1.getData().notifyDataChanged();
                mLineChart1.notifyDataSetChanged();
                break;
            case TYPE_LAST_HALF_YEAR:
                List<FuelRecordBean> recordList2 = processData(6);
                break;
            case TYPE_LAST_YEAR:
                List<FuelRecordBean> recordList3 = processData(12);
                break;
            case TYPE_ALL:
                break;
        }
    }

    private List<FuelRecordBean> processData(int offset) {
        List<FuelRecordBean> recordList = new ArrayList<>();
        if (mRecordList != null && mRecordList.size() > 0) {
            for (FuelRecordBean record : mRecordList) {
                if (record.fuelMonth > mCurrentMonth - offset) {
                    //最近三月数据
                    recordList.add(record);
                }
            }
        }
        return recordList;
    }

    private LineData getLineDate(List<FuelRecordBean> recordList) {
        if (recordList == null || recordList.size() <= 0) {
            return null;
        }
        List<Entry> values = new ArrayList<>();
//        for (int i = 0; i < recordList.size() - 1; i++) {
//            FuelRecordBean record1 = recordList.get(i);
//            FuelRecordBean record2 = recordList.get(i + 1);
//            if (record1 != null && record2 != null) {
//                //里程
//                float mileage = record1.currentMileage - record2.currentMileage;
//                float y = NumberUtil.format(record2.litres / mileage * 100);
//                float x = getX(record1);
//                WLog.d(TAG, x + " : " + y + " 升/百公里");
//                Entry entry = new Entry(x, y);
//                values.add(entry);
//            }
//        }


        for (int i = 0; i < 45; i++) {

            float val = (float) (Math.random() * 100) + 3;
            values.add(new Entry(i, val));
        }

        LineDataSet lineDataSet = new LineDataSet(values, "油耗");
        lineDataSet.setDrawIcons(false);

        // set the line to be drawn like this "- - - - - -"
        lineDataSet.enableDashedLine(10f, 5f, 0f);
        lineDataSet.enableDashedHighlightLine(10f, 5f, 0f);
        lineDataSet.setColor(Color.BLACK);
        lineDataSet.setCircleColor(Color.BLUE);
        lineDataSet.setLineWidth(1f);
        lineDataSet.setCircleRadius(3f);
        lineDataSet.setDrawCircleHole(false);
        lineDataSet.setValueTextSize(9f);
        lineDataSet.setDrawFilled(true);
        lineDataSet.setFormLineWidth(1f);
        lineDataSet.setFormLineDashEffect(new DashPathEffect(new float[]{10f, 5f}, 0f));
        lineDataSet.setFormSize(15.f);
        LineData lineData = new LineData(lineDataSet);
        return lineData;
    }

    private float getX(FuelRecordBean recordBean) {
        if (recordBean != null) {
            int days = TimeUtil.getDaysByYearMonth(recordBean.fuelYear, recordBean.fuelMonth);
            float x = recordBean.fuelMonth + NumberUtil.format(recordBean.fuelDay / (days * 1.0f));
            return x;
        }
        return 0;
    }

    private int getTotalLiters(List<FuelRecordBean> recordList) {
        return 0;
    }

    private float getAverageConsumption(List<FuelRecordBean> recordList) {
        return 0;
    }

    private float getMaximumConsumption(List<FuelRecordBean> recordList) {
        return 0;
    }

    private float getMinimumConsumption(List<FuelRecordBean> recordList) {
        return 0;
    }

    private float getRecentConsumption(List<FuelRecordBean> recordList) {
        return 0;
    }

    private float getAverageMileage(List<FuelRecordBean> recordList) {
        return 0;
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
