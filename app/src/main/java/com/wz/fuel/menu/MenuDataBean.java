package com.wz.fuel.menu;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class MenuDataBean {

    //@Id注解，标识主键
    @Id
    public int id;
    private String jumpType;
    private String template;
    private String imgName;
    @Generated(hash = 1757194259)
    public MenuDataBean(int id, String jumpType, String template, String imgName) {
        this.id = id;
        this.jumpType = jumpType;
        this.template = template;
        this.imgName = imgName;
    }
    @Generated(hash = 1244213315)
    public MenuDataBean() {
    }
    public int getId() {
        return this.id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getJumpType() {
        return this.jumpType;
    }
    public void setJumpType(String jumpType) {
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
}
