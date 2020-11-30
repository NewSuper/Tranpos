package com.newsuper.t.consumer.function.selectgoods.presenter;

import com.google.gson.Gson;
import com.xunjoy.lewaimai.consumer.bean.CollectBean;
import com.xunjoy.lewaimai.consumer.function.selectgoods.inter.ICheckGoodsMustView;
import com.xunjoy.lewaimai.consumer.function.selectgoods.inter.ISelectGoodsActivityView;
import com.xunjoy.lewaimai.consumer.manager.HttpManager;
import com.xunjoy.lewaimai.consumer.manager.listener.HttpRequestListener;

import java.util.Map;


public class CheckGoodsMustPresenter {

    private ICheckGoodsMustView checkGoodsMustView;

    public CheckGoodsMustPresenter(ICheckGoodsMustView checkGoodsMustView){
        this.checkGoodsMustView = checkGoodsMustView;
    }

    public void check(String url, Map<String,String> request,final String shop_id){
        HttpManager.sendRequest(url, request, new HttpRequestListener() {
            @Override
            public void onRequestSuccess(String response) {
                checkGoodsMustView.showSuccessVIew(shop_id);
            }

            @Override
            public void onRequestFail(String result, String code) {
                checkGoodsMustView.showCheckOrderView(result);
            }

            @Override
            public void onCompleted() {
                checkGoodsMustView.dialogDismiss();
            }
        });

    }

}