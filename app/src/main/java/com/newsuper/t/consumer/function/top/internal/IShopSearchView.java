package com.newsuper.t.consumer.function.top.internal;


import com.newsuper.t.consumer.bean.ShopSearchBean;

public interface IShopSearchView extends IBaseView {

    void showDataToVIew(ShopSearchBean bean);
    void showDataToVIewMore(ShopSearchBean bean);
    void showFilterData(ShopSearchBean bean);
}
