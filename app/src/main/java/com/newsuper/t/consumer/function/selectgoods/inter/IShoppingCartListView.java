package com.newsuper.t.consumer.function.selectgoods.inter;

import com.xunjoy.lewaimai.consumer.bean.ShopCartListBean;
import com.xunjoy.lewaimai.consumer.function.top.internal.IBaseView;

/**
 * Created by Administrator on 2017/6/26 0026.
 */

public interface IShoppingCartListView extends IBaseView {
    void loadData(ShopCartListBean bean);
    void loadFail();
}
