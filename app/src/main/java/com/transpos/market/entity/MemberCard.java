package com.transpos.market.entity;


public class MemberCard {

    /// <summary>
    /// 卡类型ID
    /// </summary>
    private String cardTypeId;

    /// <summary>
    /// 卡类型编号
    /// </summary>
    private String cardTypeNo;

    /// <summary>
    /// 卡类型名称
    /// </summary>
    private String cardTypeName;

    /// <summary>
    /// 会员卡号
    /// </summary>
    private String cardNo;

    /// <summary>
    /// 卡面号
    /// </summary>
    private String cardFaceNo;

    /// <summary>
    /// 是否积分  0-否 1-是
    /// </summary>
    private int isConsumePoint;

    /// <summary>
    /// 是否储值  0-否 1-是
    /// </summary>
    private int isStoredValue;

    /// <summary>
    /// 手机号
    /// </summary>
    private String mobile;

    /// <summary>
    /// 小额免密支付
    /// </summary>
    private int isNoPwd;

    /// <summary>
    /// 免密金额
    /// </summary>
    private double npAmount;

    /// <summary>
    /// 保底金额
    /// </summary>
    private double baseAmount;

    /// <summary>
    /// 总余额
    /// </summary>
    private double totalAmount;

    /// <summary>
    /// 实收剩余金额
    /// </summary>
    private double globalAmount;

    /// <summary>
    /// 赠送剩余金额
    /// </summary>
    private double giftAmount;

    /// <summary>
    /// 冻结余额
    /// </summary>
    private double stageAmount;

    /// <summary>
    /// 累计充值总金额
    /// </summary>
    private double rechargeAmount;

    /// <summary>
    /// 累计充值赠送金额
    /// </summary>
    private double rechargeGiftAmount;

    /// <summary>
    /// 累计消费总金额
    /// </summary>
    private double consumeAmount;

    /// <summary>
    /// 累计充值次数
    /// </summary>
    private int rechargeCount;

    /// <summary>
    /// 累计消费次数
    /// </summary>
    private int consumeCount;

    /// <summary>
    /// 原卡号
    /// </summary>
    private String oldCardNo;

    /// <summary>
    /// 状态
    /// </summary>
    private int status;

    /// <summary>
    /// 卡介质 1-电子卡 2-磁卡或条码卡 3-IC卡
    /// </summary>
    private int cardMedium;

    /// <summary>
    /// 0-新建 1-正常(开户)  2-预售  3-挂失 4-冻结  5--销户 10-转赠中
    /// </summary>
    private String statusDesc;

    /// <summary>
    /// 开户门店编号
    /// </summary>
    private String shopNo;

    /// <summary>
    /// 开户门店名称
    /// </summary>
    private String shopName;

    /// <summary>
    /// 开户门店ID
    /// </summary>
    private String shopId;

    /// <summary>
    /// 员工工号
    /// </summary>
    private String workerNo;

    /// <summary>
    /// pos
    /// </summary>
    private String posNo;

    /// <summary>
    /// 备注说明
    /// </summary>
    private String description;

    /// <summary>
    /// 错误次数
    /// </summary>
    private int countWrong;

    /// <summary>
    /// 卡有效期
    /// </summary>
    private String validDate;

    /// <summary>
    /// 限用次数
    /// </summary>
    private int limitCount;

    /// <summary>
    /// 来源标识
    /// </summary>
    private String sourceSign;


    public String getCardTypeId() {
        return cardTypeId;
    }

    public void setCardTypeId(String cardTypeId) {
        this.cardTypeId = cardTypeId;
    }

    public String getCardTypeNo() {
        return cardTypeNo;
    }

    public void setCardTypeNo(String cardTypeNo) {
        this.cardTypeNo = cardTypeNo;
    }

    public String getCardTypeName() {
        return cardTypeName;
    }

    public void setCardTypeName(String cardTypeName) {
        this.cardTypeName = cardTypeName;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getCardFaceNo() {
        return cardFaceNo;
    }

    public void setCardFaceNo(String cardFaceNo) {
        this.cardFaceNo = cardFaceNo;
    }

    public int getIsConsumePoint() {
        return isConsumePoint;
    }

    public void setIsConsumePoint(int isConsumePoint) {
        this.isConsumePoint = isConsumePoint;
    }

    public int getIsStoredValue() {
        return isStoredValue;
    }

    public void setIsStoredValue(int isStoredValue) {
        this.isStoredValue = isStoredValue;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public int getIsNoPwd() {
        return isNoPwd;
    }

    public void setIsNoPwd(int isNoPwd) {
        this.isNoPwd = isNoPwd;
    }

    public double getNpAmount() {
        return npAmount;
    }

    public void setNpAmount(double npAmount) {
        this.npAmount = npAmount;
    }

    public double getBaseAmount() {
        return baseAmount;
    }

    public void setBaseAmount(double baseAmount) {
        this.baseAmount = baseAmount;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public double getGlobalAmount() {
        return globalAmount;
    }

    public void setGlobalAmount(double globalAmount) {
        this.globalAmount = globalAmount;
    }

    public double getGiftAmount() {
        return giftAmount;
    }

    public void setGiftAmount(double giftAmount) {
        this.giftAmount = giftAmount;
    }

    public double getStageAmount() {
        return stageAmount;
    }

    public void setStageAmount(double stageAmount) {
        this.stageAmount = stageAmount;
    }

    public double getRechargeAmount() {
        return rechargeAmount;
    }

    public void setRechargeAmount(double rechargeAmount) {
        this.rechargeAmount = rechargeAmount;
    }

    public double getRechargeGiftAmount() {
        return rechargeGiftAmount;
    }

    public void setRechargeGiftAmount(double rechargeGiftAmount) {
        this.rechargeGiftAmount = rechargeGiftAmount;
    }

    public double getConsumeAmount() {
        return consumeAmount;
    }

    public void setConsumeAmount(double consumeAmount) {
        this.consumeAmount = consumeAmount;
    }

    public int getRechargeCount() {
        return rechargeCount;
    }

    public void setRechargeCount(int rechargeCount) {
        this.rechargeCount = rechargeCount;
    }

    public int getConsumeCount() {
        return consumeCount;
    }

    public void setConsumeCount(int consumeCount) {
        this.consumeCount = consumeCount;
    }

    public String getOldCardNo() {
        return oldCardNo;
    }

    public void setOldCardNo(String oldCardNo) {
        this.oldCardNo = oldCardNo;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getCardMedium() {
        return cardMedium;
    }

    public void setCardMedium(int cardMedium) {
        this.cardMedium = cardMedium;
    }

    public String getStatusDesc() {
        return statusDesc;
    }

    public void setStatusDesc(String statusDesc) {
        this.statusDesc = statusDesc;
    }

    public String getShopNo() {
        return shopNo;
    }

    public void setShopNo(String shopNo) {
        this.shopNo = shopNo;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getWorkerNo() {
        return workerNo;
    }

    public void setWorkerNo(String workerNo) {
        this.workerNo = workerNo;
    }

    public String getPosNo() {
        return posNo;
    }

    public void setPosNo(String posNo) {
        this.posNo = posNo;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCountWrong() {
        return countWrong;
    }

    public void setCountWrong(int countWrong) {
        this.countWrong = countWrong;
    }

    public String getValidDate() {
        return validDate;
    }

    public void setValidDate(String validDate) {
        this.validDate = validDate;
    }

    public int getLimitCount() {
        return limitCount;
    }

    public void setLimitCount(int limitCount) {
        this.limitCount = limitCount;
    }

    public String getSourceSign() {
        return sourceSign;
    }

    public void setSourceSign(String sourceSign) {
        this.sourceSign = sourceSign;
    }
}
