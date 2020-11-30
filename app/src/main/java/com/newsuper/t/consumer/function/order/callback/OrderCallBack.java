package com.newsuper.t.consumer.function.order.callback;

import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;

import com.xunjoy.lewaimai.consumer.bean.OrderBean;
import com.xunjoy.lewaimai.consumer.bean.TopBean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/5/8 0008.
 */

public class OrderCallBack extends DiffUtil.Callback {
    private ArrayList<OrderBean.OrderInfo> oldList, newList;

    public OrderCallBack(ArrayList<OrderBean.OrderInfo> mOldDatas, ArrayList<OrderBean.OrderInfo> mNewDatas) {
        this.oldList = mOldDatas;
        this.newList = mNewDatas;
    }

    @Override
    public int getOldListSize() {
        return oldList.size();
    }

    @Override
    public int getNewListSize() {
        return newList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return oldList.get(oldItemPosition).id.equals(newList.get(newItemPosition).id);
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        final OrderBean.OrderInfo oldItem = oldList.get(oldItemPosition);
        final OrderBean.OrderInfo newItem = newList.get(newItemPosition);

        return oldItem.shopname.equals(newItem.shopname);
    }

    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
        // Implement method if you're going to use ItemAnimator
        return super.getChangePayload(oldItemPosition, newItemPosition);
    }
}
