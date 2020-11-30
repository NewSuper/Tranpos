package com.newsuper.t.consumer.function.person.internal;

import com.xunjoy.lewaimai.consumer.bean.MsgDetailBean;
import com.xunjoy.lewaimai.consumer.function.top.internal.IBaseView;

/**
 * Created by Administrator on 2018/7/24 0024.
 */

public interface IMessageDetailView extends IBaseView {
    void getMsgDetailSuccess(MsgDetailBean bean);
    void getMsgDetailFail();
}
