package com.newsuper.t.consumer.function.vip.inter;

import com.newsuper.t.consumer.bean.BalanceDetailBean;
import com.newsuper.t.consumer.function.top.internal.IBaseView;

/**
 * Created by Administrator on 2017/12/5 0005.
 */

public interface IBalanceDetail extends IBaseView {
     void loadData(BalanceDetailBean bean);
     void loadFail();
     void loadMoreData(BalanceDetailBean bean);
     void loadMoreFail();
}
