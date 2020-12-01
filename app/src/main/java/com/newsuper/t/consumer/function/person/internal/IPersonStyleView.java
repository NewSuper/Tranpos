package com.newsuper.t.consumer.function.person.internal;

import com.newsuper.t.consumer.bean.PersonCenterBean;
import com.newsuper.t.consumer.function.top.internal.IBaseView;

public interface IPersonStyleView extends IBaseView {
    void getStyleData(PersonCenterBean bean);
    void getStyleFail();
}
