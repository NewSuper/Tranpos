package com.newsuper.t.markert.entity;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;

import java.util.List;

@Entity(tableName = "pos_order_item")
public class OrderItem extends BaseEntity {

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
    /// 购物车-序号
    /// </summary>
    @ColumnInfo
    private int orderNo;

    /// <summary>
    /// 商品ID
    /// </summary>
    @ColumnInfo
    private String productId;

    /// <summary>
    /// 商品名称
    /// </summary>
    @ColumnInfo
    private String productName;

    /// <summary>
    /// 简称
    /// </summary>
    @ColumnInfo
    private String shortName;

    /// <summary>
    /// 规格ID
    /// </summary>
    @ColumnInfo
    private String specId;

    /// <summary>
    /// 规格名称
    /// </summary>
    @ColumnInfo
    private String specName;


    /// <summary>
    /// 界面品名显示
    /// </summary>
    @ColumnInfo
    private String displayName;

    /// <summary>
    /// 购物车-数量
    /// </summary>
    @ColumnInfo
    private double quantity;

    /// <summary>
    /// 连续称重标识
    /// </summary>
    @ColumnInfo
    private boolean weightContinue;

    /// <summary>
    /// 购物车-退数量
    /// </summary>
    @ColumnInfo
    private double rquantity;

    /// <summary>
    /// 购物车-退金额
    /// </summary>
    @ColumnInfo
    private double ramount;

    /// <summary>
    /// 已寄存数量
    /// </summary>
    @ColumnInfo
    private double depositQuantity;

    /// <summary>
    /// 原订单明细ID
    /// </summary>
    @ColumnInfo
    private String orgItemId;

    /// <summary>
    /// 原销售价
    /// </summary>
    @ColumnInfo
    private double salePrice;

    /// <summary>
    /// 购物车-零售价
    /// </summary>
    @ColumnInfo
    private double price;

    /// <summary>
    /// 议价原因
    /// </summary>
    @ColumnInfo
    private String bargainReason;

    /// <summary>
    /// 最终折后价
    /// </summary>
    @ColumnInfo
    private double discountPrice;

    /// <summary>
    /// 是否为plus价格 0-否 1-是
    /// </summary>
    @ColumnInfo
    private int isPlusPrice;

    /// <summary>
    /// 超出PLUS价格限额的标识
    /// </summary>
    @Ignore
    private boolean exceedPlus;

    /// <summary>
    /// 价格类型 0-零售价 1-会员价 2-plus会员价
    /// </summary>
    @Ignore
    private int priceType;

    /// <summary>
    /// PLUS价格
    /// </summary>
    @Ignore
    private double plusPrice;

    /// <summary>
    /// PLUS商品标识
    /// </summary>
    @Ignore
    private boolean plusFlag;

    /// <summary>
    /// PLUS商品优惠开始时间
    /// </summary>
    @Ignore
    private String validStartDate;

    /// <summary>
    /// PLUS商品优惠结束时间
    /// </summary>
    @Ignore
    private String validendDate;

    /// <summary>
    /// 会员价
    /// </summary>
    @ColumnInfo
    private double vipPrice;

    /// <summary>
    /// 会员价2
    /// </summary>
    @Ignore
    private double vipPrice2;

    /// <summary>
    /// 会员价3
    /// </summary>
    @Ignore
    private double vipPrice3;

    /// <summary>
    /// 会员价4
    /// </summary>
    @Ignore
    private double vipPrice4;

    /// <summary>
    /// 会员价5
    /// </summary>
    @Ignore
    private double vipPrice5;

    /// <summary>
    /// 外送价
    /// </summary>
    @ColumnInfo
    private double otherPrice;

    /// <summary>
    /// 批发价
    /// </summary>
    @ColumnInfo
    private double batchPrice;

    /// <summary>
    /// 配送价
    /// </summary>
    @ColumnInfo
    private double postPrice;

    /// <summary>
    /// 进价
    /// </summary>
    @ColumnInfo
    private double purPrice;

    /// <summary>
    /// 最低售价
    /// </summary>
    @ColumnInfo
    private double minPrice;

    /// <summary>
    /// 进货规格
    /// </summary>
    @Ignore
    private double purchaseSpec;

    /// <summary>
    /// 赠送数量
    /// </summary>
    @ColumnInfo
    private double giftQuantity;

    /// <summary>
    /// 赠送金额
    /// </summary>
    @ColumnInfo
    private double giftAmount;

    /// <summary>
    /// 赠送原因
    /// </summary>
    @ColumnInfo
    private String giftReason;

    /// <summary>
    /// 单品做法总数量
    /// </summary>
    @ColumnInfo
    private int flavorCount;

    /// <summary>
    /// 做法/要求加价合计金额
    /// </summary>
    @ColumnInfo
    private double flavorAmount;

    /// <summary>
    /// 做法/要求加价优惠金额
    /// </summary>
    @ColumnInfo
    private double flavorDiscountAmount;

    /// <summary>
    /// 做法/要求加价应收金额
    /// </summary>
    @ColumnInfo
    private double flavorReceivableAmount;

    /// <summary>
    /// 做法/要求描述
    /// </summary>
    @ColumnInfo
    private String flavorNames;

    /// <summary>
    /// 商品金额小计 = 数量 * 零售价
    /// </summary>
    @ColumnInfo
    private double amount;

    /// <summary>
    /// 总金额 = 商品小计+做法小计
    /// </summary>
    @ColumnInfo
    private double totalAmount;

    /// <summary>
    /// 商品优惠金额
    /// </summary>
    @ColumnInfo
    private double discountAmount;

    /// <summary>
    /// 商品应收金额 = 小计 - 优惠
    /// </summary>
    @ColumnInfo
    private double receivableAmount;

    /// <summary>
    /// 订单项加入方式
    //        NONE = -1,
    //        触摸点击 = 0,
    //        扫描条码 = 1,
    //        扫描金额码 = 2,
    //        扫描数量码 = 3,
    //        系统自动添加 = 4, //促销赠送
    /// </summary>
    @ColumnInfo
    private int joinType;

    /// <summary>
    /// 条码金额， 超市条码秤打印的金额码，此金额优先级最高
    /// </summary>
    @ColumnInfo
    private double labelAmount;

    /// <summary>
    /// 总优惠金额 = 商品优惠小计+做法优惠小计
    /// </summary>
    @ColumnInfo
    private double totalDiscountAmount;

    /// <summary>
    /// 总应收金额 = 商品应收小计+做法应收小计
    /// </summary>
    @ColumnInfo
    private double totalReceivableAmount;

    /// <summary>
    /// 分摊的券占用金额
    /// </summary>
    @ColumnInfo
    private double shareCouponLeastCost;

    /// <summary>
    /// 用券金额
    /// </summary>
    @ColumnInfo
    private double couponAmount;

    /// <summary>
    /// 去券后总应收
    /// </summary>
    @ColumnInfo
    private double totalReceivableRemoveCouponAmount;

    /// <summary>
    /// 去券占用金额后总应收
    /// </summary>
    @ColumnInfo
    private double totalReceivableRemoveCouponLeastCost;

    /// <summary>
    /// 抹零金额
    /// </summary>
    @ColumnInfo
    private double malingAmount;

    /// <summary>
    /// 单品优惠率 = 单品优惠额 / 消费金额，不包含做法
    /// </summary>
    @ColumnInfo
    private double discountRate;

    /// <summary>
    /// 总单品优惠率 = 总优惠额 / 总消费金额
    /// </summary>
    @ColumnInfo
    private double totalDiscountRate;

    /// <summary>
    /// 行记录的创建时间
    /// </summary>
    @ColumnInfo
    private String saleDate;

    /// <summary>
    /// 订单完成时间(格式:yyyy-MM-dd HH:mm:ss)
    /// </summary>
    @ColumnInfo
    private String finishDate;


    /// <summary>
    /// 行备注
    /// </summary>
    @ColumnInfo
    private String remark;

    /// <summary>
    /// 订单项来源
    //        收银台 = 0,
    //        自助收银机 = 1,
    //        扫码购 = 2,
    //        网店 = 20,
    //        拼团 = 21,
    //        //美团外卖 = 30,
    //        饿了么外卖 = 31,
    //        云秤 = 40
    /// </summary>
    @ColumnInfo
    private int itemSource;

    /// <summary>
    /// posNo
    /// </summary>
    @ColumnInfo
    private String posNo;

    /// <summary>
    /// 厨打标识
    /// </summary>
    @ColumnInfo
    private String chudaFlag;

    /// <summary>
    /// 厨打方案
    /// </summary>
    @ColumnInfo
    private String chuda;

    /// <summary>
    /// 已厨打数量
    /// </summary>
    @ColumnInfo
    private double chudaQty;

    /// <summary>
    /// 出品标识
    /// </summary>
    @ColumnInfo
    private String chupinFlag;

    /// <summary>
    /// 出品方案
    /// </summary>
    @ColumnInfo
    private String chupin;

    /// <summary>
    /// 已出品数量
    /// </summary>
    @ColumnInfo
    private double chupinQty;

    /// <summary>
    /// 厨打标签标识
    /// </summary>
    @ColumnInfo
    private String chuDaLabelFlag;

    /// <summary>
    /// 厨打标签方案
    /// </summary>
    @ColumnInfo
    private String chuDaLabel;

    /// <summary>
    /// 已厨打标签数量
    /// </summary>
    @ColumnInfo
    private double chuDaLabelQty;

    /// <summary>
    /// 购物车显示-优惠
    /// </summary>
    @ColumnInfo
    private double cartDiscount;

    /// <summary>
    /// 购物车-行下划线
    /// </summary>
    @ColumnInfo
    private int underline;


    /// <summary>
    /// 购物车-组标识
    /// </summary>
    @ColumnInfo
    private String group;

    /// <summary>
    /// 购物车-父行
    /// </summary>
    @ColumnInfo
    private String parentId;

    /// <summary>
    /// 购物车-是否包含做法
    /// </summary>
    @ColumnInfo
    private int flavor;


    /// <summary>
    /// 购物车-加料方案
    /// </summary>
    @ColumnInfo
    private String scheme;

    /// <summary>
    /// 行状态普通 = 1, 捆绑主 = 2, 捆绑明 = 3
    /// </summary>
    @ColumnInfo
    private int rowType;

    /// <summary>
    ///道菜ID
    /// </summary>
    @ColumnInfo
    private String suitId;

    /// <summary>
    /// 道菜基准数量
    /// </summary>
    @ColumnInfo
    private double suitQuantity;

    /// <summary>
    /// 道菜基准加价
    /// </summary>
    @ColumnInfo
    private double suitAddPrice;

    /// <summary>
    /// 道菜基准加价后的金额
    /// </summary>
    @ColumnInfo
    private double suitAmount;

    /// <summary>
    /// 商品对应的详细信息，包含规格、价格信息
    /// </summary>
//    private ProductExt product;

    /// <summary>
    /// 单品享受的优惠列表
    /// </summary>
    @Ignore
    private List<PromotionItem> promotions;

    /// <summary>
    /// 单品参与的促销，这里指后台促销单
    /// </summary>
    @Ignore
    private List<PromotionItem> attendPromotions;

    /// <summary>
    /// 做法/要求明细
    /// </summary>
    @Ignore
    private List<FlavorItem> flavors;

    /// <summary>
    /// 商品类型 0-普通商品；1-可拆零商品；2-捆绑商品；3-自动转货；
    /// </summary>
    @ColumnInfo
    private int productType;

    /// <summary>
    /// 商品条码
    /// </summary>
    @ColumnInfo
    private String barCode;

    /// <summary>
    /// 商品自编码
    /// </summary>
    @ColumnInfo
    private String subNo;

    /// <summary>
    /// 商品批次号
    /// </summary>
    @ColumnInfo
    private String batchNo;

    /// <summary>
    /// 商品单位ID
    /// </summary>
    @ColumnInfo
    private String productUnitId;

    /// <summary>
    /// 商品单位名称
    /// </summary>
    @ColumnInfo
    private String productUnitName;

    /// <summary>
    /// 品类ID
    /// </summary>
    @ColumnInfo
    private String categoryId;

    /// <summary>
    /// 品类编号
    /// </summary>
    @ColumnInfo
    private String categoryNo;

    /// <summary>
    /// 品类名称
    /// </summary>
    @ColumnInfo
    private String categoryName;

    /// <summary>
    /// 品牌ID
    /// </summary>
    @ColumnInfo
    private String brandId;

    /// <summary>
    /// 品牌名称
    /// </summary>
    @ColumnInfo
    private String brandName;

    /// <summary>
    /// 前台打折
    /// </summary>
    @ColumnInfo
    private int foreDiscount;

    /// <summary>
    /// 是否需要称重(0否-1是)
    /// </summary>
    @ColumnInfo
    private int weightFlag;

    /// <summary>
    /// 称重计价方式(1、计重 2、计数)
    /// </summary>
    @ColumnInfo
    private int weightWay;

    /// <summary>
    /// 是否可议价(0否-1是)
    /// </summary>
    @ColumnInfo
    private int foreBargain;

    /// <summary>
    /// 是否积分(0否-1是)
    /// </summary>
    @ColumnInfo
    private int pointFlag;

    /// <summary>
    /// 积分值
    /// </summary>
    @ColumnInfo
    private double pointValue;

    /// <summary>
    /// 前台赠送
    /// </summary>
    @ColumnInfo
    private int foreGift;

    /// <summary>
    /// 能否促销
    /// </summary>
    @ColumnInfo
    private int promotionFlag;

    /// <summary>
    /// 管理库存(0否-1是)
    /// </summary>
    @ColumnInfo
    private int stockFlag;

    /// <summary>
    /// 批次管理库存
    /// </summary>
    @ColumnInfo
    private int batchStockFlag;

    /// <summary>
    /// 打印标签(0否-1是)
    /// </summary>
    @ColumnInfo
    private int labelPrintFlag;

    /// <summary>
    /// 已打印标签数量
    /// </summary>
    @ColumnInfo
    private double labelQty;

    /// <summary>
    /// 进项税
    /// </summary>
    @ColumnInfo
    private double purchaseTax;

    /// <summary>
    /// 销项税
    /// </summary>
    @ColumnInfo
    private double saleTax;

    /// <summary>
    /// 联营扣率
    /// </summary>
    @ColumnInfo
    private double lyRate;

    /// <summary>
    /// 供应商ID
    /// </summary>
    @ColumnInfo
    private String supplierId;

    /// <summary>
    /// 供应商名称
    /// </summary>
    @ColumnInfo
    private String supplierName;

    /// <summary>
    /// 经销方式
    /// </summary>
    @ColumnInfo
    private String managerType;

    /// <summary>
    /// 营业员编码
    /// </summary>
    @ColumnInfo
    private String salesCode;

    /// <summary>
    /// 营业员名称
    /// </summary>
    @ColumnInfo
    private String salesName;

    /// <summary>
    /// 增加积分
    /// </summary>
    @ColumnInfo
    private double addPoint;

    /// <summary>
    /// 退积分
    /// </summary>
    @ColumnInfo
    private double refundPoint;

    /// <summary>
    /// 优惠描述
    /// </summary>
    @ColumnInfo
    private String promotionInfo;

    /// <summary>
    /// 支付方式分摊明细
    /// </summary>
    @Ignore
    private List<OrderItemPay> itemPayList;

    /// <summary>
    /// 收银操作或者无单退货操作
    /// </summary>
//    private GlobalEnums.CashierAction cashierAction;

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

    public int getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(int orderNo) {
        this.orderNo = orderNo;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getSpecId() {
        return specId;
    }

    public void setSpecId(String specId) {
        this.specId = specId;
    }

    public String getSpecName() {
        return specName;
    }

    public void setSpecName(String specName) {
        this.specName = specName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public boolean isWeightContinue() {
        return weightContinue;
    }

    public void setWeightContinue(boolean weightContinue) {
        this.weightContinue = weightContinue;
    }

    public double getRquantity() {
        return rquantity;
    }

    public void setRquantity(double rquantity) {
        this.rquantity = rquantity;
    }

    public double getRamount() {
        return ramount;
    }

    public void setRamount(double ramount) {
        this.ramount = ramount;
    }

    public double getDepositQuantity() {
        return depositQuantity;
    }

    public void setDepositQuantity(double depositQuantity) {
        this.depositQuantity = depositQuantity;
    }

    public String getOrgItemId() {
        return orgItemId;
    }

    public void setOrgItemId(String orgItemId) {
        this.orgItemId = orgItemId;
    }

    public double getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(double salePrice) {
        this.salePrice = salePrice;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getBargainReason() {
        return bargainReason;
    }

    public void setBargainReason(String bargainReason) {
        this.bargainReason = bargainReason;
    }

    public double getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(double discountPrice) {
        this.discountPrice = discountPrice;
    }

    public int getIsPlusPrice() {
        return isPlusPrice;
    }

    public void setIsPlusPrice(int isPlusPrice) {
        this.isPlusPrice = isPlusPrice;
    }

    public boolean isExceedPlus() {
        return exceedPlus;
    }

    public void setExceedPlus(boolean exceedPlus) {
        this.exceedPlus = exceedPlus;
    }

    public int getPriceType() {
        return priceType;
    }

    public void setPriceType(int priceType) {
        this.priceType = priceType;
    }

    public double getPlusPrice() {
        return plusPrice;
    }

    public void setPlusPrice(double plusPrice) {
        this.plusPrice = plusPrice;
    }

    public boolean isPlusFlag() {
        return plusFlag;
    }

    public void setPlusFlag(boolean plusFlag) {
        this.plusFlag = plusFlag;
    }

    public String getValidStartDate() {
        return validStartDate;
    }

    public void setValidStartDate(String validStartDate) {
        this.validStartDate = validStartDate;
    }

    public String getValidendDate() {
        return validendDate;
    }

    public void setValidendDate(String validendDate) {
        this.validendDate = validendDate;
    }

    public double getVipPrice() {
        return vipPrice;
    }

    public void setVipPrice(double vipPrice) {
        this.vipPrice = vipPrice;
    }

    public double getVipPrice2() {
        return vipPrice2;
    }

    public void setVipPrice2(double vipPrice2) {
        this.vipPrice2 = vipPrice2;
    }

    public double getVipPrice3() {
        return vipPrice3;
    }

    public void setVipPrice3(double vipPrice3) {
        this.vipPrice3 = vipPrice3;
    }

    public double getVipPrice4() {
        return vipPrice4;
    }

    public void setVipPrice4(double vipPrice4) {
        this.vipPrice4 = vipPrice4;
    }

    public double getVipPrice5() {
        return vipPrice5;
    }

    public void setVipPrice5(double vipPrice5) {
        this.vipPrice5 = vipPrice5;
    }

    public double getOtherPrice() {
        return otherPrice;
    }

    public void setOtherPrice(double otherPrice) {
        this.otherPrice = otherPrice;
    }

    public double getBatchPrice() {
        return batchPrice;
    }

    public void setBatchPrice(double batchPrice) {
        this.batchPrice = batchPrice;
    }

    public double getPostPrice() {
        return postPrice;
    }

    public void setPostPrice(double postPrice) {
        this.postPrice = postPrice;
    }

    public double getPurPrice() {
        return purPrice;
    }

    public void setPurPrice(double purPrice) {
        this.purPrice = purPrice;
    }

    public double getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(double minPrice) {
        this.minPrice = minPrice;
    }

    public double getPurchaseSpec() {
        return purchaseSpec;
    }

    public void setPurchaseSpec(double purchaseSpec) {
        this.purchaseSpec = purchaseSpec;
    }

    public double getGiftQuantity() {
        return giftQuantity;
    }

    public void setGiftQuantity(double giftQuantity) {
        this.giftQuantity = giftQuantity;
    }

    public double getGiftAmount() {
        return giftAmount;
    }

    public void setGiftAmount(double giftAmount) {
        this.giftAmount = giftAmount;
    }

    public String getGiftReason() {
        return giftReason;
    }

    public void setGiftReason(String giftReason) {
        this.giftReason = giftReason;
    }

    public int getFlavorCount() {
        return flavorCount;
    }

    public void setFlavorCount(int flavorCount) {
        this.flavorCount = flavorCount;
    }

    public double getFlavorAmount() {
        return flavorAmount;
    }

    public void setFlavorAmount(double flavorAmount) {
        this.flavorAmount = flavorAmount;
    }

    public double getFlavorDiscountAmount() {
        return flavorDiscountAmount;
    }

    public void setFlavorDiscountAmount(double flavorDiscountAmount) {
        this.flavorDiscountAmount = flavorDiscountAmount;
    }

    public double getFlavorReceivableAmount() {
        return flavorReceivableAmount;
    }

    public void setFlavorReceivableAmount(double flavorReceivableAmount) {
        this.flavorReceivableAmount = flavorReceivableAmount;
    }

    public String getFlavorNames() {
        return flavorNames;
    }

    public void setFlavorNames(String flavorNames) {
        this.flavorNames = flavorNames;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
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

    public int getJoinType() {
        return joinType;
    }

    public void setJoinType(int joinType) {
        this.joinType = joinType;
    }

    public double getLabelAmount() {
        return labelAmount;
    }

    public void setLabelAmount(double labelAmount) {
        this.labelAmount = labelAmount;
    }

    public double getTotalDiscountAmount() {
        return totalDiscountAmount;
    }

    public void setTotalDiscountAmount(double totalDiscountAmount) {
        this.totalDiscountAmount = totalDiscountAmount;
    }

    public double getTotalReceivableAmount() {
        return totalReceivableAmount;
    }

    public void setTotalReceivableAmount(double totalReceivableAmount) {
        this.totalReceivableAmount = totalReceivableAmount;
    }

    public double getShareCouponLeastCost() {
        return shareCouponLeastCost;
    }

    public void setShareCouponLeastCost(double shareCouponLeastCost) {
        this.shareCouponLeastCost = shareCouponLeastCost;
    }

    public double getCouponAmount() {
        return couponAmount;
    }

    public void setCouponAmount(double couponAmount) {
        this.couponAmount = couponAmount;
    }

    public double getTotalReceivableRemoveCouponAmount() {
        return totalReceivableRemoveCouponAmount;
    }

    public void setTotalReceivableRemoveCouponAmount(double totalReceivableRemoveCouponAmount) {
        this.totalReceivableRemoveCouponAmount = totalReceivableRemoveCouponAmount;
    }

    public double getTotalReceivableRemoveCouponLeastCost() {
        return totalReceivableRemoveCouponLeastCost;
    }

    public void setTotalReceivableRemoveCouponLeastCost(double totalReceivableRemoveCouponLeastCost) {
        this.totalReceivableRemoveCouponLeastCost = totalReceivableRemoveCouponLeastCost;
    }

    public double getMalingAmount() {
        return malingAmount;
    }

    public void setMalingAmount(double malingAmount) {
        this.malingAmount = malingAmount;
    }

    public double getDiscountRate() {
        return discountRate;
    }

    public void setDiscountRate(double discountRate) {
        this.discountRate = discountRate;
    }

    public double getTotalDiscountRate() {
        return totalDiscountRate;
    }

    public void setTotalDiscountRate(double totalDiscountRate) {
        this.totalDiscountRate = totalDiscountRate;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getItemSource() {
        return itemSource;
    }

    public void setItemSource(int itemSource) {
        this.itemSource = itemSource;
    }

    public String getPosNo() {
        return posNo;
    }

    public void setPosNo(String posNo) {
        this.posNo = posNo;
    }

    public String getChudaFlag() {
        return chudaFlag;
    }

    public void setChudaFlag(String chudaFlag) {
        this.chudaFlag = chudaFlag;
    }

    public String getChuda() {
        return chuda;
    }

    public void setChuda(String chuda) {
        this.chuda = chuda;
    }

    public double getChudaQty() {
        return chudaQty;
    }

    public void setChudaQty(double chudaQty) {
        this.chudaQty = chudaQty;
    }

    public String getChupinFlag() {
        return chupinFlag;
    }

    public void setChupinFlag(String chupinFlag) {
        this.chupinFlag = chupinFlag;
    }

    public String getChupin() {
        return chupin;
    }

    public void setChupin(String chupin) {
        this.chupin = chupin;
    }

    public double getChupinQty() {
        return chupinQty;
    }

    public void setChupinQty(double chupinQty) {
        this.chupinQty = chupinQty;
    }

    public String getChuDaLabelFlag() {
        return chuDaLabelFlag;
    }

    public void setChuDaLabelFlag(String chuDaLabelFlag) {
        this.chuDaLabelFlag = chuDaLabelFlag;
    }

    public String getChuDaLabel() {
        return chuDaLabel;
    }

    public void setChuDaLabel(String chuDaLabel) {
        this.chuDaLabel = chuDaLabel;
    }

    public double getChuDaLabelQty() {
        return chuDaLabelQty;
    }

    public void setChuDaLabelQty(double chuDaLabelQty) {
        this.chuDaLabelQty = chuDaLabelQty;
    }

    public double getCartDiscount() {
        return cartDiscount;
    }

    public void setCartDiscount(double cartDiscount) {
        this.cartDiscount = cartDiscount;
    }

    public int getUnderline() {
        return underline;
    }

    public void setUnderline(int underline) {
        this.underline = underline;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public int getFlavor() {
        return flavor;
    }

    public void setFlavor(int flavor) {
        this.flavor = flavor;
    }

    public String getScheme() {
        return scheme;
    }

    public void setScheme(String scheme) {
        this.scheme = scheme;
    }

    public int getRowType() {
        return rowType;
    }

    public void setRowType(int rowType) {
        this.rowType = rowType;
    }

    public String getSuitId() {
        return suitId;
    }

    public void setSuitId(String suitId) {
        this.suitId = suitId;
    }

    public double getSuitQuantity() {
        return suitQuantity;
    }

    public void setSuitQuantity(double suitQuantity) {
        this.suitQuantity = suitQuantity;
    }

    public double getSuitAddPrice() {
        return suitAddPrice;
    }

    public void setSuitAddPrice(double suitAddPrice) {
        this.suitAddPrice = suitAddPrice;
    }

    public double getSuitAmount() {
        return suitAmount;
    }

    public void setSuitAmount(double suitAmount) {
        this.suitAmount = suitAmount;
    }


    public List<PromotionItem> getPromotions() {
        return promotions;
    }

    public void setPromotions(List<PromotionItem> promotions) {
        this.promotions = promotions;
    }

    public List<PromotionItem> getAttendPromotions() {
        return attendPromotions;
    }

    public void setAttendPromotions(List<PromotionItem> attendPromotions) {
        this.attendPromotions = attendPromotions;
    }

    public List<FlavorItem> getFlavors() {
        return flavors;
    }

    public void setFlavors(List<FlavorItem> flavors) {
        this.flavors = flavors;
    }

    public int getProductType() {
        return productType;
    }

    public void setProductType(int productType) {
        this.productType = productType;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public String getSubNo() {
        return subNo;
    }

    public void setSubNo(String subNo) {
        this.subNo = subNo;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public String getProductUnitId() {
        return productUnitId;
    }

    public void setProductUnitId(String productUnitId) {
        this.productUnitId = productUnitId;
    }

    public String getProductUnitName() {
        return productUnitName;
    }

    public void setProductUnitName(String productUnitName) {
        this.productUnitName = productUnitName;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryNo() {
        return categoryNo;
    }

    public void setCategoryNo(String categoryNo) {
        this.categoryNo = categoryNo;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getBrandId() {
        return brandId;
    }

    public void setBrandId(String brandId) {
        this.brandId = brandId;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public int getForeDiscount() {
        return foreDiscount;
    }

    public void setForeDiscount(int foreDiscount) {
        this.foreDiscount = foreDiscount;
    }

    public int getWeightFlag() {
        return weightFlag;
    }

    public void setWeightFlag(int weightFlag) {
        this.weightFlag = weightFlag;
    }

    public int getWeightWay() {
        return weightWay;
    }

    public void setWeightWay(int weightWay) {
        this.weightWay = weightWay;
    }

    public int getForeBargain() {
        return foreBargain;
    }

    public void setForeBargain(int foreBargain) {
        this.foreBargain = foreBargain;
    }

    public int getPointFlag() {
        return pointFlag;
    }

    public void setPointFlag(int pointFlag) {
        this.pointFlag = pointFlag;
    }

    public double getPointValue() {
        return pointValue;
    }

    public void setPointValue(double pointValue) {
        this.pointValue = pointValue;
    }

    public int getForeGift() {
        return foreGift;
    }

    public void setForeGift(int foreGift) {
        this.foreGift = foreGift;
    }

    public int getPromotionFlag() {
        return promotionFlag;
    }

    public void setPromotionFlag(int promotionFlag) {
        this.promotionFlag = promotionFlag;
    }

    public int getStockFlag() {
        return stockFlag;
    }

    public void setStockFlag(int stockFlag) {
        this.stockFlag = stockFlag;
    }

    public int getBatchStockFlag() {
        return batchStockFlag;
    }

    public void setBatchStockFlag(int batchStockFlag) {
        this.batchStockFlag = batchStockFlag;
    }

    public int getLabelPrintFlag() {
        return labelPrintFlag;
    }

    public void setLabelPrintFlag(int labelPrintFlag) {
        this.labelPrintFlag = labelPrintFlag;
    }

    public double getLabelQty() {
        return labelQty;
    }

    public void setLabelQty(double labelQty) {
        this.labelQty = labelQty;
    }

    public double getPurchaseTax() {
        return purchaseTax;
    }

    public void setPurchaseTax(double purchaseTax) {
        this.purchaseTax = purchaseTax;
    }

    public double getSaleTax() {
        return saleTax;
    }

    public void setSaleTax(double saleTax) {
        this.saleTax = saleTax;
    }

    public double getLyRate() {
        return lyRate;
    }

    public void setLyRate(double lyRate) {
        this.lyRate = lyRate;
    }

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getManagerType() {
        return managerType;
    }

    public void setManagerType(String managerType) {
        this.managerType = managerType;
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

    public String getPromotionInfo() {
        return promotionInfo;
    }

    public void setPromotionInfo(String promotionInfo) {
        this.promotionInfo = promotionInfo;
    }

    public List<OrderItemPay> getItemPayList() {
        return itemPayList;
    }

    public void setItemPayList(List<OrderItemPay> itemPayList) {
        this.itemPayList = itemPayList;
    }

//    public GlobalEnums.CashierAction getCashierAction() {
//        return cashierAction;
//    }
//
//    public void setCashierAction(GlobalEnums.CashierAction cashierAction) {
//        this.cashierAction = cashierAction;
//    }
}
