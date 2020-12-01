package com.newsuper.t.consumer.function.selectgoods.inter;

import com.newsuper.t.consumer.bean.CommentBean;
import com.newsuper.t.consumer.bean.GoodsSearchBean;
import com.newsuper.t.consumer.bean.ShopInfoBean;
import com.newsuper.t.consumer.function.top.internal.IBaseView;

/**
 * Created by Administrator on 2017/5/3 0003.
 */

public interface IShopInfoFragmentView extends IBaseView {

    void showDataToVIew(ShopInfoBean bean);
    void showSearchDataToView(GoodsSearchBean bean);
    void searchDataError();
}
