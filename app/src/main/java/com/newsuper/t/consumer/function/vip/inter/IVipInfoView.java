package com.newsuper.t.consumer.function.vip.inter;

import com.newsuper.t.consumer.bean.VipInfoBean;
import com.newsuper.t.consumer.bean.VipTopInfoBean;
import com.newsuper.t.consumer.function.top.internal.IBaseView;

/**
 * Created by Administrator on 2017/7/1 0001.
 */

public interface IVipInfoView extends IBaseView {
    void loadVipTopInfo(VipTopInfoBean vipInfo);
    void loadVipInfo(VipInfoBean vipInfo);
    void loadFail();

}
