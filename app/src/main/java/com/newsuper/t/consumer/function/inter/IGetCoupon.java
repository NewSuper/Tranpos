package com.newsuper.t.consumer.function.inter;


import com.newsuper.t.consumer.bean.GoodsListBean;
import com.newsuper.t.consumer.function.top.internal.IBaseView;

//领取优惠券
public interface IGetCoupon extends IBaseView {

    void getCoupon(GoodsListBean.Coupon coupon);

}
