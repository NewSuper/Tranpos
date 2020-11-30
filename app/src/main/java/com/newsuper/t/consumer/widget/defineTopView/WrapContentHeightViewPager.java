package com.newsuper.t.consumer.widget.defineTopView;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by Administrator on 2017/8/9 0009.
 * 自适应高度ViewPager
 */

public class WrapContentHeightViewPager  extends ViewPager {
    private static final String TAG = "WrapContentHeightViewPager";
    /** * Constructor * * @param context the context */
    public WrapContentHeightViewPager(Context context) {
        super(context);
    }

    /**
     * * Constructor * * @param context the context * @param attrs the attribute
     * set
     */
    public WrapContentHeightViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int height = 0;
        Log.i("WTabGridView","getChildCount == "+getChildCount());
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            child.measure(widthMeasureSpec,
                    MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
            int h = child.getMeasuredHeight();
            if (h > height)
                height = h;
        }
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(height,
                MeasureSpec.EXACTLY);
        Log.i("WTabGridView","h == "+height);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
