package com.newsuper.t.consumer.function.vip.inter;

import com.xunjoy.lewaimai.consumer.bean.VipChargeInfoBean;

/**
 * Created by Administrator on 2017/12/5 0005.
 */

public interface ISelectChargeMoney {
    void selectChargeMoney(VipChargeInfoBean.ChargeSend money);
}
