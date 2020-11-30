package com.newsuper.t.consumer.function.top.presenter;

import com.xunjoy.lewaimai.consumer.function.top.internal.IJoinView;
import com.xunjoy.lewaimai.consumer.manager.HttpManager;
import com.xunjoy.lewaimai.consumer.manager.listener.HttpRequestListener;

import java.util.Map;

/**
 * Create by Administrator on 2019/6/24 0024
 */
public class JoinPresenter {
    private IJoinView mJoinView;
    public JoinPresenter(IJoinView mJoinView) {
        this.mJoinView = mJoinView;
    }
    public void sendJoinApplication(String url, final Map<String, String> map) {
        HttpManager.sendRequest(url, map, new HttpRequestListener() {
            @Override
            public void onRequestSuccess(String response) {
                mJoinView.requestSuccess();
            }

            @Override
            public void onRequestFail(String result, String code) {
                mJoinView.showToast(result);
            }

            @Override
            public void onCompleted() {
                mJoinView.dialogDismiss();
            }
        });
    }
}
