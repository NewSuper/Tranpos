package com.newsuper.t.consumer.function.login.internal;

import com.xunjoy.lewaimai.consumer.function.top.internal.IBaseView;

/**
 * Created by Administrator on 2017/6/1 0001.
 */

public interface IGetBackPassView extends IBaseView {
    void sendCodeFail();
    void sendCodeSuccess(String token);
    void getBackSuccess();
    void getBackFail();
}
