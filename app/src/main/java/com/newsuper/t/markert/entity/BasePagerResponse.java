package com.newsuper.t.markert.entity;

public class BasePagerResponse<T> extends BaseResponse {

    private BasePagerResponseData<T> data;

    public BasePagerResponseData<T> getData() {
        return data;
    }

    public void setData(BasePagerResponseData<T> data) {
        this.data = data;
    }
}
