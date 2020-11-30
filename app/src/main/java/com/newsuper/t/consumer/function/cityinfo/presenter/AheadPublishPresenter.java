package com.newsuper.t.consumer.function.cityinfo.presenter;

import com.google.gson.Gson;
import com.newsuper.t.consumer.bean.DefaultPublishInfo;
import com.newsuper.t.consumer.bean.PublishDetailBean;
import com.newsuper.t.consumer.function.cityinfo.internal.IGetDefaultInfo;
import com.newsuper.t.consumer.function.cityinfo.internal.IPublishDetailView;
import com.newsuper.t.consumer.function.cityinfo.request.PublishRequest;
import com.newsuper.t.consumer.manager.HttpManager;
import com.newsuper.t.consumer.manager.listener.HttpRequestListener;
import com.newsuper.t.consumer.utils.Const;
import com.newsuper.t.consumer.utils.RetrofitUtil;
import com.newsuper.t.consumer.utils.UrlConst;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2018/6/26 0026.
 */

public class AheadPublishPresenter {
    private IGetDefaultInfo iGetDefaultInfo;
    public AheadPublishPresenter(IGetDefaultInfo iGetDefaultInfo){
        this.iGetDefaultInfo = iGetDefaultInfo;
    }
    public void getPublishAheadInfo(String url,Map<String,String> request){
        HttpManager.sendRequest(url, request, new HttpRequestListener() {
            @Override
            public void onRequestSuccess(String response) {
                DefaultPublishInfo info = new Gson().fromJson(response,DefaultPublishInfo.class);
                iGetDefaultInfo.getPublishData(info);
            }
            @Override
            public void onRequestFail(String result, String code) {
                iGetDefaultInfo.dialogDismiss();
                iGetDefaultInfo.showToast(result);
            }

            @Override
            public void onCompleted() {
                iGetDefaultInfo.dialogDismiss();
            }
        });
    }
}
