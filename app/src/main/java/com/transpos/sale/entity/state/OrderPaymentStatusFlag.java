package com.transpos.sale.entity.state;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.SOURCE)
@IntDef({OrderPaymentStatusFlag.NO_PAY_STATE,OrderPaymentStatusFlag.PAY_COMPLETE_STATE,
        OrderPaymentStatusFlag.SEGMENT_PAY_STATE,OrderPaymentStatusFlag.PAY_ERROR_STATE})
public @interface OrderPaymentStatusFlag {
    //未付款(0),已付款(1),部分付款(2),付款失败(3);
    int NO_PAY_STATE = 0;
    int PAY_COMPLETE_STATE = 1;
    int SEGMENT_PAY_STATE = 2;
    int PAY_ERROR_STATE = 3;
}
