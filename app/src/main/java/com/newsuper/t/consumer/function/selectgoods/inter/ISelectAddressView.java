package com.newsuper.t.consumer.function.selectgoods.inter;

import com.xunjoy.lewaimai.consumer.bean.AddressBean;
import com.xunjoy.lewaimai.consumer.function.top.internal.IBaseView;

/**
 * Created by Administrator on 2017/6/14 0014.
 */

public interface ISelectAddressView extends IBaseView {
    void loadAddress(AddressBean bean);
    void loadFail();
}
