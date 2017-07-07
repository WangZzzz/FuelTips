package com.wz.fuel.menu;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MenusBean implements Parcelable {
    @SerializedName("menus")
    List<MenuDataBean> menus;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.menus);
    }

    public MenusBean() {
    }

    protected MenusBean(Parcel in) {
        this.menus = in.createTypedArrayList(MenuDataBean.CREATOR);
    }

    public static final Parcelable.Creator<MenusBean> CREATOR = new Parcelable.Creator<MenusBean>() {
        @Override
        public MenusBean createFromParcel(Parcel source) {
            return new MenusBean(source);
        }

        @Override
        public MenusBean[] newArray(int size) {
            return new MenusBean[size];
        }
    };
}