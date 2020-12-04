package com.newsuper.t.sale.ui.register.mvp;

import com.trans.network.callback.StringCallback;
import com.newsuper.t.sale.base.mvp.IBaseModel;
import com.newsuper.t.sale.base.mvp.IBaseView;
import com.newsuper.t.sale.entity.OpenResponse;
import com.newsuper.t.sale.entity.RegistrationCode;

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
