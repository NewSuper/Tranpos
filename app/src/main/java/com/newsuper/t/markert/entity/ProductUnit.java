package com.newsuper.t.markert.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;

@Entity(tableName = "pos_product_unit")
public class ProductUnit extends BaseEntity {
    /**
     *编号
     */
    @ColumnInfo
    private String no;

    /**
     *名称
     */
    @ColumnInfo
    private String name;

    /**
     *删除标识
     */
    @ColumnInfo
    private Integer deleteFlag;

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(Integer deleteFlag) {
        this.deleteFlag = deleteFlag;
    }
}
