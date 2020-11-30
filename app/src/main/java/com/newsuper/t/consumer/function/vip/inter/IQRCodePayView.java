package com.newsuper.t.consumer.function.vip.inter;

import com.xunjoy.lewaimai.consumer.bean.CheckQRCodePayBean;
import com.xunjoy.lewaimai.consumer.bean.QRCodePayBean;
import com.xunjoy.lewaimai.consumer.function.top.internal.IBaseView;

/**
 * Created by Administrator on 2017/12/5 0005.
 */

public interface IQRCodePayView extends IBaseView {
    void loadData(QRCodePayBean bean);
    void loadFail();
    void checkPayStatus(CheckQRCodePayBean payBean);
    void checkFail();
}
