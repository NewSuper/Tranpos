package com.newsuper.t.consumer.function.login.internal;

import com.xunjoy.lewaimai.consumer.bean.CustomerInfoBean;
import com.xunjoy.lewaimai.consumer.function.top.internal.IBaseView;

/**
 * Created by Administrator on 2017/6/1 0001.
 */

public interface ILoginView extends IBaseView{
    void loginSuccess(String token, String bind);
    void sendVerificationCode(String token);
    void sendFail();
    void loginFail();
    void getUserInfo(CustomerInfoBean bean);
    void getUserFail();
}
