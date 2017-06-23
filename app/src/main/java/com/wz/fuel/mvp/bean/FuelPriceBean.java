package com.wz.fuel.mvp.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class FuelPriceBean implements Parcelable {
    public String province;
    public float price_gas_89;
    public float price_gas_92;
    public float price_gas_95;
    public float price_diesel_0;

    @Override
    public String toString() {
        return province + "[89号汽油：" + price_gas_89
                + "，92号汽油：" + price_gas_92 + "，95号汽油："
                + price_gas_95 + "，0号柴油：" + price_diesel_0 + "]";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.province);
        dest.writeFloat(this.price_gas_89);
        dest.writeFloat(this.price_gas_92);
        dest.writeFloat(this.price_gas_95);
        dest.writeFloat(this.price_diesel_0);
    }

    public FuelPriceBean() {
    }

    protected FuelPriceBean(Parcel in) {
        this.province = in.readString();
        this.price_gas_89 = in.readFloat();
        this.price_gas_92 = in.readFloat();
        this.price_gas_95 = in.readFloat();
        this.price_diesel_0 = in.readFloat();
    }

    public static final Parcelable.Creator<FuelPriceBean> CREATOR = new Parcelable.Creator<FuelPriceBean>() {
        @Override
        public FuelPriceBean createFromParcel(Parcel source) {
            return new FuelPriceBean(source);
        }

        @Override
        public FuelPriceBean[] newArray(int size) {
            return new FuelPriceBean[size];
        }
    };
}
