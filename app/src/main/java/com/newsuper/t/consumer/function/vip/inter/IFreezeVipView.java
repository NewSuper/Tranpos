package com.newsuper.t.consumer.function.vip.inter;

import com.xunjoy.lewaimai.consumer.function.top.internal.IBaseView;

/**
 * Created by Administrator on 2017/12/5 0005.
 */

public interface IFreezeVipView extends IBaseView {
    void freezeFail();
    void freezeSuccess();
}
