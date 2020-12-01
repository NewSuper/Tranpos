package com.newsuper.t.consumer.function.distribution.presenter;

import com.google.gson.Gson;
import com.newsuper.t.consumer.bean.HelpBean;
import com.newsuper.t.consumer.bean.PaotuiCouponBean;
import com.newsuper.t.consumer.bean.PaotuiOrderBean;
import com.newsuper.t.consumer.function.distribution.internal.IHelpView;
import com.newsuper.t.consumer.function.distribution.request.DistributionRequest;
import com.newsuper.t.consumer.manager.HttpManager;
import com.newsuper.t.consumer.manager.listener.HttpRequestListener;
import com.newsuper.t.consumer.utils.Const;
import com.newsuper.t.consumer.utils.SharedPreferencesUtil;
import com.newsuper.t.consumer.utils.UrlConst;

import java.util.Map;

/**
 * Created by Administrator on 2018/3/23 0023.
 */

public class HelpPresenter {
    private IHelpView helpView;
    public HelpPresenter(){
    }
    public HelpPresenter(IHelpView helpView){
        this.helpView = helpView;
    }

    /**
     * 获取数据
     */
    public void loadData(String type_id){
        Map<String,String> map = DistributionRequest.categoryRequest(SharedPreferencesUtil.getToken(), Const.ADMIN_ID,SharedPreferencesUtil.getLoginPhone(),type_id);
        HttpManager.sendRequest(UrlConst.PAO_CATEGORY, map, new HttpRequestListener() {
            @Override
            public void onRequestSuccess(String response) {
                HelpBean bean = new Gson().fromJson(response,HelpBean.class);
                helpView.loadData(bean);
                helpView.dialogDismiss();
            }

            @Override
            public void onRequestFail(String result, String code) {
                helpView.dialogDismiss();
                helpView.loadFail();
                helpView.showToast(result);
            }

            @Override
            public void onCompleted() {
                helpView.dialogDismiss();
            }
        });
    }
    /**
     * 获取数据
     */
    public void loadCustomData(String type_id,String lat,String lng){
        Map<String,String> map = DistributionRequest.categoryCustomRequest(SharedPreferencesUtil.getToken(), Const.ADMIN_ID,SharedPreferencesUtil.getLoginPhone(),type_id,lat,lng);
        HttpManager.sendRequest(UrlConst.PAO_CATEGORY, map, new HttpRequestListener() {
            @Override
            public void onRequestSuccess(String response) {
                HelpBean bean = new Gson().fromJson(response,HelpBean.class);
                helpView.loadData(bean);
                helpView.dialogDismiss();
            }

            @Override
            public void onRequestFail(String result, String code) {
                helpView.dialogDismiss();
                helpView.loadFail();
                helpView.showToast(result);
            }

            @Override
            public void onCompleted() {
                helpView.dialogDismiss();
            }
        });
    }
    /**
     * 获取跑腿优惠券
     */
    public void loadCounpon(String errand_category_id){
        Map<String,String> map = DistributionRequest.couponRequest(SharedPreferencesUtil.getToken(), Const.ADMIN_ID,"1",errand_category_id);
        HttpManager.sendRequest(UrlConst.PAO_COUPON, map, new HttpRequestListener() {
            @Override
            public void onRequestSuccess(String response) {
                PaotuiCouponBean bean = new Gson().fromJson(response,PaotuiCouponBean.class);
                helpView.loadCoupon(bean);
                helpView.dialogDismiss();
            }

            @Override
            public void onRequestFail(String result, String code) {
                helpView.dialogDismiss();
                helpView.loadCouponFail();
                helpView.showToast(result);
            }

            @Override
            public void onCompleted() {
                helpView.dialogDismiss();
            }
        });
    }

    /**
     * 跑腿下单
     * @param map
     */
    public void commitOrder( Map<String,String> map){
        if (map == null){
            return;
        }
        HttpManager.sendRequest(UrlConst.PAO_ORDER, map, new HttpRequestListener() {
            @Override
            public void onRequestSuccess(String response) {
                PaotuiOrderBean bean = new Gson().fromJson(response,PaotuiOrderBean.class);
                helpView.commitOrderSuccess(bean);
                helpView.dialogDismiss();
            }

            @Override
            public void onRequestFail(String result, String code) {
                helpView.dialogDismiss();
                helpView.commitOrderFail();
                helpView.showToast(result);
            }

            @Override
            public void onCompleted() {
                helpView.dialogDismiss();
            }
        });
    }

}
