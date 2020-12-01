package com.newsuper.t.consumer.function.selectgoods.presenter;

import com.google.gson.Gson;
import com.newsuper.t.consumer.bean.CollectBean;
import com.newsuper.t.consumer.bean.ShopInfoBean;
import com.newsuper.t.consumer.function.selectgoods.inter.ISelectGoodsActivityView;
import com.newsuper.t.consumer.manager.HttpManager;
import com.newsuper.t.consumer.manager.listener.HttpRequestListener;

import java.util.Map;


public class SelectGoodsActivityPresenter {

    private ISelectGoodsActivityView selectGoodsActivityView;

    public SelectGoodsActivityPresenter(ISelectGoodsActivityView selectGoodsActivityView){
        this.selectGoodsActivityView = selectGoodsActivityView;
    }

    public void loadData(String url, Map<String,String> request, final String flag){
        HttpManager.sendRequest(url, request, new HttpRequestListener() {
            @Override
            public void onRequestSuccess(String response) {
                selectGoodsActivityView.dialogDismiss();
                if (!"check".equals(flag)) {
                    CollectBean bean = new Gson().fromJson(response.toString(), CollectBean.class);
                    selectGoodsActivityView.showDataToVIew(bean, flag);
                } else {
                    selectGoodsActivityView.showDataToVIew(null, flag);
                }
            }

            @Override
            public void onRequestFail(String result, String code) {
                selectGoodsActivityView.dialogDismiss();
                if (!"check".equals(flag)) {
                    selectGoodsActivityView.showToast(result);
                } else {
                    selectGoodsActivityView.showCheckOrderView(result);
                }
            }

            @Override
            public void onCompleted() {
                selectGoodsActivityView.dialogDismiss();
            }
        });

    }

}