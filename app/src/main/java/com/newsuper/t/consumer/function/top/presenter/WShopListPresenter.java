package com.newsuper.t.consumer.function.top.presenter;

import android.util.Log;

import com.google.gson.Gson;
import com.newsuper.t.consumer.bean.TopBean;
import com.newsuper.t.consumer.function.top.internal.IWShopListView;
import com.newsuper.t.consumer.manager.HttpManager;
import com.newsuper.t.consumer.manager.listener.HttpRequestListener;
import com.newsuper.t.consumer.utils.SharedPreferencesUtil;
import com.newsuper.t.consumer.utils.UrlConst;

import java.util.Map;

/**
 * Created by Administrator on 2017/8/7 0007.
 */

public class WShopListPresenter {
    private IWShopListView shopListView;
    public WShopListPresenter(IWShopListView shopListView){
        this.shopListView = shopListView;
    }
    /**
     * 加载数据
     * @param map
     */
    public void loadShopData(Map<String,String> map){
        Log.i("loadShopData","map = 1111 = "+map.toString());
        HttpManager.sendRequest(UrlConst.TOP_SHOP_LIST, map, new HttpRequestListener() {
            @Override
            public void onRequestSuccess(String response) {
                shopListView.dialogDismiss();
                TopBean bean = new Gson().fromJson(response,TopBean.class);
                shopListView.showDataToView(bean);
                SharedPreferencesUtil.saveShopListSize(bean.data.shoplist.size());
            }

            @Override
            public void onRequestFail(String result, String code) {
                shopListView.dialogDismiss();
                shopListView.showToast(result);
                shopListView.loadFail();
            }

            @Override
            public void onCompleted() {
                shopListView.dialogDismiss();
            }
        });

    }

    public void loadFilterShopData(Map<String,String> map){
        Log.i("loadShopData","map = 1111 = "+map.toString());
        HttpManager.sendRequest(UrlConst.TOP_SHOP_LIST, map, new HttpRequestListener() {
            @Override
            public void onRequestSuccess(String response) {
                shopListView.dialogDismiss();
                TopBean bean = new Gson().fromJson(response,TopBean.class);
                shopListView.showFilterDataToView(bean);
                SharedPreferencesUtil.saveShopListSize(bean.data.shoplist.size());
            }

            @Override
            public void onRequestFail(String result, String code) {
                shopListView.dialogDismiss();
                shopListView.showToast(result);
                shopListView.loadFail();
            }

            @Override
            public void onCompleted() {
                shopListView.dialogDismiss();
            }
        });

    }
    /**
     * 加载数据
     * @param map
     */
    public void loadMoreShopData(Map<String,String> map){
        HttpManager.sendRequest(UrlConst.TOP_SHOP_LIST, map, new HttpRequestListener() {
            @Override
            public void onRequestSuccess(String response) {
                shopListView.dialogDismiss();
                TopBean bean = new Gson().fromJson(response,TopBean.class);
                shopListView.showMoreShop(bean);
                SharedPreferencesUtil.saveShopListSize(bean.data.shoplist.size());
            }

            @Override
            public void onRequestFail(String result, String code) {
                shopListView.dialogDismiss();
                shopListView.showToast(result);
                shopListView.loadFail();
            }

            @Override
            public void onCompleted() {
                shopListView.dialogDismiss();
            }
        });

    }
}
