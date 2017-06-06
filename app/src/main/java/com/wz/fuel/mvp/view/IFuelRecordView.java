package com.wz.fuel.mvp.view;

import com.wz.fuel.mvp.bean.FuelRecordBean;

import java.util.List;

/**
 * <br>
 * FIREFLY
 * <p>
 * com.wz.fuel.mvp.view
 *
 * @author wangzhe
 * @version 3.2.0
 * @date 2017/6/6 10:21
 * @api 7
 * <br>
 * CMBC-版权所有
 * <br>
 */
public interface IFuelRecordView extends IView<FuelRecordBean> {
    void onLoadFromDb(List<FuelRecordBean> fuelRecordList);

    void onLoadFromServer(List<FuelRecordBean> fuelRecordList);
}
