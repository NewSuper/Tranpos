package com.newsuper.t.sale.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;

@Entity(tableName = "pos_member_point_rule_category")
public class MemberPointRuleCategory extends BaseEntity {

    /// <summary>
    /// 积分方案ID
    /// </summary>
    @ColumnInfo
    private String pointRuleId;

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
    /// 金额
    /// </summary>
    @ColumnInfo
    private double amount;

    /// <summary>
    /// 积分
    /// </summary>
    @ColumnInfo
    private double point;

    /// <summary>
    /// 备注信息
    /// </summary>
    @ColumnInfo
    private String description;

    public String getPointRuleId() {
        return pointRuleId;
    }

    public void setPointRuleId(String pointRuleId) {
        this.pointRuleId = pointRuleId;
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

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getPoint() {
        return point;
    }

    public void setPoint(double point) {
        this.point = point;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
