package com.newsuper.t.consumer.function.top.internal;

import com.xunjoy.lewaimai.consumer.bean.TopBean;

/**
 * Created by Administrator on 2017/8/7 0007.
 */

public interface IWShopListView extends IBaseView{
    void loadFail();
    void showDataToView(TopBean bean);
    void loadShop();
    void loadMoreShop();
    void showMoreShop(TopBean bean);
    void showFilterDataToView(TopBean bean);
}
