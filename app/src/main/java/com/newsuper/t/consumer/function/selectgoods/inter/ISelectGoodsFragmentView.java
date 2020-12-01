package com.newsuper.t.consumer.function.selectgoods.inter;

import com.newsuper.t.consumer.bean.GoodsListBean;
import com.newsuper.t.consumer.bean.ShopInfoBean;
import com.newsuper.t.consumer.function.top.internal.IBaseView;



public interface ISelectGoodsFragmentView extends IBaseView {

    void showDataToVIew(GoodsListBean bean);
    void loadGoodsFail();

}
