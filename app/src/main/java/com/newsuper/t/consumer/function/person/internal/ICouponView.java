package com.newsuper.t.consumer.function.person.internal;

import com.xunjoy.lewaimai.consumer.bean.CouponBean;
import com.xunjoy.lewaimai.consumer.bean.PaotuiCouponBean;
import com.xunjoy.lewaimai.consumer.function.top.internal.IBaseView;


public interface ICouponView extends IBaseView{
    void showDataToVIew(CouponBean bean);
    void loadFail();
    void loadCoupon(PaotuiCouponBean bean);
    void loadCouponFail();

}
