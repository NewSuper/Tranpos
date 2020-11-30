package com.newsuper.t.consumer.widget;

import android.content.Context;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by Administrator on 2017/5/23 0023.
 */

public class CustomerNestedScrollView extends NestedScrollView {
    String TAG = "NestedScrollView";
    public CustomerNestedScrollView(Context context) {
        super(context);
    }

    public CustomerNestedScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomerNestedScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void onStopNestedScroll(View target) {
        super.onStopNestedScroll(target);
        Log.i(TAG,"onStopNestedScroll");
    }

    @Override
    public boolean onStartNestedScroll(View child, View target, int nestedScrollAxes) {
        Log.i(TAG,"onStartNestedScroll");
        return super.onStartNestedScroll(child, target, nestedScrollAxes);

    }

    @Override
    public boolean onNestedFling(View target, float velocityX, float velocityY, boolean consumed) {
        Log.i(TAG,"onNestedFling");
        return super.onNestedFling(target, velocityX, velocityY, consumed);
    }

    @Override
    public void onNestedScroll(View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        super.onNestedScroll(target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed);
        Log.i(TAG,"onNestedScroll");
    }
}
