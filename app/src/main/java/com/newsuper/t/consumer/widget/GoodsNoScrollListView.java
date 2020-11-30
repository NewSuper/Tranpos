package com.newsuper.t.consumer.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.newsuper.t.consumer.function.top.adapter.WGoodsGroupAdapter;
import com.newsuper.t.consumer.utils.UIUtils;
import com.newsuper.t.consumer.widget.CustomNoScrollListView.CustomAdapter;

public class GoodsNoScrollListView extends LinearLayout {
    private WGoodsGroupAdapter mAdapter;
    //大图模式
    private int SHOW_STYLE_BIG = 10;
    //中图模式
    private int SHOW_STYLE_MEDIUM = 11;
    //小图模式
    private int SHOW_STYLE_SMALL = 12;
    //列表模式
    private int SHOW_STYLE_LIST = 13;
    private int showStyle;
    public GoodsNoScrollListView(Context context) {
        super(context);
        setOrientation(LinearLayout.VERTICAL);
    }
    public GoodsNoScrollListView(Context context,int showStyle) {
        super(context);
        setOrientation(LinearLayout.VERTICAL);
        this.showStyle = showStyle;
    }
    public GoodsNoScrollListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOrientation(LinearLayout.VERTICAL);
    }

    public GoodsNoScrollListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOrientation(LinearLayout.VERTICAL);
    }
    public void setAdapter(WGoodsGroupAdapter adapter) {
        if (adapter == null) {
            throw new NullPointerException("CustomAdapter is null, please check setAdapter(CustomAdapter adapter)...");
        }
        mAdapter = adapter;
        adapter.setOnNotifyDataSetChangedListener(new CustomAdapter.OnNotifyDataSetChangedListener() {
            @Override
            public void OnNotifyDataSetChanged() {
                notifyDataSetChanged();
            }
        });
        adapter.notifyDataSetChanged();
    }

    public WGoodsGroupAdapter getAdapter() {
        return mAdapter;
    }


    private void notifyDataSetChanged() {
        removeAllViews();
        if (mAdapter == null || mAdapter.getCount() == 0) {
            return;
        }
        int w = UIUtils.getWindowWidth() - UIUtils.dip2px(10);
        LayoutParams params = new LayoutParams(w,ViewGroup.LayoutParams.WRAP_CONTENT);
        for (int i = 0; i < mAdapter.getCount(); i++) {
            final int index = i;
            View view = mAdapter.getView(index);
            view.setLayoutParams(params);
            if (view == null) {
                throw new NullPointerException("item layout is null, please check getView()...");
            }
            addView(view);
        }

    }

}

