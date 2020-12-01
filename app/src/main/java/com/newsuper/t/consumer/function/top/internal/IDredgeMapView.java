package com.newsuper.t.consumer.function.top.internal;


import com.newsuper.t.consumer.bean.DredgeMapBean;

/**
 * Create by Administrator on 2019/5/6 0006
 */
public interface IDredgeMapView extends IBaseView {
    void showLoadData(DredgeMapBean bean);
    void loadFail();
}
