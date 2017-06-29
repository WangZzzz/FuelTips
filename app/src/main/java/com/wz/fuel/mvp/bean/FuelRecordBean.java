package com.wz.fuel.mvp.bean;

import android.os.Parcel;
import android.os.Parcelable;
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
public class FuelRecordBean implements Parcelable {

    //89号汽油
    public static final int TYPE_GAS_89 = 0;
    //92号汽油
    public static final int TYPE_GAS_92 = 1;
    //95号汽油
    public static final int TYPE_GAS_95 = 2;
    //0号柴油
    public static final int TYPE_DIESEL_0 = 3;
    //其他类型
    public static final int TYPE_OTHERS = 4;

    @IntDef({TYPE_DIESEL_0, TYPE_GAS_89, TYPE_GAS_92, TYPE_GAS_95, TYPE_OTHERS})
    @Retention(RetentionPolicy.SOURCE)
    public @interface FuelType {
    }

    //@Id注解，标识主键
    @Id(autoincrement = true)
    public Long id;
    //加油总价
    public float totalPrice;
    //加油单价
    public float unitPrice;
    //加油时间，使用时间戳
    public long fuelDate;
    //加油容积，升
    public float litres;
    @FuelType
    public int fuelType;
    //加油类型字符串形式
    public String fuelTypeStr;

    @Generated(hash = 309144516)
    public FuelRecordBean(Long id, float totalPrice, float unitPrice, long fuelDate, float litres, int fuelType,
                          String fuelTypeStr) {
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

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public static String getFuelTypeString(int fuelType) {
        switch (fuelType) {
            case TYPE_GAS_89:
                return "89号汽油";
            case TYPE_GAS_92:
                return "92号汽油";
            case TYPE_GAS_95:
                return "95号汽油";
            case TYPE_DIESEL_0:
                return "0号柴油";
            case TYPE_OTHERS:
                return "其他";
        }
        return null;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeFloat(this.totalPrice);
        dest.writeFloat(this.unitPrice);
        dest.writeLong(this.fuelDate);
        dest.writeFloat(this.litres);
        dest.writeInt(this.fuelType);
        dest.writeString(this.fuelTypeStr);
    }

    protected FuelRecordBean(Parcel in) {
        this.id = in.readLong();
        this.totalPrice = in.readFloat();
        this.unitPrice = in.readFloat();
        this.fuelDate = in.readLong();
        this.litres = in.readFloat();
        this.fuelType = in.readInt();
        this.fuelTypeStr = in.readString();
    }

    public static final Parcelable.Creator<FuelRecordBean> CREATOR = new Parcelable.Creator<FuelRecordBean>() {
        @Override
        public FuelRecordBean createFromParcel(Parcel source) {
            return new FuelRecordBean(source);
        }

        @Override
        public FuelRecordBean[] newArray(int size) {
            return new FuelRecordBean[size];
        }
    };
}
