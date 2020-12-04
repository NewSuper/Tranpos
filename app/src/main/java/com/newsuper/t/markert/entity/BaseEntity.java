package com.newsuper.t.markert.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.io.Serializable;


public abstract class BaseEntity implements Serializable {

    /**
     * 本地主键
     */
    @NonNull
    @PrimaryKey
    private String id;

    /**
     * 租户
     */
    @ColumnInfo
    private String tenantId;
    @ColumnInfo
    private String ext1;
    @ColumnInfo
    private String ext2;
    @ColumnInfo
    private String ext3;

    /**
     * 创建人
     */
    @ColumnInfo
    private String createUser;

    /**
     * 创建日期
     */
    @ColumnInfo
    private String createDate;

    /**
     * 修改人
     */
    @ColumnInfo
    private String modifyUser;

    /**
     * 修改日期
     */
    @ColumnInfo
    private String modifyDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getExt1() {
        return ext1;
    }

    public void setExt1(String ext1) {
        this.ext1 = ext1;
    }

    public String getExt2() {
        return ext2;
    }

    public void setExt2(String ext2) {
        this.ext2 = ext2;
    }

    public String getExt3() {
        return ext3;
    }

    public void setExt3(String ext3) {
        this.ext3 = ext3;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getModifyUser() {
        return modifyUser;
    }

    public void setModifyUser(String modifyUser) {
        this.modifyUser = modifyUser;
    }

    public String getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(String modifyDate) {
        this.modifyDate = modifyDate;
    }
}
