package com.newsuper.t.consumer.function.login.presenter;

import com.google.gson.Gson;
import com.xunjoy.lewaimai.consumer.bean.APPInfoBean;
import com.xunjoy.lewaimai.consumer.function.login.internal.IAppInfo;
import com.xunjoy.lewaimai.consumer.function.login.request.APPInfoRequest;
import com.xunjoy.lewaimai.consumer.manager.HttpManager;
import com.xunjoy.lewaimai.consumer.manager.listener.HttpRequestListener;
import com.xunjoy.lewaimai.consumer.utils.UrlConst;
import java.util.HashMap;


public class AppInfoPresenter {
    private IAppInfo iAppInfo;
    public AppInfoPresenter(IAppInfo iAppInfo){
        this.iAppInfo = iAppInfo;
    }
    public void loadAPPInfo(String admin_id){
        HashMap<String,String> map = APPInfoRequest.getAPPInfoRequest(admin_id);
        HttpManager.sendRequest(UrlConst.APP_INFO, map, new HttpRequestListener() {
            @Override
            public void onRequestSuccess(String response) {
                iAppInfo.dialogDismiss();
                APPInfoBean bean = new Gson().fromJson(response,APPInfoBean.class);
                iAppInfo.loadAppInfo(bean);
            }

            @Override
            public void onRequestFail(String result, String code) {
                iAppInfo.dialogDismiss();
                iAppInfo.showToast(result);
                iAppInfo.loadFail();
            }

            @Override
            public void onCompleted() {
                iAppInfo.dialogDismiss();
            }
        });
    }
}
