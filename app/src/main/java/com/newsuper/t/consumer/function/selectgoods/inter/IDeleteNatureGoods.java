package com.newsuper.t.consumer.function.selectgoods.inter;

import com.xunjoy.lewaimai.consumer.bean.GoodsListBean;

/**
 * Created by Administrator on 2017/7/21 0021.
 */

public interface IDeleteNatureGoods {
    void deleteNatureGoods(GoodsListBean.GoodsInfo goods, int position, int refreshPos);
    void updateTotalPrice();
}
