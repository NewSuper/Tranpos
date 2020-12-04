package com.newsuper.t.markert.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;

@Entity(tableName = "pos_kit_plan_product")
public class PlanProduct extends BaseEntity{

    @ColumnInfo
    private String productId;

    @ColumnInfo
    private int chudaFlag;

    @ColumnInfo
    private String chuda;

    @ColumnInfo
    private int chupinFlag;

    @ColumnInfo
    private String chupin;

    @ColumnInfo
    private int labelFlag;

    @ColumnInfo
    private String labelValue;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public int getChudaFlag() {
        return chudaFlag;
    }

    public void setChudaFlag(int chudaFlag) {
        this.chudaFlag = chudaFlag;
    }

    public String getChuda() {
        return chuda;
    }

    public void setChuda(String chuda) {
        this.chuda = chuda;
    }

    public int getChupinFlag() {
        return chupinFlag;
    }

    public void setChupinFlag(int chupinFlag) {
        this.chupinFlag = chupinFlag;
    }

    public String getChupin() {
        return chupin;
    }

    public void setChupin(String chupin) {
        this.chupin = chupin;
    }

    public int getLabelFlag() {
        return labelFlag;
    }

    public void setLabelFlag(int labelFlag) {
        this.labelFlag = labelFlag;
    }

    public String getLabelValue() {
        return labelValue;
    }

    public void setLabelValue(String labelValue) {
        this.labelValue = labelValue;
    }
}
