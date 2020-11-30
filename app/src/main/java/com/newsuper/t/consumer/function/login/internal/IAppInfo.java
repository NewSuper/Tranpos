package com.newsuper.t.consumer.function.login.internal;


import com.newsuper.t.consumer.bean.APPInfoBean;
import com.newsuper.t.consumer.function.top.internal.IBaseView;

public interface IAppInfo extends IBaseView {
    void loadAppInfo(APPInfoBean bean);
    void loadFail();
}
