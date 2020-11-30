package com.newsuper.t.consumer.function.selectgoods.presenter;

import com.google.gson.Gson;
import com.xunjoy.lewaimai.consumer.bean.CollectBean;
import com.xunjoy.lewaimai.consumer.function.selectgoods.inter.IGetCouponResponse;
import com.xunjoy.lewaimai.consumer.function.selectgoods.inter.ISelectGoodsActivityView;
import com.xunjoy.lewaimai.consumer.manager.HttpManager;
import com.xunjoy.lewaimai.consumer.manager.listener.HttpRequestListener;
import com.xunjoy.lewaimai.consumer.utils.UIUtils;

import java.util.Map;


public class GetCouponPresenter {

    private IGetCouponResponse iGetCouponResponse;

    public GetCouponPresenter(IGetCouponResponse iGetCouponResponse){
        this.iGetCouponResponse = iGetCouponResponse;
    }

    public void loadData(String url,final Map<String,String> request){
        HttpManager.sendRequest(url, request, new HttpRequestListener() {
            @Override
            public void onRequestSuccess(String response) {
                iGetCouponResponse.dialogDismiss();
                iGetCouponResponse.getCouponSuccess();
            }

            @Override
            public void onRequestFail(String result, String code) {
                iGetCouponResponse.dialogDismiss();
                if ("-2".equals(code)){
//                    iGetCouponResponse.showToast("优惠券已领取完");
                    iGetCouponResponse.getFail();
                }
                iGetCouponResponse.showToast(result);

            }

            @Override
            public void onCompleted() {
                iGetCouponResponse.dialogDismiss();
            }
        });

    }

}