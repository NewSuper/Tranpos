package com.newsuper.t.consumer.function.vip.presenter;

import com.google.gson.Gson;
import com.newsuper.t.consumer.bean.VipLevelBean;
import com.newsuper.t.consumer.function.vip.inter.IVipLevelView;
import com.newsuper.t.consumer.manager.HttpManager;
import com.newsuper.t.consumer.manager.listener.HttpRequestListener;

import java.util.Map;

/**
 * Create by Administrator on 2019/7/1 0001
 */
public class VipLevelPresenter {
    private IVipLevelView iVipLevelView;
    public VipLevelPresenter(IVipLevelView iVipLevelView){
        this.iVipLevelView = iVipLevelView;
    }
    public void loadData(String url, final Map<String, String> map) {
        HttpManager.sendRequest(url, map, new HttpRequestListener() {
            @Override
            public void onRequestSuccess(String response) {
                VipLevelBean bean = new Gson().fromJson(response,VipLevelBean.class);
                iVipLevelView.showDataToView(bean);
            }

            @Override
            public void onRequestFail(String result, String code) {
                iVipLevelView.showToast(result);
                iVipLevelView.loadFail();
            }

            @Override
            public void onCompleted() {
                iVipLevelView.dialogDismiss();
            }
        });
    }
}
