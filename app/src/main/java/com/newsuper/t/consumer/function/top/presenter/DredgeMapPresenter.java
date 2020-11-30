package com.newsuper.t.consumer.function.top.presenter;

import com.google.gson.Gson;
import com.xunjoy.lewaimai.consumer.bean.DredgeMapBean;
import com.xunjoy.lewaimai.consumer.function.top.internal.IDredgeMapView;
import com.xunjoy.lewaimai.consumer.manager.HttpManager;
import com.xunjoy.lewaimai.consumer.manager.listener.HttpRequestListener;

import java.util.Map;

/**
 * Create by Administrator on 2019/5/6 0006
 */
public class DredgeMapPresenter {
    private IDredgeMapView mDredgeMapView;
    public DredgeMapPresenter(IDredgeMapView mDredgeMapView) {
        this.mDredgeMapView = mDredgeMapView;
    }
    public void loadData(String url, final Map<String, String> map) {
        HttpManager.sendRequest(url, map, new HttpRequestListener() {

            @Override
            public void onRequestSuccess(String response) {
                DredgeMapBean bean = new Gson().fromJson(response,DredgeMapBean.class);
                mDredgeMapView.showLoadData(bean);
            }

            @Override
            public void onRequestFail(String result, String code) {
                mDredgeMapView.showToast(result);
                mDredgeMapView.loadFail();
            }

            @Override
            public void onCompleted() {
                mDredgeMapView.dialogDismiss();
            }
        });
    }
}
