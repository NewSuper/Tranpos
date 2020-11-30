package com.newsuper.t.consumer.function.login.internal;

import com.xunjoy.lewaimai.consumer.function.top.internal.IBaseView;

/**
 * Created by Administrator on 2017/10/23 0023.
 */

public interface IBindPhoneView extends IBaseView{
    void sendVerificationCode(String token);
    void sendFail();
    void bindSuccess();
    void bindFail(String s);
}
