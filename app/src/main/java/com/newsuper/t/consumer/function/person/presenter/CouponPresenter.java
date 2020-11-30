package com.newsuper.t.consumer.function.person.presenter;

import com.google.gson.Gson;
import com.xunjoy.lewaimai.consumer.bean.CouponBean;
import com.xunjoy.lewaimai.consumer.bean.PaotuiCouponBean;
import com.xunjoy.lewaimai.consumer.function.distribution.request.DistributionRequest;
import com.xunjoy.lewaimai.consumer.function.person.internal.ICouponView;
import com.xunjoy.lewaimai.consumer.function.person.request.CouponRequest;
import com.xunjoy.lewaimai.consumer.manager.HttpManager;
import com.xunjoy.lewaimai.consumer.manager.listener.HttpRequestListener;
import com.xunjoy.lewaimai.consumer.utils.Const;
import com.xunjoy.lewaimai.consumer.utils.SharedPreferencesUtil;
import com.xunjoy.lewaimai.consumer.utils.UrlConst;

import java.util.HashMap;
import java.util.Map;

/**
 * 获取优惠券列表的业务
 */

public class CouponPresenter {
    private ICouponView mCouponView;

    public CouponPresenter(ICouponView mCouponView) {
        this.mCouponView = mCouponView;
    }

    public void loadData(String url, Map<String, String> map) {
        HttpManager.sendRequest(url, map, new HttpRequestListener() {
            @Override
            public void onRequestSuccess(String response) {
                mCouponView.dialogDismiss();
                CouponBean bean = new Gson().fromJson(response.toString(), CouponBean.class);
                mCouponView.showDataToVIew(bean);
            }

            @Override
            public void onRequestFail(String result, String code) {
                mCouponView.dialogDismiss();
                mCouponView.showToast(result);
                mCouponView.loadFail();
            }

            @Override
            public void onCompleted() {
                mCouponView.dialogDismiss();
            }
        });

    }

    public void loadData(String url,String token,String admin_id , String status) {
        HashMap<String, String> map = CouponRequest.couponRequest(token,admin_id,status);
        HttpManager.sendRequest(url, map, new HttpRequestListener() {
            @Override
            public void onRequestSuccess(String response) {
                mCouponView.dialogDismiss();
                CouponBean bean = new Gson().fromJson(response.toString(), CouponBean.class);
                mCouponView.showDataToVIew(bean);
            }

            @Override
            public void onRequestFail(String result, String code) {
                mCouponView.dialogDismiss();
                mCouponView.showToast(result);
                mCouponView.loadFail();
            }

            @Override
            public void onCompleted() {
                mCouponView.dialogDismiss();
            }
        });
    }
    /**
     * 获取跑腿优惠券
     */
    public void loadCounpon(String status){
        Map<String,String> map = DistributionRequest.couponRequest(SharedPreferencesUtil.getToken(), Const.ADMIN_ID,status);
        HttpManager.sendRequest(UrlConst.PAO_COUPON, map, new HttpRequestListener() {
            @Override
            public void onRequestSuccess(String response) {
                PaotuiCouponBean bean = new Gson().fromJson(response,PaotuiCouponBean.class);
                mCouponView.loadCoupon(bean);
                mCouponView.dialogDismiss();
            }

            @Override
            public void onRequestFail(String result, String code) {
                mCouponView.dialogDismiss();
                mCouponView.loadCouponFail();
                mCouponView.showToast(result);
            }

            @Override
            public void onCompleted() {
                mCouponView.dialogDismiss();
            }
        });
    }
}

