package com.newsuper.t.consumer.widget.defineTopView;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * Created by Administrator on 2017/8/1 0001.
 */

public class WGridView extends GridView {

    public WGridView(Context context) {
        super(context);
    }

    public WGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public WGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int expandSpec = MeasureSpec.makeMeasureSpec(
                Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
