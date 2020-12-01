package com.newsuper.t.consumer.function.login.internal;


import com.newsuper.t.consumer.function.top.internal.IBaseView;

public interface IBindPhoneView extends IBaseView {
    void sendVerificationCode(String token);
    void sendFail();
    void bindSuccess();
    void bindFail(String s);
}
