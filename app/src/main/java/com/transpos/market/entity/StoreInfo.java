package com.transpos.market.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;

@Entity(tableName = "pos_store_info")
public class StoreInfo extends BaseEntity{

    /**
     * 编码
     */
    @ColumnInfo
    private String code;

    /**
     * 名称
     */
    @ColumnInfo
    private String name;


    /**
     * 显示名称，显示使用
     */
    @ColumnInfo
    private String displayName;

    /**
     * 类型
     */
    @ColumnInfo
    private int type;

    /**
     * 上级机构
     */
    @ColumnInfo
    private String upOrg;

    /**
     * 上级机构名称
     */
    @ColumnInfo
    private String upOrgName;

    /**
     *  默认要货机构
     */
    @ColumnInfo
    private String askOrg;

    /**
     *  默认要货机构名称
     */
    @ColumnInfo
    private String askOrgName;

    /**
     * 结算扣率
     */
    @ColumnInfo
    private int balanceRate;

    /**
     * 配送价格选项
     */
    @ColumnInfo
    private int postPrice;

    /**
     * 配送加价率
     */
    @ColumnInfo
    private double addRate;

    /**
     * 区域ID
     */
    @ColumnInfo
    private String areaId;

    /**
     * 区域路径
     */
    @ColumnInfo
    private String areaPath;

    /**
     * 状态
     */
    @ColumnInfo
    private int status;

    /**
     * 联系人
     */
    @ColumnInfo
    private String contacts;

    /**
     * 固话
     */
    @ColumnInfo
    private String tel;

    /**
     * 手机号码
     */
    @ColumnInfo
    private String mobile;

    /**
     * 外送电话
     */
    @ColumnInfo
    private String orderTel;


    /**
     * 打印名称
     */
    @ColumnInfo
    private String printName;

    /**
     * 传真
     */
    @ColumnInfo
    private String fax;

    /**
     * 邮编
     */
    @ColumnInfo
    private String postcode;

    /**
     * 地址
     */
    @ColumnInfo
    private String address;

    /**
     * email
     */
    @ColumnInfo
    private String email;

    /**
     * 营业面积
     */
    @ColumnInfo
    private double acreage;

    /**
     * 经度
     */
    @ColumnInfo
    private double lng;

    /**
     * 纬度
     */
    @ColumnInfo
    private double lat;

    /**
     * 删除标识
     */
    @ColumnInfo
    private int deleteFlag;

    /**
     * 图片宽度
     */
    @ColumnInfo
    private int width;

    /**
     * 图片高度
     */
    @ColumnInfo
    private int height;

    /**
     * 存储类型
     */
    @ColumnInfo
    private int storageType;

    /**
     * 存储地址
     */
    @ColumnInfo
    private String storageAddress;

    /**
     * 是否授权
     */
    @ColumnInfo
    private int authFlag;

    /**
     * 第三方编号
     */
    @ColumnInfo
    private String thirdNo;

    /**
     * 要货信用额度
     */
    @ColumnInfo
    private double creditAmount;

    /**
     * 要货已用额度
     */
    @ColumnInfo
    private double creditAmountUsed;

    /**
     * 充值上限额度
     */
    @ColumnInfo
    private double chargeLimit;

    /**
     * 充值已用额度
     */
    @ColumnInfo
    private double chargeLimitUsed;

    /**
     * 默认标识
     */
    @ColumnInfo
    private int defaultFlag;

    /**
     * 默认仓库Id
     */
    @ColumnInfo
    private String warehouseId;

    /**
     * 默认仓库编号
     */
    @ColumnInfo
    private String warehouseNo;

    /**
     * 门店支付参数 0 使用默认参数 1 使用门店配置参数
     */
    @ColumnInfo
    private int storePaySetting;

    /**
     * 开始营业时间
     */
    @ColumnInfo
    private String mallStart;

    /**
     * 结束营业时间
     */
    @ColumnInfo
    private String mallEnd;

    /**
     * 是否营业 0 休业 1 正在营业
     */
    @ColumnInfo
    private int mallBusinessFlag;

    /**
     * 是否微商城门店 0 否 1 是
     */
    @ColumnInfo
    private int mallStroeFlag;

    /**
     * 是否允许自采 0 否 1 是 （加盟店）
     */
    @ColumnInfo
    private int allowPurchase;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getUpOrg() {
        return upOrg;
    }

    public void setUpOrg(String upOrg) {
        this.upOrg = upOrg;
    }

    public String getUpOrgName() {
        return upOrgName;
    }

    public void setUpOrgName(String upOrgName) {
        this.upOrgName = upOrgName;
    }

    public String getAskOrg() {
        return askOrg;
    }

    public void setAskOrg(String askOrg) {
        this.askOrg = askOrg;
    }

    public String getAskOrgName() {
        return askOrgName;
    }

    public void setAskOrgName(String askOrgName) {
        this.askOrgName = askOrgName;
    }

    public int getBalanceRate() {
        return balanceRate;
    }

    public void setBalanceRate(int balanceRate) {
        this.balanceRate = balanceRate;
    }

    public int getPostPrice() {
        return postPrice;
    }

    public void setPostPrice(int postPrice) {
        this.postPrice = postPrice;
    }

    public double getAddRate() {
        return addRate;
    }

    public void setAddRate(double addRate) {
        this.addRate = addRate;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getAreaPath() {
        return areaPath;
    }

    public void setAreaPath(String areaPath) {
        this.areaPath = areaPath;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getContacts() {
        return contacts;
    }

    public void setContacts(String contacts) {
        this.contacts = contacts;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getOrderTel() {
        return orderTel;
    }

    public void setOrderTel(String orderTel) {
        this.orderTel = orderTel;
    }

    public String getPrintName() {
        return printName;
    }

    public void setPrintName(String printName) {
        this.printName = printName;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public double getAcreage() {
        return acreage;
    }

    public void setAcreage(double acreage) {
        this.acreage = acreage;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public int getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(int deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
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

    public int getAuthFlag() {
        return authFlag;
    }

    public void setAuthFlag(int authFlag) {
        this.authFlag = authFlag;
    }

    public String getThirdNo() {
        return thirdNo;
    }

    public void setThirdNo(String thirdNo) {
        this.thirdNo = thirdNo;
    }

    public double getCreditAmount() {
        return creditAmount;
    }

    public void setCreditAmount(double creditAmount) {
        this.creditAmount = creditAmount;
    }

    public double getCreditAmountUsed() {
        return creditAmountUsed;
    }

    public void setCreditAmountUsed(double creditAmountUsed) {
        this.creditAmountUsed = creditAmountUsed;
    }

    public double getChargeLimit() {
        return chargeLimit;
    }

    public void setChargeLimit(double chargeLimit) {
        this.chargeLimit = chargeLimit;
    }

    public double getChargeLimitUsed() {
        return chargeLimitUsed;
    }

    public void setChargeLimitUsed(double chargeLimitUsed) {
        this.chargeLimitUsed = chargeLimitUsed;
    }

    public int getDefaultFlag() {
        return defaultFlag;
    }

    public void setDefaultFlag(int defaultFlag) {
        this.defaultFlag = defaultFlag;
    }

    public String getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(String warehouseId) {
        this.warehouseId = warehouseId;
    }

    public String getWarehouseNo() {
        return warehouseNo;
    }

    public void setWarehouseNo(String warehouseNo) {
        this.warehouseNo = warehouseNo;
    }

    public int getStorePaySetting() {
        return storePaySetting;
    }

    public void setStorePaySetting(int storePaySetting) {
        this.storePaySetting = storePaySetting;
    }

    public String getMallStart() {
        return mallStart;
    }

    public void setMallStart(String mallStart) {
        this.mallStart = mallStart;
    }

    public String getMallEnd() {
        return mallEnd;
    }

    public void setMallEnd(String mallEnd) {
        this.mallEnd = mallEnd;
    }

    public int getMallBusinessFlag() {
        return mallBusinessFlag;
    }

    public void setMallBusinessFlag(int mallBusinessFlag) {
        this.mallBusinessFlag = mallBusinessFlag;
    }

    public int getMallStroeFlag() {
        return mallStroeFlag;
    }

    public void setMallStroeFlag(int mallStroeFlag) {
        this.mallStroeFlag = mallStroeFlag;
    }

    public int getAllowPurchase() {
        return allowPurchase;
    }

    public void setAllowPurchase(int allowPurchase) {
        this.allowPurchase = allowPurchase;
    }
}
