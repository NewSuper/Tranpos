package com.newsuper.t.consumer.function.selectgoods.inter;

import com.xunjoy.lewaimai.consumer.function.top.internal.IBaseView;


public interface IGetCouponResponse extends IBaseView {
    void getCouponSuccess();
    void getFail();
}
