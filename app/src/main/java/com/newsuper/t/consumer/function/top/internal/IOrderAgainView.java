package com.newsuper.t.consumer.function.top.internal;


import com.newsuper.t.consumer.bean.OrderAgainBean;

public interface IOrderAgainView extends IBaseView {
    void getOrderGoods(OrderAgainBean bean);
    void getOrderGoodsFail();
}
