package com.newsuper.t.consumer.function.order.presenter;

import com.google.gson.Gson;
import com.newsuper.t.consumer.bean.OrderBean;
import com.newsuper.t.consumer.bean.ShopInfoBean;
import com.newsuper.t.consumer.function.inter.IOrderInfoActivityView;
import com.newsuper.t.consumer.function.inter.IOrderListFragmentView;
import com.newsuper.t.consumer.function.selectgoods.inter.IShopInfoFragmentView;
import com.newsuper.t.consumer.manager.HttpManager;
import com.newsuper.t.consumer.manager.listener.HttpRequestListener;

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