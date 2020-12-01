package com.newsuper.t.consumer.function.person.presenter;


import com.google.gson.Gson;
import com.newsuper.t.consumer.bean.UpYunBean;
import com.newsuper.t.consumer.function.person.internal.IUpYunView;
import com.newsuper.t.consumer.manager.HttpManager;
import com.newsuper.t.consumer.manager.listener.HttpRequestListener;

import java.util.Map;

public class UpYunPresenter {
    private IUpYunView mYunView;

    public UpYunPresenter(IUpYunView mYunView){
        this.mYunView = mYunView;
    }

    public void loadUpYun(String url, Map<String ,String>map){
        HttpManager.sendRequest(url, map, new HttpRequestListener() {
            @Override
            public void onRequestSuccess(String response) {
                mYunView.dialogDismiss();
                UpYunBean bean = new Gson().fromJson(response.toString(),UpYunBean.class);
                mYunView.showUpYunInfo(bean);
            }

            @Override
            public void onRequestFail(String result, String code) {
                mYunView.dialogDismiss();
                mYunView.showToast(result);
            }

            @Override
            public void onCompleted() {
              mYunView.dialogDismiss();
            }
        });
    }
}
