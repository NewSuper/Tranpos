package com.newsuper.t.consumer.function.top.internal;


import com.newsuper.t.consumer.bean.MapBean;

/**
 * Created by Administrator on 2018/5/8 0008.
 */

public interface IMapView extends IBaseView {
    void loadData(MapBean bean);
    void loadFail();
}
