package com.transpos.market.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;

@Entity(tableName = "pos_worker_role")
public class WorkRole extends BaseEntity {
    @ColumnInfo
    private String userId;
    @ColumnInfo
    private String roleId;
    @ColumnInfo
    private double discount;

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public double getFreeAmount() {
        return freeAmount;
    }

    public void setFreeAmount(double freeAmount) {
        this.freeAmount = freeAmount;
    }

    @ColumnInfo
    private double freeAmount;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }


}
