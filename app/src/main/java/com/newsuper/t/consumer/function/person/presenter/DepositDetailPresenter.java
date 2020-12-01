package com.newsuper.t.consumer.function.person.presenter;

import com.google.gson.Gson;
import com.newsuper.t.consumer.bean.DepositDetailBean;
import com.newsuper.t.consumer.function.person.internal.IDepositDetailView;
import com.newsuper.t.consumer.manager.HttpManager;
import com.newsuper.t.consumer.manager.listener.HttpRequestListener;

import java.util.Map;

/**
 * Create by Administrator on 2019/6/17 0017
 */
public class DepositDetailPresenter {
    private IDepositDetailView mDepositDetailView;
    public DepositDetailPresenter(IDepositDetailView mDepositDetailView) {
        this.mDepositDetailView = mDepositDetailView;
    }
    public void loadData(String url, final Map<String, String> map) {
        HttpManager.sendRequest(url, map, new HttpRequestListener() {
            @Override
            public void onRequestSuccess(String response) {
                DepositDetailBean bean = new Gson().fromJson(response,DepositDetailBean.class);
                mDepositDetailView.showDataToView(bean);
            }

            @Override
            public void onRequestFail(String result, String code) {
                mDepositDetailView.showToast(result);
                mDepositDetailView.loadFail();
            }

            @Override
            public void onCompleted() {
                mDepositDetailView.dialogDismiss();
            }
        });
    }
}
