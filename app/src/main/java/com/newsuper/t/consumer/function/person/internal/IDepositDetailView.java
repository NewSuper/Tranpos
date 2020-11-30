package com.newsuper.t.consumer.function.person.internal;

import com.xunjoy.lewaimai.consumer.bean.DepositDetailBean;
import com.xunjoy.lewaimai.consumer.function.top.internal.IBaseView;

/**
 * Create by Administrator on 2019/6/17 0017
 */
public interface IDepositDetailView extends IBaseView {
    void showDataToView(DepositDetailBean bean);
    void loadFail();
}
