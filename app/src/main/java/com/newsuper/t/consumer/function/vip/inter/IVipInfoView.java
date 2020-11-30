package com.newsuper.t.consumer.function.vip.inter;

import com.xunjoy.lewaimai.consumer.bean.VipInfoBean;
import com.xunjoy.lewaimai.consumer.bean.VipTopInfoBean;
import com.xunjoy.lewaimai.consumer.function.top.internal.IBaseView;

/**
 * Created by Administrator on 2017/7/1 0001.
 */

public interface IVipInfoView extends IBaseView {
    void loadVipTopInfo(VipTopInfoBean vipInfo);
    void loadVipInfo(VipInfoBean vipInfo);
    void loadFail();

}
