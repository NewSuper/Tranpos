package com.newsuper.t.consumer.function.selectgoods.inter;

import com.newsuper.t.consumer.bean.GoodsByType;
import com.newsuper.t.consumer.function.top.internal.IBaseView;


public interface ISelectGoodsByTypeView extends IBaseView {

    void showDataToVIew(GoodsByType bean);
    void loadGoodsFail();

}
