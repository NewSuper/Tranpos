package com.newsuper.t.consumer.function.login.internal;


import com.newsuper.t.consumer.bean.AgreementBean;
import com.newsuper.t.consumer.function.top.internal.IBaseView;

public interface IAgreementView extends IBaseView {
    void loadData(AgreementBean bean);
    void loadFail();

}
