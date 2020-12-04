package com.newsuper.t.markert.ui.register.mvp;

import com.trans.network.callback.StringCallback;
import com.newsuper.t.markert.base.mvp.IBaseModel;
import com.newsuper.t.markert.base.mvp.IBaseView;
import com.newsuper.t.markert.entity.OpenResponse;
import com.newsuper.t.markert.entity.RegistrationCode;


public interface RegisterContract {

    interface Model extends IBaseModel {
        void register(String authCode, StringCallback stringCallback);
    }

    interface View extends IBaseView {
        void registerSuccess(OpenResponse<RegistrationCode> result);
    }

    interface Presenter {

        /**
         * 注册
         */
        void register(String authCode);
    }
}
