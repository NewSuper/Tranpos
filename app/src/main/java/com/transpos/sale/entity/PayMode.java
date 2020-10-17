package com.transpos.sale.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;

@Entity(tableName = "pos_pay_mode")
public class PayMode extends BaseEntity {

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
    /// 快捷键
    /// </summary>
    @ColumnInfo
    private String shortcut;

    /// <summary>
    /// 是否参与积分
    /// </summary>
    @ColumnInfo
    private int pointFlag;

    /// <summary>
    /// 前台是否启用
    /// </summary>
    @ColumnInfo
    private int frontFlag;

    /// <summary>
    /// 后台是否启用
    /// </summary>
    @ColumnInfo
    private int backFlag;

    /// <summary>
    /// 会员充值支付方式
    /// </summary>
    @ColumnInfo
    private int rechargeFlag;

    /// <summary>
    /// plus购买能否使用
    /// </summary>
    @ColumnInfo
    private int plusFlag;

    /// <summary>
    /// 面值
    /// </summary>
    @ColumnInfo
    private double faceMoney;

    /// <summary>
    /// 实收
    /// </summary>
    @ColumnInfo
    private double paidMoney;

    /// <summary>
    /// 是否实收
    /// </summary>
    @ColumnInfo
    private int incomeFlag;

    /// <summary>
    /// 删除标识
    /// </summary>
    @ColumnInfo
    private int deleteFlag;

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

    public String getShortcut() {
        return shortcut;
    }

    public void setShortcut(String shortcut) {
        this.shortcut = shortcut;
    }

    public int getPointFlag() {
        return pointFlag;
    }

    public void setPointFlag(int pointFlag) {
        this.pointFlag = pointFlag;
    }

    public int getFrontFlag() {
        return frontFlag;
    }

    public void setFrontFlag(int frontFlag) {
        this.frontFlag = frontFlag;
    }

    public int getBackFlag() {
        return backFlag;
    }

    public void setBackFlag(int backFlag) {
        this.backFlag = backFlag;
    }

    public int getRechargeFlag() {
        return rechargeFlag;
    }

    public void setRechargeFlag(int rechargeFlag) {
        this.rechargeFlag = rechargeFlag;
    }

    public int getPlusFlag() {
        return plusFlag;
    }

    public void setPlusFlag(int plusFlag) {
        this.plusFlag = plusFlag;
    }

    public double getFaceMoney() {
        return faceMoney;
    }

    public void setFaceMoney(double faceMoney) {
        this.faceMoney = faceMoney;
    }

    public double getPaidMoney() {
        return paidMoney;
    }

    public void setPaidMoney(double paidMoney) {
        this.paidMoney = paidMoney;
    }

    public int getIncomeFlag() {
        return incomeFlag;
    }

    public void setIncomeFlag(int incomeFlag) {
        this.incomeFlag = incomeFlag;
    }

    public int getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(int deleteFlag) {
        this.deleteFlag = deleteFlag;
    }
}
