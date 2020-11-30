package com.newsuper.t.consumer.function.person.internal;

import com.newsuper.t.consumer.bean.CustomerInfoBean;
import com.newsuper.t.consumer.bean.MsgCountBean;
import com.newsuper.t.consumer.function.top.internal.IBaseView;

public interface ICustomerView extends IBaseView {
    //void loadUserCenter(CustomerInfoBean bean);

    void showUserCenter(CustomerInfoBean bean);
    void getMsgCount(MsgCountBean bean);
}
