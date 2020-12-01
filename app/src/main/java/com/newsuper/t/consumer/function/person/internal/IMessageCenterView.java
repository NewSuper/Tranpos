package com.newsuper.t.consumer.function.person.internal;

import com.newsuper.t.consumer.bean.MsgCenterBean;
import com.newsuper.t.consumer.function.top.internal.IBaseView;

/**
 * Created by Administrator on 2018/7/23 0023.
 */

public interface IMessageCenterView extends IBaseView {
    void getMessageDataSuccess(MsgCenterBean bean);
    void getMessageDataFail();

}
