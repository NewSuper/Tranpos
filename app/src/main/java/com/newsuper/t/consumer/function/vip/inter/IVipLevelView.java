package com.newsuper.t.consumer.function.vip.inter;

import com.xunjoy.lewaimai.consumer.bean.VipLevelBean;
import com.xunjoy.lewaimai.consumer.function.top.internal.IBaseView;

/**
 * Create by Administrator on 2019/7/1 0001
 */
public interface IVipLevelView extends IBaseView {
    void showDataToView(VipLevelBean bean);
    void loadFail();
}
