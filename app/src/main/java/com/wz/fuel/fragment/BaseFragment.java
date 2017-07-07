package com.wz.fuel.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * <br>
 * FIREFLY
 * <p>
 * com.wz.fuel.fragment
 *
 * @author wangzhe
 * @version 3.2.0
 * @date 2017/7/4 15:13
 * @api 7
 * <br>
 * CMBC-版权所有
 * <br>
 */
public abstract class BaseFragment extends Fragment {
    protected View mRootView = null;
    protected Activity mActivity;
    protected Context mContext;

    protected Unbinder mUnbinder;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mActivity = getActivity();
        mContext = getContext();
        if (mRootView == null) {
            mRootView = initView(inflater, container, savedInstanceState);
            initData();
        }

        //缓存的rootView，需要判断是否已经被加过parent
        //如果有parent，需要从parent中将rootView删除
        ViewGroup parent = (ViewGroup) mRootView.getParent();
        if (parent != null) {
            parent.removeView(mRootView);
        }

        return mRootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mUnbinder = ButterKnife.bind(this, mRootView);
//        initData();
    }

    public abstract View initView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState);

    public abstract void initData();

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }
}
