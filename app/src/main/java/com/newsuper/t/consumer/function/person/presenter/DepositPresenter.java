package com.newsuper.t.consumer.function.person.presenter;

import com.google.gson.Gson;
import com.xunjoy.lewaimai.consumer.bean.DepositBean;
import com.xunjoy.lewaimai.consumer.function.person.internal.IDepositView;
import com.xunjoy.lewaimai.consumer.manager.HttpManager;
import com.xunjoy.lewaimai.consumer.manager.listener.HttpRequestListener;

import java.util.Map;

/**
 * Create by Administrator on 2019/6/17 0017
 */
public class DepositPresenter {
    private IDepositView mDepositView;
    public DepositPresenter(IDepositView mDepositView) {
        this.mDepositView = mDepositView;
    }
    public void loadData(String url, final Map<String, String> map) {
        HttpManager.sendRequest(url, map, new HttpRequestListener() {
            @Override
            public void onRequestSuccess(String response) {
                DepositBean bean = new Gson().fromJson(response,DepositBean.class);
                mDepositView.showDataToView(bean);
            }

            @Override
            public void onRequestFail(String result, String code) {
                mDepositView.showToast(result);
                mDepositView.loadFail();
            }

            @Override
            public void onCompleted() {
                mDepositView.dialogDismiss();
            }
        });
    }
}
