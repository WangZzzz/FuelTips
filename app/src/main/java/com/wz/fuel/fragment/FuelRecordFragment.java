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

import com.wz.fuel.AppConstants;
import com.wz.fuel.R;
import com.wz.fuel.activity.AddFuelRecordActivity;
import com.wz.fuel.adapter.FuelRecordAdapter;
import com.wz.fuel.db.GreenDaoManager;
import com.wz.fuel.mvp.bean.FuelRecordBean;
import com.wz.fuel.mvp.bean.FuelRecordBeanDao;
import com.wz.util.DialogUtil;
import com.wz.util.ScreenUtil;
import com.wz.util.ToastMsgUtil;
import com.wz.util.WLog;
import com.wz.view.LoadMoreOnScrollListener;
import com.wz.view.OnItemClickListener;
import com.wz.view.OnItemLongClickListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * A simple {@link Fragment} subclass.
 */
public class FuelRecordFragment extends BaseFragment {

    private static final String TAG = FuelRecordFragment.class.getSimpleName();

    private LinearLayout mLlAddFuelRecord;
    private RecyclerView mRvFuelRecord;


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

    private static final int LOAD_DATA_DELAY = 500;

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
    public View initView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fuel_record, container, false);
        return view;
    }

    @Override
    public void findViewById(View view) {
        mLlAddFuelRecord = (LinearLayout) view.findViewById(R.id.ll_add_fuel_record);
        mRvFuelRecord = (RecyclerView) view.findViewById(R.id.rv_fuel_record);
    }

    @Override
    public void initData() {
        WLog.d(TAG, "tag: " + getTag());
        mLlAddFuelRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addFuelRecord();
            }
        });
        initRecyclerView();
        queryDb(mLimit, mOffset);
    }

    @Override
    protected void refresh(Bundle data) {

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

    private void addFuelRecord() {
        Intent intent = new Intent(getActivity(), AddFuelRecordActivity.class);
        intent.putExtra(AppConstants.EXTRA_FUEL_PRICE_BEAN, AppConstants.sFuelPriceBean);
        startActivityForResult(intent, AppConstants.REQUEST_ADD_FUEL_RECORD);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case AppConstants.REQUEST_ADD_FUEL_RECORD:
                if (resultCode == Activity.RESULT_OK) {
                    //添加成功
                    mFuelRecords.clear();
                    mOffset = 0;
                    queryDb(mLimit, mOffset);
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

    private void queryDb(final int limit, final int offset) {
        mFooterViewLoadMore.setVisibility(View.GONE);
        Observable.create(new ObservableOnSubscribe<List<FuelRecordBean>>() {

            @Override
            public void subscribe(ObservableEmitter<List<FuelRecordBean>> e) throws Exception {
                FuelRecordBeanDao recordDao = GreenDaoManager.getInstance().getDaoSession().getFuelRecordBeanDao();
                if (recordDao != null) {
                    List<FuelRecordBean> recordBeans = recordDao.queryBuilder().limit(limit).offset(offset).orderDesc(FuelRecordBeanDao.Properties.Id).list();
                    e.onNext(recordBeans);
                }
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<FuelRecordBean>>() {
                    @Override
                    public void accept(List<FuelRecordBean> fuelRecordBeanList) throws Exception {
                        if (fuelRecordBeanList != null && fuelRecordBeanList.size() > 0) {
                            mFuelRecords.addAll(fuelRecordBeanList);
                            mOffset += fuelRecordBeanList.size();
                            mAdapter.notifyDataSetChanged();
                        } else {
                            //没有更多数据了
                        }
                    }
                });
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
