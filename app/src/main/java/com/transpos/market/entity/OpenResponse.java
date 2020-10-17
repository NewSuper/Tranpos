package com.transpos.market.entity;

public class OpenResponse<T> extends BaseResponse {
    /**
     * 返回数据
     */
    private T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
