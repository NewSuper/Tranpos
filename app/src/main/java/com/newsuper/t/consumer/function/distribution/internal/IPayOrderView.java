package com.newsuper.t.consumer.function.distribution.internal;

import com.newsuper.t.consumer.bean.PayOrderBean;
import com.newsuper.t.consumer.function.top.internal.IBaseView;

/**
 * Created by Administrator on 2018/4/3 0003.
 */

public interface IPayOrderView extends IBaseView{
    void paySuccess(PayOrderBean bean);
    void payFail();
}
