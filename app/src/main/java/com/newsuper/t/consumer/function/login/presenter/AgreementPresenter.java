package com.newsuper.t.consumer.function.login.presenter;

import com.google.gson.Gson;
import com.newsuper.t.consumer.bean.AgreementBean;
import com.newsuper.t.consumer.bean.LoginBean;
import com.newsuper.t.consumer.function.login.internal.IAgreementView;
import com.newsuper.t.consumer.function.login.request.LoginRequest;
import com.newsuper.t.consumer.manager.HttpManager;
import com.newsuper.t.consumer.manager.listener.HttpRequestListener;
import com.newsuper.t.consumer.utils.UrlConst;

import java.util.HashMap;

/**
 * Created by Administrator on 2017/8/15 0015.
 */

public class AgreementPresenter {
    private IAgreementView agreementView;
    public AgreementPresenter(IAgreementView agreementView){
        this.agreementView = agreementView;
    }

    public void getData(String admin_id,String appId){
        HashMap<String,String> map = LoginRequest.getUserXieYiRequest(admin_id,appId);
        HttpManager.sendRequest(UrlConst.USER_XIEYI, map, new HttpRequestListener() {
            @Override
            public void onRequestSuccess(String response) {
                AgreementBean bean = new Gson().fromJson(response,AgreementBean.class);
                agreementView.loadData(bean);
            }

            @Override
            public void onRequestFail(String result, String code) {
                agreementView.dialogDismiss();
                agreementView.loadFail();
                agreementView.showToast(result);

            }

            @Override
            public void onCompleted() {
                agreementView.dialogDismiss();
            }
        });
    }
}
