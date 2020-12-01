package com.newsuper.t.consumer.function.selectgoods.inter;

import com.newsuper.t.consumer.bean.OrderPayResultBean;
import com.newsuper.t.consumer.bean.ShoppingCartBean;
import com.newsuper.t.consumer.function.top.internal.IBaseView;

/**
 * Created by Administrator on 2017/6/20 0020.
 */

public interface IShoppingCartView extends IBaseView {
    void loadShoppingCart(ShoppingCartBean bean);
    void loadFail();
    void paySuccess(OrderPayResultBean bean);
    void payFail();
    void sendCodeSuccess();
    void sendCodeFail();
    void onResultCode(String code, String msg);
}
