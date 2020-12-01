package com.newsuper.t.consumer.function.login.internal;


import com.newsuper.t.consumer.function.top.internal.IBaseView;

public interface IGetBackPassView extends IBaseView {
    void sendCodeFail();
    void sendCodeSuccess(String token);
    void getBackSuccess();
    void getBackFail();
}
