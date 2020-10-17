package com.transpos.market.ui.login.mvp;

import com.trans.network.callback.StringCallback;
import com.transpos.market.base.mvp.IBaseModel;
import com.transpos.market.base.mvp.IBaseView;
import com.transpos.market.entity.Worker;


public interface LoginContract {

    interface Model extends IBaseModel {
        void login(String user_name, String pwd, StringCallback callback);
    }

    interface View extends IBaseView {
        void loginSuccess(Worker resp);
    }

    interface Presenter{
        void startLogin(String user_name, String pwd);
    }
}
