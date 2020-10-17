package com.transpos.sale.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;

@Entity(tableName = "pos_order_item_pay")
public class OrderItemPay extends BaseEntity {

    /// <summary>
    /// 订单ID
    /// </summary>
    @ColumnInfo
    private String orderId;


    /// <summary>
    /// 订单号
    /// </summary>
    @ColumnInfo
    private String tradeNo;

    /// <summary>
    /// 订单明细ID
    /// </summary>
    @ColumnInfo
    private String itemId;

    /// <summary>
    /// 商品ID
    /// </summary>
    @ColumnInfo
    private String productId;

    /// <summary>
    /// 规格ID
    /// </summary>
    @ColumnInfo
    private String specId;

    /// <summary>
    /// 支付记录ID
    /// </summary>
    @ColumnInfo
    private String payId;

    /// <summary>
    /// 付款方式编号
    /// </summary>
    @ColumnInfo
    private String no;

    /// <summary>
    /// 付款方式名称
    /// </summary>
    @ColumnInfo
    private String name;

    /// <summary>
    /// 券ID 方案ID
    /// </summary>
    @ColumnInfo
    private String couponId;

    /// <summary>
    /// 每张唯一的号码
    /// </summary>
    @ColumnInfo
    private String couponNo;

    /// <summary>
    /// 电子券来源(plus :plus会员 ，weixin:微信)
    /// </summary>
    @ColumnInfo
    private String sourceSign;

    /// <summary>
    /// 券名称
    /// </summary>
    @ColumnInfo
    private String couponName;

    /// <summary>
    /// 面值-代金券
    /// </summary>
    @ColumnInfo
    private double faceAmount;

    /// <summary>
    /// 分摊的券占用金额，比如满20元可用5元，这个字段就是分摊占用的20元，因为这个是要排除后才能够计算，剩余可用券金额
    /// </summary>
    @ColumnInfo
    private double shareCouponLeastCost;

    /// <summary>
    /// 分摊金额
    /// </summary>
    @ColumnInfo
    private double shareAmount;

    /// <summary>
    /// 退款金额
    /// </summary>
    @ColumnInfo
    private double ramount;

    /// <summary>
    /// 订单完成时间(格式:yyyy-MM-dd HH:mm:ss)
    /// </summary>
    @ColumnInfo
    private String finishDate;

    @ColumnInfo
    public double average;

    @ColumnInfo
    public double endAverage;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getSpecId() {
        return specId;
    }

    public void setSpecId(String specId) {
        this.specId = specId;
    }

    public String getPayId() {
        return payId;
    }

    public void setPayId(String payId) {
        this.payId = payId;
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

    public String getCouponId() {
        return couponId;
    }

    public void setCouponId(String couponId) {
        this.couponId = couponId;
    }

    public String getCouponNo() {
        return couponNo;
    }

    public void setCouponNo(String couponNo) {
        this.couponNo = couponNo;
    }

    public String getSourceSign() {
        return sourceSign;
    }

    public void setSourceSign(String sourceSign) {
        this.sourceSign = sourceSign;
    }

    public String getCouponName() {
        return couponName;
    }

    public void setCouponName(String couponName) {
        this.couponName = couponName;
    }

    public double getFaceAmount() {
        return faceAmount;
    }

    public void setFaceAmount(double faceAmount) {
        this.faceAmount = faceAmount;
    }

    public double getShareCouponLeastCost() {
        return shareCouponLeastCost;
    }

    public void setShareCouponLeastCost(double shareCouponLeastCost) {
        this.shareCouponLeastCost = shareCouponLeastCost;
    }

    public double getShareAmount() {
        return shareAmount;
    }

    public void setShareAmount(double shareAmount) {
        this.shareAmount = shareAmount;
    }

    public double getRamount() {
        return ramount;
    }

    public void setRamount(double ramount) {
        this.ramount = ramount;
    }

    public String getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(String finishDate) {
        this.finishDate = finishDate;
    }
}
