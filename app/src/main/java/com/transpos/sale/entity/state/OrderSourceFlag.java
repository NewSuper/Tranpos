package com.transpos.sale.entity.state;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.SOURCE)
@IntDef({OrderSourceFlag.CASH_PLATFORM,OrderSourceFlag.ASSISTANT_CASH,OrderSourceFlag.SCAN_CODE,
        OrderSourceFlag.NET_SHOP,OrderSourceFlag.MEITUAN,OrderSourceFlag.ELME,OrderSourceFlag.CLOUD})
public @interface OrderSourceFlag {
    //收银台(0),
    //    自助收银机(1),
    //    扫码购(2),
    //    网店(20),
    //    拼团(21),
    //    //美团外卖(30),
    //    饿了么外卖(31),
    //    云秤(40);

    int CASH_PLATFORM = 0;
    int ASSISTANT_CASH = 1;
    int SCAN_CODE = 2;
    int NET_SHOP = 20;
    int MEITUAN = 30;
    int ELME = 31;
    int CLOUD = 40;
}
