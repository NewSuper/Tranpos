package com.newsuper.t.consumer.function.selectgoods.presenter;

import com.google.gson.Gson;
import com.newsuper.t.consumer.bean.GoodsCouponBean;
import com.newsuper.t.consumer.bean.GoodsSearchBean;
import com.newsuper.t.consumer.function.selectgoods.inter.ICouponGoods;
import com.newsuper.t.consumer.function.selectgoods.request.ShopInfoRequest;
import com.newsuper.t.consumer.manager.HttpManager;
import com.newsuper.t.consumer.manager.listener.HttpRequestListener;
import com.newsuper.t.consumer.utils.UrlConst;
import java.util.HashMap;

/**
 * Created by Administrator on 2017/7/4 0004.
 */

public class CouponGoodsPresenter {
    private ICouponGoods couponGoodsView;
    public CouponGoodsPresenter(ICouponGoods couponGoodsView){
        this.couponGoodsView = couponGoodsView;
    }
    public void loadGoods(String token,String admin_id,String shop_id){
        HashMap<String,String> map = ShopInfoRequest.shopInfoRequest(token,admin_id,shop_id);
        HttpManager.sendRequest(UrlConst.GOODS_COUPON, map, new HttpRequestListener() {
            @Override
            public void onRequestSuccess(String response) {
                couponGoodsView.dialogDismiss();
                GoodsCouponBean bean = new Gson().fromJson(response,GoodsCouponBean.class);
                couponGoodsView.loadData(bean);
            }

            @Override
            public void onRequestFail(String result, String code) {
                couponGoodsView.dialogDismiss();
                couponGoodsView.showToast(result);
                couponGoodsView.loadFail();
            }

            @Override
            public void onCompleted() {
                couponGoodsView.dialogDismiss();
            }

        });
    }
}