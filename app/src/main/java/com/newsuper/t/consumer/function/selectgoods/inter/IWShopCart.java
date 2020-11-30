package com.newsuper.t.consumer.function.selectgoods.inter;

import android.view.View;

import com.newsuper.t.consumer.bean.GoodsListBean;
import com.newsuper.t.consumer.function.top.adapter.WGoodsGroupScrollAdapter;


//微页面购物车数据
public interface IWShopCart {
    void add(GoodsListBean.GoodsInfo goods);
    void remove(GoodsListBean.GoodsInfo goods);
    void showNatureDialog(GoodsListBean.GoodsInfo goods, int position, String flag);
    void showTipInfo(String content);
    void notifyItemChanged(WGoodsGroupScrollAdapter scrollAdapter);
    void onLogin();
    void onGoodsDetailPage(int position, GoodsListBean.GoodsInfo goods, WGoodsGroupScrollAdapter scrollAdapter);
}
