package com.newsuper.t.sale.entity;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;

@Entity(tableName = "pos_order_pay")
public class OrderPay extends BaseEntity {

    /// <summary>
    /// 单据编号
    /// </summary>
    @ColumnInfo
    private String tradeNo;

    /// <summary>
    /// 随机单号
    /// </summary>
    @ColumnInfo
    private String orderId;


    /// <summary>
    /// 原单号，退款单使用
    /// </summary>
    @ColumnInfo
    private String orgPayId;

    /// <summary>
    /// 序号
    /// </summary>
    @ColumnInfo
    private int orderNo;

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
    /// 券ID
    /// </summary>
    @ColumnInfo
    private String couponId;

    /// <summary>
    /// 每张券唯一的号码
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
    /// 应收金额 当前订单应收金额
    /// </summary>
    @ColumnInfo
    private double amount;

    /// <summary>
    /// 收银员录入金额
    /// </summary>
    @ColumnInfo
    private double inputAmount;

    /// <summary>
    /// 面值-代金券
    /// </summary>
    @ColumnInfo
    private double faceAmount;

    /// <summary>
    /// 实收金额
    /// </summary>
    @ColumnInfo
    private double paidAmount;

    /// <summary>
    /// 退款金额
    /// </summary>
    @ColumnInfo
    private double ramount;

    /// <summary>
    /// 溢收金额
    /// </summary>
    @ColumnInfo
    private double overAmount;

    /// <summary>
    /// 找零金额
    /// </summary>
    @ColumnInfo
    private double changeAmount;

    /// <summary>
    /// 券占用金额，比如满20元可用5元，这个字段就是占用的20元
    /// </summary>
    @ColumnInfo
    private double couponLeastCost;

    /// <summary>
    /// 平台优惠
    /// </summary>
    @ColumnInfo
    private double platformDiscount;

    /// <summary>
    /// 平台实付
    /// </summary>
    @ColumnInfo
    private double platformPaid;

    /// <summary>
    /// 支付单号
    /// </summary>
    @ColumnInfo
    private String payNo;

    /// <summary>
    /// 预支付单号
    /// </summary>
    @ColumnInfo
    private String prePayNo;

    /// <summary>
    /// 渠道单号
    /// </summary>
    @ColumnInfo
    private String channelNo;

    /// <summary>
    /// 最终支付平台订单号
    /// </summary>
    @ColumnInfo
    private String voucherNo;

    /// <summary>
    /// 支付状态
    /// </summary>
    @ColumnInfo
    private int status;

    /// <summary>
    /// 支付状态描述
    /// </summary>
    @ColumnInfo
    private String statusDesc;

    /// <summary>
    /// 是否订阅微信公众号
    /// </summary>
    @ColumnInfo
    private String subscribe;

    /// <summary>
    /// 用户是否确认输入密码
    /// </summary>
    @ColumnInfo
    private int useConfirmed;

    /// <summary>
    /// 支付密码
    /// </summary>
    @Ignore
    private String pwd;

    /// <summary>
    /// 平台用户名
    /// </summary>
    @ColumnInfo
    private String accountName;

    /// <summary>
    /// 付款银行
    /// </summary>
    @ColumnInfo
    private String bankType;

    /// <summary>
    /// 备注说明
    /// </summary>
    @ColumnInfo
    private String memo;


    /// <summary>
    /// 支付时间(格式:yyyy-MM-dd HH:mm:ss)
    /// </summary>
    @ColumnInfo
    private String payTime;

    /// <summary>
    /// 订单完成时间(格式:yyyy-MM-dd HH:mm:ss)
    /// </summary>
    @ColumnInfo
    private String finishDate;

    /// <summary>
    /// 支付渠道 区分各种聚合支付、原生支付
    /// </summary>
    @ColumnInfo
    private int payChannel;

    /// <summary>
    /// 是否参与积分(0否1是)
    /// </summary>
    @ColumnInfo
    private int pointFlag;

    /// <summary>
    /// 是否实收(0-否;1-是;)
    /// </summary>
    @ColumnInfo
    private int incomeFlag;

    /// <summary>
    /// 付款卡号
    /// </summary>
    @ColumnInfo
    private String cardNo;

    /// <summary>
    /// 卡支付前余额
    /// </summary>
    @ColumnInfo
    private double cardPreAmount;

    /// <summary>
    /// 刷卡后金额
    /// </summary>
    @ColumnInfo
    private double cardAftAmount;

    /// <summary>
    /// 卡变动金额
    /// </summary>
    @ColumnInfo
    private double cardChangeAmount;

    /// <summary>
    /// 卡支付前积分
    /// </summary>
    @ColumnInfo
    private double cardPrePoint;

    /// <summary>
    /// 卡变动积分
    /// </summary>
    @ColumnInfo
    private double cardChangePoint;

    /// <summary>
    /// 卡支付后积分
    /// </summary>
    @ColumnInfo
    private double cardAftPoint;

    /// <summary>
    /// 会员卡手机号
    /// </summary>
    @ColumnInfo
    private String memberMobileNo;

    /// <summary>
    /// 会员ID
    /// </summary>
    @ColumnInfo
    private String memberId;

    /// <summary>
    /// 会员卡卡面号
    /// </summary>
    @ColumnInfo
    private String cardFaceNo;

    /// <summary>
    /// 积分兑换金额比例
    /// </summary>
    @ColumnInfo
    private double pointAmountRate;

    /// <summary>
    /// 班次ID
    /// </summary>
    @ColumnInfo
    private String shiftId;

    /// <summary>
    /// 班次编号
    /// </summary>
    @ColumnInfo
    private String shiftNo;

    /// <summary>
    /// 班次名称
    /// </summary>
    @ColumnInfo
    private String shiftName;

    /// <summary>
    /// 代金券
    /// </summary>
//    private ElecCoupon coupon;

    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrgPayId() {
        return orgPayId;
    }

    public void setOrgPayId(String orgPayId) {
        this.orgPayId = orgPayId;
    }

    public int getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(int orderNo) {
        this.orderNo = orderNo;
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

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getInputAmount() {
        return inputAmount;
    }

    public void setInputAmount(double inputAmount) {
        this.inputAmount = inputAmount;
    }

    public double getFaceAmount() {
        return faceAmount;
    }

    public void setFaceAmount(double faceAmount) {
        this.faceAmount = faceAmount;
    }

    public double getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(double paidAmount) {
        this.paidAmount = paidAmount;
    }

    public double getRamount() {
        return ramount;
    }

    public void setRamount(double ramount) {
        this.ramount = ramount;
    }

    public double getOverAmount() {
        return overAmount;
    }

    public void setOverAmount(double overAmount) {
        this.overAmount = overAmount;
    }

    public double getChangeAmount() {
        return changeAmount;
    }

    public void setChangeAmount(double changeAmount) {
        this.changeAmount = changeAmount;
    }

    public double getCouponLeastCost() {
        return couponLeastCost;
    }

    public void setCouponLeastCost(double couponLeastCost) {
        this.couponLeastCost = couponLeastCost;
    }

    public double getPlatformDiscount() {
        return platformDiscount;
    }

    public void setPlatformDiscount(double platformDiscount) {
        this.platformDiscount = platformDiscount;
    }

    public double getPlatformPaid() {
        return platformPaid;
    }

    public void setPlatformPaid(double platformPaid) {
        this.platformPaid = platformPaid;
    }

    public String getPayNo() {
        return payNo;
    }

    public void setPayNo(String payNo) {
        this.payNo = payNo;
    }

    public String getPrePayNo() {
        return prePayNo;
    }

    public void setPrePayNo(String prePayNo) {
        this.prePayNo = prePayNo;
    }

    public String getChannelNo() {
        return channelNo;
    }

    public void setChannelNo(String channelNo) {
        this.channelNo = channelNo;
    }

    public String getVoucherNo() {
        return voucherNo;
    }

    public void setVoucherNo(String voucherNo) {
        this.voucherNo = voucherNo;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getStatusDesc() {
        return statusDesc;
    }

    public void setStatusDesc(String statusDesc) {
        this.statusDesc = statusDesc;
    }

    public String getSubscribe() {
        return subscribe;
    }

    public void setSubscribe(String subscribe) {
        this.subscribe = subscribe;
    }

    public int getUseConfirmed() {
        return useConfirmed;
    }

    public void setUseConfirmed(int useConfirmed) {
        this.useConfirmed = useConfirmed;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getBankType() {
        return bankType;
    }

    public void setBankType(String bankType) {
        this.bankType = bankType;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getPayTime() {
        return payTime;
    }

    public void setPayTime(String payTime) {
        this.payTime = payTime;
    }

    public String getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(String finishDate) {
        this.finishDate = finishDate;
    }

    public int getPayChannel() {
        return payChannel;
    }

    public void setPayChannel(int payChannel) {
        this.payChannel = payChannel;
    }

    public int getPointFlag() {
        return pointFlag;
    }

    public void setPointFlag(int pointFlag) {
        this.pointFlag = pointFlag;
    }

    public int getIncomeFlag() {
        return incomeFlag;
    }

    public void setIncomeFlag(int incomeFlag) {
        this.incomeFlag = incomeFlag;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public double getCardPreAmount() {
        return cardPreAmount;
    }

    public void setCardPreAmount(double cardPreAmount) {
        this.cardPreAmount = cardPreAmount;
    }

    public double getCardAftAmount() {
        return cardAftAmount;
    }

    public void setCardAftAmount(double cardAftAmount) {
        this.cardAftAmount = cardAftAmount;
    }

    public double getCardChangeAmount() {
        return cardChangeAmount;
    }

    public void setCardChangeAmount(double cardChangeAmount) {
        this.cardChangeAmount = cardChangeAmount;
    }

    public double getCardPrePoint() {
        return cardPrePoint;
    }

    public void setCardPrePoint(double cardPrePoint) {
        this.cardPrePoint = cardPrePoint;
    }

    public double getCardChangePoint() {
        return cardChangePoint;
    }

    public void setCardChangePoint(double cardChangePoint) {
        this.cardChangePoint = cardChangePoint;
    }

    public double getCardAftPoint() {
        return cardAftPoint;
    }

    public void setCardAftPoint(double cardAftPoint) {
        this.cardAftPoint = cardAftPoint;
    }

    public String getMemberMobileNo() {
        return memberMobileNo;
    }

    public void setMemberMobileNo(String memberMobileNo) {
        this.memberMobileNo = memberMobileNo;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getCardFaceNo() {
        return cardFaceNo;
    }

    public void setCardFaceNo(String cardFaceNo) {
        this.cardFaceNo = cardFaceNo;
    }

    public double getPointAmountRate() {
        return pointAmountRate;
    }

    public void setPointAmountRate(double pointAmountRate) {
        this.pointAmountRate = pointAmountRate;
    }

    public String getShiftId() {
        return shiftId;
    }

    public void setShiftId(String shiftId) {
        this.shiftId = shiftId;
    }

    public String getShiftNo() {
        return shiftNo;
    }

    public void setShiftNo(String shiftNo) {
        this.shiftNo = shiftNo;
    }

    public String getShiftName() {
        return shiftName;
    }

    public void setShiftName(String shiftName) {
        this.shiftName = shiftName;
    }

//    public ElecCoupon getCoupon() {
//        return coupon;
//    }
//
//    public void setCoupon(ElecCoupon coupon) {
//        this.coupon = coupon;
//    }
}
