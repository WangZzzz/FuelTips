package com.wz.fuel.mvp.view;

import com.wz.fuel.mvp.bean.FuelRecordBean;

import java.util.List;

public interface IFuelRecordView extends IView<FuelRecordBean> {
    void onLoadFromDb(List<FuelRecordBean> fuelRecordList);

    void onLoadFromServer(List<FuelRecordBean> fuelRecordList);
}
