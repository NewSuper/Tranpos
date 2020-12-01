package com.newsuper.t.consumer.function.selectgoods.presenter;

import com.google.gson.Gson;
import com.newsuper.t.consumer.bean.CommentBean;
import com.newsuper.t.consumer.bean.GoodsSearchBean;
import com.newsuper.t.consumer.bean.ShopInfoBean;
import com.newsuper.t.consumer.function.selectgoods.inter.ICommentFragmentView;
import com.newsuper.t.consumer.function.selectgoods.inter.IShopInfoFragmentView;
import com.newsuper.t.consumer.manager.HttpManager;
import com.newsuper.t.consumer.manager.listener.HttpRequestListener;

import java.util.Map;


public class ShopInfoFragmentPresenter {

    private IShopInfoFragmentView shopInfoFragmentView;

    public ShopInfoFragmentPresenter(IShopInfoFragmentView shopInfoFragmentView){
        this.shopInfoFragmentView = shopInfoFragmentView;
    }

    public void loadData(String url,Map<String,String> request){
        HttpManager.sendRequest(url, request, new HttpRequestListener() {
            @Override
            public void onRequestSuccess(String response) {
                shopInfoFragmentView.dialogDismiss();
                ShopInfoBean bean = new Gson().fromJson(response.toString(),ShopInfoBean.class);
                shopInfoFragmentView.showDataToVIew(bean);
            }

            @Override
            public void onRequestFail(String result, String code) {
                shopInfoFragmentView.dialogDismiss();
                shopInfoFragmentView.showToast(result);
            }

            @Override
            public void onCompleted() {
                shopInfoFragmentView.dialogDismiss();
            }
        });

    }

    public void searchGoodsData(String url,Map<String,String> request) {
        HttpManager.sendRequest(url, request, new HttpRequestListener() {
            @Override
            public void onRequestSuccess(String response) {
                shopInfoFragmentView.dialogDismiss();
                GoodsSearchBean bean = new Gson().fromJson(response,GoodsSearchBean.class);
                shopInfoFragmentView.showSearchDataToView(bean);
            }

            @Override
            public void onRequestFail(String result, String code) {
                shopInfoFragmentView.dialogDismiss();
                shopInfoFragmentView.searchDataError();
            }

            @Override
            public void onCompleted() {
                shopInfoFragmentView.dialogDismiss();
            }
        });
    }

}