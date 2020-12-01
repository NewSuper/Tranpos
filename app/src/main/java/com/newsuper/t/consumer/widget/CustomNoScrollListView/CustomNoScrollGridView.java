package com.newsuper.t.consumer.widget.CustomNoScrollListView;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.newsuper.t.R;
import com.newsuper.t.consumer.utils.UIUtils;

/**
 * Created by Administrator on 2017/11/13 0013.
 */

public class CustomNoScrollGridView extends LinearLayout {

    private CustomAdapter mAdapter;
    private LinearLayout leftLinearLayout;
    private LinearLayout rightLinearLayout;
    private Context context;


    public CustomNoScrollGridView(Context context) {
        super(context);
        this.context=context;
        setOrientation(LinearLayout.VERTICAL);
        initView();
    }

    public CustomNoScrollGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context=context;
        setOrientation(LinearLayout.VERTICAL);
        initView();
    }

    public CustomNoScrollGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context=context;
        setOrientation(LinearLayout.VERTICAL);
        initView();
    }

    private void initView(){
        View view=View.inflate(context, R.layout.view_custom_gridview,null);
        addView(view,new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        leftLinearLayout=(LinearLayout)view.findViewById(R.id.leftLinearLayout);
        rightLinearLayout=(LinearLayout)view.findViewById(R.id.rightLinearLayout);

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

        leftLinearLayout.removeAllViews();
        rightLinearLayout.removeAllViews();
        if (mAdapter == null || mAdapter.getCount() == 0) {
            return;
        }
        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(0,0,0, UIUtils.dip2px(9));
        for (int i = 0; i < mAdapter.getCount(); i++) {
            View view = mAdapter.getView(i);
            if (view == null) {
                throw new NullPointerException("item layout is null, please check getView()...");
            }
            if(i%2==0){
                leftLinearLayout.addView(view, i/2, layoutParams);
            }else{
                rightLinearLayout.addView(view, (i-1)/2, layoutParams);
            }
        }

    }

}

