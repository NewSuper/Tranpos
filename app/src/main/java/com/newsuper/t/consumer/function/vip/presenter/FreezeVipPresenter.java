package com.newsuper.t.consumer.function.vip.presenter;

import com.newsuper.t.consumer.function.vip.inter.IFreezeVipView;
import com.newsuper.t.consumer.function.vip.request.VipCardRequest;
import com.newsuper.t.consumer.manager.HttpManager;
import com.newsuper.t.consumer.manager.listener.HttpRequestListener;
import com.newsuper.t.consumer.utils.UrlConst;

import java.util.HashMap;

/**
 * Created by Administrator on 2017/12/5 0005.
 */

public class FreezeVipPresenter {
    IFreezeVipView iFreezeVipView;
    public FreezeVipPresenter(IFreezeVipView iFreezeVipView){
        this.iFreezeVipView = iFreezeVipView;
    }
    public void freezeVip(String token,String admin_id){
        HashMap<String,String> map = VipCardRequest.freezeVipRequest(token, admin_id);
        HttpManager.sendRequest(UrlConst.FREEZE_VIP, map, new HttpRequestListener() {
            @Override

            public void onRequestSuccess(String response) {
                iFreezeVipView.dialogDismiss();
                iFreezeVipView.freezeSuccess();

            }

            @Override
            public void onRequestFail(String result, String code) {
                iFreezeVipView.dialogDismiss();
                iFreezeVipView.showToast(result);
                iFreezeVipView.freezeFail();
            }

            @Override
            public void onCompleted() {
                iFreezeVipView.dialogDismiss();
            }
        });
    }
}
