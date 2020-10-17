package com.transpos.sale.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;

@Entity(tableName = "pos_product_code")
public class ProductCode extends BaseEntity {

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
    /// 编码
    /// </summary>
    @ColumnInfo
    private String code;

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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
