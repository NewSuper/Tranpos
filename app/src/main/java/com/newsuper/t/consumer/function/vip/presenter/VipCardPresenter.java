package com.newsuper.t.consumer.function.vip.presenter;

import com.google.gson.Gson;
import com.xunjoy.lewaimai.consumer.bean.VipCardInfoBean;
import com.xunjoy.lewaimai.consumer.function.vip.inter.IVipCardView;
import com.xunjoy.lewaimai.consumer.function.vip.request.VipCardRequest;
import com.xunjoy.lewaimai.consumer.manager.HttpManager;
import com.xunjoy.lewaimai.consumer.manager.listener.HttpRequestListener;
import com.xunjoy.lewaimai.consumer.utils.UrlConst;
import java.util.HashMap;


public class VipCardPresenter {
    private IVipCardView cardInfoView;
    public VipCardPresenter(IVipCardView cardInfoView){
        this.cardInfoView = cardInfoView;
    }
    public void loadDetail(String token, String admin_id){
        HashMap<String,String> map = VipCardRequest.cardInfoRequest(token, admin_id);
        HttpManager.sendRequest(UrlConst.VIP_CARD, map, new HttpRequestListener() {
            @Override
            public void onRequestSuccess(String response) {
                cardInfoView.dialogDismiss();
                VipCardInfoBean bean = new Gson().fromJson(response,VipCardInfoBean.class);
                cardInfoView.loadCardInfo(bean);
            }

            @Override
            public void onRequestFail(String result, String code) {
                cardInfoView.dialogDismiss();
                cardInfoView.showToast(result);
                cardInfoView.loadFail();
            }

            @Override
            public void onCompleted() {
                cardInfoView.dialogDismiss();
            }
        });
    }
}
