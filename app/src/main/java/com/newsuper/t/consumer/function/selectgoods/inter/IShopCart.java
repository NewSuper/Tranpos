package com.newsuper.t.consumer.function.selectgoods.inter;

import android.view.View;
import com.xunjoy.lewaimai.consumer.bean.GoodsListBean;


public interface IShopCart {
    void add(View view, int postiion, GoodsListBean.GoodsInfo goods);
    void remove(int position, GoodsListBean.GoodsInfo goods);
    void showNatureDialog(GoodsListBean.GoodsInfo goods, int position);
    void showTipDialog();
    void showVipDialog();
}
