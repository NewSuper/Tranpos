package com.transpos.sale.ui.register.mvp;

import com.trans.network.callback.StringCallback;
import com.transpos.sale.base.mvp.IBaseModel;
import com.transpos.sale.base.mvp.IBaseView;
import com.transpos.sale.entity.OpenResponse;
import com.transpos.sale.entity.RegistrationCode;

public interface RegisterContract {

    interface Model extends IBaseModel{
        void register(String authCode, StringCallback stringCallback);
    }

    interface View extends IBaseView{
        void registerSuccess(OpenResponse<RegistrationCode> result);
    }

    interface Presenter {

        /**
         * 注册
         */
        void register(String authCode);
    }
}
