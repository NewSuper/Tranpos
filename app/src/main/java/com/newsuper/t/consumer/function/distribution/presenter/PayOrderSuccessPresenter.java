package com.newsuper.t.consumer.function.distribution.presenter;

import com.google.gson.Gson;
import com.newsuper.t.consumer.bean.PaotuiPayOrderInfo;
import com.newsuper.t.consumer.bean.PayOrderBean;
import com.newsuper.t.consumer.function.distribution.internal.IPayOrderSuccessView;
import com.newsuper.t.consumer.function.distribution.request.DistributionRequest;
import com.newsuper.t.consumer.manager.HttpManager;
import com.newsuper.t.consumer.manager.listener.HttpRequestListener;
import com.newsuper.t.consumer.utils.Const;
import com.newsuper.t.consumer.utils.SharedPreferencesUtil;
import com.newsuper.t.consumer.utils.UrlConst;

import java.util.Map;

public class PayOrderSuccessPresenter {
    private IPayOrderSuccessView successView;
    public PayOrderSuccessPresenter(IPayOrderSuccessView successView){
        this.successView = successView;
    }
    public void getOrderInfo(String order_id){
        Map<String,String> map = DistributionRequest.payOrderInfoRequest(SharedPreferencesUtil.getToken(), Const.ADMIN_ID,order_id);
        HttpManager.sendRequest(UrlConst.PAO_ORDER_DETAIL, map, new HttpRequestListener() {
            @Override
            public void onRequestSuccess(String response) {
                PaotuiPayOrderInfo bean = new Gson().fromJson(response,PaotuiPayOrderInfo.class);
                successView.getPayOrderInfo(bean);
                successView.dialogDismiss();

            }

            @Override
            public void onRequestFail(String result, String code) {
                successView.dialogDismiss();
                successView.getFaile();
                successView.showToast(result);
            }

            @Override
            public void onCompleted() {
                successView.dialogDismiss();
            }
        });
    }
}
