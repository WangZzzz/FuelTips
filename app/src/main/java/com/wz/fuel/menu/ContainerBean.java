package com.wz.fuel.menu;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.wz.fuel.db.StringConverter;

import org.greenrobot.greendao.annotation.Convert;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

import java.util.List;

/**
 * 动态Container，内部包含menu
 */
@Entity
public class ContainerBean implements Parcelable {
    @Id
    public String id;
    //该容器包含的menu列表
    @SerializedName("menus")
    @Convert(columnType = String.class, converter = StringConverter.class)
    public List<String> menuIdList;
    //附加信息
    public String extra;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeStringList(this.menuIdList);
        dest.writeString(this.extra);
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<String> getMenuIdList() {
        return this.menuIdList;
    }

    public void setMenuIdList(List<String> menuIdList) {
        this.menuIdList = menuIdList;
    }

    public String getExtra() {
        return this.extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    public ContainerBean() {
    }

    protected ContainerBean(Parcel in) {
        this.id = in.readString();
        this.menuIdList = in.createStringArrayList();
        this.extra = in.readString();
    }

    @Generated(hash = 1476164422)
    public ContainerBean(String id, List<String> menuIdList, String extra) {
        this.id = id;
        this.menuIdList = menuIdList;
        this.extra = extra;
    }

    public static final Creator<ContainerBean> CREATOR = new Creator<ContainerBean>() {
        @Override
        public ContainerBean createFromParcel(Parcel source) {
            return new ContainerBean(source);
        }

        @Override
        public ContainerBean[] newArray(int size) {
            return new ContainerBean[size];
        }
    };
}
