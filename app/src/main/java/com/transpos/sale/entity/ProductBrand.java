package com.transpos.sale.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(tableName = "pos_product_brand")
public class ProductBrand extends BaseEntity {
    /// <summary>
    /// 品牌名称
    /// </summary>
    @ColumnInfo
    private String name;

    /// <summary>
    /// 商品数量
    /// </summary>
    @ColumnInfo
    private int products;

    /// <summary>
    /// 提成比率
    /// </summary>
    @ColumnInfo
    private int returnRate;

    /// <summary>
    /// 存储类型
    /// </summary>
    @ColumnInfo
    private int storageType;

    /// <summary>
    /// logo存储地址
    /// </summary>
    @ColumnInfo
    private String storageAddress;

    /// <summary>
    /// 排序
    /// </summary>
    @ColumnInfo
    private int orderNo;

    /// <summary>
    /// 删除标识
    /// </summary>
    @ColumnInfo
    private int deleteFlag;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getProducts() {
        return products;
    }

    public void setProducts(int products) {
        this.products = products;
    }

    public int getReturnRate() {
        return returnRate;
    }

    public void setReturnRate(int returnRate) {
        this.returnRate = returnRate;
    }

    public int getStorageType() {
        return storageType;
    }

    public void setStorageType(int storageType) {
        this.storageType = storageType;
    }

    public String getStorageAddress() {
        return storageAddress;
    }

    public void setStorageAddress(String storageAddress) {
        this.storageAddress = storageAddress;
    }

    public int getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(int orderNo) {
        this.orderNo = orderNo;
    }

    public int getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(int deleteFlag) {
        this.deleteFlag = deleteFlag;
    }
}
