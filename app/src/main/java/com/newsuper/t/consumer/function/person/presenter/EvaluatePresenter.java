package com.newsuper.t.consumer.function.person.presenter;

import com.google.gson.Gson;
import com.xunjoy.lewaimai.consumer.bean.EvaluateBean;
import com.xunjoy.lewaimai.consumer.bean.TraceBean;
import com.xunjoy.lewaimai.consumer.function.person.internal.IEvaluateView;
import com.xunjoy.lewaimai.consumer.manager.HttpManager;
import com.xunjoy.lewaimai.consumer.manager.listener.HttpRequestListener;

import java.util.Map;

/**
 * Create by Administrator on 2019/5/5 0005
 */
public class EvaluatePresenter {
    private IEvaluateView mEvaluateView;
    public EvaluatePresenter(IEvaluateView mEvaluateView) {
        this.mEvaluateView = mEvaluateView;
    }

    public void loadData(String url, final Map<String, String> map) {
        HttpManager.sendRequest(url, map, new HttpRequestListener() {

            @Override
            public void onRequestSuccess(String response) {
                EvaluateBean bean = new Gson().fromJson(response,EvaluateBean.class);
                mEvaluateView.showDataToView(bean);
            }

            @Override
            public void onRequestFail(String result, String code) {
                mEvaluateView.showToast(result);
                mEvaluateView.loadFail();
            }

            @Override
            public void onCompleted() {
                mEvaluateView.dialogDismiss();
            }
        });
    }

    public void deleteTrace(String url, final Map<String, String> map) {
        HttpManager.sendRequest(url, map, new HttpRequestListener() {

            @Override
            public void onRequestSuccess(String response) {
                mEvaluateView.deleteEvalSucc();
            }

            @Override
            public void onRequestFail(String result, String code) {
                mEvaluateView.showToast(result);
            }

            @Override
            public void onCompleted() {
                mEvaluateView.dialogDismiss();
            }
        });
    }
}
