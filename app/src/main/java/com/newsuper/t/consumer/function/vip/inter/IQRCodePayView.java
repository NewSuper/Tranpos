package com.newsuper.t.consumer.function.vip.inter;

import com.newsuper.t.consumer.bean.CheckQRCodePayBean;
import com.newsuper.t.consumer.bean.QRCodePayBean;
import com.newsuper.t.consumer.function.top.internal.IBaseView;

/**
 * Created by Administrator on 2017/12/5 0005.
 */

public interface IQRCodePayView extends IBaseView {
    void loadData(QRCodePayBean bean);
    void loadFail();
    void checkPayStatus(CheckQRCodePayBean payBean);
    void checkFail();
}
