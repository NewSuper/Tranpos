package com.newsuper.t.consumer.function.inter;

import com.newsuper.t.consumer.bean.OrderBean;
import com.newsuper.t.consumer.bean.OrderInfoBean;
import com.newsuper.t.consumer.function.top.internal.IBaseView;


public interface IOrderInfoActivityView extends IBaseView {

    void setActivityData(OrderInfoBean bean);
}
