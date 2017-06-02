package com.wz.fuel.mvp.view;

import java.util.List;

/**
 * @param <T>
 */
public interface IView<T> {
    void onError(String errorMsg);

    void onSuccess(List<T> tList);

    void showProgressDialog();

    void hideProgressDialog();
}