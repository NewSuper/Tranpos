package com.newsuper.t.consumer.function.distribution.internal;

import com.newsuper.t.consumer.bean.HelpBean;
import com.newsuper.t.consumer.bean.PaotuiCouponBean;
import com.newsuper.t.consumer.bean.PaotuiOrderBean;
import com.newsuper.t.consumer.function.top.internal.IBaseView;

/**
 * Created by Administrator on 2018/3/23 0023.
 */

public interface IHelpView extends IBaseView {
    void loadData(HelpBean bean);
    void loadFail();
    void commitOrderSuccess(PaotuiOrderBean bean);
    void commitOrderFail();
    void loadCoupon(PaotuiCouponBean bean);
    void loadCouponFail();
}
