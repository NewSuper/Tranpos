package com.transpos.sale.entity;

import java.util.List;

public class BaseListResponse<T> extends BaseResponse {

    /// <summary>
    /// 返回数据
    /// </summary>
    private List<T> data;

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}
