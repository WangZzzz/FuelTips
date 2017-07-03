package com.wz.fuel.fragment;


import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.DialogInterface;
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
import android.view.animation.Animation;
import android.widget.ImageView;
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
import com.wz.util.DialogUtil;
import com.wz.util.ScreenUtil;
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

    private MainActivity mMainActivity;

    private FuelRecordAdapter mAdapter;
    private List<FuelRecordBean> mFuelRecords;

    /**
     * 起始位置
     */
    private int mOffset = 0;
    /**
     * 一次取数据的数量
     */
    private int mLimit = 5;

    /**
     * 显示加载更多尾标
     */
    private View mFooterViewLoadMore;

    private static final int MSG_LOAD_MORE_DATA = 1;

    private static final int LOAD_DATA_DELAY = 2000;

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
        mMainActivity = (MainActivity) getActivity();
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
        initFooterViewLoadMore();
        mAdapter.setFooterView(mFooterViewLoadMore);
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
                mFooterViewLoadMore.setVisibility(View.VISIBLE);
                mHandler.sendEmptyMessageDelayed(MSG_LOAD_MORE_DATA, LOAD_DATA_DELAY);
            }
        });
    }

    private void initFooterViewLoadMore() {
        mFooterViewLoadMore = LayoutInflater.from(getActivity()).inflate(R.layout.footerview_load_more_layout, null);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(ScreenUtil.dpToPx(mContext, 5), ScreenUtil.dpToPx(mContext, 5), ScreenUtil.dpToPx(mContext, 5), ScreenUtil.dpToPx(mContext, 5));
        mFooterViewLoadMore.setLayoutParams(layoutParams);
        ImageView imageView = (ImageView) mFooterViewLoadMore.findViewById(R.id.iv_load_icon);
        ObjectAnimator animator = ObjectAnimator.ofFloat(imageView, "rotation", 0, 360).setDuration(1000);
        animator.setRepeatCount(Animation.INFINITE);
        animator.setRepeatMode(ValueAnimator.RESTART);
        animator.start();
        mFooterViewLoadMore.setVisibility(View.GONE);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void addFuelRecord() {
        FuelRecordBeanDao recordDao = GreenDaoManager.getInstance().getDaoSession().getFuelRecordBeanDao();
        Intent intent = new Intent(getActivity(), AddFuelRecordActivity.class);
        intent.putExtra(AppConstants.EXTRA_FUEL_PRICE_BEAN, mMainActivity.getCurrentFuelPriceBean());
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
        public boolean onItemLongClick(View view, final int position) {
            DialogUtil.showDialog(getActivity(), "是否删除记录？", "确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    FuelRecordBean fuelRecordBean = mFuelRecords.get(position);
                    mFuelRecords.remove(fuelRecordBean);
                    if (fuelRecordBean != null) {
                        deleteRecord(fuelRecordBean);
                    }
                    mAdapter.notifyItemRemoved(position);
                }
            }, "取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            return true;
        }
    };

    private void queryDb(int limit, int offset) {
        mFooterViewLoadMore.setVisibility(View.GONE);
        FuelRecordBeanDao recordDao = GreenDaoManager.getInstance().getDaoSession().getFuelRecordBeanDao();
        List<FuelRecordBean> recordBeans = recordDao.queryBuilder().limit(limit).offset(offset).orderDesc(FuelRecordBeanDao.Properties.Id).list();
        if (recordBeans != null && recordBeans.size() > 0) {
            mFuelRecords.addAll(recordBeans);
            mOffset += recordBeans.size();
            mAdapter.notifyDataSetChanged();
        } else {
            ToastMsgUtil.info(getActivity(), "没有更多数据了~~~", 0);
        }
    }

    private void deleteRecord(FuelRecordBean fuelRecordBean) {
        FuelRecordBeanDao recordDao = GreenDaoManager.getInstance().getDaoSession().getFuelRecordBeanDao();
        recordDao.delete(fuelRecordBean);
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
