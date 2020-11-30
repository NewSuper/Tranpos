package com.newsuper.t.consumer.function.selectgoods.inter;

import com.xunjoy.lewaimai.consumer.bean.GoodsSearchBean;
import com.xunjoy.lewaimai.consumer.function.top.internal.IBaseView;

/**
 * Created by Administrator on 2017/7/4 0004.
 */

public interface IGoodsSearchView extends IBaseView {
    void loadData(GoodsSearchBean bean);
    void loadFail();
}
