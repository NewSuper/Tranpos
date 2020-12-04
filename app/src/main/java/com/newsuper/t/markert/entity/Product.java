package com.newsuper.t.markert.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;

@Entity(tableName = "pos_product")
public class Product extends BaseEntity {

    /// <summary>
    /// 商品分类ID
    /// </summary>
    @ColumnInfo
    private String categoryId;

    /// <summary>
    /// 商品分类路径
    /// </summary>
    @ColumnInfo
    private String categoryPath;

    /// <summary>
    /// 商品类型  0-普通商品；1-可拆零商品；2-捆绑商品；3-自动转货；
    /// </summary>
    @ColumnInfo
    private int type;

    /// <summary>
    /// 货号
    /// </summary>
    @ColumnInfo
    private String no;

    /// <summary>
    /// 条码
    /// </summary>
    @ColumnInfo
    private String barCode;

    /// <summary>
    /// 自编码
    /// </summary>
    @ColumnInfo
    private String subNo;

    /// <summary>
    /// 第三方编码
    /// </summary>
    @ColumnInfo
    private String otherNo;

    /// <summary>
    /// 商品名称
    /// </summary>
    @ColumnInfo
    private String name;

    /// <summary>
    /// 英文名称
    /// </summary>
    @ColumnInfo
    private String english;

    /// <summary>
    /// 助记码
    /// </summary>
    @ColumnInfo
    private String rem;

    /// <summary>
    /// 简称
    /// </summary>
    @ColumnInfo
    private String shortName;

    /// <summary>
    /// 单位
    /// </summary>
    @ColumnInfo
    private String unitId;

    /// <summary>
    /// 品牌ID
    /// </summary>
    @ColumnInfo
    private String brandId;

    /// <summary>
    /// 存储类型
    /// </summary>
    @ColumnInfo
    private int storageType;

    /// <summary>
    /// 主图存储地址
    /// </summary>
    @ColumnInfo
    private String storageAddress;

    /// <summary>
    /// 主供应商ID
    /// </summary>
    @ColumnInfo
    private String supplierId;

    /// <summary>
    /// 经销方式
    /// </summary>
    @ColumnInfo
    private String managerType;

    /// <summary>
    /// 采购管控
    /// </summary>
    @ColumnInfo
    private int purchaseControl;

    /// <summary>
    /// 采购周期
    /// </summary>
    @ColumnInfo
    private double purchaserCycle;

    /// <summary>
    /// 保质期
    /// </summary>
    @ColumnInfo
    private double validDays;

    /// <summary>
    /// 产地
    /// </summary>
    @ColumnInfo
    private String productArea;

    /// <summary>
    /// 商品状态 1-在售；2-停购；3-停售；4-淘汰；
    /// </summary>
    @ColumnInfo
    private int status;

    /// <summary>
    /// 规格数量
    /// </summary>
    @ColumnInfo
    private int spNum;

    /// <summary>
    /// 是否管理库存
    /// </summary>
    @ColumnInfo
    private int stockFlag;

    /// <summary>
    /// 是否按批次管理
    /// </summary>
    @ColumnInfo
    private int batchStockFlag;

    /// <summary>
    /// 是否需要称重
    /// </summary>
    @ColumnInfo
    private int weightFlag;

    /// <summary>
    /// 称重计价方式  1、计重 2、计数
    /// </summary>
    @ColumnInfo
    private int weightWay;


    /// <summary>
    /// 秤内码
    /// </summary>
    @ColumnInfo
    private String steelyardCode;

    /// <summary>
    /// 打印标签
    /// </summary>
    @ColumnInfo
    private int labelPrintFlag;

    /// <summary>
    /// 前台打折
    /// </summary>
    @ColumnInfo
    private int foreDiscount;

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
    /// 分店变价
    /// </summary>
    @ColumnInfo
    private int branchPrice;

    /// <summary>
    /// 前台议价
    /// </summary>
    @ColumnInfo
    private int foreBargain;

    /// <summary>
    /// 提成方式
    /// </summary>
    @ColumnInfo
    private int returnType;

    /// <summary>
    /// 提成数额
    /// </summary>
    @ColumnInfo
    private double returnRate;

    /// <summary>
    /// 是否积分
    /// </summary>
    @ColumnInfo
    private int pointFlag;

    /// <summary>
    /// 积分值
    /// </summary>
    @ColumnInfo
    private double pointValue;

    /// <summary>
    /// 商品介绍
    /// </summary>
    @ColumnInfo
    private String introduction;

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
    /// 编码集合
    /// </summary>
    @ColumnInfo
    private String allCode;

    /// <summary>
    /// 删除标识
    /// </summary>
    @ColumnInfo
    private int deleteFlag;

    /// <summary>
    /// 允许加盟商修改主供应商
    /// </summary>
    @ColumnInfo
    private int allowEditSup;

    /// <summary>
    /// 是否快速盘点 0-否 1-是
    /// </summary>
    @ColumnInfo
    private int quickInventoryFlag;

    /// <summary>
    /// POS销售标识 0-否 1-是
    /// </summary>
    @ColumnInfo
    private int posSellFlag;

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryPath() {
        return categoryPath;
    }

    public void setCategoryPath(String categoryPath) {
        this.categoryPath = categoryPath;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
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

    public String getOtherNo() {
        return otherNo;
    }

    public void setOtherNo(String otherNo) {
        this.otherNo = otherNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEnglish() {
        return english;
    }

    public void setEnglish(String english) {
        this.english = english;
    }

    public String getRem() {
        return rem;
    }

    public void setRem(String rem) {
        this.rem = rem;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getUnitId() {
        return unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    public String getBrandId() {
        return brandId;
    }

    public void setBrandId(String brandId) {
        this.brandId = brandId;
    }

    public int getStorageType() {
        return storageType;
    }

    public void setStorageType(int storageType) {
        this.storageType = storageType;
    }

    public String getStorageAddress() {
        return storageAddress;
    }

    public void setStorageAddress(String storageAddress) {
        this.storageAddress = storageAddress;
    }

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    public String getManagerType() {
        return managerType;
    }

    public void setManagerType(String managerType) {
        this.managerType = managerType;
    }

    public int getPurchaseControl() {
        return purchaseControl;
    }

    public void setPurchaseControl(int purchaseControl) {
        this.purchaseControl = purchaseControl;
    }

    public double getPurchaserCycle() {
        return purchaserCycle;
    }

    public void setPurchaserCycle(double purchaserCycle) {
        this.purchaserCycle = purchaserCycle;
    }

    public double getValidDays() {
        return validDays;
    }

    public void setValidDays(double validDays) {
        this.validDays = validDays;
    }

    public String getProductArea() {
        return productArea;
    }

    public void setProductArea(String productArea) {
        this.productArea = productArea;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getSpNum() {
        return spNum;
    }

    public void setSpNum(int spNum) {
        this.spNum = spNum;
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

    public String getSteelyardCode() {
        return steelyardCode;
    }

    public void setSteelyardCode(String steelyardCode) {
        this.steelyardCode = steelyardCode;
    }

    public int getLabelPrintFlag() {
        return labelPrintFlag;
    }

    public void setLabelPrintFlag(int labelPrintFlag) {
        this.labelPrintFlag = labelPrintFlag;
    }

    public int getForeDiscount() {
        return foreDiscount;
    }

    public void setForeDiscount(int foreDiscount) {
        this.foreDiscount = foreDiscount;
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

    public int getBranchPrice() {
        return branchPrice;
    }

    public void setBranchPrice(int branchPrice) {
        this.branchPrice = branchPrice;
    }

    public int getForeBargain() {
        return foreBargain;
    }

    public void setForeBargain(int foreBargain) {
        this.foreBargain = foreBargain;
    }

    public int getReturnType() {
        return returnType;
    }

    public void setReturnType(int returnType) {
        this.returnType = returnType;
    }

    public double getReturnRate() {
        return returnRate;
    }

    public void setReturnRate(double returnRate) {
        this.returnRate = returnRate;
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

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
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

    public String getAllCode() {
        return allCode;
    }

    public void setAllCode(String allCode) {
        this.allCode = allCode;
    }

    public int getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(int deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public int getAllowEditSup() {
        return allowEditSup;
    }

    public void setAllowEditSup(int allowEditSup) {
        this.allowEditSup = allowEditSup;
    }

    public int getQuickInventoryFlag() {
        return quickInventoryFlag;
    }

    public void setQuickInventoryFlag(int quickInventoryFlag) {
        this.quickInventoryFlag = quickInventoryFlag;
    }

    public int getPosSellFlag() {
        return posSellFlag;
    }

    public void setPosSellFlag(int posSellFlag) {
        this.posSellFlag = posSellFlag;
    }
}
