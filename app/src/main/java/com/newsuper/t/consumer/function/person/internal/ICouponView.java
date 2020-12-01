package com.newsuper.t.consumer.function.person.internal;

import com.newsuper.t.consumer.bean.CouponBean;
import com.newsuper.t.consumer.bean.PaotuiCouponBean;
import com.newsuper.t.consumer.function.top.internal.IBaseView;


public interface ICouponView extends IBaseView{
    void showDataToVIew(CouponBean bean);
    void loadFail();
    void loadCoupon(PaotuiCouponBean bean);
    void loadCouponFail();

}
