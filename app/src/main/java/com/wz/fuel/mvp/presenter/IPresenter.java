package com.wz.fuel.mvp.presenter;

import com.wz.fuel.mvp.view.IView;

/**
 * <br>
 * FIREFLY
 * <p>
 * com.wz.selection.mvp.presenter
 *
 * @author wangzhe
 * @version 3.2.0
 * @date 2017/2/23 10:09
 * @api 7
 * <br>
 * CMBC-版权所有
 * <br>
 */
public abstract class IPresenter<T> {
    protected IView<T> mView;

    public IPresenter(IView<T> iView) {
        mView = iView;
    }

}
