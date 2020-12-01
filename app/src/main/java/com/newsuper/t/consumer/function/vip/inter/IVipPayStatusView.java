package com.newsuper.t.consumer.function.vip.inter;

import com.newsuper.t.consumer.bean.VipPayStatusBean;
import com.newsuper.t.consumer.function.top.internal.IBaseView;


public interface IVipPayStatusView extends IBaseView {

    void checkStatus(VipPayStatusBean status);
    void loadFail();

}
