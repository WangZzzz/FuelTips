package com.wz.fuel.mvp.bean;

import android.support.annotation.IntDef;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 每条加油记录
 */
@Entity
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

    //@Id注解，标识主键
    @Id
    public long id;
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

    @Generated(hash = 2016722369)
    public FuelRecordBean(long id, float totalPrice, float unitPrice, long fuelDate,
                          float litres, int fuelType, String fuelTypeStr) {
        this.id = id;
        this.totalPrice = totalPrice;
        this.unitPrice = unitPrice;
        this.fuelDate = fuelDate;
        this.litres = litres;
        this.fuelType = fuelType;
        this.fuelTypeStr = fuelTypeStr;
    }

    @Generated(hash = 64443763)
    public FuelRecordBean() {
    }

    public float getTotalPrice() {
        return this.totalPrice;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public float getUnitPrice() {
        return this.unitPrice;
    }

    public void setUnitPrice(float unitPrice) {
        this.unitPrice = unitPrice;
    }

    public long getFuelDate() {
        return this.fuelDate;
    }

    public void setFuelDate(long fuelDate) {
        this.fuelDate = fuelDate;
    }

    public float getLitres() {
        return this.litres;
    }

    public void setLitres(float litres) {
        this.litres = litres;
    }

    public int getFuelType() {
        return this.fuelType;
    }

    public void setFuelType(int fuelType) {
        this.fuelType = fuelType;
    }

    public String getFuelTypeStr() {
        return this.fuelTypeStr;
    }

    public void setFuelTypeStr(String fuelTypeStr) {
        this.fuelTypeStr = fuelTypeStr;
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
