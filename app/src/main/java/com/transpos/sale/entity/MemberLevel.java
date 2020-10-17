package com.transpos.sale.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;

import java.util.List;

@Entity(tableName = "pos_member_level")
public class MemberLevel extends BaseEntity {
    /// <summary>
    /// 编号
    /// </summary>
    @ColumnInfo
    private String no;

    /// <summary>
    /// 名称
    /// </summary>
    @ColumnInfo
    private String name;

    /// <summary>
    /// 级别
    /// </summary>
    @ColumnInfo
    private int level;

    /// <summary>
    /// 优惠方式
    /// </summary>
    @ColumnInfo
    private int discountWay;

    /// <summary>
    /// 默认折扣
    /// </summary>
    @ColumnInfo
    private double discount;

    /// <summary>
    /// 有效期
    /// </summary>
    @ColumnInfo
    private int validDays;

    /// <summary>
    /// 条件
    /// </summary>
    @ColumnInfo
    private String conditions;

    /// <summary>
    /// 特权描述
    /// </summary>
    @ColumnInfo
    private String prerogative;

    /// <summary>
    /// 备注信息
    /// </summary>
    @ColumnInfo
    private String description;

    /// <summary>
    /// 默认标识
    /// </summary>
    @ColumnInfo
    private int defaultFlag;

    /// <summary>
    /// 是否启用
    /// </summary>
    @ColumnInfo
    private int enable;

    /// <summary>
    /// 删除标识
    /// </summary>
    @ColumnInfo
    private int deleteFlag;

    /// <summary>
    /// 升级条件
    /// </summary>
    @ColumnInfo
    private int upgradRuleType;

    /// <summary>
    /// 升级值
    /// </summary>
    @ColumnInfo
    private double upgradValue;

    /// <summary>
    /// 是否允许自动降级
    /// </summary>
    @ColumnInfo
    private int downFlag;

    /// <summary>
    /// 特定分类折扣
    /// </summary>
    @Ignore
    private List<MemberLevelCategoryDiscount> categoryDiscountList;

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

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getDiscountWay() {
        return discountWay;
    }

    public void setDiscountWay(int discountWay) {
        this.discountWay = discountWay;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public int getValidDays() {
        return validDays;
    }

    public void setValidDays(int validDays) {
        this.validDays = validDays;
    }

    public String getConditions() {
        return conditions;
    }

    public void setConditions(String conditions) {
        this.conditions = conditions;
    }

    public String getPrerogative() {
        return prerogative;
    }

    public void setPrerogative(String prerogative) {
        this.prerogative = prerogative;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDefaultFlag() {
        return defaultFlag;
    }

    public void setDefaultFlag(int defaultFlag) {
        this.defaultFlag = defaultFlag;
    }

    public int getEnable() {
        return enable;
    }

    public void setEnable(int enable) {
        this.enable = enable;
    }

    public int getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(int deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public int getUpgradRuleType() {
        return upgradRuleType;
    }

    public void setUpgradRuleType(int upgradRuleType) {
        this.upgradRuleType = upgradRuleType;
    }

    public double getUpgradValue() {
        return upgradValue;
    }

    public void setUpgradValue(double upgradValue) {
        this.upgradValue = upgradValue;
    }

    public int getDownFlag() {
        return downFlag;
    }

    public void setDownFlag(int downFlag) {
        this.downFlag = downFlag;
    }

    public List<MemberLevelCategoryDiscount> getCategoryDiscountList() {
        return categoryDiscountList;
    }

    public void setCategoryDiscountList(List<MemberLevelCategoryDiscount> categoryDiscountList) {
        this.categoryDiscountList = categoryDiscountList;
    }
}
