package com.newsuper.t.consumer.function.top.internal;

import com.xunjoy.lewaimai.consumer.bean.GetCouponBean;
import com.xunjoy.lewaimai.consumer.bean.TopBean;
import com.xunjoy.lewaimai.consumer.bean.WTopBean;

/**
 * Created by Administrator on 2017/7/24 0024.
 */

public interface IWTopFragmentView extends IBaseView {
    void loadDataToView(WTopBean bean);
    void loadFail();
    void showDataToView(TopBean bean);
    void loadShop();
    void loadMoreShop();
    void showMoreShop(TopBean bean);
    void loadMoreShopFail();
    void loadShopFail();
    void showResponeDialog();
    void loadCouponSuccess(GetCouponBean.CouponInfo info);
    void showFilterDataToView(TopBean bean);

}
