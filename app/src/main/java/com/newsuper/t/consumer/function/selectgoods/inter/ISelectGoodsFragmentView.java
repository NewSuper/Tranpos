package com.newsuper.t.consumer.function.selectgoods.inter;

import com.xunjoy.lewaimai.consumer.bean.GoodsListBean;
import com.xunjoy.lewaimai.consumer.bean.ShopInfoBean;
import com.xunjoy.lewaimai.consumer.function.top.internal.IBaseView;



public interface ISelectGoodsFragmentView extends IBaseView {

    void showDataToVIew(GoodsListBean bean);
    void loadGoodsFail();

}
