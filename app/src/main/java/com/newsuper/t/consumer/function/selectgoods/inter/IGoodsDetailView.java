package com.newsuper.t.consumer.function.selectgoods.inter;

import com.xunjoy.lewaimai.consumer.bean.GoodsDetailBean;
import com.xunjoy.lewaimai.consumer.bean.PackageDetailBean;
import com.xunjoy.lewaimai.consumer.function.top.internal.IBaseView;

/**
 * Created by Administrator on 2017/7/1 0001.
 */

public interface IGoodsDetailView extends IBaseView {
    void loadDetail(GoodsDetailBean detail);
    void loadDetail(PackageDetailBean detail);
    void loadFail();

}
