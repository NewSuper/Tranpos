package com.newsuper.t.sale.entity;

public class BasePagerResponse<T> extends BaseResponse {

    private BasePagerResponseData<T> data;

    public BasePagerResponseData<T> getData() {
        return data;
    }

    public void setData(BasePagerResponseData<T> data) {
        this.data = data;
    }
}
