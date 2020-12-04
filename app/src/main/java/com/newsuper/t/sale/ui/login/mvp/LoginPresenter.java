package com.newsuper.t.sale.ui.login.mvp;

import com.google.gson.reflect.TypeToken;
import com.trans.network.callback.StringCallback;
import com.trans.network.model.Response;
import com.trans.network.utils.GsonHelper;
import com.newsuper.t.sale.base.mvp.BasePresenter;
import com.newsuper.t.sale.entity.EntityResponse;
import com.newsuper.t.sale.entity.Worker;

public class LoginPresenter extends BasePresenter<LoginContract.Model,LoginContract.View> implements LoginContract.Presenter {
    @Override
    protected LoginContract.Model createModule() {
        return new LoginModel();
    }

    @Override
    public void startLogin(String user_name, String pwd) {
        getView().showLoading();
        getModule().login(user_name, pwd, new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {
                EntityResponse<Worker> resp =  GsonHelper.fromJson(response.body(),new TypeToken<EntityResponse<Worker>>(){}.getType());
                if(resp.getCode() == 0){
                    getView().loginSuccess(resp.getData());
                } else {
                    getView().onError(response,resp.getMsg());
                }
            }

            @Override
            public void onError(Response<String> response) {
                getView().onError(response,response.message());
            }

            @Override
            public void onFinish() {
                getView().dismissLoading();
            }
        });
    }
}
