package com.newsuper.t.consumer.function.selectgoods.inter;

import com.xunjoy.lewaimai.consumer.bean.CollectBean;
import com.xunjoy.lewaimai.consumer.function.top.internal.IBaseView;


public interface ISelectGoodsActivityView extends IBaseView {

    void showDataToVIew(CollectBean bean, String flag);
    void showCheckOrderView(String msg);
}
