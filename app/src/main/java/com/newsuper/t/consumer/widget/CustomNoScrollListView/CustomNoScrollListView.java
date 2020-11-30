package com.newsuper.t.consumer.widget.CustomNoScrollListView;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.newsuper.t.consumer.utils.UIUtils;


/**
 * Created by Administrator on 2017/11/13 0013.
 */

public class CustomNoScrollListView extends LinearLayout {

    private CustomAdapter mAdapter;


    public CustomNoScrollListView(Context context) {
        super(context);
        setOrientation(LinearLayout.VERTICAL);
    }

    public CustomNoScrollListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOrientation(LinearLayout.VERTICAL);
    }

    public CustomNoScrollListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOrientation(LinearLayout.VERTICAL);
    }


    public void setAdapter(CustomAdapter adapter) {
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

    public CustomAdapter getAdapter() {
        return mAdapter;
    }


    private void notifyDataSetChanged() {

        removeAllViews();
        if (mAdapter == null || mAdapter.getCount() == 0) {
            return;
        }
        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        for (int i = 0; i < mAdapter.getCount(); i++) {
            final int index = i;
            View view = mAdapter.getView(index);
            if (view == null) {
                throw new NullPointerException("item layout is null, please check getView()...");
            }
            layoutParams.setMargins(0,0,0, UIUtils.dip2px(9));
            addView(view, index, layoutParams);
        }

    }

}

