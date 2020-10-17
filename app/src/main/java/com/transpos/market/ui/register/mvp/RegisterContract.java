package com.transpos.market.ui.register.mvp;

import com.trans.network.callback.StringCallback;
import com.transpos.market.base.mvp.IBaseModel;
import com.transpos.market.base.mvp.IBaseView;
import com.transpos.market.entity.OpenResponse;
import com.transpos.market.entity.RegistrationCode;


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
