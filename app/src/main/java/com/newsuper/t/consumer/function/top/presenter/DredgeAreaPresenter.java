package com.newsuper.t.consumer.function.top.presenter;

import com.google.gson.Gson;
import com.xunjoy.lewaimai.consumer.bean.DredgeAreaBean;
import com.xunjoy.lewaimai.consumer.function.top.internal.IDredgeAreaView;
import com.xunjoy.lewaimai.consumer.manager.HttpManager;
import com.xunjoy.lewaimai.consumer.manager.listener.HttpRequestListener;

import java.util.Map;

/**
 * Create by Administrator on 2019/5/5 0005
 */
public class DredgeAreaPresenter {
    private IDredgeAreaView mDredgeAreaView;
    public DredgeAreaPresenter(IDredgeAreaView mDredgeAreaView) {
        this.mDredgeAreaView = mDredgeAreaView;
    }

    public void loadData(String url, final Map<String, String> map) {
        HttpManager.sendRequest(url, map, new HttpRequestListener() {

            @Override
            public void onRequestSuccess(String response) {
                DredgeAreaBean bean = new Gson().fromJson(response,DredgeAreaBean.class);
                mDredgeAreaView.showLoadData(bean);
            }

            @Override
            public void onRequestFail(String result, String code) {
                mDredgeAreaView.showToast(result);
                mDredgeAreaView.loadFail();
            }

            @Override
            public void onCompleted() {
                mDredgeAreaView.dialogDismiss();
            }
        });
    }
}
