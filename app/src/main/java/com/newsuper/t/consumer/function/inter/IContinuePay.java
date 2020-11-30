package com.newsuper.t.consumer.function.inter;

import com.newsuper.t.consumer.bean.ContinuePayResultBean;
import com.newsuper.t.consumer.bean.ContinuePayTypeBean;
import com.newsuper.t.consumer.bean.OrderInfoBean;
import com.newsuper.t.consumer.function.top.internal.IBaseView;


public interface IContinuePay extends IBaseView {

    void getPayParams(ContinuePayResultBean bean);
    void getPayType(ContinuePayTypeBean bean);
}
