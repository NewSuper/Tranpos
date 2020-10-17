package com.transpos.sale.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;

import java.util.Date;

@Entity(tableName = "pos_order_promotion")
public class PromotionOrder extends BaseEntity {
    /// <summary>
    /// 订单ID
    /// </summary>
    @ColumnInfo
    private String orderId;

    /// <summary>
    /// ItemId
    /// </summary>
    @ColumnInfo
    private String itemId;

    /// <summary>
    /// 订单号
    /// </summary>
    @ColumnInfo
    private String tradeNo;

    /// <summary>
    /// 是否线上促销
    /// </summary>
    @ColumnInfo
    private int onlineFlag;

    /// <summary>
    /// 促销方案
    /// </summary>
    @ColumnInfo
    private int promotionType;

    /// <summary>
    /// 促销方案名称
    /// </summary>
    //[JsonProperty(PropertyName = "promotionName")]
    //[Column("promotionName")]
    //private String PromotionName;

    /// <summary>
    /// 促销原因
    /// </summary>
    @ColumnInfo
    private String reason;

    /// <summary>
    /// 档期ID
    /// </summary>
    @ColumnInfo
    private String scheduleId;

    /// <summary>
    /// 档期编号
    /// </summary>
    @ColumnInfo
    private String scheduleSn;

    /// <summary>
    /// 促销方案ID
    /// </summary>
    @ColumnInfo
    private String promotionId;

    /// <summary>
    /// 促销方案编号
    /// </summary>
    @ColumnInfo
    private String promotionSn;

    /// <summary>
    /// 促销模式 D:折扣；T:特价；F：买满送；
    /// </summary>
    @ColumnInfo
    private String promotionMode;

    /// <summary>
    /// 促销范围类型 A、全场；C、分类；B、品牌；P、商品；CAB、分类下的品牌；COB、分类或品牌；
    /// </summary>
    @ColumnInfo
    private String scopeType;

    /// <summary>
    /// 促销方案名称
    /// </summary>
    @ColumnInfo
    private String promotionPlan;

    /// <summary>
    /// 优惠前价格
    /// </summary>
    @ColumnInfo
    private double price;

    /// <summary>
    /// 优惠后价格
    /// </summary>
    @ColumnInfo
    private double bargainPrice;

    /// <summary>
    /// 优惠前的金额
    /// </summary>
    @ColumnInfo
    private double amount;

    /// <summary>
    /// 优惠金额
    /// </summary>
    @ColumnInfo
    private double discountAmount;

    /// <summary>
    /// 优惠后的金额
    /// </summary>
    @ColumnInfo
    private double receivableAmount;

    /// <summary>
    /// 优惠率
    /// </summary>
    @ColumnInfo
    private double discountRate;

    /// <summary>
    /// 是否启用该优惠
    /// </summary>
    @ColumnInfo
    private int enabled;

    /// <summary>
    /// 整单优惠分摊对应的关系
    /// </summary>
    @ColumnInfo
    private int relationId;

    /// <summary>
    /// 券ID
    /// </summary>
    @ColumnInfo
    private String couponId;

    /// <summary>
    /// 券号
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
    /// 订单完成时间(格式:yyyy-MM-dd HH:mm:ss)
    /// </summary>
    @ColumnInfo
    private String finishDate;

    /// <summary>
    /// 优惠截止时间(格式:yyyy-MM-dd HH:mm:ss)  做商品展示、加入购物车，判断商品特价使用，沒有入库
    /// </summary>
    @Ignore
    private Date limitDate;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }

    public int getOnlineFlag() {
        return onlineFlag;
    }

    public void setOnlineFlag(int onlineFlag) {
        this.onlineFlag = onlineFlag;
    }

    public int getPromotionType() {
        return promotionType;
    }

    public void setPromotionType(int promotionType) {
        this.promotionType = promotionType;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(String scheduleId) {
        this.scheduleId = scheduleId;
    }

    public String getScheduleSn() {
        return scheduleSn;
    }

    public void setScheduleSn(String scheduleSn) {
        this.scheduleSn = scheduleSn;
    }

    public String getPromotionId() {
        return promotionId;
    }

    public void setPromotionId(String promotionId) {
        this.promotionId = promotionId;
    }

    public String getPromotionSn() {
        return promotionSn;
    }

    public void setPromotionSn(String promotionSn) {
        this.promotionSn = promotionSn;
    }

    public String getPromotionMode() {
        return promotionMode;
    }

    public void setPromotionMode(String promotionMode) {
        this.promotionMode = promotionMode;
    }

    public String getScopeType() {
        return scopeType;
    }

    public void setScopeType(String scopeType) {
        this.scopeType = scopeType;
    }

    public String getPromotionPlan() {
        return promotionPlan;
    }

    public void setPromotionPlan(String promotionPlan) {
        this.promotionPlan = promotionPlan;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getBargainPrice() {
        return bargainPrice;
    }

    public void setBargainPrice(double bargainPrice) {
        this.bargainPrice = bargainPrice;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(double discountAmount) {
        this.discountAmount = discountAmount;
    }

    public double getReceivableAmount() {
        return receivableAmount;
    }

    public void setReceivableAmount(double receivableAmount) {
        this.receivableAmount = receivableAmount;
    }

    public double getDiscountRate() {
        return discountRate;
    }

    public void setDiscountRate(double discountRate) {
        this.discountRate = discountRate;
    }

    public int getEnabled() {
        return enabled;
    }

    public void setEnabled(int enabled) {
        this.enabled = enabled;
    }

    public int getRelationId() {
        return relationId;
    }

    public void setRelationId(int relationId) {
        this.relationId = relationId;
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

    public String getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(String finishDate) {
        this.finishDate = finishDate;
    }

    public Date getLimitDate() {
        return limitDate;
    }

    public void setLimitDate(Date limitDate) {
        this.limitDate = limitDate;
    }
}
