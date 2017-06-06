package com.wz.fuel.mvp.bean;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 每条加油记录
 */
public class FuelRecordBean {

    //89号汽油
    public static final int TYPE_GAS_89 = 89;
    //92号汽油
    public static final int TYPE_GAS_92 = 92;
    //95号汽油
    public static final int TYPE_GAS_95 = 95;
    //0号柴油
    public static final int TYPE_DIESEL_0 = 0;
    //其他类型
    public static final int TYPE_OTHERS = -1;

    @IntDef({TYPE_DIESEL_0, TYPE_GAS_89, TYPE_GAS_92, TYPE_GAS_95, TYPE_OTHERS})
    @Retention(RetentionPolicy.SOURCE)
    public @interface FuelTypes {
    }

    //加油总价
    public float totalPrice;
    //加油单价
    public float unitPrice;
    //加油时间，使用时间戳
    public long fuelDate;
    //加油容积，升
    public float litres;
    @FuelTypes
    public int fuelType;
    //加油类型字符串形式
    public String fuelTypeStr;
}
