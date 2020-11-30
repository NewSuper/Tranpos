package com.newsuper.t.consumer.function.distribution.internal;

import com.newsuper.t.consumer.bean.PaoTuiBean;
import com.newsuper.t.consumer.function.top.internal.IBaseView;

public interface IDistributionView extends IBaseView {
    void onLoadData(PaoTuiBean bean);
    void onLoadFail();
}
