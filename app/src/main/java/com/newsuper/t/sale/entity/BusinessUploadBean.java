package com.newsuper.t.sale.entity;

public class BusinessUploadBean {
    private String serverId;
    private String tradeNo;
    private int status;
    private String msg;

    public String getServerId() {
        return serverId;
    }

    public BusinessUploadBean setServerId(String serverId) {
        this.serverId = serverId;
        return this;
    }

    public String getTradeNo() {
        return tradeNo;
    }

    public BusinessUploadBean setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
        return this;
    }

    public int getStatus() {
        return status;
    }

    public BusinessUploadBean setStatus(int status) {
        this.status = status;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public BusinessUploadBean setMsg(String msg) {
        this.msg = msg;
        return this;
    }
}
