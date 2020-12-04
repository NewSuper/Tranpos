package com.newsuper.t.markert.entity.state;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.SOURCE)
@IntDef({PostWayFlag.ZITI_STATE,PostWayFlag.WS_STATE,PostWayFlag.EAT_STATE,
        PostWayFlag.WD_STATE,PostWayFlag.WM_STATE,PostWayFlag.EMAIL_STATE})
public @interface PostWayFlag {
    //自提(0),外送(1),堂食(2),外带(3),外卖(4),快递(5);
    int ZITI_STATE = 0;
    int WS_STATE = 1;
    int EAT_STATE = 2;
    int WD_STATE = 3;
    int WM_STATE = 4;
    int EMAIL_STATE = 5;
}
