package com.wz.fuel.menu;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.IntDef;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Entity
public class MenuDataBean implements Parcelable {
    public static final int TYPE_URL = 1;
    public static final int TYPE_SUB_MENUS = 2;
    public static final int TYPE_NATIVE = 3;

    @IntDef({TYPE_URL, TYPE_SUB_MENUS, TYPE_NATIVE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface JumpType {

    }

    //@Id注解，标识主键
    @Id
    public String id;
    private String menuName;
    private String menuDesc;
    //1-url 2-子菜单 3-native
    private int jumpType;
    private String template;
    private String imgName;
    private String menuParent;
    private String isActive;
    private int sort;

    @Generated(hash = 189663858)
    public MenuDataBean(String id, String menuName, String menuDesc, int jumpType,
                        String template, String imgName, String menuParent, String isActive,
                        int sort) {
        this.id = id;
        this.menuName = menuName;
        this.menuDesc = menuDesc;
        this.jumpType = jumpType;
        this.template = template;
        this.imgName = imgName;
        this.menuParent = menuParent;
        this.isActive = isActive;
        this.sort = sort;
    }

    @Generated(hash = 1244213315)
    public MenuDataBean() {
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMenuName() {
        return this.menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getMenuDesc() {
        return this.menuDesc;
    }

    public void setMenuDesc(String menuDesc) {
        this.menuDesc = menuDesc;
    }

    public int getJumpType() {
        return this.jumpType;
    }

    public void setJumpType(int jumpType) {
        this.jumpType = jumpType;
    }

    public String getTemplate() {
        return this.template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public String getImgName() {
        return this.imgName;
    }

    public void setImgName(String imgName) {
        this.imgName = imgName;
    }

    public String getMenuParent() {
        return this.menuParent;
    }

    public void setMenuParent(String menuParent) {
        this.menuParent = menuParent;
    }

    public String getIsActive() {
        return this.isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }

    public int getSort() {
        return this.sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.menuName);
        dest.writeString(this.menuDesc);
        dest.writeInt(this.jumpType);
        dest.writeString(this.template);
        dest.writeString(this.imgName);
        dest.writeString(this.menuParent);
        dest.writeString(this.isActive);
        dest.writeInt(this.sort);
    }

    protected MenuDataBean(Parcel in) {
        this.id = in.readString();
        this.menuName = in.readString();
        this.menuDesc = in.readString();
        this.jumpType = in.readInt();
        this.template = in.readString();
        this.imgName = in.readString();
        this.menuParent = in.readString();
        this.isActive = in.readString();
        this.sort = in.readInt();
    }

    public static final Parcelable.Creator<MenuDataBean> CREATOR = new Parcelable.Creator<MenuDataBean>() {
        @Override
        public MenuDataBean createFromParcel(Parcel source) {
            return new MenuDataBean(source);
        }

        @Override
        public MenuDataBean[] newArray(int size) {
            return new MenuDataBean[size];
        }
    };

    @Override
    public String toString() {
        return id + "@" + menuName;
    }
}
