package com.newsuper.t.consumer.function.person.internal;

import com.xunjoy.lewaimai.consumer.bean.TraceBean;
import com.xunjoy.lewaimai.consumer.function.top.internal.IBaseView;

/**
 * Create by Administrator on 2019/4/25 0025
 */
public interface ITraceView extends IBaseView {
    void showDataToView(TraceBean bean);
    void loadFail();
    void deleteTraceSuss();
}
