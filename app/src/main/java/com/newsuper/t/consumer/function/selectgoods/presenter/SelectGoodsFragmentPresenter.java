package com.newsuper.t.consumer.function.selectgoods.presenter;

import com.google.gson.Gson;
import com.newsuper.t.consumer.bean.GoodsListBean;
import com.newsuper.t.consumer.bean.ShopInfoBean;
import com.newsuper.t.consumer.function.selectgoods.inter.ISelectGoodsFragmentView;
import com.newsuper.t.consumer.function.selectgoods.inter.IShopInfoFragmentView;
import com.newsuper.t.consumer.manager.HttpManager;
import com.newsuper.t.consumer.manager.listener.HttpRequestListener;

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