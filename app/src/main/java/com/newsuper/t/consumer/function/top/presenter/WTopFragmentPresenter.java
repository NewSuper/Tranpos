package com.newsuper.t.consumer.function.top.presenter;

import com.google.gson.Gson;
import com.newsuper.t.consumer.bean.GetCouponBean;
import com.newsuper.t.consumer.bean.TopBean;
import com.newsuper.t.consumer.bean.WTopBean;
import com.newsuper.t.consumer.function.top.internal.IWTopFragmentView;
import com.newsuper.t.consumer.function.top.request.TopRequest;
import com.newsuper.t.consumer.manager.HttpManager;
import com.newsuper.t.consumer.manager.listener.HttpRequestListener;
import com.newsuper.t.consumer.utils.RetrofitUtil;
import com.newsuper.t.consumer.utils.SharedPreferencesUtil;
import com.newsuper.t.consumer.utils.StringUtils;
import com.newsuper.t.consumer.utils.UrlConst;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/7/24 0024.
 */

public class WTopFragmentPresenter  {
    private IWTopFragmentView fragmentView;
    public WTopFragmentPresenter(IWTopFragmentView fragmentView){
        this.fragmentView = fragmentView;
    }


    /**
     *
     *
     * @param admin_id
     * @param id 微页面id
     * @param user_lat 用户纬度
     * @param user_lnt 用户经度
     * ，根据不同平台可选 h5/app/wxapp
     */
    public void loadData(String admin_id,String id,String user_lat,String user_lnt){
        id = StringUtils.isEmpty(id) ? "" : id;
        HashMap<String,String> map = TopRequest.weiRequest(admin_id,id,user_lat,user_lnt,"app","1", RetrofitUtil.ADMIN_APP_ID);
        HttpManager.sendRequest(UrlConst.TOP_WEI, map, new HttpRequestListener() {
            @Override
            public void onRequestSuccess(String response) {
                WTopBean bean = new Gson().fromJson(response,WTopBean.class);
                fragmentView.dialogDismiss();
                fragmentView.loadDataToView(bean);
            }
            @Override
            public void onRequestFail(String result, String code) {
                if(result.equals("no_area")){
                    fragmentView.showResponeDialog();
                }else{
                    fragmentView.dialogDismiss();
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
