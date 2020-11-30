package com.newsuper.t.consumer.function.vip.presenter;

import com.google.gson.Gson;
import com.xunjoy.lewaimai.consumer.bean.VipInfoBean;
import com.xunjoy.lewaimai.consumer.function.vip.inter.IVipInfoView;
import com.xunjoy.lewaimai.consumer.function.vip.request.VipCardRequest;
import com.xunjoy.lewaimai.consumer.manager.HttpManager;
import com.xunjoy.lewaimai.consumer.manager.listener.HttpRequestListener;
import com.xunjoy.lewaimai.consumer.utils.UrlConst;

import java.util.HashMap;


public class VipInfoPresenter {
    private IVipInfoView vipInfoView;
    public VipInfoPresenter(IVipInfoView vipInfoView){
        this.vipInfoView = vipInfoView;
    }
    public void showVipInfo(String token, String admin_id){
        HashMap<String,String> map = VipCardRequest.cardInfoRequest(token,admin_id);
        HttpManager.sendRequest(UrlConst.VIP_INFO, map, new HttpRequestListener() {
            @Override
            public void onRequestSuccess(String response) {
                vipInfoView.dialogDismiss();
                VipInfoBean bean = new Gson().fromJson(response,VipInfoBean.class);
                vipInfoView.loadVipInfo(bean);


            }

            @Override
            public void onRequestFail(String result, String code) {
                vipInfoView.dialogDismiss();
                vipInfoView.showToast(result);
                vipInfoView.loadFail();
            }

            @Override
            public void onCompleted() {
                vipInfoView.dialogDismiss();
            }
        });
    }
}
