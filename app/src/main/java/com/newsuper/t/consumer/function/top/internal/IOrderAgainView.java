package com.newsuper.t.consumer.function.top.internal;

import com.xunjoy.lewaimai.consumer.bean.OrderAgainBean;

public interface IOrderAgainView extends IBaseView {
    void getOrderGoods(OrderAgainBean bean);
    void getOrderGoodsFail();
}
