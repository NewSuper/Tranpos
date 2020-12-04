package com.newsuper.t.markert.entity.state;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@IntDef({PayMode.PAY,
        PayMode.REJECTED_ORDER,
        PayMode.REJECTED_PRODUCT})
@Retention(RetentionPolicy.SOURCE)
public @interface PayMode {
    int PAY = 0;//付款
    int REJECTED_ORDER = 1;//退款按单
    int REJECTED_PRODUCT = 2;//退款按商品
}
