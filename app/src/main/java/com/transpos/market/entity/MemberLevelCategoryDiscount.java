package com.transpos.market.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;

/**
 * 会员等级品类折扣规则
 */
@Entity(tableName = "pos_member_level_category_discount")
public class MemberLevelCategoryDiscount extends BaseEntity {

    /// <summary>
    /// 等级ID
    /// </summary>
    @ColumnInfo
    private String levelId;

    /// <summary>
    /// 等级编码
    /// </summary>
    @ColumnInfo
    private String levelNo;

    /// <summary>
    /// 类别编号
    /// </summary>
    @ColumnInfo
    private String categoryId;

    /// <summary>
    /// 类别编号
    /// </summary>
    @ColumnInfo
    private String no;

    /// <summary>
    /// 类别名称
    /// </summary>
    @ColumnInfo
    private String name;

    /// <summary>
    /// 折扣值
    /// </summary>
    @ColumnInfo
    private double discount;

    public String getLevelId() {
        return levelId;
    }

    public void setLevelId(String levelId) {
        this.levelId = levelId;
    }

    public String getLevelNo() {
        return levelNo;
    }

    public void setLevelNo(String levelNo) {
        this.levelNo = levelNo;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

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

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }
}
