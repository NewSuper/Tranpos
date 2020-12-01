package com.newsuper.t.consumer.function.top.presenter;

import android.util.Log;

import com.amap.api.services.core.PoiItem;
import com.google.gson.Gson;
import com.newsuper.t.consumer.bean.ShopHistoryBean;
import com.newsuper.t.consumer.bean.ShopSearchBean;
import com.newsuper.t.consumer.function.top.internal.IShopSearchView;
import com.newsuper.t.consumer.function.top.request.ShopSearchRequest;
import com.newsuper.t.consumer.manager.HttpManager;
import com.newsuper.t.consumer.manager.listener.HttpRequestListener;
import com.newsuper.t.consumer.utils.SharedPreferencesUtil;
import com.newsuper.t.consumer.utils.StringUtils;
import com.newsuper.t.consumer.utils.UrlConst;

import java.util.HashMap;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/5/8 0008.
 * 店铺搜索
 */

public class ShopSearchPresenter  {
    private IShopSearchView mView;
    public  ShopSearchPresenter(IShopSearchView mView){
        this.mView = mView;
    }

    //保存搜索记录
    public void saveHistory(ShopHistoryBean.HistoryBean item){
        SharedPreferencesUtil.saveShopSearchInfo(item);
    }


    public void searchShop(String search, String search_area_id, String type_id, String order_type, String filter, String services, final int page){
        String area_id = StringUtils.isEmpty(search_area_id) ? "":search_area_id;
        HashMap<String,String> map = ShopSearchRequest.searchRequest(SharedPreferencesUtil.getAdminId(),search,type_id,order_type,filter,services,SharedPreferencesUtil.getLatitude(),SharedPreferencesUtil.getLongitude(),page+"","2",area_id);
        HttpManager.sendRequest(UrlConst.TOP_SHOP_LIST, map, new HttpRequestListener() {
            @Override
            public void onRequestSuccess(String response) {
                mView.dialogDismiss();
                ShopSearchBean bean = new Gson().fromJson(response.toString(),ShopSearchBean.class);
                if (page > 1 ){
                    mView.showDataToVIewMore(bean);
                }else {
                    mView.showDataToVIew(bean);
                }

            }

            @Override
            public void onRequestFail(String result, String code) {
                mView.dialogDismiss();
                mView.showToast(result);
            }

            @Override
            public void onCompleted() {
                mView.dialogDismiss();
            }
        });
    }

    public void filterShop(String search,String search_area_id,String type_id, String order_type, String filter,String services,String page){
        String area_id = StringUtils.isEmpty(search_area_id) ? "":search_area_id;
        HashMap<String,String> map = ShopSearchRequest.searchRequest(SharedPreferencesUtil.getAdminId(),search,type_id,order_type,filter,services,SharedPreferencesUtil.getLatitude(),SharedPreferencesUtil.getLongitude(),page,"2",area_id);
        HttpManager.sendRequest(UrlConst.TOP_SHOP_LIST, map, new HttpRequestListener() {
            @Override
            public void onRequestSuccess(String response) {
                mView.dialogDismiss();
                ShopSearchBean bean = new Gson().fromJson(response.toString(),ShopSearchBean.class);
                mView.showFilterData(bean);
            }

            @Override
            public void onRequestFail(String result, String code) {
                mView.dialogDismiss();
                mView.showToast(result);
            }

            @Override
            public void onCompleted() {
                mView.dialogDismiss();
            }
        });
    }
}
