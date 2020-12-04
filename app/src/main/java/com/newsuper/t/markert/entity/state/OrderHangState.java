package com.newsuper.t.markert.entity.state;
import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


@StringDef({OrderHangState.HANG,
        OrderHangState.DELETE,
        OrderHangState.TAKE,
        OrderHangState.TAKE_PRETREATMENT})
@Retention(RetentionPolicy.SOURCE)
public @interface OrderHangState {
    String HANG = "hang";//挂单
    String DELETE = "delete";//删除挂单
    String TAKE = "take";//取单
    String TAKE_PRETREATMENT = "pretreatment";//取单
}
