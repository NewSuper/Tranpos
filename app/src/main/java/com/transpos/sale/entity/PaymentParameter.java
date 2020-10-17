package com.transpos.sale.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;

@Entity(tableName = "pos_payment_parameter")
public class PaymentParameter extends BaseEntity {

    /// <summary>
    /// 编号
    /// </summary>
    @ColumnInfo
    private String no;

    /// <summary>
    /// 门店id
    /// </summary>
    @ColumnInfo
    private String storeId;

    /// <summary>
    /// 支付类型
    /// </summary>
    @ColumnInfo
    private String sign;

    /// <summary>
    /// 支付参数
    /// </summary>
    @ColumnInfo
    private String pbody;

    /// <summary>
    /// 是否启用
    /// </summary>
    @ColumnInfo
    private int enabled;

    /// <summary>
    /// 证书
    /// </summary>
    @ColumnInfo
    private String certText;

    /// <summary>
    /// 本地配置标识
    /// </summary>
    @ColumnInfo
    private int localFlag;

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getPbody() {
        return pbody;
    }

    public void setPbody(String pbody) {
        this.pbody = pbody;
    }

    public int getEnabled() {
        return enabled;
    }

    public void setEnabled(int enabled) {
        this.enabled = enabled;
    }

    public String getCertText() {
        return certText;
    }

    public void setCertText(String certText) {
        this.certText = certText;
    }

    public int getLocalFlag() {
        return localFlag;
    }

    public void setLocalFlag(int localFlag) {
        this.localFlag = localFlag;
    }
}
