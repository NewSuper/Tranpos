package com.transpos.sale.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(tableName = "pos_product_category")
public class ProductCategory extends BaseEntity {

    /// <summary>
    /// 父ID
    /// </summary>
    @ColumnInfo
    private String parentId;

    /// <summary>
    /// 分类名称
    /// </summary>
    @ColumnInfo
    private String name;

    /// <summary>
    /// 分类编码
    /// </summary>
    @ColumnInfo
    private String code;

    /// <summary>
    /// 分类路径
    /// </summary>
    @ColumnInfo
    private String path;

    /// <summary>
    /// 分类类型
    /// </summary>
    @ColumnInfo
    private int categoryType;

    /// <summary>
    /// 英文名称
    /// </summary>
    @ColumnInfo
    private String english;

    /// <summary>
    /// 提成比率
    /// </summary>
    @ColumnInfo
    private int returnRate;

    /// <summary>
    /// 备注说明
    /// </summary>
    @ColumnInfo
    private String description;

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

    /// <summary>
    /// 商品数量
    /// </summary>
    @ColumnInfo
    private int products;

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getCategoryType() {
        return categoryType;
    }

    public void setCategoryType(int categoryType) {
        this.categoryType = categoryType;
    }

    public String getEnglish() {
        return english;
    }

    public void setEnglish(String english) {
        this.english = english;
    }

    public int getReturnRate() {
        return returnRate;
    }

    public void setReturnRate(int returnRate) {
        this.returnRate = returnRate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public int getProducts() {
        return products;
    }

    public void setProducts(int products) {
        this.products = products;
    }
}
