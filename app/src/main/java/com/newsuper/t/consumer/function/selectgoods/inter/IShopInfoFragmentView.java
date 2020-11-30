package com.newsuper.t.consumer.function.selectgoods.inter;

import com.xunjoy.lewaimai.consumer.bean.CommentBean;
import com.xunjoy.lewaimai.consumer.bean.GoodsSearchBean;
import com.xunjoy.lewaimai.consumer.bean.ShopInfoBean;
import com.xunjoy.lewaimai.consumer.function.top.internal.IBaseView;

/**
 * Created by Administrator on 2017/5/3 0003.
 */

public interface IShopInfoFragmentView extends IBaseView {

    void showDataToVIew(ShopInfoBean bean);
    void showSearchDataToView(GoodsSearchBean bean);
    void searchDataError();
}
