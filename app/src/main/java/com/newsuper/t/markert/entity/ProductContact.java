package com.newsuper.t.markert.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;

@Entity(tableName = "pos_product_contact")
public class ProductContact extends BaseEntity {
    /// <summary>
    /// 主商品ID
    /// </summary>
    @ColumnInfo
    private String masterId;

    /// <summary>
    /// 主商品规格ID
    /// </summary>
    @ColumnInfo
    private String masterSpecId;

    /// <summary>
    /// 子商品ID
    /// </summary>
    @ColumnInfo
    private String slaveId;

    /// <summary>
    /// 子商品规格ID
    /// </summary>
    @ColumnInfo
    private String slaveSpecId;

    /// <summary>
    /// 子商品数量
    /// </summary>
    @ColumnInfo
    private double slaveNum;

    /// <summary>
    /// 删除标识
    /// </summary>
    @ColumnInfo
    private int deleteFlag;

    /// <summary>
    /// 排序
    /// </summary>
    @ColumnInfo
    private int orderNo;

    public String getMasterId() {
        return masterId;
    }

    public void setMasterId(String masterId) {
        this.masterId = masterId;
    }

    public String getMasterSpecId() {
        return masterSpecId;
    }

    public void setMasterSpecId(String masterSpecId) {
        this.masterSpecId = masterSpecId;
    }

    public String getSlaveId() {
        return slaveId;
    }

    public void setSlaveId(String slaveId) {
        this.slaveId = slaveId;
    }

    public String getSlaveSpecId() {
        return slaveSpecId;
    }

    public void setSlaveSpecId(String slaveSpecId) {
        this.slaveSpecId = slaveSpecId;
    }

    public double getSlaveNum() {
        return slaveNum;
    }

    public void setSlaveNum(double slaveNum) {
        this.slaveNum = slaveNum;
    }

    public int getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(int deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public int getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(int orderNo) {
        this.orderNo = orderNo;
    }
}
