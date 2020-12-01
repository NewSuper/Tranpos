package com.newsuper.t.consumer.function.vip.presenter;

import com.google.gson.Gson;
import com.newsuper.t.consumer.bean.VipChargeBean;
import com.newsuper.t.consumer.function.vip.inter.IVipChargeView;
import com.newsuper.t.consumer.function.vip.request.VipChargeRequest;
import com.newsuper.t.consumer.manager.HttpManager;
import com.newsuper.t.consumer.manager.listener.HttpRequestListener;
import com.newsuper.t.consumer.utils.UrlConst;
import java.util.HashMap;


public class VipChargePresenter {
    private IVipChargeView vipChargeView;
    public VipChargePresenter(IVipChargeView vipChargeView){
        this.vipChargeView = vipChargeView;
    }
    public void vipCharge(String token, String admin_id, String pay_money,String online_pay_type,String grade_id){
        HashMap<String,String> map = VipChargeRequest.vipChargeRequest(token,admin_id,pay_money,online_pay_type,"",grade_id);
        HttpManager.sendRequest(UrlConst.VIP_CHARGE, map, new HttpRequestListener() {
            @Override
            public void onRequestSuccess(String response) {
                vipChargeView.dialogDismiss();
                VipChargeBean bean = new Gson().fromJson(response,VipChargeBean.class);
                vipChargeView.chargeVip(bean);


            }

            @Override
            public void onRequestFail(String result, String code) {
                vipChargeView.dialogDismiss();
                vipChargeView.showToast(result);
                vipChargeView.loadFail();
            }

            @Override
            public void onCompleted() {
                vipChargeView.dialogDismiss();
            }
        });
    }
}
