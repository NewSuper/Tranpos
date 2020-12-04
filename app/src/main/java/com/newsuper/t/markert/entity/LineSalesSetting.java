package com.newsuper.t.markert.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;

@Entity(tableName = "pos_line_sales_setting")
public class LineSalesSetting extends BaseEntity {

    /// <summary>
    /// 属性
    /// </summary>
    @ColumnInfo
    private String setKey;

    /// <summary>
    /// 值  属性值(0-否 1-是 ；注：特殊属性除外)actual、yuan、jiao、yuan_round、jiao_round
    /// </summary>
    @ColumnInfo
    private String setValue;

    /// <summary>
    /// 模板Id
    /// </summary>
    @ColumnInfo
    private String templateId;

    /// <summary>
    /// 模板编号
    /// </summary>
    @ColumnInfo
    private String templateNo;

    public String getSetKey() {
        return setKey;
    }

    public void setSetKey(String setKey) {
        this.setKey = setKey;
    }

    public String getSetValue() {
        return setValue;
    }

    public void setSetValue(String setValue) {
        this.setValue = setValue;
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public String getTemplateNo() {
        return templateNo;
    }

    public void setTemplateNo(String templateNo) {
        this.templateNo = templateNo;
    }
}
