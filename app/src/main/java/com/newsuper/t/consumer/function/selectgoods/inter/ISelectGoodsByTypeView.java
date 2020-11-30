package com.newsuper.t.consumer.function.selectgoods.inter;

import com.xunjoy.lewaimai.consumer.bean.GoodsByType;
import com.xunjoy.lewaimai.consumer.function.top.internal.IBaseView;


public interface ISelectGoodsByTypeView extends IBaseView {

    void showDataToVIew(GoodsByType bean);
    void loadGoodsFail();

}
