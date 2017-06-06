package com.wz.fuel.mvp.presenter;

import com.wz.fuel.mvp.view.IView;

public abstract class IPresenter<T> {
    protected IView<T> mView;

    public IPresenter(IView<T> iView) {
        mView = iView;
    }

}
