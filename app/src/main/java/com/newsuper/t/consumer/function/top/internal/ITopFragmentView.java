package com.newsuper.t.consumer.function.top.internal;


import com.newsuper.t.consumer.bean.AddressBean;
import com.newsuper.t.consumer.bean.TopBean;

import java.util.Map;


public interface ITopFragmentView extends IBaseView {

    void showDataToView(TopBean bean);
    void showAddress(AddressBean bean);
    void loadFail(String errorCode);
    void loadMoreShop(TopBean bean);


}
