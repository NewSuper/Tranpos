package com.newsuper.t.consumer.function.vip.inter;

import com.newsuper.t.consumer.bean.VipCommitInfoBean;
import com.newsuper.t.consumer.function.top.internal.IBaseView;

/**
 * Created by Administrator on 2017/7/1 0001.
 */

public interface IVipCommitView extends IBaseView {
    void commitVipInfo(VipCommitInfoBean vipCommitInfoBean);
    void loadFail();

}
