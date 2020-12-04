package com.newsuper.t.sale.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;

/**
 * 线上系统设置
 */
@Entity(tableName = "pos_line_system_set")
public class LineSystemSet extends BaseEntity {

    /// <summary>
    /// 组名
    /// </summary>
    @ColumnInfo
    private String groupName;

    /// <summary>
    /// 名称
    /// </summary>
    @ColumnInfo
    private String name;

    /// <summary>
    /// 键
    /// </summary>
    @ColumnInfo
    private String setKey;

    /// <summary>
    /// 值
    /// </summary>
    @ColumnInfo
    private String setValue;

    /// <summary>
    /// 备注
    /// </summary>
    @ColumnInfo
    private String memo;

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

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

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }
}
