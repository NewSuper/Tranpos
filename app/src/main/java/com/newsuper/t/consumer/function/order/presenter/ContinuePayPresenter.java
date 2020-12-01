package com.newsuper.t.consumer.function.order.presenter;

import com.google.gson.Gson;
import com.newsuper.t.consumer.bean.ContinuePayResultBean;
import com.newsuper.t.consumer.bean.ContinuePayTypeBean;
import com.newsuper.t.consumer.bean.OrderInfoBean;
import com.newsuper.t.consumer.function.inter.IContinuePay;
import com.newsuper.t.consumer.function.inter.IOrderInfoActivityView;
import com.newsuper.t.consumer.manager.HttpManager;
import com.newsuper.t.consumer.manager.listener.HttpRequestListener;

import java.util.Map;


public class ContinuePayPresenter {

    private IContinuePay iContinuePay;

    public ContinuePayPresenter(IContinuePay iContinuePay){
        this.iContinuePay = iContinuePay;
    }

    public void loadData(String url,Map<String,String> request){
        HttpManager.sendRequest(url, request, new HttpRequestListener() {
            @Override
            public void onRequestSuccess(String response) {
                iContinuePay.dialogDismiss();
                ContinuePayResultBean bean = new Gson().fromJson(response.toString(),ContinuePayResultBean.class);
                iContinuePay.getPayParams(bean);
            }

            @Override
            public void onRequestFail(String result, String code) {
                iContinuePay.dialogDismiss();
                iContinuePay.showToast(result);
            }

            @Override
            public void onCompleted() {
                iContinuePay.dialogDismiss();
            }
        });

    }
    public void getPayType(String url,Map<String,String> request){
        HttpManager.sendRequest(url, request, new HttpRequestListener() {
            @Override
            public void onRequestSuccess(String response) {
                iContinuePay.dialogDismiss();
                ContinuePayTypeBean bean = new Gson().fromJson(response.toString(),ContinuePayTypeBean.class);
                iContinuePay.getPayType(bean);
            }

            @Override
            public void onRequestFail(String result, String code) {
                iContinuePay.dialogDismiss();
                iContinuePay.showToast(result);
            }

            @Override
            public void onCompleted() {
                iContinuePay.dialogDismiss();
            }
        });

    }

}