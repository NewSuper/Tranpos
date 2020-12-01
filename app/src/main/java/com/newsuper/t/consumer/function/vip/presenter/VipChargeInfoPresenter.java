package com.newsuper.t.consumer.function.vip.presenter;

import com.google.gson.Gson;
import com.newsuper.t.consumer.bean.VipChargeInfoBean;
import com.newsuper.t.consumer.function.vip.inter.IVipChargeOpenView;
import com.newsuper.t.consumer.function.vip.request.VipCardRequest;
import com.newsuper.t.consumer.manager.HttpManager;
import com.newsuper.t.consumer.manager.listener.HttpRequestListener;
import com.newsuper.t.consumer.utils.Const;
import com.newsuper.t.consumer.utils.UrlConst;

import java.util.HashMap;


public class VipChargeInfoPresenter {
    private IVipChargeOpenView vipChargeOpenView;
    public VipChargeInfoPresenter(IVipChargeOpenView vipChargeOpenView){
        this.vipChargeOpenView = vipChargeOpenView;
    }
    public void openCharge(String token, String admin_id){
        HashMap<String,String> map = VipCardRequest.chargeInfoRequest(token,admin_id,"app", Const.ADMIN_APP_ID);
        HttpManager.sendRequest(UrlConst.VIP_CHARGE_INFO, map, new HttpRequestListener() {
            @Override
            public void onRequestSuccess(String response) {
                vipChargeOpenView.dialogDismiss();
                VipChargeInfoBean bean = new Gson().fromJson(response,VipChargeInfoBean.class);
                vipChargeOpenView.chargeOpenInfo(bean);


            }

            @Override
            public void onRequestFail(String result, String code) {
                vipChargeOpenView.dialogDismiss();
                vipChargeOpenView.showToast(result);
                vipChargeOpenView.loadFail();
            }

            @Override
            public void onCompleted() {
                vipChargeOpenView.dialogDismiss();
            }
        });
    }
}
