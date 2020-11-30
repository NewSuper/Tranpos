package com.newsuper.t.consumer.function.vip.presenter;

import com.google.gson.Gson;
import com.xunjoy.lewaimai.consumer.bean.BalanceDetailBean;
import com.xunjoy.lewaimai.consumer.function.vip.inter.IBalanceDetail;
import com.xunjoy.lewaimai.consumer.function.vip.request.VipCardRequest;
import com.xunjoy.lewaimai.consumer.manager.HttpManager;
import com.xunjoy.lewaimai.consumer.manager.listener.HttpRequestListener;
import com.xunjoy.lewaimai.consumer.utils.UrlConst;

import java.util.HashMap;

/**
 * Created by Administrator on 2017/12/5 0005.
 */

public class BalanceDetailPresenter {
    IBalanceDetail iBalanceDetail;
    public BalanceDetailPresenter(IBalanceDetail iBalanceDetail){
        this.iBalanceDetail = iBalanceDetail;
    }
    public void getData(String token,String admin_id,String page){
        HashMap<String,String> map = VipCardRequest.balanceDetailRequest(token, admin_id,page);
        HttpManager.sendRequest(UrlConst.VIP_BALANCE_DETAI, map, new HttpRequestListener() {
            @Override

            public void onRequestSuccess(String response) {
                iBalanceDetail.dialogDismiss();
                BalanceDetailBean balanceDetailBean = new Gson().fromJson(response,BalanceDetailBean.class);
                iBalanceDetail.loadData(balanceDetailBean);

            }

            @Override
            public void onRequestFail(String result, String code) {
                iBalanceDetail.dialogDismiss();
                iBalanceDetail.showToast(result);
                iBalanceDetail.loadFail();
            }

            @Override
            public void onCompleted() {
                iBalanceDetail.dialogDismiss();
            }
        });
    }
    public void getDataMore(String token,String admin_id,int page){
        int page1 = page + 1;
        HashMap<String,String> map = VipCardRequest.balanceDetailRequest(token, admin_id,page1+"");
        HttpManager.sendRequest(UrlConst.VIP_BALANCE_DETAI, map, new HttpRequestListener() {
            @Override

            public void onRequestSuccess(String response) {
                iBalanceDetail.dialogDismiss();
                BalanceDetailBean balanceDetailBean = new Gson().fromJson(response,BalanceDetailBean.class);
                iBalanceDetail.loadMoreData(balanceDetailBean);

            }

            @Override
            public void onRequestFail(String result, String code) {
                iBalanceDetail.dialogDismiss();
                iBalanceDetail.showToast(result);
                iBalanceDetail.loadMoreFail();
            }

            @Override
            public void onCompleted() {
                iBalanceDetail.dialogDismiss();
            }
        });
    }
}
