package com.transpos.market.ui.register.mvp;

import com.google.gson.reflect.TypeToken;
import com.trans.network.callback.StringCallback;
import com.trans.network.model.Response;
import com.trans.network.utils.GsonHelper;
import com.transpos.market.base.mvp.BasePresenter;
import com.transpos.market.entity.BaseResponse;
import com.transpos.market.entity.OpenResponse;
import com.transpos.market.entity.RegistrationCode;
import com.transpos.market.utils.UiUtils;


public class ReginsterPresenter extends BasePresenter<RegisterContract.Model,RegisterContract.View> implements RegisterContract.Presenter {
    @Override
    protected RegisterContract.Model createModule() {
        return new RegisterModel();
    }


    @Override
    public void register(String authCode) {
        getModule().register(authCode, new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {
                OpenResponse<RegistrationCode> result = GsonHelper.fromJson(response.body(), new TypeToken<OpenResponse<RegistrationCode>>(){}.getType());
                if(result.getCode() == BaseResponse.SUCCESS){
                    getView().registerSuccess(result);
                } else {
                   // UiUtils.showToastShort(result.getMsg());
                    UiUtils.showToastShort("请填写正确的注册码");
                }
            }

            @Override
            public void onError(Response<String> response) {
                getView().onError(response,response.message());
            }
        });
    }
}
