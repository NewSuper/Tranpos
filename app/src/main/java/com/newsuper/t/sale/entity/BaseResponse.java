package com.newsuper.t.sale.entity;

public class BaseResponse {
    public static final int SUCCESS = 0;
    public static final int MONEY_NOT_ENOUGH = -9;
    /**
     * 0成功  其他都是失败
     */
    private int code;

    /**
     * 提示信息
     */
    private String msg;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
