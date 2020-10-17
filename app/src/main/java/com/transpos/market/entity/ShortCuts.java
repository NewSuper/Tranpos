package com.transpos.market.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;

@Entity(tableName = "pos_short_cuts")
public class ShortCuts extends BaseEntity {
    @ColumnInfo
    private String group;
    @ColumnInfo
    private int menuType;
    @ColumnInfo
    private String module;
    @ColumnInfo
    private String keyCode;

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public int getMenuType() {
        return menuType;
    }

    public void setMenuType(int menuType) {
        this.menuType = menuType;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getKeyCode() {
        return keyCode;
    }

    public void setKeyCode(String keyCode) {
        this.keyCode = keyCode;
    }

    public String getDefaultKeyCode() {
        return defaultKeyCode;
    }

    public void setDefaultKeyCode(String defaultKeyCode) {
        this.defaultKeyCode = defaultKeyCode;
    }

    @ColumnInfo
    private String defaultKeyCode;

}
