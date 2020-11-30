package com.newsuper.t.consumer.function.top.presenter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.Log;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.xunjoy.lewaimai.consumer.bean.GetCouponBean;
import com.xunjoy.lewaimai.consumer.bean.TopBean;
import com.xunjoy.lewaimai.consumer.bean.WTopBean;
import com.xunjoy.lewaimai.consumer.function.top.internal.IWTopFragmentView;
import com.xunjoy.lewaimai.consumer.function.top.request.TopRequest;
import com.xunjoy.lewaimai.consumer.manager.HttpManager;
import com.xunjoy.lewaimai.consumer.manager.listener.HttpRequestListener;
import com.xunjoy.lewaimai.consumer.utils.LogUtil;
import com.xunjoy.lewaimai.consumer.utils.RetrofitUtil;
import com.xunjoy.lewaimai.consumer.utils.SharedPreferencesUtil;
import com.xunjoy.lewaimai.consumer.utils.UrlConst;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/7/24 0024.
 */

public class WAreaTopFragmentPresenter {
    private IWTopFragmentView fragmentView;
    public WAreaTopFragmentPresenter(IWTopFragmentView fragmentView){
        this.fragmentView = fragmentView;
    }


    /**
     *
     * @param token
     * @param admin_id
     * @param user_lat 用户纬度
     * @param user_lnt 用户经度
     * 根据不同平台可选 h5/app/wxapp
     */
    public void loadData(String token,String admin_id,String user_lat,String user_lnt,String version){
        HashMap<String,String> map = TopRequest.weiAreaRequest(token,admin_id,user_lat,user_lnt,version, RetrofitUtil.ADMIN_APP_ID,"1");
        HttpManager.sendRequest(UrlConst.TOP_WEI_AREA, map, new HttpRequestListener() {
            @Override
            public void onRequestSuccess(String response) {
                WTopBean bean = new Gson().fromJson(response,WTopBean.class);
                fragmentView.dialogDismiss();
                fragmentView.loadDataToView(bean);
            }
            @Override
            public void onRequestFail(String result, String code) {
                fragmentView.dialogDismiss();
                if(result.equals("no_area")){
                    fragmentView.showResponeDialog();
                }else{
                    fragmentView.showToast(result);
                    fragmentView.loadFail();
                }

            }
            @Override
            public void onCompleted() {
                fragmentView.dialogDismiss();
            }
        });
    }
    /**
     * 加载数据
     * @param map
     */
    public void loadShopData(Map<String,String> map){
        Log.i("loadShopData","map == "+map.toString());
        HttpManager.sendRequest(UrlConst.TOP_SHOP_LIST, map, new HttpRequestListener() {
            @Override
            public void onRequestSuccess(String response) {
                fragmentView.dialogDismiss();
                TopBean bean = new Gson().fromJson(response.toString(),TopBean.class);
                fragmentView.showDataToView(bean);
                SharedPreferencesUtil.saveShopListSize(bean.data.shoplist.size());
            }

            @Override
            public void onRequestFail(String result, String code) {
                fragmentView.dialogDismiss();
                fragmentView.showToast(result);
                fragmentView.loadShopFail();
            }

            @Override
            public void onCompleted() {
                fragmentView.dialogDismiss();
            }
        });

    }
    /**
     * 加载数据
     * @param map
     */
    public void loadFilterShopData(Map<String,String> map){
        Log.i("loadShopData","map == "+map.toString());
        HttpManager.sendRequest(UrlConst.TOP_SHOP_LIST, map, new HttpRequestListener() {
            @Override
            public void onRequestSuccess(String response) {
                fragmentView.dialogDismiss();
                TopBean bean = new Gson().fromJson(response.toString(),TopBean.class);
                fragmentView.showFilterDataToView(bean);
                SharedPreferencesUtil.saveShopListSize(bean.data.shoplist.size());
            }

            @Override
            public void onRequestFail(String result, String code) {
                fragmentView.dialogDismiss();
                fragmentView.showToast(result);
                fragmentView.loadShopFail();
            }

            @Override
            public void onCompleted() {
                fragmentView.dialogDismiss();
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
                fragmentView.dialogDismiss();
                TopBean bean = new Gson().fromJson(response.toString(),TopBean.class);
                fragmentView.showMoreShop(bean);
                SharedPreferencesUtil.saveShopListSize(bean.data.shoplist.size());
            }

            @Override
            public void onRequestFail(String result, String code) {
                fragmentView.dialogDismiss();
                fragmentView.showToast(result);
                fragmentView.loadMoreShopFail();
            }

            @Override
            public void onCompleted() {
                fragmentView.dialogDismiss();
            }
        });

    }
    public void loadCouponData(String divpage_id){
        HashMap<String,String> map = TopRequest.couponRequest(SharedPreferencesUtil.getAdminId(),SharedPreferencesUtil.getToken(),"app",divpage_id);
        HttpManager.sendRequest(UrlConst.TOP_COUPON, map, new HttpRequestListener() {
            @Override
            public void onRequestSuccess(String response) {
                GetCouponBean bean = new Gson().fromJson(response,GetCouponBean.class);
                fragmentView.dialogDismiss();
                fragmentView.loadCouponSuccess(bean.data);
            }
            @Override
            public void onRequestFail(String result, String code) {
            }
            @Override
            public void onCompleted() {
                fragmentView.dialogDismiss();
            }
        });
    }
}
