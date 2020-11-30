package com.newsuper.t.consumer.function.distribution.internal;

import com.newsuper.t.consumer.bean.PaotuiPayOrderInfo;
import com.newsuper.t.consumer.function.top.internal.IBaseView;

public interface IPayOrderSuccessView extends IBaseView {
    void getPayOrderInfo(PaotuiPayOrderInfo info);
    void getFaile();
}
