package com.newsuper.t.consumer.function.person.presenter;


import com.google.gson.Gson;
import com.newsuper.t.consumer.bean.UpdateImgBean;
import com.newsuper.t.consumer.function.person.internal.IUpdateImgView;
import com.newsuper.t.consumer.function.person.request.UpdateImgRequest;
import com.newsuper.t.consumer.manager.HttpManager;
import com.newsuper.t.consumer.manager.listener.HttpRequestListener;

import java.util.HashMap;
import java.util.Map;

public class UpdateImgPresenter {
    private IUpdateImgView mView;

    public UpdateImgPresenter(IUpdateImgView mView) {
        this.mView = mView;
    }

    public void showImg(String url, Map<String, String> map) {
        HttpManager.sendRequest(url, map, new HttpRequestListener() {
            @Override
            public void onRequestSuccess(String response) {
                mView.dialogDismiss();
                UpdateImgBean bean = new Gson().fromJson(response.toString(), UpdateImgBean.class);
                mView.showUpdateImg(bean);
            }

            @Override
            public void onRequestFail(String result, String code) {
                mView.dialogDismiss();
                mView.showToast(result);
            }

            @Override
            public void onCompleted() {
                mView.dialogDismiss();
            }
        });
    }

}
