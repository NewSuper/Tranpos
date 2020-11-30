package com.newsuper.t.consumer.function.top.callback;

import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;
import com.xunjoy.lewaimai.consumer.bean.TopBean;

import java.util.ArrayList;

public class TopDiffCallBack  extends DiffUtil.Callback {
    private ArrayList<TopBean.ShopList> oldList, newList;

    public TopDiffCallBack( ArrayList<TopBean.ShopList> mOldDatas, ArrayList<TopBean.ShopList> mNewDatas) {
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
        final TopBean.ShopList oldItem = oldList.get(oldItemPosition);
        final TopBean.ShopList newItem = newList.get(newItemPosition);

        return oldItem.shopname.equals(newItem.shopname);
    }

    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
        // Implement method if you're going to use ItemAnimator
        return super.getChangePayload(oldItemPosition, newItemPosition);
    }
}
