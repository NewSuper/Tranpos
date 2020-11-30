package com.newsuper.t.consumer.function.cityinfo.presenter;

import com.google.gson.Gson;
import com.newsuper.t.consumer.bean.PublishCollectBean;
import com.newsuper.t.consumer.function.cityinfo.internal.IPublishCollectView;
import com.newsuper.t.consumer.function.cityinfo.request.PublishRequest;
import com.newsuper.t.consumer.manager.HttpManager;
import com.newsuper.t.consumer.manager.listener.HttpRequestListener;
import com.newsuper.t.consumer.utils.Const;
import com.newsuper.t.consumer.utils.SharedPreferencesUtil;
import com.newsuper.t.consumer.utils.UrlConst;

import java.util.HashMap;

/**
 * Created by Administrator on 2018/6/29 0029.
 */

public class MyPublishCollectPresenter  {
    private IPublishCollectView publishView;
    public MyPublishCollectPresenter(IPublishCollectView collectView){
        this.publishView = collectView;
    }
    public void getPublishCollectList(final int page){
        HashMap<String,String> map = PublishRequest.getMyCollectRequest(Const.ADMIN_ID, SharedPreferencesUtil.getToken(),page+"");
        HttpManager.sendRequest(UrlConst.PUBLISH_MY_COLLECT, map, new HttpRequestListener() {
            @Override
            public void onRequestSuccess(String response) {
                PublishCollectBean bean = new Gson().fromJson(response,PublishCollectBean.class);
                if (page > 1){
                    publishView.getMoreCollectData(bean);
                }else {
                    publishView.getCollectData(bean);
                }

                publishView.dialogDismiss();
            }
            @Override
            public void onRequestFail(String result, String code) {
                publishView.showToast(result);
                publishView.getCollectFail();
            }

            @Override
            public void onCompleted() {
                publishView.dialogDismiss();
            }
        });
    }
}
