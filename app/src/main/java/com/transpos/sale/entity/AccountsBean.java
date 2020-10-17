package com.transpos.sale.entity;

import java.io.Serializable;

public class AccountsBean implements Serializable {
    //总金额
    private String totalMoney;
    //件数
    private int totalCount;
    //优惠
    private String reduction = "0";

    public String getTotalMoney() {
        return totalMoney;
    }

    public AccountsBean setTotalMoney(String totalMoney) {
        this.totalMoney = totalMoney;
        return this;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public AccountsBean setTotalCount(int totalCount) {
        this.totalCount = totalCount;
        return this;
    }

    public String getReduction() {
        return reduction;
    }

    public AccountsBean setReduction(String reduction) {
        this.reduction = reduction;
        return this;
    }
}
