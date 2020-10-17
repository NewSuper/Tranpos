package com.transpos.sale.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(tableName = "pos_supplier")
public class Supplier extends BaseEntity {

    /// <summary>
    /// 编号
    /// </summary>
    @ColumnInfo
    private String no;

    /// <summary>
    /// 供应商名称
    /// </summary>
    @ColumnInfo
    private String name;

    /// <summary>
    /// 助记码
    /// </summary>
    @ColumnInfo
    private String rem;

    /// <summary>
    /// 采购周期
    /// </summary>
    @ColumnInfo
    private int purchaseCycle;

    /// <summary>
    /// 经销方式
    /// </summary>
    @ColumnInfo
    private String managerType;

    /// <summary>
    /// 结算机构
    /// </summary>
    @ColumnInfo
    private String dealOrganize;

    /// <summary>
    /// 单据默认价格
    /// </summary>
    @ColumnInfo
    private int defaultPrice;

    /// <summary>
    /// 结算方式
    /// </summary>
    @ColumnInfo
    private int dealType;

    /// <summary>
    /// 结账周期
    /// </summary>
    @ColumnInfo
    private int dealCycle;

    /// <summary>
    /// 月结日期
    /// </summary>
    @ColumnInfo
    private int dealDate;

    /// <summary>
    /// 扣率
    /// </summary>
    @ColumnInfo
    private Double costRate;

    /// <summary>
    /// 保底结算金额
    /// </summary>
    @ColumnInfo
    private Double minAmount;

    /// <summary>
    /// 联系人
    /// </summary>
    @ColumnInfo
    private String contacts;

    /// <summary>
    /// 手机号
    /// </summary>
    //[JsonProperty(PropertyName = "mobile")]
    //[Column("mobile")]
    //private String Mobile;

    /// <summary>
    /// openid
    /// </summary>
    //[JsonProperty(PropertyName = "openid")]
    //[Column("openid")]
    //private String Openid;

    /// <summary>
    /// 座机
    /// </summary>
    @ColumnInfo
    private String tel;

    /// <summary>
    /// 地址
    /// </summary>
    @ColumnInfo
    private String address;

    /// <summary>
    /// 传真
    /// </summary>
    @ColumnInfo
    private String fax;

    /// <summary>
    /// 邮编
    /// </summary>
    @ColumnInfo
    private String postcode;

    /// <summary>
    /// 邮箱
    /// </summary>
    @ColumnInfo
    private String email;

    /// <summary>
    /// 开户行
    /// </summary>
    @ColumnInfo
    private String bankName;

    /// <summary>
    /// 账号
    /// </summary>
    @ColumnInfo
    private String bankCardNo;

    /// <summary>
    /// 税务登记号
    /// </summary>
    @ColumnInfo
    private String taxId;

    /// <summary>
    /// 企业类型
    /// </summary>
    @ColumnInfo
    private int companyType;

    /// <summary>
    /// 冻结账款
    /// </summary>
    @ColumnInfo
    private int frozenMoney;

    /// <summary>
    /// 冻结业务
    /// </summary>
    @ColumnInfo
    private int frozenBusiness;

    /// <summary>
    /// 营业执照存储类型
    /// </summary>
    @ColumnInfo
    private int busStorageType;

    /// <summary>
    /// 营业执照图片
    /// </summary>
    @ColumnInfo
    private String businessPic;

    /// <summary>
    /// 许可证存储类型
    /// </summary>
    @ColumnInfo
    private int licStorageType;

    /// <summary>
    /// 许可证图片
    /// </summary>
    @ColumnInfo
    private String licensePic;

    /// <summary>
    /// 预付款余额
    /// </summary>
    @ColumnInfo
    private Double prePayAmount;

    /// <summary>
    /// 排序
    /// </summary>
    @ColumnInfo
    private int orderNo;

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

    public String getRem() {
        return rem;
    }

    public void setRem(String rem) {
        this.rem = rem;
    }

    public int getPurchaseCycle() {
        return purchaseCycle;
    }

    public void setPurchaseCycle(int purchaseCycle) {
        this.purchaseCycle = purchaseCycle;
    }

    public String getManagerType() {
        return managerType;
    }

    public void setManagerType(String managerType) {
        this.managerType = managerType;
    }

    public String getDealOrganize() {
        return dealOrganize;
    }

    public void setDealOrganize(String dealOrganize) {
        this.dealOrganize = dealOrganize;
    }

    public int getDefaultPrice() {
        return defaultPrice;
    }

    public void setDefaultPrice(int defaultPrice) {
        this.defaultPrice = defaultPrice;
    }

    public int getDealType() {
        return dealType;
    }

    public void setDealType(int dealType) {
        this.dealType = dealType;
    }

    public int getDealCycle() {
        return dealCycle;
    }

    public void setDealCycle(int dealCycle) {
        this.dealCycle = dealCycle;
    }

    public int getDealDate() {
        return dealDate;
    }

    public void setDealDate(int dealDate) {
        this.dealDate = dealDate;
    }

    public Double getCostRate() {
        return costRate;
    }

    public void setCostRate(Double costRate) {
        this.costRate = costRate;
    }

    public Double getMinAmount() {
        return minAmount;
    }

    public void setMinAmount(Double minAmount) {
        this.minAmount = minAmount;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankCardNo() {
        return bankCardNo;
    }

    public void setBankCardNo(String bankCardNo) {
        this.bankCardNo = bankCardNo;
    }

    public String getTaxId() {
        return taxId;
    }

    public void setTaxId(String taxId) {
        this.taxId = taxId;
    }

    public int getCompanyType() {
        return companyType;
    }

    public void setCompanyType(int companyType) {
        this.companyType = companyType;
    }

    public int getFrozenMoney() {
        return frozenMoney;
    }

    public void setFrozenMoney(int frozenMoney) {
        this.frozenMoney = frozenMoney;
    }

    public int getFrozenBusiness() {
        return frozenBusiness;
    }

    public void setFrozenBusiness(int frozenBusiness) {
        this.frozenBusiness = frozenBusiness;
    }

    public int getBusStorageType() {
        return busStorageType;
    }

    public void setBusStorageType(int busStorageType) {
        this.busStorageType = busStorageType;
    }

    public String getBusinessPic() {
        return businessPic;
    }

    public void setBusinessPic(String businessPic) {
        this.businessPic = businessPic;
    }

    public int getLicStorageType() {
        return licStorageType;
    }

    public void setLicStorageType(int licStorageType) {
        this.licStorageType = licStorageType;
    }

    public String getLicensePic() {
        return licensePic;
    }

    public void setLicensePic(String licensePic) {
        this.licensePic = licensePic;
    }

    public Double getPrePayAmount() {
        return prePayAmount;
    }

    public void setPrePayAmount(Double prePayAmount) {
        this.prePayAmount = prePayAmount;
    }

    public int getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(int orderNo) {
        this.orderNo = orderNo;
    }
}
