package com.newsuper.t.consumer.function.selectgoods.presenter;

import com.google.gson.Gson;
import com.xunjoy.lewaimai.consumer.bean.GoodsDetailBean;
import com.xunjoy.lewaimai.consumer.bean.PackageDetailBean;
import com.xunjoy.lewaimai.consumer.bean.ShoppingCartBean;
import com.xunjoy.lewaimai.consumer.function.selectgoods.inter.IGoodsDetailView;
import com.xunjoy.lewaimai.consumer.function.selectgoods.request.ShopInfoRequest;
import com.xunjoy.lewaimai.consumer.manager.HttpManager;
import com.xunjoy.lewaimai.consumer.manager.listener.HttpRequestListener;
import com.xunjoy.lewaimai.consumer.utils.UrlConst;

import java.util.HashMap;

/**
 * Created by Administrator on 2017/7/1 0001.
 */

public class GoodsDetailPresenter {
    private IGoodsDetailView detailView;
    public GoodsDetailPresenter(IGoodsDetailView detailView){
        this.detailView = detailView;
    }
    public void loadDetail(String token, String admin_id, String food_id, final String food_type){
        HashMap<String,String> map = ShopInfoRequest.goodsDetailRequest(token,admin_id,food_id,food_type);
        HttpManager.sendRequest(UrlConst.GOODS_DETAIL, map, new HttpRequestListener() {
            @Override
            public void onRequestSuccess(String response) {
                detailView.dialogDismiss();
                if ("1".equals(food_type)) {
                    GoodsDetailBean bean = new Gson().fromJson(response,GoodsDetailBean.class);
                    detailView.loadDetail(bean);
                }else if("2".equals(food_type)){
                    PackageDetailBean bean = new Gson().fromJson(response,PackageDetailBean.class);
                    detailView.loadDetail(bean);
                }

            }

            @Override
            public void onRequestFail(String result, String code) {
                detailView.dialogDismiss();
                detailView.showToast(result);
                detailView.loadFail();
            }

            @Override
            public void onCompleted() {
                detailView.dialogDismiss();
            }
        });
    }
}
