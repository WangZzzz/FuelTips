package com.wz.fuel.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wz.fuel.AppConstants;
import com.wz.fuel.message.MessageEvent;
import com.wz.util.WLog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseFragment extends Fragment {

    private static final String TAG = BaseFragment.class.getSimpleName();

    protected View mRootView = null;
    protected Activity mActivity;
    protected Context mContext;

    protected Unbinder mUnbinder;
    /**
     * 使用此flag，防止频繁加载数据
     */
    protected boolean isNeedInit = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mActivity = getActivity();
        mContext = getContext();
        if (mRootView == null) {
            isNeedInit = true;
            mRootView = initView(inflater, container, savedInstanceState);
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
        if (isNeedInit) {
            initData();
            isNeedInit = false;
        }
    }

    public abstract View initView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState);

    public abstract void initData();

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
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

    protected abstract void refresh(Bundle data);

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        WLog.d(TAG, "onMessageEvent : " + getTag());
        if (event != null) {
            int type = event.messageType;
            switch (type) {
                case MessageEvent.TYPE_REFRESH_ALL_FRAGMENT:
                    refresh(event.data);
                    break;
                case MessageEvent.TYPE_REFRESH_FUEL_PRICE_FRAGMENT:
                    if (AppConstants.TAB_TITLES[0].equals(getTag())) {
                        refresh(event.data);
                    }
                    break;
                case MessageEvent.TYPE_REFRESH_FUEL_RECORD_FRAGMENT:
                    if (AppConstants.TAB_TITLES[1].equals(getTag())) {
                        refresh(event.data);
                    }
                    break;
                case MessageEvent.TYPE_REFRESH_FUEL_STATISTICS_FRAGMENT:
                    if (AppConstants.TAB_TITLES[2].equals(getTag())) {
                        refresh(event.data);
                    }
                    break;
                case MessageEvent.TYPE_REFRESH_MINE_FRAGMENT:
                    if (AppConstants.TAB_TITLES[3].equals(getTag())) {
                        refresh(event.data);
                    }
                    break;
            }
        }
    }
}
