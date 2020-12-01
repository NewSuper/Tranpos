package com.newsuper.t.consumer.function.vip.inter;

import com.newsuper.t.consumer.bean.VipChargeBean;
import com.newsuper.t.consumer.function.top.internal.IBaseView;

/**
 * Created by Administrator on 2017/7/1 0001.
 */

public interface IVipChargeView extends IBaseView {
    void chargeVip(VipChargeBean vipInfo);
    void loadFail();
}
