package com.newsuper.t.consumer.function.vip.presenter;

import com.google.gson.Gson;
import com.newsuper.t.consumer.bean.VipPayStatusBean;
import com.newsuper.t.consumer.function.vip.inter.IVipPayStatusView;
import com.newsuper.t.consumer.function.vip.request.ChargeStatusRequest;
import com.newsuper.t.consumer.manager.HttpManager;
import com.newsuper.t.consumer.manager.listener.HttpRequestListener;
import com.newsuper.t.consumer.utils.UrlConst;

import java.util.HashMap;


public class ChargeStatusPresenter {
    private IVipPayStatusView statusView;
    public ChargeStatusPresenter(IVipPayStatusView statusView){
        this.statusView = statusView;
    }
    public void checkChargeStatus(String token, String admin_id, String member_id, String weixin_password){
        HashMap<String,String> map = ChargeStatusRequest.chargeStatusRequest(token,admin_id,member_id,weixin_password);
        HttpManager.sendRequest(UrlConst.VIP_CHECK_CHARG, map, new HttpRequestListener() {
            @Override
            public void onRequestSuccess(String response) {
                statusView.dialogDismiss();
                VipPayStatusBean bean = new Gson().fromJson(response,VipPayStatusBean.class);
                statusView.checkStatus(bean);

            }

            @Override
            public void onRequestFail(String result, String code) {
                statusView.dialogDismiss();
                statusView.showToast(result);
                statusView.loadFail();
            }

            @Override
            public void onCompleted() {
                statusView.dialogDismiss();
            }
        });
    }
}
