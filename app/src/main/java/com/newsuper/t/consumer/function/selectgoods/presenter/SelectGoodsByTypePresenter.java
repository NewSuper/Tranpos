package com.newsuper.t.consumer.function.selectgoods.presenter;

import com.google.gson.Gson;
import com.xunjoy.lewaimai.consumer.bean.GoodsByType;
import com.xunjoy.lewaimai.consumer.function.selectgoods.inter.ISelectGoodsByTypeView;
import com.xunjoy.lewaimai.consumer.manager.HttpManager;
import com.xunjoy.lewaimai.consumer.manager.listener.HttpRequestListener;

import java.util.Map;


public class SelectGoodsByTypePresenter {

    private ISelectGoodsByTypeView selectGoodsByTypeView;

    public SelectGoodsByTypePresenter(ISelectGoodsByTypeView selectGoodsByTypeView){
        this.selectGoodsByTypeView = selectGoodsByTypeView;
    }

    public void loadData(String url,Map<String,String> request){
        HttpManager.sendRequest(url, request, new HttpRequestListener() {
            @Override
            public void onRequestSuccess(String response) {
                selectGoodsByTypeView.dialogDismiss();
                GoodsByType bean = new Gson().fromJson(response.toString(),GoodsByType.class);
                selectGoodsByTypeView.showDataToVIew(bean);
            }

            @Override
            public void onRequestFail(String result, String code) {
                selectGoodsByTypeView.dialogDismiss();
                selectGoodsByTypeView.showToast(result);
                selectGoodsByTypeView.loadGoodsFail();
            }

            @Override
            public void onCompleted() {
                selectGoodsByTypeView.dialogDismiss();
            }

        });

    }

}