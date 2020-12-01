package com.newsuper.t.consumer.function.top.internal;


import com.newsuper.t.consumer.bean.TopTabBean;

/**
 * Created by Administrator on 2018/5/10 0010.
 */

public interface ITopView extends IBaseView {
    void loadTabData(TopTabBean bean);
    void loadFail();
}
