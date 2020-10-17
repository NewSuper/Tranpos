package com.transpos.sale.entity;



import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;

import com.transpos.sale.utils.StringUtils;

import java.util.List;
@Entity(tableName = "pos_order")
public class OrderObject extends BaseEntity {

    /// <summary>
    /// 订单内部标识
    /// </summary>'
    @ColumnInfo
    private String objectId = "";

    /// <summary>
    /// 单据编号
    /// </summary>
    @ColumnInfo
    private String tradeNo = "";

    /// <summary>
    /// 订单序号
    /// </summary>
    @ColumnInfo
    private String orderNo = "";

    /// <summary>
    /// 门店ID
    /// </summary>
    @ColumnInfo
    private String storeId = "";

    /// <summary>
    /// 门店编号
    /// </summary>
    @ColumnInfo
    private String storeNo = "";

    /// <summary>
    /// 门店名称
    /// </summary>
    @ColumnInfo
    private String storeName = "";

    /// <summary>
    /// 操作员工号
    /// </summary>
    @ColumnInfo
    private String workerNo = "";

    /// <summary>
    /// 操作员名称
    /// </summary>
    @ColumnInfo
    private String workerName = "";

    /// <summary>
    /// POS
    /// </summary>
    @ColumnInfo
    private String posNo = "";

    /// <summary>
    /// 收银设备名称
    /// </summary>
    @ColumnInfo
    private String deviceName = "";

    /// <summary>
    /// 收银设备Mac地址
    /// </summary>
    @ColumnInfo
    private String macAddress = "";

    /// <summary>
    /// 收银设备IP地址
    /// </summary>
    @ColumnInfo
    private String ipAddress = "";

    /// <summary>
    /// 营业员编码
    /// </summary>
    @ColumnInfo
    private String salesCode = "";

    /// <summary>
    /// 营业员名称
    /// </summary>
    @ColumnInfo
    private String salesName = "";

    /// <summary>
    /// 餐桌号
    /// </summary>
    @ColumnInfo
    private String tableNo = "";

    /// <summary>
    /// 餐桌名称
    /// </summary>
    @ColumnInfo
    private String tableName = "";

    /// <summary>
    /// 人数
    /// </summary>
    @ColumnInfo
    private int people;

    /// <summary>
    /// 班次ID
    /// </summary>
    @ColumnInfo
    private String shiftId = "";

    /// <summary>
    /// 班次编号
    /// </summary>
    @ColumnInfo
    private String shiftNo = "";

    /// <summary>
    /// 班次名称
    /// </summary>
    @ColumnInfo
    private String shiftName = "";

    /// <summary>
    /// 销售订单创建时间
    /// </summary>
    @ColumnInfo
    private String saleDate = "";

    /// <summary>
    /// 订单完成时间(格式:yyyy-MM-dd HH:mm:ss)
    /// </summary>
    @ColumnInfo
    private String finishDate = "";

    /// <summary>
    /// 星期
    /// </summary>
    @ColumnInfo
    private String weeker = "";

    /// <summary>
    /// 天气
    /// </summary>
    @ColumnInfo
    private String weather = "";

    /// <summary>
    /// 整单的数量合计，不包含套餐明细
    /// </summary>
    @ColumnInfo
    private double totalQuantity;

    /// <summary>
    /// 连续称重明细
    /// </summary>
    @Ignore
    private List<OrderItem> weightContinueItems;

    /// <summary>
    /// 订单项列表
    /// </summary>
    @Ignore
    private List<OrderItem> items;

    /// <summary>
    /// 订单明细行数
    /// </summary>
    @ColumnInfo
    private int itemCount;

    /// <summary>
    /// 支付明细行数
    /// </summary>
    @ColumnInfo
    private int payCount;

    /// <summary>
    /// 支付明细
    /// </summary>
    @Ignore
    private List<OrderPay> pays;

    /// <summary>
    /// 整单的消费金额，不包含套餐明细
    /// </summary>
    @ColumnInfo
    private double amount;

    /// <summary>
    /// 整单的优惠金额，不包含套餐明细
    /// </summary>
    @ColumnInfo
    private double discountAmount;


    /// <summary>
    /// 应收金额 = 消费金额 - 优惠金额
    /// </summary>
    @ColumnInfo
    private double receivableAmount;

    /// <summary>
    /// 券后应收
    /// </summary>
    @ColumnInfo
    private double receivableRemoveCouponAmount;

    /// <summary>
    /// 实收金额 = 应收金额 - 抹零金额
    /// </summary>
    @ColumnInfo
    private double paidAmount;

    /// <summary>
    /// 已收金额，各种支付明细的实收金额合计
    /// </summary>
    @ColumnInfo
    private double receivedAmount;

    /// <summary>
    /// 抹零金额
    /// </summary>
    @ColumnInfo
    private double malingAmount;

    /// <summary>
    /// 找零金额
    /// </summary>
    @ColumnInfo
    private double changeAmount;

    /// <summary>
    /// 开票金额
    /// </summary>
    @ColumnInfo
    private double invoicedAmount;

    /// <summary>
    /// 溢收金额
    /// </summary>
    @ColumnInfo
    private double overAmount;

    /// <summary>
    /// plus优惠金额
    /// </summary>
    @ColumnInfo
    private double plusDiscountAmount;

    /// <summary>
    /// 订单优惠列表
    /// </summary>
    @Ignore
    private List<PromotionOrder> promotions;

    /// <summary>
    /// 整单参与的促销，这里指后台促销单
    /// </summary>
    @Ignore
    private List<PromotionOrder> attendPromotions;

    /// <summary>
    /// 总优惠率
    /// </summary>
    @ColumnInfo
    private double discountRate = 0;

    /// <summary>
    /// 配送方式
    /// </summary>
    @ColumnInfo
    private int postWay;

    /// <summary>
    /// 订单来源
    /// </summary>
    @ColumnInfo
    private int orderSource;

    /// <summary>
    /// 订单状态
    /// </summary>
    @ColumnInfo
    private int orderStatus;

    /// <summary>
    /// 订单支付状态
    /// </summary>
    @ColumnInfo
    private int paymentStatus;

    /// <summary>
    /// 打印状态
    /// </summary>
    @ColumnInfo
    private int printStatus;

    /// <summary>
    /// 打印次数
    /// </summary>
    @ColumnInfo
    private int printTimes;

    /// <summary>
    /// 原单号
    /// </summary>
    @Ignore
    private String noOrg = "";

    /// <summary>
    /// 退单原因
    /// </summary>
    @Ignore
    private String backCause = "";
    @Ignore
    private Member member;

    /// <summary>
    /// 是否使用会员卡(0否1是)
    /// </summary>
    @ColumnInfo
    private int isMember;

    /// <summary>
    /// 会员Id
    /// </summary>
    @ColumnInfo
    private String memberId = "";

    /// <summary>
    /// 会员卡号
    /// </summary>
    @ColumnInfo
    private String memberNo = "";

    /// <summary>
    /// 会员名称
    /// </summary>
    @ColumnInfo
    private String memberName = "";

    /// <summary>
    /// 会员手机号
    /// </summary>
    @ColumnInfo
    private String memberMobileNo = "";

    /// <summary>
    /// 会员卡面号
    /// </summary>
    @ColumnInfo
    private String cardFaceNo = "";

    /// <summary>
    /// 消费前积分
    /// </summary>
    @ColumnInfo
    private double prePoint;

    /// <summary>
    /// 本单积分
    /// </summary>
    @ColumnInfo
    private double addPoint;

    /// <summary>
    /// 已退积分，原单使用
    /// </summary>
    @ColumnInfo
    private double refundPoint;

    /// <summary>
    /// 消费后积分
    /// </summary>
    @ColumnInfo
    private double aftPoint;



    /// <summary>
    /// 单独增加积分的卡余额，如果使用了会员卡支付，不能使用该字段
    /// </summary>
    @ColumnInfo
    private double aftAmount;

    /// <summary>
    /// 数据上传状态
    /// </summary>
    @ColumnInfo
    private int uploadStatus;

    /// <summary>
    /// 数据上传错误次数
    /// </summary>
    @ColumnInfo
    private int uploadErrors;

    /// <summary>
    /// 数据上传消息说明
    /// </summary>
    @ColumnInfo
    private String uploadMessage = "";

    /// <summary>
    /// 数据上传返回码
    /// </summary>
    @ColumnInfo
    private String uploadCode = "";

    /// <summary>
    /// 上传成功后服务端ID
    /// </summary>
    @ColumnInfo
    private String serverId = "";

    /// <summary>
    /// 数据上传成功的时间
    /// </summary>
    @ColumnInfo
    private String uploadTime = "";

    /// <summary>
    /// 单注
    /// </summary>
    @ColumnInfo
    private String remark = "";

    /// <summary>
    /// 是否为plus会员 0-否 1-是
    /// </summary>
    @ColumnInfo
    private int isPlus;

    /// <summary>
    /// 优惠券
    /// </summary>
//    private List<ElecCoupon> couponList;

    /// <summary>
    /// 用于记录当前收银界面的模式(收银模式、调价模式、练习模式、快速盘点)
    /// </summary>
    @Ignore
    private String menuItemMode = "";

    /// <summary>
    /// 重打标识
    /// </summary>
    @Ignore
    private boolean rePrint;

    /**
     * 新增字段
     * @return
     */
    @ColumnInfo
    public int freightAmount;
    @ColumnInfo
    public String orgTradeNo = "";
    @ColumnInfo
    public String refundCause = "";

//    public GlobalEnums.CashierAction cashierAction;

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getStoreNo() {
        return storeNo;
    }

    public void setStoreNo(String storeNo) {
        this.storeNo = storeNo;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getWorkerNo() {
        return workerNo;
    }

    public void setWorkerNo(String workerNo) {
        this.workerNo = workerNo;
    }

    public String getWorkerName() {
        return workerName;
    }

    public void setWorkerName(String workerName) {
        this.workerName = workerName;
    }

    public String getPosNo() {
        return posNo;
    }

    public void setPosNo(String posNo) {
        this.posNo = posNo;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getSalesCode() {
        return salesCode;
    }

    public void setSalesCode(String salesCode) {
        this.salesCode = salesCode;
    }

    public String getSalesName() {
        return salesName;
    }

    public void setSalesName(String salesName) {
        this.salesName = salesName;
    }

    public String getTableNo() {
        return tableNo;
    }

    public void setTableNo(String tableNo) {
        this.tableNo = tableNo;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public int getPeople() {
        return people;
    }

    public void setPeople(int people) {
        this.people = people;
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

    public String getSaleDate() {
        return saleDate;
    }

    public void setSaleDate(String saleDate) {
        this.saleDate = saleDate;
    }

    public String getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(String finishDate) {
        this.finishDate = finishDate;
    }

    public String getWeeker() {
        return weeker;
    }

    public void setWeeker(String weeker) {
        this.weeker = weeker;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public double getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(double totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public List<OrderItem> getWeightContinueItems() {
        return weightContinueItems;
    }

    public void setWeightContinueItems(List<OrderItem> weightContinueItems) {
        this.weightContinueItems = weightContinueItems;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public void setItems(List<OrderItem> items) {
        this.items = items;
    }

    public int getItemCount() {
        return itemCount;
    }

    public void setItemCount(int itemCount) {
        this.itemCount = itemCount;
    }

    public int getPayCount() {
        return payCount;
    }

    public void setPayCount(int payCount) {
        this.payCount = payCount;
    }

    public List<OrderPay> getPays() {
        return pays;
    }

    public void setPays(List<OrderPay> pays) {
        this.pays = pays;
    }

    public double getAmount() {
        return StringUtils.Companion.formatPriceDouble((float) amount);
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getDiscountAmount() {
        return StringUtils.Companion.formatPriceDouble((float)discountAmount);
    }

    public void setDiscountAmount(double discountAmount) {
        this.discountAmount = discountAmount;
    }

    public double getReceivableAmount() {
        return StringUtils.Companion.formatPriceDouble((float) receivableAmount);
    }

    public void setReceivableAmount(double receivableAmount) {
        this.receivableAmount = receivableAmount;
    }

    public double getReceivableRemoveCouponAmount() {
        return receivableRemoveCouponAmount;
    }

    public void setReceivableRemoveCouponAmount(double receivableRemoveCouponAmount) {
        this.receivableRemoveCouponAmount = receivableRemoveCouponAmount;
    }

    public double getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(double paidAmount) {
        this.paidAmount = paidAmount;
    }

    public double getReceivedAmount() {
        return StringUtils.Companion.formatPriceDouble((float) receivedAmount);
    }

    public void setReceivedAmount(double receivedAmount) {
        this.receivedAmount = receivedAmount;
    }

    public double getMalingAmount() {
        return malingAmount;
    }

    public void setMalingAmount(double malingAmount) {
        this.malingAmount = malingAmount;
    }

    public double getChangeAmount() {
        return changeAmount;
    }

    public void setChangeAmount(double changeAmount) {
        this.changeAmount = changeAmount;
    }

    public double getInvoicedAmount() {
        return invoicedAmount;
    }

    public void setInvoicedAmount(double invoicedAmount) {
        this.invoicedAmount = invoicedAmount;
    }

    public double getOverAmount() {
        return overAmount;
    }

    public void setOverAmount(double overAmount) {
        this.overAmount = overAmount;
    }

    public double getPlusDiscountAmount() {
        return plusDiscountAmount;
    }

    public void setPlusDiscountAmount(double plusDiscountAmount) {
        this.plusDiscountAmount = plusDiscountAmount;
    }

    public List<PromotionOrder> getPromotions() {
        return promotions;
    }

    public void setPromotions(List<PromotionOrder> promotions) {
        this.promotions = promotions;
    }

    public List<PromotionOrder> getAttendPromotions() {
        return attendPromotions;
    }

    public void setAttendPromotions(List<PromotionOrder> attendPromotions) {
        this.attendPromotions = attendPromotions;
    }

    public double getDiscountRate() {
        return discountRate;
    }

    public void setDiscountRate(double discountRate) {
        this.discountRate = discountRate;
    }

    public int getPostWay() {
        return postWay;
    }

    public void setPostWay(int postWay) {
        this.postWay = postWay;
    }

    public int getOrderSource() {
        return orderSource;
    }

    public void setOrderSource(int orderSource) {
        this.orderSource = orderSource;
    }

    public int getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(int orderStatus) {
        this.orderStatus = orderStatus;
    }

    public int getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(int paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public int getPrintStatus() {
        return printStatus;
    }

    public void setPrintStatus(int printStatus) {
        this.printStatus = printStatus;
    }

    public int getPrintTimes() {
        return printTimes;
    }

    public void setPrintTimes(int printTimes) {
        this.printTimes = printTimes;
    }

    public String getNoOrg() {
        return noOrg;
    }

    public void setNoOrg(String noOrg) {
        this.noOrg = noOrg;
    }

    public String getBackCause() {
        return backCause;
    }

    public void setBackCause(String backCause) {
        this.backCause = backCause;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public int getIsMember() {
        return isMember;
    }

    public void setIsMember(int isMember) {
        this.isMember = isMember;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getMemberNo() {
        return memberNo;
    }

    public void setMemberNo(String memberNo) {
        this.memberNo = memberNo;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getMemberMobileNo() {
        return memberMobileNo;
    }

    public void setMemberMobileNo(String memberMobileNo) {
        this.memberMobileNo = memberMobileNo;
    }

    public String getCardFaceNo() {
        return cardFaceNo;
    }

    public void setCardFaceNo(String cardFaceNo) {
        this.cardFaceNo = cardFaceNo;
    }

    public double getPrePoint() {
        return prePoint;
    }

    public void setPrePoint(double prePoint) {
        this.prePoint = prePoint;
    }

    public double getAddPoint() {
        return addPoint;
    }

    public void setAddPoint(double addPoint) {
        this.addPoint = addPoint;
    }

    public double getRefundPoint() {
        return refundPoint;
    }

    public void setRefundPoint(double refundPoint) {
        this.refundPoint = refundPoint;
    }

    public double getAftPoint() {
        return aftPoint;
    }

    public void setAftPoint(double aftPoint) {
        this.aftPoint = aftPoint;
    }

    public double getAftAmount() {
        return aftAmount;
    }

    public void setAftAmount(double aftAmount) {
        this.aftAmount = aftAmount;
    }

    public int getUploadStatus() {
        return uploadStatus;
    }

    public void setUploadStatus(int uploadStatus) {
        this.uploadStatus = uploadStatus;
    }

    public int getUploadErrors() {
        return uploadErrors;
    }

    public void setUploadErrors(int uploadErrors) {
        this.uploadErrors = uploadErrors;
    }

    public String getUploadMessage() {
        return uploadMessage;
    }

    public void setUploadMessage(String uploadMessage) {
        this.uploadMessage = uploadMessage;
    }

    public String getUploadCode() {
        return uploadCode;
    }

    public void setUploadCode(String uploadCode) {
        this.uploadCode = uploadCode;
    }

    public String getServerId() {
        return serverId;
    }

    public void setServerId(String serverId) {
        this.serverId = serverId;
    }

    public String getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(String uploadTime) {
        this.uploadTime = uploadTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getIsPlus() {
        return isPlus;
    }

    public void setIsPlus(int isPlus) {
        this.isPlus = isPlus;
    }

//    public List<ElecCoupon> getCouponList() {
//        return couponList;
//    }

//    public void setCouponList(List<ElecCoupon> couponList) {
//        this.couponList = couponList;
//    }

    public String getMenuItemMode() {
        return menuItemMode;
    }

    public void setMenuItemMode(String menuItemMode) {
        this.menuItemMode = menuItemMode;
    }

    public boolean isRePrint() {
        return rePrint;
    }

    public void setRePrint(boolean rePrint) {
        this.rePrint = rePrint;
    }

//    public GlobalEnums.CashierAction getCashierAction() {
//        return cashierAction;
//    }
//
//    public void setCashierAction(GlobalEnums.CashierAction cashierAction) {
//        this.cashierAction = cashierAction;
//    }
}
