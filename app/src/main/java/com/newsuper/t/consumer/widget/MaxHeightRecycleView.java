package com.newsuper.t.consumer.widget;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.widget.ScrollView;

import com.newsuper.t.consumer.utils.UIUtils;


public class MaxHeightRecycleView extends RecyclerView {
    private Context mContext;
    private int height;

    public MaxHeightRecycleView(Context context) {
        super(context);
        init(context);
    }

    public MaxHeightRecycleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);

    }

    public MaxHeightRecycleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mContext = context;
    }

    public void setMaxHeight(int height){
        this.height=height;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        try {
            //此处是关键，设置控件高度（在此替换成自己需要的高度）
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(UIUtils.dip2px(height), MeasureSpec.AT_MOST);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //重新计算控件高、宽
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
