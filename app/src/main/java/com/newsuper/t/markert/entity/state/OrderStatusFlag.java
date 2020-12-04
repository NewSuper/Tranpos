package com.newsuper.t.markert.entity.state;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.SOURCE)
@IntDef({OrderStatusFlag.WAIT_STATE,OrderStatusFlag.PAY_STATE,OrderStatusFlag.RETURN_STATE,
        OrderStatusFlag.CANCEL_STATE,OrderStatusFlag.COMPLETE_STATE,OrderStatusFlag.SEGMENT_RETURN_STATE,OrderStatusFlag.HANG_STATE})
public @interface OrderStatusFlag {
//等待付款(0),已支付(1),已退单(2),已取消(3),已完成(4),部分退款(5);
    int WAIT_STATE = 0;
    int PAY_STATE = 1;
    int RETURN_STATE = 2;
    int CANCEL_STATE = 3;
    int COMPLETE_STATE = 4;
    int SEGMENT_RETURN_STATE = 5;
    int HANG_STATE = 6;
}
