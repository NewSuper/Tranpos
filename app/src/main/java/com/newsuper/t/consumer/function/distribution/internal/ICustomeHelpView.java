package com.newsuper.t.consumer.function.distribution.internal;


import com.newsuper.t.consumer.bean.CustomAddressBean;

public interface ICustomeHelpView extends IHelpView {
    void getAddress(CustomAddressBean bean);
}
