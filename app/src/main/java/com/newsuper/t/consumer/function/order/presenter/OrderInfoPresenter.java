package com.newsuper.t.consumer.function.order.presenter;

import com.google.gson.Gson;
import com.newsuper.t.consumer.bean.OrderInfoBean;
import com.newsuper.t.consumer.function.inter.IOrderInfoActivityView;
import com.newsuper.t.consumer.function.inter.IOrderListFragmentView;
import com.newsuper.t.consumer.manager.HttpManager;
import com.newsuper.t.consumer.manager.listener.HttpRequestListener;

import java.util.Map;


public class OrderInfoPresenter {

    private IOrderInfoActivityView orderInfoActivityView;

    public OrderInfoPresenter(IOrderInfoActivityView orderInfoActivityView){
        this.orderInfoActivityView = orderInfoActivityView;
    }

    public void loadData(String url,Map<String,String> request){
        HttpManager.sendRequest(url, request, new HttpRequestListener() {
            @Override
            public void onRequestSuccess(String response) {
                orderInfoActivityView.dialogDismiss();
                OrderInfoBean bean = new Gson().fromJson(response.toString(),OrderInfoBean.class);
                orderInfoActivityView.setActivityData(bean);
            }

            @Override
            public void onRequestFail(String result, String code) {
                orderInfoActivityView.dialogDismiss();
                orderInfoActivityView.showToast(result);
            }

            @Override
            public void onCompleted() {
                orderInfoActivityView.dialogDismiss();
            }
        });

    }

}