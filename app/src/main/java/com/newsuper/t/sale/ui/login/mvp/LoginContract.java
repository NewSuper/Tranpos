package com.newsuper.t.sale.ui.login.mvp;

import com.trans.network.callback.StringCallback;
import com.newsuper.t.sale.base.mvp.IBaseModel;
import com.newsuper.t.sale.base.mvp.IBaseView;
import com.newsuper.t.sale.entity.EntityResponse;
import com.newsuper.t.sale.entity.Worker;

public interface LoginContract {

    interface Model extends IBaseModel {
        void login(String user_name, String pwd, StringCallback callback);
    }

    interface View extends IBaseView{
        void loginSuccess(Worker resp);
    }

    interface Presenter{
        void startLogin(String user_name, String pwd);
    }
}
