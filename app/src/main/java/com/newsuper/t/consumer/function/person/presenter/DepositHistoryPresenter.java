package com.newsuper.t.consumer.function.person.presenter;

import com.google.gson.Gson;
import com.xunjoy.lewaimai.consumer.bean.DepositHistoryBean;
import com.xunjoy.lewaimai.consumer.function.person.internal.IDepositHistoryView;
import com.xunjoy.lewaimai.consumer.manager.HttpManager;
import com.xunjoy.lewaimai.consumer.manager.listener.HttpRequestListener;

import java.util.Map;

/**
 * Create by Administrator on 2019/6/17 0017
 */
public class DepositHistoryPresenter {
    private IDepositHistoryView mDepositHistoryView;
    public DepositHistoryPresenter(IDepositHistoryView mDepositHistoryView) {
        this.mDepositHistoryView = mDepositHistoryView;
    }
    public void loadData(String url, final Map<String, String> map) {
        HttpManager.sendRequest(url, map, new HttpRequestListener() {
            @Override
            public void onRequestSuccess(String response) {
                DepositHistoryBean bean = new Gson().fromJson(response,DepositHistoryBean.class);
                mDepositHistoryView.showDataToView(bean);
            }

            @Override
            public void onRequestFail(String result, String code) {
                mDepositHistoryView.showToast(result);
                mDepositHistoryView.loadFail();
            }

            @Override
            public void onCompleted() {
                mDepositHistoryView.dialogDismiss();
            }
        });
    }
}
