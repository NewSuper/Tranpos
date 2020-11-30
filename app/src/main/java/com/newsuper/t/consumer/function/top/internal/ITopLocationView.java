package com.newsuper.t.consumer.function.top.internal;

import com.xunjoy.lewaimai.consumer.bean.AddressBean;

/**
 * Created by Administrator on 2017/7/19 0019.
 */

public interface ITopLocationView extends IBaseView {
    void showAddress(AddressBean bean);
    void loadFail();
}
