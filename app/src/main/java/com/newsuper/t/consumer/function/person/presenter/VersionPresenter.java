package com.newsuper.t.consumer.function.person.presenter;


import com.google.gson.Gson;
import com.xunjoy.lewaimai.consumer.bean.VersionBean;
import com.xunjoy.lewaimai.consumer.function.person.internal.IVersionView;
import com.xunjoy.lewaimai.consumer.manager.HttpManager;
import com.xunjoy.lewaimai.consumer.manager.listener.HttpRequestListener;

import java.util.Map;

public class VersionPresenter {
    private IVersionView mIVersionView;

    public VersionPresenter(IVersionView mIVersionView) {
        this.mIVersionView = mIVersionView;
    }

    public void loadData(String url, Map<String, String> map) {
        HttpManager.sendRequest(url, map, new HttpRequestListener() {
            @Override
            public void onRequestSuccess(String response) {
                mIVersionView.dialogDismiss();
                VersionBean bean = new Gson().fromJson(response.toString(), VersionBean.class);
                mIVersionView.showVersionView(bean);
               // mIVersionView.loadVersion(bean);
            }

            @Override
            public void onRequestFail(String result, String code) {
                mIVersionView.dialogDismiss();
                mIVersionView.showToast(result);
            }

            @Override
            public void onCompleted() {
                mIVersionView.dialogDismiss();
            }
        });
    }

}
