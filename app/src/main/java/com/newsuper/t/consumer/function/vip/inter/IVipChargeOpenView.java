package com.newsuper.t.consumer.function.vip.inter;

import com.xunjoy.lewaimai.consumer.bean.VipChargeBean;
import com.xunjoy.lewaimai.consumer.bean.VipChargeInfoBean;
import com.xunjoy.lewaimai.consumer.function.top.internal.IBaseView;

/**
 * Created by Administrator on 2017/7/1 0001.
 */

public interface IVipChargeOpenView extends IBaseView {
    void chargeOpenInfo(VipChargeInfoBean chargeInfo);
    void loadFail();
}
