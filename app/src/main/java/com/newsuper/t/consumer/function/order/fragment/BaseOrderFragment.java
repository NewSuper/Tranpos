package com.newsuper.t.consumer.function.order.fragment;

import android.support.v4.app.Fragment;

import com.xunjoy.lewaimai.consumer.bean.OrderInfoBean;
import com.xunjoy.lewaimai.consumer.function.selectgoods.inter.INotifyOrderInfo;

/**
 * Created by Administrator on 2017/7/20 0020.
 */

public class BaseOrderFragment extends Fragment implements INotifyOrderInfo{
    @Override
    public void notifyOrderInfo(OrderInfoBean bean) {

    }
}
