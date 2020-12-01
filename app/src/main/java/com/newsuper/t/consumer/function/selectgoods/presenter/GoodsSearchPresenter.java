package com.newsuper.t.consumer.function.selectgoods.presenter;

import com.google.gson.Gson;
import com.newsuper.t.consumer.bean.GoodsSearchBean;
import com.newsuper.t.consumer.function.selectgoods.inter.IGoodsSearchView;
import com.newsuper.t.consumer.function.selectgoods.request.ShopInfoRequest;
import com.newsuper.t.consumer.manager.HttpManager;
import com.newsuper.t.consumer.manager.listener.HttpRequestListener;
import com.newsuper.t.consumer.utils.UrlConst;

import java.util.HashMap;

/**
 * Created by Administrator on 2017/7/4 0004.
 */

public class GoodsSearchPresenter {
    private IGoodsSearchView searchView;
    public GoodsSearchPresenter(IGoodsSearchView searchView){
        this.searchView = searchView;
    }
    public void loadGoods(String token,String admin_id,String shop_id,String searchWord,String page){
        HashMap<String,String> map = ShopInfoRequest.goodsSearchRequest(token,admin_id,shop_id,searchWord,page,"3");
        HttpManager.sendRequest(UrlConst.GOODS_SEARCH, map, new HttpRequestListener() {
            @Override
            public void onRequestSuccess(String response) {
                searchView.dialogDismiss();
                GoodsSearchBean bean = new Gson().fromJson(response,GoodsSearchBean.class);
                searchView.loadData(bean);
            }

            @Override
            public void onRequestFail(String result, String code) {
                searchView.dialogDismiss();
                searchView.showToast(result);
                searchView.loadFail();
            }

            @Override
            public void onCompleted() {
                searchView.dialogDismiss();
            }
        });
    }
}
