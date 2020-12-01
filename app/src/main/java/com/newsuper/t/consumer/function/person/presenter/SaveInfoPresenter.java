package com.newsuper.t.consumer.function.person.presenter;

import com.google.gson.Gson;
import com.newsuper.t.consumer.bean.UpdateImgBean;
import com.newsuper.t.consumer.function.person.internal.ISaveInfoView;
import com.newsuper.t.consumer.manager.HttpManager;
import com.newsuper.t.consumer.manager.listener.HttpRequestListener;

import java.util.Map;


public class SaveInfoPresenter {
    private ISaveInfoView mSaveInfoView;

    public SaveInfoPresenter(ISaveInfoView view) {
        this.mSaveInfoView = view;
    }
    public void showSaveInfo(String url, Map<String, String> map){
        HttpManager.sendRequest(url, map, new HttpRequestListener() {
            @Override
            public void onRequestSuccess(String response) {
                mSaveInfoView.dialogDismiss();
                UpdateImgBean bean = new Gson().fromJson(response.toString(), UpdateImgBean.class);
                mSaveInfoView.showSaveInfo(bean);
            }

            @Override
            public void onRequestFail(String result, String code) {
                mSaveInfoView.dialogDismiss();
                mSaveInfoView.showToast(result);
                mSaveInfoView.updateFail();
            }

            @Override
            public void onCompleted() {
                mSaveInfoView.dialogDismiss();
            }
        });
    }
}
