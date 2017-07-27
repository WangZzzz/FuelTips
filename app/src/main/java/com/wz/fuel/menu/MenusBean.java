package com.wz.fuel.menu;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MenusBean implements Parcelable {
    @SerializedName("menus")
    List<MenuDataBean> menus;
    @SerializedName("containers")
    List<ContainerBean> containers;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.menus);
        dest.writeTypedList(this.containers);
    }

    public MenusBean() {
    }

    protected MenusBean(Parcel in) {
        this.menus = in.createTypedArrayList(MenuDataBean.CREATOR);
        this.containers = in.createTypedArrayList(ContainerBean.CREATOR);
    }

    public static final Creator<MenusBean> CREATOR = new Creator<MenusBean>() {
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