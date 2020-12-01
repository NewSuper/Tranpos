package com.newsuper.t.consumer.function.login.internal;

import com.newsuper.t.consumer.bean.CustomerInfoBean;
import com.newsuper.t.consumer.function.top.internal.IBaseView;

public interface ILoginView extends IBaseView {
    void loginSuccess(String token, String bind);
    void sendVerificationCode(String token);
    void sendFail();
    void loginFail();
    void getUserInfo(CustomerInfoBean bean);
    void getUserFail();
}
