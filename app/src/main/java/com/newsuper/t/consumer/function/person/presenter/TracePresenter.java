package com.newsuper.t.consumer.function.person.presenter;

import com.google.gson.Gson;
import com.newsuper.t.consumer.bean.TraceBean;
import com.newsuper.t.consumer.function.person.internal.ITraceView;
import com.newsuper.t.consumer.function.person.request.AddressRequest;
import com.newsuper.t.consumer.manager.HttpManager;
import com.newsuper.t.consumer.manager.listener.HttpRequestListener;
import com.newsuper.t.consumer.utils.SharedPreferencesUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Create by Administrator on 2019/4/25 0025
 */
public class TracePresenter {
    private ITraceView mTraceView;
    public TracePresenter(ITraceView mTraceView) {
        this.mTraceView = mTraceView;
    }

    public void loadData(String url, final Map<String, String> map) {
        HttpManager.sendRequest(url, map, new HttpRequestListener() {

            @Override
            public void onRequestSuccess(String response) {
                TraceBean bean = new Gson().fromJson(response,TraceBean.class);
                mTraceView.showDataToView(bean);
            }

            @Override
            public void onRequestFail(String result, String code) {
                mTraceView.showToast(result);
                mTraceView.loadFail();
            }

            @Override
            public void onCompleted() {
                mTraceView.dialogDismiss();
            }
        });
    }

    public void deleteTrace(String url, final Map<String, String> map) {
        HttpManager.sendRequest(url, map, new HttpRequestListener() {

            @Override
            public void onRequestSuccess(String response) {
                mTraceView.deleteTraceSuss();
            }

            @Override
            public void onRequestFail(String result, String code) {
                mTraceView.showToast(result);
            }

            @Override
            public void onCompleted() {
                mTraceView.dialogDismiss();
            }
        });
    }
}
