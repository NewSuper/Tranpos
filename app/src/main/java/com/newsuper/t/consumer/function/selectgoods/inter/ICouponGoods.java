package com.newsuper.t.consumer.function.selectgoods.inter;

import com.newsuper.t.consumer.bean.GoodsCouponBean;
import com.newsuper.t.consumer.function.top.internal.IBaseView;

/**
 * Created by Administrator on 2017/7/4 0004.
 */

public interface ICouponGoods extends IBaseView {
    void loadData(GoodsCouponBean bean);
    void loadFail();
}
