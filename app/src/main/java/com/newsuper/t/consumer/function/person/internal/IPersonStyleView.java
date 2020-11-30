package com.newsuper.t.consumer.function.person.internal;

import com.xunjoy.lewaimai.consumer.bean.PersonCenterBean;
import com.xunjoy.lewaimai.consumer.function.top.internal.IBaseView;

public interface IPersonStyleView extends IBaseView {
    void getStyleData(PersonCenterBean bean);
    void getStyleFail();
}
