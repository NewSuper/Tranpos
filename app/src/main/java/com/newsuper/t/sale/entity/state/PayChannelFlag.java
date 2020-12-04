package com.newsuper.t.sale.entity.state;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


/**
 * 无(-1),
 *     原生支付(0),
 *     富友支付(1),
 *     扫呗支付(2),
 *     江西农商行(3),
 *     建行支付(4),
 *     乐刷支付(5),
 *     浙江农信(6);
 */
@IntDef({})
@Retention(RetentionPolicy.SOURCE)
public @interface PayChannelFlag {
    int NONE = -1;
    int NATIVE_PAY = 0;
    int FUYOU_PAY = 1;
    int SAOBEI_PAY = 2;
    int NONGSHANG_PAY = 3;
    int BUILD_PAY = 4;
    int LESHUA_PAY = 5;
    int NONGXIN_PAY = 6;
}
