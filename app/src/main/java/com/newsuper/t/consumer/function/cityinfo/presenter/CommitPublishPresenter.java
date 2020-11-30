package com.newsuper.t.consumer.function.cityinfo.presenter;

import com.google.gson.Gson;
import com.newsuper.t.consumer.bean.DefaultPublishInfo;
import com.newsuper.t.consumer.bean.PublishResultBean;
import com.newsuper.t.consumer.function.cityinfo.internal.ICommitPublishInfo;
import com.newsuper.t.consumer.function.cityinfo.internal.IGetDefaultInfo;
import com.newsuper.t.consumer.manager.HttpManager;
import com.newsuper.t.consumer.manager.listener.HttpRequestListener;

import java.util.Map;

/**
 * Created by Administrator on 2018/6/26 0026.
 */

public class CommitPublishPresenter {
    private ICommitPublishInfo iCommitPublishInfo;
    public CommitPublishPresenter(ICommitPublishInfo iCommitPublishInfo){
        this.iCommitPublishInfo = iCommitPublishInfo;
    }
    public void commitPublishInfo(String url,Map<String,String> request){
        HttpManager.sendRequest(url, request, new HttpRequestListener() {
            @Override
            public void onRequestSuccess(String response) {
                iCommitPublishInfo.dialogDismiss();
                PublishResultBean info = new Gson().fromJson(response,PublishResultBean.class);
                iCommitPublishInfo.publishData(info);
            }
            @Override
            public void onRequestFail(String result, String code) {
                iCommitPublishInfo.dialogDismiss();
                iCommitPublishInfo.showToast(result);
            }

            @Override
            public void onCompleted() {
                iCommitPublishInfo.dialogDismiss();
            }
        });
    }
}
