package com.newsuper.t.consumer.function.order.presenter;

import com.google.gson.Gson;
import com.xunjoy.lewaimai.consumer.bean.OrderBean;
import com.xunjoy.lewaimai.consumer.bean.ShopInfoBean;
import com.xunjoy.lewaimai.consumer.function.inter.IOrderInfoActivityView;
import com.xunjoy.lewaimai.consumer.function.inter.IOrderListFragmentView;
import com.xunjoy.lewaimai.consumer.function.selectgoods.inter.IShopInfoFragmentView;
import com.xunjoy.lewaimai.consumer.manager.HttpManager;
import com.xunjoy.lewaimai.consumer.manager.listener.HttpRequestListener;

import java.util.Map;


public class OrderListPresenter {

    private IOrderListFragmentView orderListFragmentView;

    public OrderListPresenter(IOrderListFragmentView orderListFragmentView){
        this.orderListFragmentView = orderListFragmentView;
    }

    public void loadData(String url,Map<String,String> request){
        HttpManager.sendRequest(url, request, new HttpRequestListener() {
            @Override
            public void onRequestSuccess(String response) {
                orderListFragmentView.dialogDismiss();
                OrderBean bean = new Gson().fromJson(response.toString(),OrderBean.class);
                orderListFragmentView.showDataToVIew(bean);
            }

            @Override
            public void onRequestFail(String result, String code) {
                orderListFragmentView.dialogDismiss();
//                orderListFragmentView.showToast(result);
                orderListFragmentView.loadFail();
            }

            @Override
            public void onCompleted() {
                orderListFragmentView.dialogDismiss();
            }
        });

    }

}