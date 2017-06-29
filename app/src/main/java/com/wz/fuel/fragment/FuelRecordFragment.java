package com.wz.fuel.fragment;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.wz.fragment.WBaseFragment;
import com.wz.fuel.AppConstants;
import com.wz.fuel.R;
import com.wz.fuel.activity.AddFuelRecordActivity;
import com.wz.fuel.activity.MainActivity;
import com.wz.fuel.adapter.FuelRecordAdapter;
import com.wz.fuel.db.GreenDaoManager;
import com.wz.fuel.mvp.bean.FuelRecordBean;
import com.wz.fuel.mvp.bean.FuelRecordBeanDao;
import com.wz.util.ToastMsgUtil;
import com.wz.view.LoadMoreOnScrollListener;
import com.wz.view.OnItemClickListener;
import com.wz.view.OnItemLongClickListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class FuelRecordFragment extends WBaseFragment {

    private static final String TAG = FuelRecordFragment.class.getSimpleName();
    @BindView(R.id.ll_add_fuel_record)
    LinearLayout mLlAddFuelRecord;
    @BindView(R.id.rv_fuel_record)
    RecyclerView mRvFuelRecord;
    Unbinder unbinder;

    private MainActivity mActivity;

    private FuelRecordAdapter mAdapter;
    private List<FuelRecordBean> mFuelRecords;

    /**
     * 起始位置
     */
    private int mOffset = 0;
    /**
     * 一次取数据的数量
     */
    private int mLimit = 10;

    private View mFooterView;

    private static final int MSG_LOAD_MORE_DATA = 1;

    private static final int LOAD_DATA_DELAY = 1000;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_LOAD_MORE_DATA:
                    queryDb(mLimit, mOffset);
                    break;
            }
        }
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = (MainActivity) getActivity();
    }

    @Override
    public View initView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fuel_record, container, false);
        unbinder = ButterKnife.bind(this, view);
        init();
        mLlAddFuelRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addFuelRecord();
            }
        });
        return view;
    }

    private void init() {
        initRecyclerView();
        queryDb(mLimit, mOffset);
    }

    private void initRecyclerView() {
        mFuelRecords = new ArrayList<>();
        mAdapter = new FuelRecordAdapter(getActivity(), mFuelRecords);
        initFooterView();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRvFuelRecord.setLayoutManager(layoutManager);
        mRvFuelRecord.setAdapter(mAdapter);

        mAdapter.setOnItemLongClickListener(mOnItemLongClickListener);

        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                ToastMsgUtil.showToast(getActivity(), "点击：" + position, 0);
            }
        });

        mRvFuelRecord.addOnScrollListener(new LoadMoreOnScrollListener(layoutManager) {
            @Override
            public void loadMoreData() {
                mHandler.sendEmptyMessageDelayed(MSG_LOAD_MORE_DATA, LOAD_DATA_DELAY);
            }
        });
    }

    private void initFooterView() {
        mFooterView = LayoutInflater.from(getActivity()).inflate(R.layout.footerview_layout, null);
        mAdapter.setFooterView(mFooterView);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void addFuelRecord() {
        FuelRecordBeanDao recordDao = GreenDaoManager.getInstance().getDaoSession().getFuelRecordBeanDao();
        Intent intent = new Intent(getActivity(), AddFuelRecordActivity.class);
        intent.putExtra(AppConstants.EXTRA_FUEL_PRICE_BEAN, mActivity.getCurrentFuelPriceBean());
        startActivityForResult(intent, AppConstants.REQUEST_ADD_FUEL_RECORD);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case AppConstants.REQUEST_ADD_FUEL_RECORD:
                if (resultCode == Activity.RESULT_OK) {
                    //添加成功
                    if (data != null) {
                        FuelRecordBean fuelRecordBean = data.getParcelableExtra(AppConstants.EXTRA_FUEL_RECORD_BEAN);
                        mFuelRecords.add(fuelRecordBean);
                        sortRecordList();
                        mAdapter.notifyDataSetChanged();
                    }
                } else if (resultCode == Activity.RESULT_CANCELED) {
                    ToastMsgUtil.info(getActivity(), "取消添加", 0);
                }
                break;
        }
    }

    private OnItemLongClickListener mOnItemLongClickListener = new OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(View view, int position) {
            ToastMsgUtil.info(getActivity(), "删除：" + position, 0);
            return true;
        }
    };

    private void queryDb(int limit, int offset) {
        FuelRecordBeanDao recordDao = GreenDaoManager.getInstance().getDaoSession().getFuelRecordBeanDao();
        List<FuelRecordBean> recordBeans = recordDao.queryBuilder().limit(limit).offset(offset).orderDesc(FuelRecordBeanDao.Properties.Id).list();
        if (recordBeans != null) {
            mFuelRecords.addAll(recordBeans);
            mOffset = recordBeans.size();
            mAdapter.notifyDataSetChanged();
        }
    }

    /**
     * 将List<FuelRecordBean> 按照加油时间的降序、id的降序排列
     */
    private void sortRecordList() {
        if (mFuelRecords != null && mFuelRecords.size() > 0) {
            Collections.sort(mFuelRecords, new Comparator<FuelRecordBean>() {
                @Override
                public int compare(FuelRecordBean o1, FuelRecordBean o2) {
                    if (o1.fuelDate != o2.fuelDate) {
                        return (int) (o2.fuelDate - o1.fuelDate);
                    } else {
                        return (int) (o2.id - o1.id);
                    }
                }
            });
        }
    }
}
