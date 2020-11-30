package com.newsuper.t.consumer.function.person.internal;

import com.xunjoy.lewaimai.consumer.bean.AddressBean;
import com.xunjoy.lewaimai.consumer.bean.DelAddressBean;
import com.xunjoy.lewaimai.consumer.function.top.internal.IBaseView;

public interface IAddressView extends IBaseView{
    void showDataToVIew(AddressBean bean);
    void refresh(AddressBean bean);
    void loadFail();

    void showDelDataView(DelAddressBean bean);
    void refresh2(DelAddressBean bean);
}
