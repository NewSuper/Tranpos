package com.newsuper.t.consumer.function.person.internal;

import com.newsuper.t.consumer.bean.DepositDetailBean;
import com.newsuper.t.consumer.function.top.internal.IBaseView;

/**
 * Create by Administrator on 2019/6/17 0017
 */
public interface IDepositDetailView extends IBaseView {
    void showDataToView(DepositDetailBean bean);
    void loadFail();
}
