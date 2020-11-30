package com.newsuper.t.consumer.function.inter;

import com.newsuper.t.consumer.bean.OrderBean;
import com.newsuper.t.consumer.bean.OrderInfoBean;
import com.newsuper.t.consumer.bean.ShopInfoBean;
import com.newsuper.t.consumer.function.top.internal.IBaseView;

/**
 * Created by Administrator on 2017/5/3 0003.
 */

public interface IOrderListFragmentView extends IBaseView {

    void showDataToVIew(OrderBean bean);

    void loadFail();

}
