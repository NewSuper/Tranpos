package com.newsuper.t.consumer.function.person.internal;

import com.xunjoy.lewaimai.consumer.bean.DepositHistoryBean;
import com.xunjoy.lewaimai.consumer.function.top.internal.IBaseView;

/**
 * Create by Administrator on 2019/6/17 0017
 */
public interface IDepositHistoryView extends IBaseView {
    void showDataToView(DepositHistoryBean bean);
    void loadFail();
}
