package com.newsuper.t.consumer.function.vip.presenter;

import com.google.gson.Gson;
import com.newsuper.t.consumer.bean.VipTopInfoBean;
import com.newsuper.t.consumer.function.vip.inter.IVipInfoView;
import com.newsuper.t.consumer.function.vip.request.VipCardRequest;
import com.newsuper.t.consumer.manager.HttpManager;
import com.newsuper.t.consumer.manager.listener.HttpRequestListener;
import com.newsuper.t.consumer.utils.UrlConst;

import java.util.HashMap;


public class VipTopInfoPresenter {
    private IVipInfoView vipInfoView;
    public VipTopInfoPresenter(IVipInfoView vipInfoView){
        this.vipInfoView = vipInfoView;
    }
    public void showVipInfo(String token, String admin_id){
        HashMap<String,String> map = VipCardRequest.cardInfoRequest(token,admin_id);
        HttpManager.sendRequest(UrlConst.VIP_TOP_INFO, map, new HttpRequestListener() {
            @Override
            public void onRequestSuccess(String response) {
                vipInfoView.dialogDismiss();
                VipTopInfoBean bean = new Gson().fromJson(response,VipTopInfoBean.class);
                vipInfoView.loadVipTopInfo(bean);


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
