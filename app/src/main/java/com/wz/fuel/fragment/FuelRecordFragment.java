package com.wz.fuel.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.wz.fragment.WBaseFragment;
import com.wz.fuel.R;
import com.wz.fuel.activity.AddFuelRecordActivity;
import com.wz.fuel.adapter.FuelRecordAdapter;
import com.wz.fuel.db.GreenDaoManager;
import com.wz.fuel.mvp.bean.FuelRecordBean;
import com.wz.fuel.mvp.bean.FuelRecordBeanDao;
import com.wz.fuel.view.AddFuelRecordPopupWindow;
import com.wz.util.ToastMsgUtil;
import com.wz.util.WLog;
import com.wz.view.OnItemClickListener;
import com.wz.view.OnItemLongClickListener;

import java.util.ArrayList;
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

    private FuelRecordAdapter mAdapter;
    private List<FuelRecordBean> mFuelRecords;

    private AddFuelRecordPopupWindow mPopupWindow;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WLog.d(TAG, "onCreate");
    }

    @Override
    public View initView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fuel_record, container, false);
        unbinder = ButterKnife.bind(this, view);
        initRecyclerView();
        mLlAddFuelRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addFuelRecord();
            }
        });
        return view;
    }

    private void initRecyclerView() {
        mFuelRecords = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            FuelRecordBean recordBean = new FuelRecordBean();
            recordBean.fuelType = FuelRecordBean.TYPE_GAS_92;
            recordBean.fuelTypeStr = "92号汽油";
            recordBean.litres = 52.3f;
            recordBean.totalPrice = 300.5f;
            recordBean.unitPrice = 6.45f;
            recordBean.fuelDate = System.currentTimeMillis() - i * 200000;
            mFuelRecords.add(recordBean);
        }
        mAdapter = new FuelRecordAdapter(getActivity(), mFuelRecords);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRvFuelRecord.setLayoutManager(layoutManager);
        mRvFuelRecord.setAdapter(mAdapter);

        mAdapter.setOnItemLongClickListener(new OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(View view, int position) {
                ToastMsgUtil.info(getActivity(), "删除：" + position, 0);
                return true;
            }
        });

        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                ToastMsgUtil.showToast(getActivity(), "点击：" + position, 0);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        if (mPopupWindow != null && mPopupWindow.isShowing()) {
            mPopupWindow.dismiss();
        }
    }

    private void addFuelRecord() {
        FuelRecordBeanDao recordDao = GreenDaoManager.getInstance().getDaoSession().getFuelRecordBeanDao();
        ToastMsgUtil.showToast(getContext(), "添加记录", 0);
//        showPopWindow();
        startActivity(new Intent(getActivity(), AddFuelRecordActivity.class));
    }

    private void showPopWindow() {
        if (mPopupWindow == null) {
            WLog.d(TAG, "initPopWindow");
            mPopupWindow = new AddFuelRecordPopupWindow(getActivity());
        }
        mPopupWindow.showPopupWidow(getView());
    }

    private void hidePopWindow() {
        if (mPopupWindow != null) {
            mPopupWindow.hidePopupWindow();
        }
    }
}
