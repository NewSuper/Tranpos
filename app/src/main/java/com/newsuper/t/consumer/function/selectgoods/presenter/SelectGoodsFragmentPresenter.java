package com.newsuper.t.consumer.function.selectgoods.presenter;

import com.google.gson.Gson;
import com.xunjoy.lewaimai.consumer.bean.GoodsListBean;
import com.xunjoy.lewaimai.consumer.bean.ShopInfoBean;
import com.xunjoy.lewaimai.consumer.function.selectgoods.inter.ISelectGoodsFragmentView;
import com.xunjoy.lewaimai.consumer.function.selectgoods.inter.IShopInfoFragmentView;
import com.xunjoy.lewaimai.consumer.manager.HttpManager;
import com.xunjoy.lewaimai.consumer.manager.listener.HttpRequestListener;

import java.util.Map;


public class SelectGoodsFragmentPresenter {

    private ISelectGoodsFragmentView selectGoodsFragmentView;

    public SelectGoodsFragmentPresenter(ISelectGoodsFragmentView selectGoodsFragmentView){
        this.selectGoodsFragmentView = selectGoodsFragmentView;
    }

    public void loadData(String url,Map<String,String> request){
        HttpManager.sendRequest(url, request, new HttpRequestListener() {
            @Override
            public void onRequestSuccess(String response) {
                selectGoodsFragmentView.dialogDismiss();
                GoodsListBean bean = new Gson().fromJson(response.toString(),GoodsListBean.class);
                selectGoodsFragmentView.showDataToVIew(bean);
            }

            @Override
            public void onRequestFail(String result, String code) {
                selectGoodsFragmentView.dialogDismiss();
                selectGoodsFragmentView.showToast(result);
                selectGoodsFragmentView.loadGoodsFail();
            }

            @Override
            public void onCompleted() {
                selectGoodsFragmentView.dialogDismiss();
            }

        });

    }

}