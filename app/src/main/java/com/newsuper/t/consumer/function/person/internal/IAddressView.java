package com.newsuper.t.consumer.function.person.internal;

import com.newsuper.t.consumer.bean.AddressBean;
import com.newsuper.t.consumer.bean.DelAddressBean;
import com.newsuper.t.consumer.function.top.internal.IBaseView;

public interface IAddressView extends IBaseView{
    void showDataToVIew(AddressBean bean);
    void refresh(AddressBean bean);
    void loadFail();

    void showDelDataView(DelAddressBean bean);
    void refresh2(DelAddressBean bean);
}
