package com.newsuper.t.markert.ui.login.mvp;

import com.trans.network.callback.StringCallback;
import com.newsuper.t.markert.base.mvp.IBaseModel;
import com.newsuper.t.markert.base.mvp.IBaseView;
import com.newsuper.t.markert.entity.Worker;


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