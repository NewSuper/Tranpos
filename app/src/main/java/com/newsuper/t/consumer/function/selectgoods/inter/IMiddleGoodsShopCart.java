package com.newsuper.t.consumer.function.selectgoods.inter;

import android.view.View;

import com.newsuper.t.consumer.bean.GoodsListBean;


public interface IMiddleGoodsShopCart {
    void add(View view, int postiion, GoodsListBean.GoodsInfo goods);
    void remove(int position, GoodsListBean.GoodsInfo goods);
    void showNatureDialog(GoodsListBean.GoodsInfo goods, int position, int typePos);
    void showTipDialog();
    void showVipDialog();
}
