package com.newsuper.t.consumer.function.distribution.presenter;

import com.google.gson.Gson;
import com.newsuper.t.consumer.bean.PayOrderBean;
import com.newsuper.t.consumer.function.distribution.internal.IPayOrderView;
import com.newsuper.t.consumer.function.distribution.request.DistributionRequest;
import com.newsuper.t.consumer.manager.HttpManager;
import com.newsuper.t.consumer.manager.listener.HttpRequestListener;
import com.newsuper.t.consumer.utils.Const;
import com.newsuper.t.consumer.utils.SharedPreferencesUtil;
import com.newsuper.t.consumer.utils.UrlConst;

import java.util.Map;

/**
 * Created by Administrator on 2018/4/3 0003.
 */

public class PayOrderPresenter {
    private IPayOrderView orderView;
    public PayOrderPresenter(IPayOrderView orderView){
        this.orderView = orderView;
    }
    /**
     * 支付
     */
    public void payOrder(String order_id,String pay_type){
        Map<String,String> map = DistributionRequest.payOrderRequest(SharedPreferencesUtil.getToken(), Const.ADMIN_ID,"2",pay_type,order_id);
        HttpManager.sendRequest(UrlConst.PAO_PAY, map, new HttpRequestListener() {
            @Override
            public void onRequestSuccess(String response) {
                PayOrderBean bean = new Gson().fromJson(response,PayOrderBean.class);
                orderView.paySuccess(bean);
                orderView.dialogDismiss();

            }

            @Override
            public void onRequestFail(String result, String code) {
                orderView.dialogDismiss();
                orderView.payFail();
                orderView.showToast(result);
            }

            @Override
            public void onCompleted() {
                orderView.dialogDismiss();
            }
        });
    }
    /**
     * 小费
     */
    public void paySmallFee(String order_id,String price){
        Map<String,String> map = DistributionRequest.smallFeeRequest(SharedPreferencesUtil.getToken(), Const.ADMIN_ID,"2",order_id,price);
        HttpManager.sendRequest(UrlConst.PAO_SAMLL_FEE, map, new HttpRequestListener() {
            @Override
            public void onRequestSuccess(String response) {
                PayOrderBean bean = new Gson().fromJson(response,PayOrderBean.class);
                orderView.paySuccess(bean);
                orderView.dialogDismiss();

            }

            @Override
            public void onRequestFail(String result, String code) {
                orderView.dialogDismiss();
                orderView.payFail();
                orderView.showToast(result);
            }

            @Override
            public void onCompleted() {
                orderView.dialogDismiss();
            }
        });
    }
}
