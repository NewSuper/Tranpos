package com.newsuper.t.consumer.function.top.presenter;

import com.google.gson.Gson;
import com.xunjoy.lewaimai.consumer.bean.TopTabBean;
import com.xunjoy.lewaimai.consumer.function.top.internal.ITopView;
import com.xunjoy.lewaimai.consumer.function.top.request.TopRequest;
import com.xunjoy.lewaimai.consumer.manager.HttpManager;
import com.xunjoy.lewaimai.consumer.manager.listener.HttpRequestListener;
import com.xunjoy.lewaimai.consumer.utils.SharedPreferencesUtil;
import com.xunjoy.lewaimai.consumer.utils.UrlConst;

import java.util.Map;

/**
 * Created by Administrator on 2018/5/10 0010.
 */

public class TopPresenter {
    private ITopView iTopView;
    public TopPresenter(ITopView iTopView){
        this.iTopView = iTopView;
    }
    public void loadData(String id){
        Map<String,String> map = TopRequest.tabRequest(SharedPreferencesUtil.getAdminId(),id);
        HttpManager.sendRequest(UrlConst.TOP_TAB, map, new HttpRequestListener() {
            @Override
            public void onRequestSuccess(String response) {
                TopTabBean bean = new Gson().fromJson(response,TopTabBean.class);
                iTopView.dialogDismiss();
                iTopView.loadTabData(bean);
            }
            @Override
            public void onRequestFail(String result, String code) {
                iTopView.showToast(result);
                iTopView.loadFail();
            }
            @Override
            public void onCompleted() {
                iTopView.dialogDismiss();
            }
        });

    }

}
