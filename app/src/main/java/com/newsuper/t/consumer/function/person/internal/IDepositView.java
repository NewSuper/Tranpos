package com.newsuper.t.consumer.function.person.internal;

import com.newsuper.t.consumer.bean.DepositBean;
import com.newsuper.t.consumer.function.top.internal.IBaseView;

/**
 * Create by Administrator on 2019/6/17 0017
 */
public interface IDepositView extends IBaseView {
    void showDataToView(DepositBean bean);
    void loadFail();
}
