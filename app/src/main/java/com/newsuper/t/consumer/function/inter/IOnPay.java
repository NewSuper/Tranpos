package com.newsuper.t.consumer.function.inter;


import com.newsuper.t.consumer.function.top.internal.IBaseView;

public interface IOnPay extends IBaseView {

    void onPay(String order_id, String pay_type);
}
